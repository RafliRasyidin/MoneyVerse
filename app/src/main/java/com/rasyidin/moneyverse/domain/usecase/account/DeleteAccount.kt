package com.rasyidin.moneyverse.domain.usecase.account

import com.rasyidin.moneyverse.data.repository.account.AccountRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.performAction
import kotlinx.coroutines.flow.Flow

class DeleteAccount(private val repository: AccountRepository) {

    suspend operator fun invoke(accountId: Int): Flow<ResultState<Nothing>> {
        return performAction { repository.deleteAccountById(accountId) }
    }
}