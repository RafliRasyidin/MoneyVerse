package com.rasyidin.moneyverse.data.repository.account

import com.rasyidin.moneyverse.data.local.entities.account.AccountEntity

interface AccountRepository {

    suspend fun getListAccount(): List<AccountEntity>
    suspend fun getAccountById(accountId: Int): AccountEntity
    suspend fun addAccount(accountEntity: AccountEntity)
    suspend fun deleteAccountById(accountId: Int)
    suspend fun updateAccount(accountEntity: AccountEntity)
    suspend fun getTotalSaldo(): Long
}