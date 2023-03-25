package com.rasyidin.moneyverse.domain.usecase.transaction.outcome

import com.rasyidin.moneyverse.domain.usecase.transaction.AddTransaction
import com.rasyidin.moneyverse.domain.usecase.transaction.GetListAccount

data class OutcomeUseCase(
    val addTransaction: AddTransaction,
    val getListAccount: GetListAccount,
    val getOutcomeCategories: GetOutcomeCategories
)
