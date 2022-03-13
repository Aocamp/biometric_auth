package com.example.biometric_auth.main

import android.content.Context
import com.example.biometric_auth.util.SharedPrefUtil

interface MainContract {
    interface ViewContract {
        fun goToAuth()
        fun goToRegistration()
        fun connectionMissed()
    }

    interface PresenterContract {
        fun checkIfUserRegistered(sharedPref: SharedPrefUtil)
        fun connectSocket(context: Context)
        fun disconnectFromSocket()
        fun sendAction(action: String)
    }
}