package com.rasyidin.moneyverse.data.local.entities.account

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    suspend fun getListAccount(): List<AccountEntity>

    @Query("SELECT * FROM account WHERE id = :accountId")
    suspend fun getAccountById(accountId: Int): AccountEntity

    @Insert
    suspend fun addAccount(accountEntity: AccountEntity)

    @Query("DELETE FROM account WHERE id = :accountId")
    suspend fun deleteAccountById(accountId: Int)

    @Update
    suspend fun updateAccount(accountEntity: AccountEntity)
} 