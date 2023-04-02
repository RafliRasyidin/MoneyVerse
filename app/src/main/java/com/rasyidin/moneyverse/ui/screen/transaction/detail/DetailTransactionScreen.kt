package com.rasyidin.moneyverse.ui.screen.transaction.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.transaction.DetailTransactionUi
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType.*
import com.rasyidin.moneyverse.ui.component.MVToolbar
import com.rasyidin.moneyverse.ui.theme.*
import com.rasyidin.moneyverse.utils.DateUtils.formatDate
import com.rasyidin.moneyverse.utils.dropShadow
import com.rasyidin.moneyverse.utils.isNull
import com.rasyidin.moneyverse.utils.toCurrency

@Composable
fun DetailTransactionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DetailTransactionViewModel = hiltViewModel()
) {
    val detailUi = viewModel.uiState.value
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        MVToolbar(
            modifier = Modifier.padding(horizontal = 12.dp),
            title = stringResource(id = R.string.detail_transaksi),
            iconEnd = R.drawable.ic_edit,
            onBackClick = { navController.popBackStack() },
            onIconEndClick = {
                val action = when (detailUi.transactionType) {
                    OUTCOME -> DetailTransactionFragmentDirections.actionDetailTransactionFragmentToOutcomeFragment(detailUi.id)
                    INCOME -> DetailTransactionFragmentDirections.actionDetailTransactionFragmentToIncomeFragment(detailUi.id)
                    TRANSFER -> DetailTransactionFragmentDirections.actionDetailTransactionFragmentToTransferFragment(detailUi.id)
                }
                navController.navigate(action)
            }
        )
        Spacer(modifier = Modifier.height(36.dp))
        CardDetailTransaction(
            modifier = Modifier.padding(horizontal = 12.dp),
            detailUi = detailUi
        )
    }
}

@Composable
fun CardDetailTransaction(
    modifier: Modifier = Modifier,
    detailUi: DetailTransactionUi,
) {
    val bgColorCategory = Color(detailUi.categoryBgColor ?: ColorBgGray.toArgb())
    val iconCategory = painterResource(id = detailUi.categoryIconPath ?: R.drawable.ic_transfer)
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
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .background(bgColorCategory, shape = MaterialTheme.shapes.medium)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(36.dp),
                    painter = iconCategory,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = detailUi.createdAt.formatDate(to = "dd MMMM yyyy, HH.mm"),
                style = MaterialTheme.typography.h6,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (detailUi.notes.isNullOrEmpty()) "-" else detailUi.notes!!,
                style = MaterialTheme.typography.h6,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rp " + detailUi.nominal.toCurrency(),
                style = MaterialTheme.typography.h3,
                color = when (detailUi.transactionType) {
                    OUTCOME -> ColorRed500
                    INCOME -> ColorGreen500
                    TRANSFER -> ColorGray500
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CardIncomes(detailUi = detailUi)
            Spacer(modifier = Modifier.height(16.dp))
            RincianTransactions(detailUi = detailUi)
        }
    }
}

@Composable
fun CardIncomes(
    modifier: Modifier = Modifier,
    detailUi: DetailTransactionUi,
    viewModel: DetailTransactionViewModel = hiltViewModel()
) {
    val bgColorFromAccount = Color(detailUi.accountBgColor)
    val bgColorToAccount = Color(detailUi.categoryBgColor ?: ColorBgBlue.toArgb())
    var dominantColorFromAccount by remember { mutableStateOf(ColorGray400) }
    var dominantColorToAccount by remember { mutableStateOf(ColorGray400) }
    val context = LocalContext.current
    ContextCompat.getDrawable(context, detailUi.accountIconPath)?.let {
        viewModel.findDominantColor(it) { colorValue ->
            dominantColorFromAccount = colorValue
        }
    }
    ContextCompat.getDrawable(context, detailUi.categoryIconPath ?: R.drawable.ic_transfer)?.let {
        viewModel.findDominantColor(it) { colorValue ->
            dominantColorToAccount = colorValue
        }
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .background(bgColorFromAccount, shape = MaterialTheme.shapes.medium),
        ) {
            Row(
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = detailUi.accountIconPath),
                    contentDescription = null
                )
                Text(
                    text = detailUi.accountName,
                    style = MaterialTheme.typography.h6,
                    fontSize = 12.sp,
                    color = dominantColorFromAccount
                )
            }
        }
        Spacer(modifier = Modifier.width(if (detailUi.toAccountId.isNull) 0.dp else 12.dp))
        AnimatedVisibility(visible = detailUi.toAccountId != null) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(if (detailUi.toAccountId.isNull) 0.dp else 12.dp))
        AnimatedVisibility(visible = detailUi.toAccountId != null) {
            Box(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .background(bgColorToAccount, shape = MaterialTheme.shapes.medium),
            ) {
                Row(
                    modifier = Modifier.padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = detailUi.categoryIconPath!!),
                        contentDescription = null
                    )
                    Text(
                        text = detailUi.categoryName.toString(),
                        style = MaterialTheme.typography.h6,
                        fontSize = 12.sp,
                        color = dominantColorToAccount
                    )
                }
            }
        }
    }
}

@Composable
fun RincianTransactions(
    modifier: Modifier = Modifier,
    detailUi: DetailTransactionUi
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(id = R.string.rincian_transaksi),
            style = MaterialTheme.typography.h5,
        )
        Spacer(modifier = Modifier.height(12.dp))
        AnimatedVisibility(visible = detailUi.transactionType != TRANSFER) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.weight(1F),
                    text = stringResource(id = R.string.kategori),
                    style = MaterialTheme.typography.subtitle2,
                    color = ColorGray500
                )
                Text(
                    modifier = Modifier.weight(1F),
                    text = detailUi.categoryName.toString(),
                    style = MaterialTheme.typography.subtitle2,
                    textAlign = TextAlign.End
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(id = R.string.jenis),
                style = MaterialTheme.typography.subtitle2,
                color = ColorGray500
            )
            Text(
                modifier = Modifier.weight(1F),
                text = when (detailUi.transactionType) {
                    OUTCOME -> stringResource(id = R.string.pengeluaran)
                    INCOME -> stringResource(id = R.string.pemasukan)
                    TRANSFER -> stringResource(id = R.string.transfer)
                },
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(id = R.string.nomor_transaksi),
                style = MaterialTheme.typography.subtitle2,
                color = ColorGray500
            )
            Text(
                modifier = Modifier.weight(1F),
                text = detailUi.id.toString(),
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.End
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
private fun PreviewDetailTransactionScreen() {
    MoneyVerseTheme {
        DetailTransactionScreen(
            detailUi = DetailTransactionUi(
                id = -1,
                nominal = 20000,
                createdAt = DateUtils.getCurrentDate(),
                notes = "Catatan",
                transactionType = OUTCOME,
                categoryId = -1,
                fromAccountId = -1,
                toAccountId = -1,
                accountName = "Cash",
                accountIconPath = R.drawable.ic_cash,
                accountBgColor = ColorBgGreen.toArgb(),
                categoryName = "Makan & Minum",
                categoryIconPath = R.drawable.ic_food_n_drink,
                categoryBgColor = ColorBgRed.toArgb()
            )
        )
    }
}*/
