package com.rasyidin.moneyverse.domain.usecase.home

import com.rasyidin.moneyverse.domain.usecase.account.GetTotalSaldo

data class HomeUseCase(
    val getTotalSaldo: GetTotalSaldo
)
