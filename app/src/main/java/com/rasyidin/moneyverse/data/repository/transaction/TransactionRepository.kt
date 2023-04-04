package com.rasyidin.moneyverse.data.repository.transaction

import androidx.paging.PagingData
import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionEntity
import com.rasyidin.moneyverse.domain.model.transaction.DetailTransactionUi
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun upsertTransaction(transactionEntity: TransactionEntity, editedFromAccountId: Int, editedToAccountId: Int)

    suspend fun getRecentTransactions(): List<ItemTransaction>

    fun getHistoryTransactions(): Flow<PagingData<ItemTransaction>>

    suspend fun getDetailTransaction(transactionId: String): DetailTransactionUi

    suspend fun deleteTransaction(transactionEntity: TransactionEntity)
}