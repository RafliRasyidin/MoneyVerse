package com.rasyidin.moneyverse.domain.usecase.transaction.outcome

import com.rasyidin.moneyverse.data.repository.account.AccountRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.fetchDataList
import com.rasyidin.moneyverse.domain.model.account.Account
import kotlinx.coroutines.flow.Flow

class GetListAccount(private val accountRepo: AccountRepository) {

    suspend operator fun invoke(): Flow<ResultState<List<Account>>> {
        return fetchDataList {
            accountRepo.getListAccount().map { entity ->
                entity.toDomain()
            }
        }
    }
}