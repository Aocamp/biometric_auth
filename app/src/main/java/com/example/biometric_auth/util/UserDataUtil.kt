package com.example.biometric_auth.util

import android.content.Context
import android.text.TextUtils
import com.example.biometric_auth.model.UserData
import com.google.gson.Gson

class UserDataUtil {
    companion object {
        fun getUserData(context: Context): UserData? {
            val sharedPref = SharedPrefUtil(context)
            val userName = sharedPref.getStringValue(Constants.USER_NAME)
            val alias = sharedPref.getStringValue(Constants.ALIAS)
            val secret = Gson().fromJson(sharedPref.getStringValue(Constants.SECRET), ByteArray::class.java)
            val iv = Gson().fromJson(sharedPref.getStringValue(Constants.IV), ByteArray::class.java)
            return if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(alias)) {
                null
            } else {
                UserData(
                    userName!!,
                    alias!!,
                    secret!!,
                    iv!!
                )
            }
        }
    }
}