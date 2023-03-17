package com.rasyidin.moneyverse.domain.usecase.account

data class AccountUseCase(
    val addAccount: AddAccount,
    val deleteAccount: DeleteAccount,
    val getListAccount: GetListAccount,
    val getTotalSaldo: GetTotalSaldo,
    val updateAccount: UpdateAccount,
)
