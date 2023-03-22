package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.data.local.db.MoneyVerseDb
import com.rasyidin.moneyverse.domain.model.category.Category
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme


@Composable
fun SheetContentCategories(
    modifier: Modifier = Modifier,
    title: String,
    categories: List<Category>,
    isShowName: Boolean,
    onItemClick: (Category) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        SheetHeader(title = title)
        ListCategories(categories = categories, onItemClick = onItemClick, isShowName = isShowName)
    }
}

@Composable
fun SheetHeader(
    modifier: Modifier = Modifier,
    title: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null
        )
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMVBotSheet() {
    MoneyVerseTheme {
        val context = LocalContext.current
        SheetContentCategories(
            title = "Pengeluaran",
            categories = MoneyVerseDb.initCategories(context).map { it.toDomain() },
            onItemClick = {},
            isShowName = true
        )
    }
}