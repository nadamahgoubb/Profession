package com.example.profession.data

import android.content.Context
import android.content.SharedPreferences
import com.example.profession.util.Constants

object PrefsHelper {


    private lateinit var preferences: SharedPreferences
    private const val PREFS_NAME = "shared_prefs"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String?) {
        preferences.edit().putString(Constants.TOKEN, token).apply()

    }

    fun setLanguage(language: String) {
        preferences.edit().putString(Constants.LANG, language).apply()
    }

    fun getLanguage(): String {
        return preferences.getString(Constants.LANG, Constants.EN).toString()
    }
    fun getToken(): String {
        return preferences.getString(Constants.TOKEN, "").toString()
    }

    fun saveName(name: String?) {
        preferences.edit().putString(Constants.NAME, name).apply()

    }

    fun getName(): String {
        return preferences.getString(Constants.NAME, "").toString()
    }


    fun setStatus(status: String) {
        preferences.edit().putString(Constants.STATUS, status).apply()
    }

    fun getStatus(): String {
        return preferences.getString(Constants.STATUS, Constants.ONLINE).toString()
    }

    fun setLoggedBefore(logged: Boolean) {
        preferences.edit().putBoolean(Constants.IS_LOGGED_BEFORE, logged).apply()
    }

    fun isLoggedBefore(): Boolean {
        return preferences.getBoolean(Constants.IS_LOGGED_BEFORE, false)
    }



    fun clear( ) {
        preferences.edit().clear()
        preferences.edit().putString(Constants.TOKEN, "").apply()
        preferences.edit().putString(Constants.PHONE, "").apply()
        preferences.edit().putString(Constants.STATUS, Constants.ONLINE).toString()
        preferences.edit().putString(Constants.NAME, "").apply()

    }
    fun savePhone(phone: String) {
        preferences.edit().putString(Constants.PHONE, phone).apply()
    }
    fun getPhone(): String {
        return preferences.getString(Constants.PHONE,"").toString()
    }


}