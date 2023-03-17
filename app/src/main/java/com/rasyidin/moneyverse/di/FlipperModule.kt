package com.rasyidin.moneyverse.di

import android.content.Context
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.rasyidin.moneyverse.data.local.db.MoneyVerseDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FlipperModule {

    @Provides
    @Singleton
    fun providesNetworkPlugin() = NetworkFlipperPlugin()

    @Provides
    @Singleton
    fun providesInspectorPlugin(@ApplicationContext context: Context) =
        InspectorFlipperPlugin(context, DescriptorMapping.withDefaults())

    @Provides
    @Singleton
    fun providesDatabasePlugin(@ApplicationContext context: Context) =
        DatabasesFlipperPlugin(context)

    @Provides
    @Singleton
    fun providesSharedPreferencesPlugin(@ApplicationContext context: Context) =
        SharedPreferencesFlipperPlugin(context, MoneyVerseDb.PREF_NAME)
}