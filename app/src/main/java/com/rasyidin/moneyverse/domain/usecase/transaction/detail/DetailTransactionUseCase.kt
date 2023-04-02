package com.rasyidin.moneyverse.domain.usecase.transaction.detail

import com.rasyidin.moneyverse.domain.usecase.transaction.DeleteTransaction
import com.rasyidin.moneyverse.domain.usecase.transaction.GetDetailTransaction

data class DetailTransactionUseCase(
    val getDetailTransaction: GetDetailTransaction,
    val deleteTransaction: DeleteTransaction
)
