package com.rasyidin.moneyverse.data.repository.transaction

import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionDao
import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionEntity
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType.*
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val transactionDao: TransactionDao) : TransactionRepository {
    override suspend fun upsertTransaction(transactionEntity: TransactionEntity) {
        when (transactionEntity.transactionType) {
            OUTCOME -> {
                transactionDao.debitAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                transactionDao.upsert(transactionEntity)
            }
            INCOME -> {
                transactionDao.creditAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                transactionDao.upsert(transactionEntity)
            }
            TRANSFER -> {
                transactionDao.transfer(transactionEntity.nominal, transactionEntity.fromAccountId, transactionEntity.toAccountId!!)
                transactionDao.upsert(transactionEntity)
            }
        }
    }
}