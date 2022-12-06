package com.example.nadifalaundries.data.repositoy

import android.content.Context
import android.content.SharedPreferences
import com.example.profession.util.Constants

object PrefsHelper {



        private lateinit var preferences: SharedPreferences
        private const val PREFS_NAME = "shared_prefs"

        fun init(context: Context){
            preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }

        fun setLanguage(language: String) {
            preferences.edit().putString(Constants.LANG, language).apply()
        }

        fun getLanguage(): String {
            return preferences.getString(Constants.LANG, Constants.EN).toString()
        }


    fun saveLaundryToken(token: String?) {
        preferences.edit().putString(Constants.TOKEN, token).apply()

    }
    fun getLaundryToken(): String {
        return preferences.getString(Constants.TOKEN, "").toString()
    }
    fun saveBranchToken(token: String?) {
        preferences.edit().putString(Constants.TOKEN, token).apply()

    }
    fun getBranchToken(): String {
        return preferences.getString(Constants.TOKEN, "").toString()
    }

    fun clear() {
preferences.edit().clear()   }

}
