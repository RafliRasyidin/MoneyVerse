package com.rasyidin.moneyverse.data.repository.account

import com.rasyidin.moneyverse.data.local.entities.account.AccountDao
import com.rasyidin.moneyverse.data.local.entities.account.AccountEntity
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val accountDao: AccountDao) : AccountRepository {
    override suspend fun getListAccount(): List<AccountEntity> {
        return accountDao.getListAccount()
    }

    override suspend fun getAccountById(accountId: Int): AccountEntity {
        return accountDao.getAccountById(accountId)
    }

    override suspend fun addAccount(accountEntity: AccountEntity) {
        accountDao.addAccount(accountEntity)
    }

    override suspend fun deleteAccountById(accountId: Int) {
        accountDao.deleteAccountById(accountId)
    }

    override suspend fun updateAccount(accountEntity: AccountEntity) {
        accountDao.updateAccount(accountEntity)
    }

    override suspend fun getTotalSaldo(): Long {
        return accountDao.getTotalSaldo()
    }
}