package com.rasyidin.moneyverse.domain.usecase.transaction.outcome

import com.rasyidin.moneyverse.domain.usecase.transaction.AddTransaction

data class OutcomeUseCase(
    val addTransaction: AddTransaction,
    val getListAccount: GetListAccount,
    val getOutcomeCategories: GetOutcomeCategories
)
