package com.rasyidin.moneyverse.data.local.entities.transaction

import androidx.paging.PagingSource
import androidx.room.*
import com.rasyidin.moneyverse.domain.model.transaction.DetailTransactionUi
import com.rasyidin.moneyverse.domain.model.transaction.ItemTransaction

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(transactionEntity: TransactionEntity)

    @Query("UPDATE account SET nominal = (nominal - :nominal) WHERE id = :accountId")
    suspend fun debitAccountById(nominal: Long, accountId: Int)

    @Query("UPDATE account SET nominal = (nominal + :nominal) WHERE id = :accountId")
    suspend fun creditAccountById(nominal: Long, accountId: Int)

    @Transaction
    suspend fun transfer(
        nominal: Long,
        fromAccountId: Int,
        toAccountId: Int
    ) {
        debitAccountById(nominal, fromAccountId)
        creditAccountById(nominal, toAccountId)
    }

    @Query("""
        SELECT 
            a.id,
            a.nominal,
            a.createdAt,
            a.notes,
            a.transactionType,
            a.categoryId,
            a.sumberAkunId as fromAccountId,
            a.tujuanAkunId as toAccountId,
            b.iconPath,
            b.bgColor,
            b.name as categoryName,
            c.name as accountName
        FROM transaksi a
        LEFT JOIN category b ON a.categoryId = b.id
        LEFT JOIN account c ON a.sumberAkunId = c.id
        ORDER BY a.createdAt DESC
        LIMIT 3
    """)
    suspend fun getRecentTransactions(): List<ItemTransaction>

    @Query("""
        SELECT 
            a.id,
            a.nominal,
            a.createdAt,
            a.notes,
            a.transactionType,
            a.categoryId,
            a.sumberAkunId as fromAccountId,
            a.tujuanAkunId as toAccountId,
            b.iconPath,
            b.bgColor,
            b.name as categoryName,
            c.name as accountName
        FROM transaksi a
        LEFT JOIN category b ON a.categoryId = b.id
        LEFT JOIN account c ON a.sumberAkunId = c.id
        ORDER BY a.createdAt DESC
    """)
    fun getHistoryTransactions(): PagingSource<Int, ItemTransaction>

    @Query("""
        SELECT
            a.id,
            a.nominal,
            a.createdAt,
            a.notes,
            a.transactionType,
            a.categoryId,
            a.sumberAkunId as fromAccountId,
            a.tujuanAkunId as toAccountId,
            b.name as accountName,
            b.iconPath as accountIconPath,
            b.bgColor as accountBgColor,
            c.bgColor as categoryBgColor,
            c.iconPath as categoryIconPath,
            c.name as categoryName
        FROM transaksi a
        LEFT JOIN account b ON a.sumberAkunId = b.id
        LEFT JOIN category c ON a.categoryId = c.id
        WHERE a.id = :transactionId
    """)
    fun getDetailTransactionById(transactionId: Int): DetailTransactionUi
}