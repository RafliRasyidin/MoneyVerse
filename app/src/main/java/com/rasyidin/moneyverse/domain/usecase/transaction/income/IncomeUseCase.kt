package com.rasyidin.moneyverse.domain.usecase.transaction.income

import com.rasyidin.moneyverse.domain.usecase.transaction.AddTransaction
import com.rasyidin.moneyverse.domain.usecase.transaction.GetDetailTransaction
import com.rasyidin.moneyverse.domain.usecase.transaction.GetListAccount

data class IncomeUseCase(
    val addTransaction: AddTransaction,
    val getIncomeCategories: GetIncomeCategories,
    val getListAccount: GetListAccount,
    val getDetailTransaction: GetDetailTransaction
)