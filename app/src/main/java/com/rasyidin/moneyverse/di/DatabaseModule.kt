package com.rasyidin.moneyverse.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rasyidin.moneyverse.data.local.db.MoneyVerseDb
import com.rasyidin.moneyverse.data.local.entities.account.AccountDao
import com.rasyidin.moneyverse.data.local.entities.category.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    private val coroutineScope = CoroutineScope(SupervisorJob())

    private suspend fun populateDatabase(
        context: Context,
        accountProvider: Provider<AccountDao>,
        categoryProvider: Provider<CategoryDao>
    ) {
        withContext(Dispatchers.IO) {
            val accountDao = accountProvider.get()
            accountDao.upsertAccount(MoneyVerseDb.initDefaultAccount(context).first())

            val categoryDao = categoryProvider.get()
            categoryDao.addCategories(MoneyVerseDb.initCategories(context))
        }

    }

    @Provides
    @Singleton
    fun providesMoneyVerseDatabase(
        @ApplicationContext context: Context,
        accountProvider: Provider<AccountDao>,
        categoryProvider: Provider<CategoryDao>
    ) = Room.databaseBuilder(
        context,
        MoneyVerseDb::class.java,
        MoneyVerseDb.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                coroutineScope.launch {
                    populateDatabase(context, accountProvider, categoryProvider)
                }
            }
        })
        .build()

    @Provides
    @Singleton
    fun providesAccountDao(db: MoneyVerseDb) = db.accountDao()

    @Provides
    @Singleton
    fun providesCategoryDao(db: MoneyVerseDb) = db.categoryDao()

    @Provides
    @Singleton
    fun providesTransactionDao(db: MoneyVerseDb) = db.transactionDao()
}