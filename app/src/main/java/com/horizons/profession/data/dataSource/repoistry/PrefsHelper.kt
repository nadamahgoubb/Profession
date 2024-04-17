package com.horizons.profession.data.dataSource.repoistry

import android.content.Context
import android.content.SharedPreferences
import com.horizons.profession.data.dataSource.response.UserResponse
import com.horizons.profession.util.Constants
import com.google.gson.Gson

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
            return preferences.getString(Constants.LANG, Constants.AR).toString()
        }

    fun saveToken(token: String?) {
        preferences.edit().putString(Constants.TOKEN, token).apply()

    }
    fun getToken(): String {
        return preferences.getString(Constants.TOKEN, "").toString()
    }
    fun setloggedInBefore(logged: Boolean) {
        preferences.edit().putBoolean(Constants.LOGGED_IN, logged).apply()

    }
    fun getIsloggedInBefore(): Boolean {
        return preferences.getBoolean(Constants.LOGGED_IN,false)
    }


        fun saveUserData(user:UserResponse?){
            //set variables of 'myObject', etc.

            var prefsEditor = preferences.edit()
            var gson =  Gson()
            //  String
            var  json = gson.toJson(user)
            prefsEditor.putString(Constants.USER, json)
            prefsEditor.commit()
        }
        fun getUserData():UserResponse?{
            //set variables of 'myObject', etc.

            val gson = Gson()
            val json: String? = preferences.getString(Constants.USER, "")
            return gson.fromJson(json, UserResponse::class.java)
    }
    fun clear() {
preferences.edit().clear()
    saveUserData(null)
saveToken("")    }
    fun setFCMToken(token: String) {
        preferences.edit().putString(Constants.FCM_TOKEN, token).apply()
    }
    fun getFcmToken(): String {
        return preferences.getString(Constants.FCM_TOKEN,"").toString()
    }
}
