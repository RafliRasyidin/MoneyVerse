package com.rasyidin.moneyverse.domain.usecase.account

data class AccountUseCase(
    val addAccount: AddAccount,
    val updateAccount: UpdateAccount,
    val deleteAccount: DeleteAccount,
    val getListAccount: GetListAccount
)
