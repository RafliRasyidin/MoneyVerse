package com.rasyidin.moneyverse.data.repository.transaction

import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionDao
import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionEntity
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val transactionDao: TransactionDao) : TransactionRepository {
    override suspend fun upsertTransaction(transactionEntity: TransactionEntity) {
        transactionDao.upsert(transactionEntity)
    }
}