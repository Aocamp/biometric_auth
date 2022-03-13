package com.example.biometric_auth.model

data class EncryptData (val iv: ByteArray, val message: ByteArray) {
}