package com.rasyidin.moneyverse.domain.usecase.home

import com.rasyidin.moneyverse.data.repository.transaction.TransactionRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.fetchDataList
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction
import kotlinx.coroutines.flow.Flow

class GetRecentTransactions(private val transactionRepo: TransactionRepository) {

    suspend operator fun invoke(): Flow<ResultState<List<ItemTransaction>>> {
        return fetchDataList {
            transactionRepo.getRecentTransactions()
        }
    }
}