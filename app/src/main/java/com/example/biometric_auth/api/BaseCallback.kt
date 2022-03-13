package com.example.biometric_auth.api

interface BaseCallback<T> {
    fun onSuccess(t : T?)
    fun onError(t : T?)
}