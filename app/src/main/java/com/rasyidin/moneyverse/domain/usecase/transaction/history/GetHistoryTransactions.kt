package com.rasyidin.moneyverse.domain.usecase.transaction.history

import androidx.paging.PagingData
import com.rasyidin.moneyverse.data.repository.transaction.TransactionRepository
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction
import kotlinx.coroutines.flow.Flow

class GetHistoryTransactions(private val repository: TransactionRepository) {

    operator fun invoke(): Flow<PagingData<ItemTransaction>> {
        return repository.getHistoryTransactions()
    }
}