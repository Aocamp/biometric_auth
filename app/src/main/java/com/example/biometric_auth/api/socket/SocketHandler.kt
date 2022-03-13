package com.example.biometric_auth.api.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {
    var mSocket: Socket? = null

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket("http://172.20.10.12:8080")
        } catch (e: URISyntaxException) {
            Log.e("TAG", "setSocket: ", e.cause)
        }
    }

    @Synchronized
    fun getSocket(): Socket? {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket?.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket?.disconnect()
    }
}