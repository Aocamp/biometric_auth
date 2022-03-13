package com.example.biometric_auth.main

import android.content.Context
import android.util.Log
import com.example.biometric_auth.api.socket.SocketHandler
import com.example.biometric_auth.model.UserAction
import com.example.biometric_auth.util.CipherUtil
import com.example.biometric_auth.util.Constants
import com.example.biometric_auth.util.SharedPrefUtil
import com.example.biometric_auth.util.UserDataUtil
import com.google.gson.Gson

class MainPresenter(private val view: MainContract.ViewContract) : MainContract.PresenterContract {
    private lateinit var login: String
    private lateinit var pass: String
    override fun checkIfUserRegistered(sharedPref: SharedPrefUtil) {
        val isUserRegistered = sharedPref.getBooleanValue(Constants.USER_REGISTERED)
        if (isUserRegistered) {
            view.goToAuth()
        } else{
            view.goToRegistration()
        }
    }

    override fun connectSocket(context: Context) {
        val userData = UserDataUtil.getUserData(context)
        pass = CipherUtil.decrypt(userData!!.pass, CipherUtil.getKey(userData.alias), userData.iv)
        login = userData.username
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
    }

    override fun disconnectFromSocket() {
        SocketHandler.closeConnection()
    }

    override fun sendAction(action: String) {
        val userAction = UserAction(login, pass, action)
        if (SocketHandler.getSocket()!!.connected()) {
            SocketHandler.getSocket()!!.emit("message", Gson().toJson(userAction))
        } else {
            view.connectionMissed()
        }
    }

}