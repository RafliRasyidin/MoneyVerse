package com.rasyidin.moneyverse.domain.usecase.account

data class AccountUseCase(
    val upsertAccount: UpsertAccount,
    val deleteAccount: DeleteAccount,
    val getListAccount: GetListAccount,
    val getTotalSaldo: GetTotalSaldo,
    val updateAccount: UpdateAccount,
    val getDetailAccount: GetDetailAccount,
    val getAccountCategories: GetAccountCategories
)
