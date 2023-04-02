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
import kotlin.math.abs

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val accountDao: AccountDao
) : TransactionRepository {
    override suspend fun upsertTransaction(
        transactionEntity: TransactionEntity,
        editedFromAccountId: Int,
        editedToAccountId: Int
    ) {
        when (transactionEntity.transactionType) {
            OUTCOME -> {
                if (transactionEntity.id == 0) {
                    transactionDao.debitAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                } else {
                    val currentTransaction = transactionDao.getDetailTransactionById(transactionEntity.id)
                    if (editedFromAccountId != -1) {
                        if (transactionEntity.nominal != currentTransaction.nominal) {
                            transactionDao.creditAccountById(currentTransaction.nominal, editedFromAccountId)
                            transactionDao.debitAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                        } else {
                            transactionDao.debitAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                            transactionDao.creditAccountById(transactionEntity.nominal, editedFromAccountId)
                        }
                    } else {
                        if (transactionEntity.nominal != currentTransaction.nominal) {
                            val diff = currentTransaction.nominal - transactionEntity.nominal
                            if (diff > 0) {
                                transactionDao.creditAccountById(diff, transactionEntity.fromAccountId)
                            } else {
                                transactionDao.debitAccountById(abs(diff), transactionEntity.fromAccountId)
                            }
                        }
                    }
                }
                transactionDao.upsert(transactionEntity)
            }
            INCOME -> {
                if (transactionEntity.id == 0) {
                    transactionDao.creditAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                } else {
                    val currentTransaction = transactionDao.getDetailTransactionById(transactionEntity.id)
                    if (editedFromAccountId != -1) {
                        if (transactionEntity.nominal != currentTransaction.nominal) {
                            transactionDao.debitAccountById(currentTransaction.nominal, editedFromAccountId)
                            transactionDao.creditAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                        } else {
                            transactionDao.debitAccountById(transactionEntity.nominal, editedFromAccountId)
                            transactionDao.creditAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                        }
                    } else {
                        if (transactionEntity.nominal != currentTransaction.nominal) {
                            val diff = transactionEntity.nominal - currentTransaction.nominal
                            if (diff > 0) {
                                transactionDao.creditAccountById(diff, transactionEntity.fromAccountId)
                            } else {
                                transactionDao.debitAccountById(abs(diff), transactionEntity.fromAccountId)
                            }
                        }
                    }
                }
                transactionDao.upsert(transactionEntity)
            }
            TRANSFER -> {
                if (transactionEntity.id != 0) {
                    val currentTransaction = transactionDao.getDetailTransactionById(transactionEntity.id)
                    when {
                        editedFromAccountId != -1 && editedToAccountId != -1 -> {
                            val isReversedAccount = editedFromAccountId == transactionEntity.toAccountId && editedToAccountId == transactionEntity.fromAccountId
                            if (transactionEntity.nominal != currentTransaction.nominal) {
                                val nominal = currentTransaction.nominal
                                transactionDao.debitAccountById(nominal, editedToAccountId)
                                transactionDao.creditAccountById(nominal, editedFromAccountId)
                                if (isReversedAccount.not()) {
                                    transactionDao.transfer(transactionEntity.nominal, transactionEntity.fromAccountId, transactionEntity.toAccountId!!)
                                }
                            } else {
                                val nominal = transactionEntity.nominal
                                transactionDao.debitAccountById(nominal, editedToAccountId)
                                transactionDao.creditAccountById(nominal, editedFromAccountId)
                                if (isReversedAccount.not()) {
                                    transactionDao.transfer(transactionEntity.nominal, transactionEntity.fromAccountId, transactionEntity.toAccountId!!)
                                }
                            }
                        }
                        editedToAccountId != -1 -> {
                            if (transactionEntity.nominal != currentTransaction.nominal) {
                                val nominal = currentTransaction.nominal
                                transactionDao.debitAccountById(nominal, editedToAccountId)
                                transactionDao.creditAccountById(nominal, transactionEntity.fromAccountId)
                                transactionDao.transfer(transactionEntity.nominal, transactionEntity.fromAccountId, transactionEntity.toAccountId!!)
                            } else {
                                val nominal = transactionEntity.nominal
                                transactionDao.debitAccountById(nominal, editedToAccountId)
                                transactionDao.creditAccountById(nominal, transactionEntity.fromAccountId)
                                transactionDao.transfer(transactionEntity.nominal, transactionEntity.fromAccountId, transactionEntity.toAccountId!!)
                            }
                        }
                        editedFromAccountId != -1 -> {
                            if (transactionEntity.nominal != currentTransaction.nominal) {
                                val nominal = currentTransaction.nominal
                                transactionDao.creditAccountById(nominal, editedFromAccountId)
                                transactionDao.debitAccountById(nominal, transactionEntity.toAccountId!!)
                                transactionDao.transfer(transactionEntity.nominal, transactionEntity.fromAccountId, transactionEntity.toAccountId!!)
                            } else {
                                val nominal = transactionEntity.nominal
                                transactionDao.creditAccountById(nominal, editedFromAccountId)
                                transactionDao.debitAccountById(nominal, transactionEntity.toAccountId!!)
                                transactionDao.transfer(transactionEntity.nominal, transactionEntity.fromAccountId, transactionEntity.toAccountId!!)
                            }
                        }
                        else -> {
                            if (transactionEntity.nominal != currentTransaction.nominal) {
                                val nominal = currentTransaction.nominal
                                transactionDao.creditAccountById(nominal, transactionEntity.fromAccountId)
                                transactionDao.debitAccountById(nominal, transactionEntity.toAccountId!!)
                                transactionDao.transfer(transactionEntity.nominal, transactionEntity.fromAccountId, transactionEntity.toAccountId!!)
                            } else {
                                val nominal = transactionEntity.nominal
                                transactionDao.creditAccountById(nominal, transactionEntity.fromAccountId)
                                transactionDao.debitAccountById(nominal, transactionEntity.toAccountId!!)
                                transactionDao.transfer(transactionEntity.nominal, transactionEntity.fromAccountId, transactionEntity.toAccountId!!)
                            }
                        }
                    }
                } else {
                    transactionDao.transfer(transactionEntity.nominal, transactionEntity.fromAccountId, transactionEntity.toAccountId!!)
                }
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

    override suspend fun deleteTransaction(transactionEntity: TransactionEntity) {
        when (transactionEntity.transactionType) {
            OUTCOME -> transactionDao.creditAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
            INCOME -> transactionDao.debitAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
            TRANSFER -> {
                transactionDao.creditAccountById(transactionEntity.nominal, transactionEntity.fromAccountId)
                transactionDao.debitAccountById(transactionEntity.nominal, transactionEntity.toAccountId!!)
            }
        }
        transactionDao.deleteTransaction(transactionEntity)
    }
}