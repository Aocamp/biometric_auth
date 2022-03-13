package com.example.biometric_auth.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.biometric_auth.api.BaseCallback

interface AuthContract {
    interface ViewContract {
        fun openMainView()
        fun showError()
        fun showError(error: CharSequence)
        fun showBiometricAuthOption(show : Boolean)
        fun getUserData(): com.example.biometric_auth.model.UserData?
    }

    interface PresenterContract : BaseCallback<Any> {
        fun validateField(login : String, pass : String) : Boolean
        fun authUser(login : String, pass : String)
        fun checkForBiometric(context: Context, isUserChooseBiometricAuth : Boolean)
        fun showBiometricAuthPromt(activity: AppCompatActivity)
    }
}