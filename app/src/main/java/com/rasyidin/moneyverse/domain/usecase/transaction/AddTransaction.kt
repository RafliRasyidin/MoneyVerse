package com.rasyidin.moneyverse.domain.usecase.transaction

import com.rasyidin.moneyverse.data.repository.transaction.TransactionRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.model.transaction.Transaction
import com.rasyidin.moneyverse.domain.performAction
import kotlinx.coroutines.flow.Flow

class AddTransaction(private val transactionRepo: TransactionRepository) {

    suspend operator fun invoke(transaction: Transaction, editedAccountId: Int = -1): Flow<ResultState<Nothing>> {
        return performAction {
            transactionRepo.upsertTransaction(transaction.toEntity(), editedAccountId)
        }
    }
}