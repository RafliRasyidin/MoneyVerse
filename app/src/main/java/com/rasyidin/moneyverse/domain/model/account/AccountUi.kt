package com.rasyidin.moneyverse.domain.model.account

data class AccountUi(
    var totalSaldo: Long = 0,
    var accounts: List<Account> = emptyList(),
    var errorMessage: String = ""
)
