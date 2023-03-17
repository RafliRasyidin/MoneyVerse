package com.rasyidin.moneyverse

import android.app.Application
import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.stetho.Stetho
import com.pixplicity.easyprefs.library.Prefs
import com.rasyidin.moneyverse.data.local.db.MoneyVerseDb
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MoneyVerseApp : Application() {

    @Inject lateinit var networkPlugin: NetworkFlipperPlugin

    @Inject lateinit var inspectorPlugin: InspectorFlipperPlugin

    @Inject lateinit var databasePlugin: DatabasesFlipperPlugin

    @Inject lateinit var sharedPrefPlugin: SharedPreferencesFlipperPlugin

    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        if (appContext == null) {
            appContext = this
        }

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)

            SoLoader.init(this, false)
            val flipper = AndroidFlipperClient.getInstance(this)
            flipper.apply {
                addPlugin(networkPlugin)
                addPlugin(inspectorPlugin)
                addPlugin(databasePlugin)
                addPlugin(sharedPrefPlugin)
            }
            flipper.start()
        }

        Prefs.Builder()
            .setContext(this)
            .setMode(Context.MODE_PRIVATE)
            .setPrefsName(MoneyVerseDb.PREF_NAME)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}