package com.rasyidin.moneyverse.ui.screen.transaction.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction
import com.rasyidin.moneyverse.ui.component.ItemTransaction
import com.rasyidin.moneyverse.ui.component.MVToolbar
import com.rasyidin.moneyverse.utils.DateUtils.DEFAULT_DATE_FORMAT
import com.rasyidin.moneyverse.utils.DateUtils.formatDate

@Composable
fun HistoryTransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryTransactionViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onItemClick: (ItemTransaction) -> Unit
) {
    val items = viewModel.getHistoryTransactions().collectAsLazyPagingItems()
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MVToolbar(
            modifier = Modifier.padding(horizontal = 12.dp),
            title = stringResource(id = R.string.riwayat_aktivitas),
            onBackClick = onBackClick
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(12.dp)
        ) {
            val groupedTransaction = items.itemSnapshotList.items.groupBy { it.createdAt.formatDate(to = DEFAULT_DATE_FORMAT) }
            groupedTransaction.forEach { (date, transactions) ->
                item {
                    Spacer(modifier = Modifier.height(9.dp))
                    Text(
                        text = date.formatDate(from = DEFAULT_DATE_FORMAT, to = "dd MMMM yyyy"),
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(9.dp))
                }

                items(transactions) { transaction ->
                    Spacer(modifier = Modifier.height(6.dp))
                    ItemTransaction(item = transaction, onItemClick = onItemClick, isShowDate = false)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }


            if (items.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}