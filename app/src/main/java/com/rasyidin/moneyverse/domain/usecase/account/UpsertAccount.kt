package com.rasyidin.moneyverse.domain.usecase.account

import com.rasyidin.moneyverse.data.repository.account.AccountRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.model.account.Account
import com.rasyidin.moneyverse.domain.performAction
import kotlinx.coroutines.flow.Flow

class UpsertAccount(private val repository: AccountRepository) {

    suspend operator fun invoke(account: Account): Flow<ResultState<Nothing>> {
        return performAction {
            repository.addAccount(account.toEntity())
        }
    }
}