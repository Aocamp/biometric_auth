package com.example.biometric_auth.util

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class SharedPrefUtil (val context: Context) {
    private val sharedPref : SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF,
        AppCompatActivity.MODE_PRIVATE
    )

    fun saveValue(key: String, value: Boolean) {
        sharedPref.edit()
            .putBoolean(key, value)
            .apply()
    }

    fun saveValue(key: String, value: String) {
        sharedPref.edit()
            .putString(key, value)
            .apply()
    }

    fun getStringValue(key: String) : String? {
        return sharedPref.getString(key, null)
    }

    fun getBooleanValue(key: String) : Boolean {
        return sharedPref.getBoolean(key, false)
    }
}