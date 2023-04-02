package com.rasyidin.moneyverse.domain.usecase.transaction

import com.rasyidin.moneyverse.data.repository.transaction.TransactionRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.fetch
import com.rasyidin.moneyverse.domain.model.transaction.DetailTransactionUi
import kotlinx.coroutines.flow.Flow

class GetDetailTransaction(private val transactionRepo: TransactionRepository) {

    suspend operator fun invoke(transactionId: Int): Flow<ResultState<DetailTransactionUi>> {
        return fetch {
            transactionRepo.getDetailTransaction(transactionId)
        }
    }
}