package com.rasyidin.moneyverse.domain.model.home

import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction

data class HomeUi(
    val totalSaldo: Long = 0,
    val recentTransactions: List<ItemTransaction> = emptyList(),
    val errorMessage: String? = null
)
