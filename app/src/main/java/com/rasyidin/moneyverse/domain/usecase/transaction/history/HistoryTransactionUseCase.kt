package com.rasyidin.moneyverse.domain.usecase.transaction.history

import com.rasyidin.moneyverse.domain.usecase.home.GetRecentFiveTransactions

data class HistoryTransactionUseCase(
    val getRecentFiveTransactions: GetRecentFiveTransactions
)
