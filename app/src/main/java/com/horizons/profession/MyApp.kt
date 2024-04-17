package com.horizons.profession

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.horizons.profession.data.dataSource.repoistry.PrefsHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    companion object CompanionObject {

        lateinit var dataStore: DataStore<Preferences>
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        dataStore = createDataStore(name = "settings")
        PrefsHelper.init(applicationContext)


    }

    /*override fun attachBaseContext(newBase: Context) {
        val locale = Locale(PrefsHelper.getLanguage())
        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, locale)
        super.attachBaseContext(localeUpdatedContext)
    }*/
}