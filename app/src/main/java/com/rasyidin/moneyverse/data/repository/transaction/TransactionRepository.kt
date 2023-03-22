package com.rasyidin.moneyverse.data.repository.transaction

import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionEntity

interface TransactionRepository {

    suspend fun upsertTransaction(transactionEntity: TransactionEntity)
}