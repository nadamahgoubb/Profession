package com.example.profession.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import com.example.profession.MyApp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(DelicateCoroutinesApi::class)
@Singleton
class SessionManager @Inject constructor(val context: Context) {

    suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        MyApp.dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = MyApp.dataStore.data.first()
        return preferences[dataStoreKey]
    }

    fun setLang(value: String, coroutineScope: CoroutineScope = GlobalScope) {
        writePrefString(
            Constants.LANG,
            value,
            coroutineScope
        )

    }

     fun getlang(): String {
        var lang:String =""
        GlobalScope.launch {
            val dataStoreKey = preferencesKey<String>(Constants.LANG)
            val preferences = MyApp.dataStore.data.first()
            lang= preferences[dataStoreKey].toString()

        }
        return lang
    }
    suspend fun saveBoolean(key: String, value: Boolean) {
        val dataStoreKey = preferencesKey<Boolean>(key)
        MyApp.dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun readBoolean(key: String): Boolean? {
        val dataStoreKey = preferencesKey<Boolean>(key)
        val preferences = MyApp.dataStore.data.first()
        return preferences[dataStoreKey]
    }

}
fun writePrefString(
    key: String,
    value: String?,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        MyApp.dataStore.edit { settings ->
            if (value != null) {
                val dataStoreKey = preferencesKey<String>(Constants.LANG)
                MyApp.dataStore.edit { settings ->
                    settings[dataStoreKey] = value
                }
            }
        }
    }
    // save profile info

}

