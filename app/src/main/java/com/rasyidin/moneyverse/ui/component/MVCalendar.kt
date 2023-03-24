package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.core.DayPosition.*
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.*
import com.rasyidin.moneyverse.utils.DateUtils.displayText
import com.rasyidin.moneyverse.utils.DateUtils.getCurrentTime
import com.rasyidin.moneyverse.utils.clickable
import com.rasyidin.moneyverse.utils.rememberFirstMostVisibleMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle

@Composable
fun MVCalendar(
    modifier: Modifier = Modifier,
    onSelectedDateClick: (String) -> Unit
) {
    val today = remember { LocalDate.now() }
    val currentMonth = remember(today) { today.yearMonth }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    var selection by remember { mutableStateOf<CalendarDay?>(null) }
    val daysOfWeek = remember { daysOfWeek() }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first()
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(state = state)
        LaunchedEffect(key1 = visibleMonth) {
            selection = null
        }
        CalendarTitle(
            currentMonth = visibleMonth.yearMonth,
            onPreviousClick = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            onNextClick = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            }
        )
        HorizontalCalendar(
            state = state,
            userScrollEnabled = false,
            dayContent = { day ->
                DayOfMonth(
                    day = day,
                    isSelected = selection == day,
                    isToday = day.position == MonthDate && day.date == today,
                    onClick = { selectedDay ->
                        selection = selectedDay
                        val date = selection!!.date
                        val dayOfMonth = date.dayOfMonth
                        val month = date.monthValue
                        val year = date.year
                        val times = getCurrentTime()
                        val selectedDate = "$year-$month-$dayOfMonth $times"
                        onSelectedDateClick.invoke(selectedDate)
                    }
                )
            },
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            }
        )
    }
}

@Composable
fun CalendarTitle(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clickable { onPreviousClick.invoke() }
                .size(16.dp),
            painter = painterResource(id = R.drawable.ic_backward),
            contentDescription = null
        )
        Text(
            modifier = Modifier.weight(1F),
            text = currentMonth.displayText(),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier
                .clickable { onNextClick.invoke() }
                .size(16.dp),
            painter = painterResource(id = R.drawable.ic_forward),
            contentDescription = null
        )
    }
}

@Composable
fun MonthHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1F),
                text = dayOfWeek.displayText(TextStyle.NARROW, true),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun DayOfMonth(
    modifier: Modifier = Modifier,
    day: CalendarDay,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1F)
            .padding(6.dp)
            .clip(CircleShape)
            .background(
                color = when {
                    isSelected -> ColorPurple500
                    isToday -> ColorPurple200
                    else -> Color.Transparent
                }
            )
            .clickable(
                enabled = day.position == MonthDate,
                showRipple = !isSelected,
                onClick = { onClick.invoke(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        val textColor = when (day.position) {
            MonthDate -> if (isSelected) Color.White else Color.Unspecified
            InDate, OutDate -> ColorGray300
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMVCalendar() {
    MoneyVerseTheme {
        MVCalendar(
            onSelectedDateClick = {}
        )
    }
}