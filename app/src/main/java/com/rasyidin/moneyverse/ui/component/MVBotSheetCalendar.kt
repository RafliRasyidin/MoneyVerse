package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.theme.ColorPurple500
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.DateUtils
import com.rasyidin.moneyverse.utils.clickable

@Composable
fun SheetContentCalendar(
    modifier: Modifier = Modifier,
    selectedDateValue: String = DateUtils.getCurrentDate(),
    onSelectedDateClick: (String) -> Unit,
) {
    var selectedDate by remember { mutableStateOf(selectedDateValue) }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        MVCalendar(
            onSelectedDateClick = { value ->
                selectedDate = value
            }
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onSelectedDateClick.invoke(selectedDate)
                },
            text = stringResource(id = R.string.kembali),
            style = MaterialTheme.typography.button,
            color = ColorPurple500
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMVBotSheetCalendar() {
    MoneyVerseTheme {
        SheetContentCalendar(onSelectedDateClick = {})
    }
}