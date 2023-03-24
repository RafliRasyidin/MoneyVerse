package com.rasyidin.moneyverse.data.local.entities.transaction

import androidx.room.*

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
}