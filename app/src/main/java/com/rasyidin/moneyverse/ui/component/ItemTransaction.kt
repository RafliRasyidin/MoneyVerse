package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType.*
import com.rasyidin.moneyverse.ui.theme.*
import com.rasyidin.moneyverse.utils.DateUtils.getRelativeDate
import com.rasyidin.moneyverse.utils.dropShadow
import com.rasyidin.moneyverse.utils.fromHtml
import com.rasyidin.moneyverse.utils.toCurrency

@Composable
fun ItemTransaction(
    modifier: Modifier = Modifier,
    item: ItemTransaction,
    onItemClick: (ItemTransaction) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .clickable { onItemClick.invoke(item) }
            .dropShadow(),
        shape = MaterialTheme.shapes.small,
        elevation = 0.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Color(item.bgColor ?: ColorBgGray.toArgb()),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(12.dp)
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = item.iconPath ?: R.drawable.ic_transfer),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1F),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                val bulletPoint = stringResource(id = R.string.bullet_symbol).fromHtml().toString()
                Text(
                    text = "Rp ${item.nominal.toCurrency()}",
                    style = MaterialTheme.typography.h5,
                    color = when (item.transactionType) {
                        OUTCOME -> ColorRed500
                        INCOME -> ColorGreen500
                        TRANSFER -> ColorGray500
                    }
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = if (item.notes.isNullOrEmpty()) "-" else item.notes,
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = bulletPoint,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = item.accountName,
                        style = MaterialTheme.typography.subtitle2,
                        color = ColorGray500
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.align(Alignment.Top),
                text = item.createdAt.getRelativeDate(LocalContext.current),
                style = MaterialTheme.typography.subtitle2,
                color = ColorGray500
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewItemTransaction() {
    MoneyVerseTheme {
        ItemTransaction(
            item = ItemTransaction(
                id = 0,
                nominal = 70000,
                createdAt = "2023-03-26 15:21:00",
                notes = "Catatan",
                transactionType = OUTCOME,
                categoryId = -1,
                fromAccountId = -1,
                toAccountId = -1,
                iconPath = R.drawable.ic_food_n_drink,
                bgColor = ColorBgRed.toArgb(),
                categoryName = "Makanan & Minuman",
                accountName = "Cash"
            ),
            onItemClick = {}
        )
    }
}