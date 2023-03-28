package com.rasyidin.moneyverse.ui.screen.transaction.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.component.MVToolbar
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.utils.dropShadow

@Composable
fun DetailTransactionScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MVToolbar(
            modifier = Modifier.padding(horizontal = 12.dp),
            title = stringResource(id = R.string.detail_transaksi),
            iconEnd = R.drawable.ic_edit,
            onBackClick = { navController?.popBackStack() },
            onIconEndClick = {  }
        )
        CardDetailTransaction(
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

@Composable
fun CardDetailTransaction(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .dropShadow(),
        elevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDetailTransactionScreen() {
    MoneyVerseTheme {
        DetailTransactionScreen()
    }
}