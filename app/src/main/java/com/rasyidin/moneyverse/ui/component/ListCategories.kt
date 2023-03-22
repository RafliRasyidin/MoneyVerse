package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.data.local.db.MoneyVerseDb
import com.rasyidin.moneyverse.domain.model.category.Category
import com.rasyidin.moneyverse.domain.model.category.CategoryKey
import com.rasyidin.moneyverse.domain.model.category.CategoryType
import com.rasyidin.moneyverse.ui.theme.ColorBgRed
import com.rasyidin.moneyverse.ui.theme.ColorGray500
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme

@Composable
fun ListCategories(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onItemClick: (Category) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
    ) {
        items(categories) { category ->
            ItemCategory(item = category, onItemClick = onItemClick)
        }
    }
}

@Composable
fun ItemCategory(
    modifier: Modifier = Modifier,
    item: Category,
    onItemClick: (Category) -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onItemClick.invoke(item) }
            .padding(horizontal = 12.dp, vertical = 8.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = Color(item.bgColor), shape = MaterialTheme.shapes.small)
                .padding(14.dp)
                .height(IntrinsicSize.Max),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = item.iconPath),
                contentDescription = null
            )
        }
        Text(
            text = item.name,
            style = MaterialTheme.typography.h6,
            color = ColorGray500
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListCategories() {
    MoneyVerseTheme() {
        val context = LocalContext.current
        ListCategories(
            categories = MoneyVerseDb.initCategories(context).map { it.toDomain() },
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemCategory() {
    MoneyVerseTheme() {
        ItemCategory(
            item = Category(
                CategoryKey.FOOD,
                ColorBgRed.toArgb(),
                R.drawable.ic_food_n_drink,
                "Makanan & Minuman",
                CategoryType.TransactionOutcome
            ),
            onItemClick = {}
        )
    }
}