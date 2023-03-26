package com.rasyidin.moneyverse.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction
import com.rasyidin.moneyverse.ui.component.ItemTransaction
import com.rasyidin.moneyverse.ui.theme.ColorGray100
import com.rasyidin.moneyverse.ui.theme.ColorGray500
import com.rasyidin.moneyverse.ui.theme.ColorPurple500
import com.rasyidin.moneyverse.utils.toCurrency

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onBtnChatClick: () -> Unit,
    onBtnNotifClick: () -> Unit,
    onBtnSaldoClick: () -> Unit,
    onBtnSeeAllClick: () -> Unit,
    onItemClick: (ItemTransaction) -> Unit
) {
    val uiState = viewModel.uiState.value
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        TopAppBar(
            onBtnChatClick = { onBtnChatClick.invoke() },
            onBtnNotifClick = { onBtnNotifClick.invoke() }
        )
        Spacer(modifier = Modifier.height(12.dp))
        SaldoCard(saldo = uiState.totalSaldo, onBtnSaldoClick = { onBtnSaldoClick.invoke() })
        Spacer(modifier = Modifier.height(12.dp))
        RecentTransactions(
            modifier = Modifier.padding(horizontal = 12.dp),
            items = uiState.recentTransactions,
            onBtnSeeAllClick = onBtnSeeAllClick,
            onItemClick = onItemClick
        )
    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    onBtnChatClick: () -> Unit,
    onBtnNotifClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(46.dp),
            painter = painterResource(id = R.drawable.ic_avatar_panda),
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1F)
        ) {
            Text(
                text = "Hi!",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "Hari yang cerah!",
                style = MaterialTheme.typography.subtitle1,
                color = ColorGray500
            )
        }
        Row(
            modifier = Modifier,
        ) {
            Image(
                modifier = Modifier
                    .sizeIn(minWidth = 20.dp, minHeight = 20.dp)
                    .clickable { onBtnChatClick.invoke() },
                painter = painterResource(id = R.drawable.ic_chat),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(18.dp))
            Image(
                modifier = Modifier
                    .sizeIn(minWidth = 20.dp, minHeight = 20.dp)
                    .clickable { onBtnNotifClick.invoke() },
                painter = painterResource(id = R.drawable.ic_notif),
                contentDescription = null
            )
        }
    }
}

@Composable
fun SaldoCard(
    modifier: Modifier = Modifier,
    saldo: Long,
    onBtnSaldoClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onBtnSaldoClick.invoke() }
            .paint(
                painter = painterResource(id = R.drawable.bg_card_saldo),
                contentScale = ContentScale.FillWidth
            )
            .padding(start = 18.dp, end = 26.dp),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Column(
                modifier = Modifier.weight(1F),
            ) {
                Text(
                    text = stringResource(id = R.string.total_saldo),
                    style = MaterialTheme.typography.h6,
                    color = ColorGray100
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "Rp.",
                        style = MaterialTheme.typography.h6,
                        color = ColorGray100
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = saldo.toCurrency(),
                        style = MaterialTheme.typography.h2,
                        color = ColorGray100
                    )
                }
            }
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(14.dp),
                painter = painterResource(id = R.drawable.ic_forward_white),
                contentDescription = null
            )
        }
    }

}

@Composable
fun RecentTransactions(
    modifier: Modifier = Modifier,
    items: List<ItemTransaction>,
    onBtnSeeAllClick: () -> Unit,
    onItemClick: (ItemTransaction) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(id = R.string.aktivitas_terakhir),
                style = MaterialTheme.typography.h5
            )
            Text(
                modifier = Modifier.clickable { onBtnSeeAllClick.invoke() },
                text = stringResource(id = R.string.lihat_semua),
                style = MaterialTheme.typography.subtitle2,
                color = ColorPurple500
            )
        }
        items.forEach { item ->
            Spacer(modifier = Modifier.height(6.dp))
            ItemTransaction(
                item = item,
                onItemClick = onItemClick
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}
