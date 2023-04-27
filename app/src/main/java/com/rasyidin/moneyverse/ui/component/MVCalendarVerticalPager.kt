package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType.*
import com.rasyidin.moneyverse.ui.theme.ColorGray500
import com.rasyidin.moneyverse.ui.theme.ColorPurple200
import com.rasyidin.moneyverse.ui.theme.ColorPurple500
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.DateUtils.displayText
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MVCalendarVerticalPager(
    modifier: Modifier = Modifier,
    onSelected: (Int) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val months = Month.values()
        val currentMonth = months.indexOf(LocalDate.now().month)
        val pagerState = rememberPagerState(initialPage = currentMonth)
        val currentPage = pagerState.currentPage
        var isSelected by remember { mutableStateOf(false) }
        val pageSize = 40.dp
        val coroutineScope = rememberCoroutineScope()
        VerticalPager(
            state = pagerState,
            pageCount = months.size,
            pageSize = PageSize.Fixed(pageSize),
            contentPadding = PaddingValues(
                top = (maxHeight - pageSize) / 2,
                bottom = (maxHeight - pageSize) / 2
            ),
            modifier = modifier
                .fillMaxWidth()
                .height(pageSize * 3),
        ) { page ->
            Box(
                modifier = Modifier
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                        onSelected.invoke(page)
                    },
                contentAlignment = Alignment.Center
            ) {
                isSelected = pagerState.currentPage == page
                val opacity = when (page) {
                    currentPage -> 1f
                    currentPage - 1 -> if (currentPage > 0) 0.5f else 0f
                    currentPage + 1 -> if (currentPage < months.size - 1) 0.5f else 0f
                    else -> 0f
                }
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .alpha(opacity),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = if (isSelected) ColorPurple200 else Color.Transparent,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(8.dp),
                        text = months[page].displayText(false),
                        style = if (isSelected) MaterialTheme.typography.h5 else MaterialTheme.typography.h6,
                        color = if (isSelected) ColorPurple500 else ColorGray500
                    )
                }


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewMVCalendarMonth() {
    MoneyVerseTheme {
        MVCalendarVerticalPager(
            onSelected = {}
        )
    }
}
