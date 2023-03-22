package com.rasyidin.moneyverse.di

import com.rasyidin.moneyverse.data.repository.account.AccountRepository
import com.rasyidin.moneyverse.data.repository.category.CategoryRepository
import com.rasyidin.moneyverse.domain.usecase.account.*
import com.rasyidin.moneyverse.domain.usecase.home.HomeUseCase
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
}