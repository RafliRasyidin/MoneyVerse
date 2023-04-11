package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType
import com.rasyidin.moneyverse.domain.model.anggaran.ItemAnggaranType
import com.rasyidin.moneyverse.ui.theme.*
import com.rasyidin.moneyverse.utils.clickable

@Composable
fun SheetContentAnggaranType(
    modifier: Modifier = Modifier,
    items: List<ItemAnggaranType>,
    onCloseClick: () -> Unit,
    onItemClick: (ItemAnggaranType) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SheetHeader(title = stringResource(id = R.string.pilih_jenis_anggaran), onCloseClick = onCloseClick)
        LazyColumn {
            items(items) { item ->
                ItemAnggaranType(
                    item = item,
                    onItemClick = onItemClick
                )
                Divider()
            }

        }
    }
}

@Composable
fun ItemAnggaranType(
    modifier: Modifier = Modifier,
    item: ItemAnggaranType,
    onItemClick: (ItemAnggaranType) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(12.dp)
            .clickable {
                onItemClick.invoke(item)
            },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(color = Color(item.bgColor), shape = MaterialTheme.shapes.small)
                .padding(10.dp)
                .height(IntrinsicSize.Max),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = item.iconPath),
                contentDescription = null
            )
        }
        Text(
            text = item.name,
            style = MaterialTheme.typography.h6,
            color = ColorBlack
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemAnggaranType() {
    MoneyVerseTheme {
        ItemAnggaranType(
            item = ItemAnggaranType(
                anggaranType = AnggaranType.MONTHLY,
                name = "Bulanan",
                iconPath = R.drawable.ic_calendar_monthly,
                bgColor = ColorBgPurple.toArgb()
            ),
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSheetContentDataTile() {
    MoneyVerseTheme {
        val dataTiles = mutableListOf(
            ItemAnggaranType(
                anggaranType = AnggaranType.MONTHLY,
                name = "Bulanan",
                iconPath = R.drawable.ic_calendar_monthly,
                bgColor = ColorBgPurple.toArgb()
            ),
            ItemAnggaranType(
                anggaranType = AnggaranType.WEEKLY,
                name = "Mingguan",
                iconPath = R.drawable.ic_calendar_weekly,
                bgColor = ColorBgGreen.toArgb()
            ),
            ItemAnggaranType(
                anggaranType = AnggaranType.ANNUAL,
                name = "Tahunan",
                iconPath = R.drawable.ic_calendar_annual,
                bgColor = ColorBgBlue.toArgb()
            ),
        )
        SheetContentAnggaranType(
            items = dataTiles,
            onCloseClick = {},
            onItemClick = {}
        )
    }
}