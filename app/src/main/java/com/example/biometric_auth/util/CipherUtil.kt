package com.example.biometric_auth.util

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.example.biometric_auth.model.EncryptData
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidParameterSpecException
import javax.crypto.*
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec


class CipherUtil {
    companion object {
        private const val TRANSFORMATION : String = "AES/GCM/NoPadding"
        private const val ANDROID_KEY_STORE : String = "AndroidKeyStore"


        fun generateKey(alias: String) : SecretKey {
            val keyGenerator: KeyGenerator = KeyGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)

            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    "alias",
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
            return keyGenerator.generateKey()
        }

        fun getKey(alias: String) : SecretKey {
            val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
            keyStore.load(null)
            return (keyStore.getEntry("alias", null) as KeyStore.SecretKeyEntry).secretKey
        }

        @Throws(
            NoSuchAlgorithmException::class,
            NoSuchPaddingException::class,
            InvalidKeyException::class,
            InvalidParameterSpecException::class,
            IllegalBlockSizeException::class,
            BadPaddingException::class,
            UnsupportedEncodingException::class
        )
        fun encrypt(message: String, secret: SecretKey?): EncryptData {
            var cipher: Cipher? = null
            cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, secret)
            return EncryptData(cipher.iv, cipher.doFinal(message.toByteArray(StandardCharsets.UTF_8)))
        }

        @Throws(
            NoSuchPaddingException::class,
            NoSuchAlgorithmException::class,
            InvalidParameterSpecException::class,
            InvalidAlgorithmParameterException::class,
            InvalidKeyException::class,
            BadPaddingException::class,
            IllegalBlockSizeException::class,
            UnsupportedEncodingException::class
        )
        fun decrypt(cipherText: ByteArray?, secret: SecretKey?, iv: ByteArray): String {
            var cipher: Cipher? = null
            cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, secret, GCMParameterSpec(128, iv))
            return String(cipher.doFinal(cipherText), StandardCharsets.UTF_8)
        }
    }

}