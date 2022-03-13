package com.example.biometric_auth.util

import java.security.KeyStore
import javax.crypto.SecretKey

class KeyStoreUtil {
    companion object {
        fun writeSecretKeyToStore(key: SecretKey) {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val secretKeyEntry : KeyStore.SecretKeyEntry = KeyStore.SecretKeyEntry(key)
            keyStore.setKeyEntry("Key", secretKeyEntry.secretKey.encoded, null)
        }
    }
}