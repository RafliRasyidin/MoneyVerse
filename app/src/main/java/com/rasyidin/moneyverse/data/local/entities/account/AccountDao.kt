package com.rasyidin.moneyverse.data.local.entities.account

import androidx.room.*

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    suspend fun getListAccount(): List<AccountEntity>

    @Query("SELECT * FROM account WHERE id = :accountId")
    suspend fun getAccountById(accountId: Int): AccountEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAccount(accountEntity: AccountEntity)

    @Query("DELETE FROM account WHERE id = :accountId")
    suspend fun deleteAccountById(accountId: Int)

    @Update
    suspend fun updateAccount(accountEntity: AccountEntity)

    @Query("SELECT SUM(nominal) FROM account")
    suspend fun getTotalSaldo(): Long
} 