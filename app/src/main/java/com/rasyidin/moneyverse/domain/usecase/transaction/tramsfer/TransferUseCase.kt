package com.rasyidin.moneyverse.domain.usecase.transaction.tramsfer

import com.rasyidin.moneyverse.domain.usecase.transaction.AddTransaction
import com.rasyidin.moneyverse.domain.usecase.transaction.GetDetailTransaction
import com.rasyidin.moneyverse.domain.usecase.transaction.GetListAccount

data class TransferUseCase(
    val addTransaction: AddTransaction,
    val getListAccount: GetListAccount,
    val getDetailTransaction: GetDetailTransaction
)
