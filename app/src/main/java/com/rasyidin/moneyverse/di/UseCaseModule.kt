package com.rasyidin.moneyverse.di

import com.rasyidin.moneyverse.data.repository.account.AccountRepository
import com.rasyidin.moneyverse.data.repository.category.CategoryRepository
import com.rasyidin.moneyverse.data.repository.transaction.TransactionRepository
import com.rasyidin.moneyverse.domain.usecase.account.*
import com.rasyidin.moneyverse.domain.usecase.home.HomeUseCase
import com.rasyidin.moneyverse.domain.usecase.transaction.AddTransaction
import com.rasyidin.moneyverse.domain.usecase.transaction.income.GetIncomeCategories
import com.rasyidin.moneyverse.domain.usecase.transaction.income.IncomeUseCase
import com.rasyidin.moneyverse.domain.usecase.transaction.outcome.GetOutcomeCategories
import com.rasyidin.moneyverse.domain.usecase.transaction.outcome.OutcomeUseCase
import com.rasyidin.moneyverse.domain.usecase.transaction.tramsfer.TransferUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun providesAccountUseCase(
        accountRepo: AccountRepository,
        categoryRepo: CategoryRepository
    ): AccountUseCase = AccountUseCase(
        upsertAccount = UpsertAccount(accountRepo),
        deleteAccount = DeleteAccount(accountRepo),
        getListAccount = GetListAccount(accountRepo),
        getTotalSaldo = GetTotalSaldo(accountRepo),
        updateAccount = UpdateAccount(accountRepo),
        getDetailAccount = GetDetailAccount(accountRepo),
        getAccountCategories = GetAccountCategories(categoryRepo)
    )

    @Provides
    fun providesHomeUseCase(accountRepo: AccountRepository): HomeUseCase = HomeUseCase(
        getTotalSaldo = GetTotalSaldo(accountRepo)
    )

    @Provides
    fun providesOutcomeUseCase(
        transactionRepo: TransactionRepository,
        categoryRepo: CategoryRepository,
        accountRepo: AccountRepository
    ): OutcomeUseCase = OutcomeUseCase(
        addTransaction = AddTransaction(transactionRepo),
        getListAccount = com.rasyidin.moneyverse.domain.usecase.transaction.GetListAccount(accountRepo),
        getOutcomeCategories = GetOutcomeCategories(categoryRepo)
    )

    @Provides
    fun providesIncomeUseCase(
        transactionRepo: TransactionRepository,
        categoryRepo: CategoryRepository,
        accountRepo: AccountRepository
    ): IncomeUseCase = IncomeUseCase(
        addTransaction = AddTransaction(transactionRepo),
        getIncomeCategories = GetIncomeCategories(categoryRepo),
        getListAccount = com.rasyidin.moneyverse.domain.usecase.transaction.GetListAccount(accountRepo)
    )

    @Provides
    fun providesTransferUseCase(
        transactionRepo: TransactionRepository,
        accountRepo: AccountRepository
    ): TransferUseCase = TransferUseCase(
        addTransaction = AddTransaction(transactionRepo),
        getListAccount = com.rasyidin.moneyverse.domain.usecase.transaction.GetListAccount(accountRepo)
    )
}