package com.rasyidin.moneyverse.domain.usecase.account

import com.rasyidin.moneyverse.data.repository.account.AccountRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.fetch
import com.rasyidin.moneyverse.domain.model.account.Account
import kotlinx.coroutines.flow.Flow

class GetDetailAccount(private val repository: AccountRepository) {

    suspend operator fun invoke(accountId: Int): Flow<ResultState<Account>> {
        return fetch {
            repository.getAccountById(accountId).toDomain()
        }
    }
}
