package com.rasyidin.moneyverse.domain.usecase.account

import com.rasyidin.moneyverse.data.repository.account.AccountRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.fetch
import kotlinx.coroutines.flow.Flow

class GetTotalSaldo(private val repository: AccountRepository) {

    suspend operator fun invoke(): Flow<ResultState<Long>> = fetch {
        repository.getTotalSaldo()
    }
}