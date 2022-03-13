package com.example.biometric_auth.auth

import android.content.Context
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.example.biometric_auth.api.ApiService
import com.example.biometric_auth.api.RequestConstants
import com.example.biometric_auth.api.ResponseHandler
import com.example.biometric_auth.model.Auth
import com.example.biometric_auth.util.CipherUtil
import com.example.biometric_auth.util.UserDataUtil
import com.example.biometric_auth.util.biometric.BiometricUtil

class AuthPresenter (private val view : AuthContract.ViewContract) : AuthContract.PresenterContract,
    BiometricPrompt.AuthenticationCallback() {

    override fun validateField(login: String, pass: String): Boolean {
        return !TextUtils.isEmpty(login) && !TextUtils.isEmpty(login)
    }

    override fun authUser(login: String, pass: String) {
        if (validateField(login, pass)) {
            ApiService.getInstance()
                .postRequest(RequestConstants.AUTH, Auth(login, pass))
                .enqueue(ResponseHandler(this))
        }
    }

    override fun checkForBiometric(context: Context, isUserChooseBiometricAuth: Boolean) {
        if (isUserChooseBiometricAuth) {
            view.showBiometricAuthOption(isBiometricAvailable(context))
        } else {
            view.showBiometricAuthOption(false)
        }
    }

    override fun showBiometricAuthPromt(activity: AppCompatActivity) {
        BiometricUtil.showBiometricPrompt(
            activity = activity,
            listener = this,
            cryptoObject = null,
            allowDeviceCredential = true
        )
    }

    private fun isBiometricAvailable(context: Context): Boolean {
        return BiometricUtil.isBiometricAvailable(context)
    }

    override fun onSuccess(t: Any?) {
        view.openMainView()
    }

    override fun onError(t: Any?) {
        view.showError()
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)
        view.showError(errString)
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        val userData = view.getUserData()
        val pass = CipherUtil.decrypt(userData!!.pass, CipherUtil.getKey(userData.alias), userData.iv)
        authUser(userData.username, pass)
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        view.showError()
    }
}