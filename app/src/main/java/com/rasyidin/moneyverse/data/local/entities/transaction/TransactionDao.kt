package com.rasyidin.moneyverse.data.local.entities.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(transactionEntity: TransactionEntity)
}