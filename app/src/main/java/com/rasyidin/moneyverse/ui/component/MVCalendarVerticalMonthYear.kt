package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.ColorGray500
import com.rasyidin.moneyverse.ui.theme.ColorPurple200
import com.rasyidin.moneyverse.ui.theme.ColorPurple500
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.DateUtils.displayText
import com.rasyidin.moneyverse.utils.DateUtils.getCurrentTime
import com.rasyidin.moneyverse.utils.clickable
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.Year

@Composable
fun MVCalendarVerticalMonthYear(
    modifier: Modifier = Modifier,
    onSelectedDate: (String) -> Unit
) {
    val months = Month.values().toList()
    val currentMonth = months.indexOf(LocalDate.now().month + 1)
    val monthState = rememberLazyListState()

    val year = Year.now()
    val currentYear = year.value
    val years = IntRange(currentYear, currentYear + 100).toList()
    val yearState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    var selectedMonth by remember { mutableStateOf(currentMonth) }
    var selectedYear by remember { mutableStateOf(currentYear) }
    var selectedMonthYear by remember { mutableStateOf("$selectedYear-$selectedMonth-01 ${getCurrentTime()}") }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4F),
            horizontalArrangement = Arrangement.Center
        ) {
            LazyColumn(
                state = monthState,
                contentPadding = PaddingValues(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(months) { month ->
                    ItemMonthYear(
                        textValue = month.displayText(false),
                        selectedMonthYear = month.value,
                        isSelected = month.value == selectedMonth,
                        onSelected = { selected ->
                            selectedMonth = selected
                            selectedMonthYear = "$selectedYear-$selectedMonth-01 ${getCurrentTime()}"
                            val scrollToIndex = if (selectedMonth > 3) selectedMonth - 4 else selectedMonth - 1
                            coroutineScope.launch {
                                monthState.animateScrollToItem(scrollToIndex)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            LazyColumn(
                state = yearState,
                contentPadding = PaddingValues(8.dp),
            ) {
                items(years) { year ->
                    ItemMonthYear(
                        textValue = year.toString(),
                        selectedMonthYear = year,
                        isSelected = year == selectedYear,
                        onSelected = { selected ->
                            selectedYear = selected
                            selectedMonthYear = "$selectedYear-$selectedMonth-01 ${getCurrentTime()}"
                            val selectedIndex = years.indexOf(selectedYear)
                            val scrollToIndex = if (selectedIndex > 4) selectedIndex - 3 else selectedIndex
                            coroutineScope.launch {
                                yearState.animateScrollToItem(scrollToIndex)
                            }
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onSelectedDate.invoke(selectedMonthYear)
                },
            text = stringResource(id = R.string.pilih),
            style = MaterialTheme.typography.button,
            color = ColorPurple500
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun ItemMonthYear(
    modifier: Modifier = Modifier,
    textValue: String,
    selectedMonthYear: Int,
    isSelected: Boolean,
    onSelected: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clickable {
                onSelected.invoke(selectedMonthYear)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = if (isSelected) ColorPurple200 else Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(8.dp),
            text = textValue,
            style = if (isSelected) MaterialTheme.typography.h5 else MaterialTheme.typography.h6,
            color = if (isSelected) ColorPurple500 else ColorGray500
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMVCalendarVerticalMonthYear() {
    MoneyVerseTheme {
        MVCalendarVerticalMonthYear(onSelectedDate = {})
    }
}