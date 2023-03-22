package com.rasyidin.moneyverse.ui.screen.account.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.account.Account
import com.rasyidin.moneyverse.ui.component.MVToolbar
import com.rasyidin.moneyverse.ui.theme.*
import com.rasyidin.moneyverse.utils.shadow
import com.rasyidin.moneyverse.utils.toCurrency

@Composable
fun ListAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: ListAccountViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onAddAccountClick: () -> Unit,
    onItemAccountClick: (Account) -> Unit
) {
    val uiState = viewModel.uiState.value
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MVToolbar(
            modifier = Modifier.padding(horizontal = 12.dp),
            title = stringResource(id = R.string.total_saldo),
            onBackClick = onBackClick
        )
        TotalSaldo(totalSaldo = uiState.totalSaldo)
        Spacer(modifier = Modifier.height(18.dp))
        ListAccount(
            modifier = Modifier.weight(1F),
            accounts = uiState.accounts,
            onItemClick = onItemAccountClick
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
            onClick = onAddAccountClick,
            shape = MaterialTheme.shapes.large
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.tambah_baru),
                style = MaterialTheme.typography.button,
                color = ColorWhite
            )
        }
    }
}

@Composable
fun TotalSaldo(
    modifier: Modifier = Modifier,
    totalSaldo: Long
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = stringResource(id = R.string.rp),
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = totalSaldo.toCurrency(),
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
fun ListAccount(
    modifier: Modifier = Modifier,
    accounts: List<Account>,
    onItemClick: (Account) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(accounts) { account ->
            ItemAccount(account = account, onItemClick = onItemClick)
        }
    }
}

@Composable
fun ItemAccount(
    modifier: Modifier = Modifier,
    account: Account,
    onItemClick: (Account) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .shadow(
                color = ColorShadow,
                alpha = .1F,
                shadowBlurRadius = 16.dp,
                cornersRadius = 16.dp
            ),
        shape = MaterialTheme.shapes.small,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick.invoke(account) }
                .padding(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(account.bgColor),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(11.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = account.iconPath),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
                    .padding(start = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = if (account.desc.isNullOrEmpty()) "-" else account.desc,
                    style = MaterialTheme.typography.subtitle2,
                    color = ColorGray500
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Rp ${account.nominal.toCurrency()}",
                    style = MaterialTheme.typography.h5,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewListAccountScreen() {
    MoneyVerseTheme {
        ListAccountScreen(onBackClick = {}, onAddAccountClick = {}, onItemAccountClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemAccount() {
    MoneyVerseTheme {
        ItemAccount(
            account = Account(
                id = 1,
                nominal = 100000,
                name = "Cash",
                updatedAt = "",
                iconPath = R.drawable.ic_cash,
                bgColor = ColorBgGreen.toArgb()
            ),
            onItemClick = {}
        )
    }
}