package com.rasyidin.moneyverse.data.repository.transaction

import androidx.paging.PagingData
import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionEntity
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun upsertTransaction(transactionEntity: TransactionEntity)

    suspend fun getRecentTransactions(): List<ItemTransaction>

    suspend fun getHistoryTransactions(): Flow<PagingData<ItemTransaction>>
}