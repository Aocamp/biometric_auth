package com.example.biometric_auth.registration

import android.text.TextUtils
import com.example.biometric_auth.api.ApiService
import com.example.biometric_auth.api.BaseCallback
import com.example.biometric_auth.api.RequestConstants
import com.example.biometric_auth.api.ResponseHandler
import com.example.biometric_auth.model.Auth
import com.example.biometric_auth.model.Register
import com.example.biometric_auth.util.CipherUtil

class RegistrationPresenter(private val view: RegistrationContract.ViewContract) :
    RegistrationContract.PresenterContract {
    private lateinit var login: String
    private lateinit var pass: String

    override fun validateField(login: String, pass: String, email: String): Boolean {
        return !TextUtils.isEmpty(login) && !TextUtils.isEmpty(login) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                !TextUtils.isEmpty(login)
    }

    override fun registrateUser(login: String, pass: String, email: String, biometric: Boolean) {
        if (validateField(login, pass, email)) {
            this.login = login
            this.pass = pass
            ApiService.getInstance().postRequest<Any>(
                RequestConstants.REGISTER,
                Register(login, pass, email, biometric)
            ).enqueue(ResponseHandler(object : BaseCallback<Any> {
                override fun onSuccess(t: Any?) {
                    val alias = login + "_app_alias"
                    val key = CipherUtil.generateKey(alias)
                    val encrypted = CipherUtil.encrypt(pass, key)
                    view.saveRegistrationData(login, email, biometric, alias, encrypted.message, encrypted.iv)
                    authUser(login, pass)
                }

                override fun onError(t: Any?) {
                    view.showError()
                }
            }))
        }
    }

    override fun authUser(login: String, pass: String) {
        ApiService.getInstance().postRequest<Any>(
            RequestConstants.AUTH,
            Auth(login, pass)
        ).enqueue(ResponseHandler(object : BaseCallback<Any> {
            override fun onSuccess(t: Any?) {
                view.openMainView()
            }

            override fun onError(t: Any?) {
                view.showError()
            }
        }))
    }
}