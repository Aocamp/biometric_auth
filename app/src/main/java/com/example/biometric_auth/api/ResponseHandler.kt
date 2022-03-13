package com.example.biometric_auth.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResponseHandler<T> (private val callback : BaseCallback<T>) : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            callback.onSuccess(response.body())
        } else{
            callback.onError(response.body())
        }
        if (call.isExecuted && !call.isCanceled) {
            call.cancel()
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        callback.onError(null)
        if (call.isExecuted && !call.isCanceled) {
            call.cancel()
        }
    }

}