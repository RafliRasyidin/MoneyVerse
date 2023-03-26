package com.rasyidin.moneyverse.data.repository.transaction

import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionEntity
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction

interface TransactionRepository {

    suspend fun upsertTransaction(transactionEntity: TransactionEntity)

    suspend fun getRecentFiveTransactions(): List<ItemTransaction>
}