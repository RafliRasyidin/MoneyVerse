package com.rasyidin.moneyverse.data.repository.transaction

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rasyidin.moneyverse.data.local.entities.account.AccountDao
import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionDao
import com.rasyidin.moneyverse.data.local.entities.transaction.TransactionEntity
import com.rasyidin.moneyverse.domain.model.transaction.DetailTransactionUi
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction
import com.rasyidin.moneyverse.domain.model.transaction.TransactionType.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao
) : TransactionRepository {
    override suspend fun upsertTransaction(
        transactionEntity: TransactionEntity,
        editedAccountId: Int
    ) {
        when (transactionEntity.transactionType) {
            OUTCOME -> {
                if (transactionEntity.id == 0) {
                    transactionDao.debitAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                } else {
                    if (editedAccountId != -1) {
                        transactionDao.debitAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                        transactionDao.creditAccountById(transactionEntity.nominal, editedAccountId)
                    }
                }
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

    override suspend fun getRecentTransactions(): List<ItemTransaction> {
        return transactionDao.getRecentTransactions()
    }

    override fun getHistoryTransactions(): Flow<PagingData<ItemTransaction>> {
        val pagingConfig = PagingConfig(pageSize = 10, initialLoadSize = 15)
        return Pager(
            config = pagingConfig
        ) {
            transactionDao.getHistoryTransactions()
        }.flow.flowOn(Dispatchers.IO)
    }

    override suspend fun getDetailTransaction(transactionId: Int): DetailTransactionUi {
        val detailTransaction = transactionDao.getDetailTransactionById(transactionId)
        return if (detailTransaction.categoryId != null) {
            detailTransaction
        } else {
            val detailToAccount = accountDao.getAccountById(detailTransaction.toAccountId!!)
            detailTransaction.apply {
                categoryBgColor = detailToAccount.bgColor
                categoryIconPath = detailToAccount.iconPath
                categoryName = detailToAccount.name
            }
            detailTransaction
        }
    }
}