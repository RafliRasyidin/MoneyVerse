package com.rasyidin.moneyverse.di

import com.rasyidin.moneyverse.data.repository.account.AccountRepository
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
    fun providesAccountUseCase(accountRepo: AccountRepository): AccountUseCase = AccountUseCase(
        addAccount = AddAccount(accountRepo),
        deleteAccount = DeleteAccount(accountRepo),
        getListAccount = GetListAccount(accountRepo),
        getTotalSaldo = GetTotalSaldo(accountRepo),
        updateAccount = UpdateAccount(accountRepo)
    )

    @Provides
    fun providesHomeUseCase(accountRepo: AccountRepository): HomeUseCase = HomeUseCase(
        getTotalSaldo = GetTotalSaldo(accountRepo)
    )
}