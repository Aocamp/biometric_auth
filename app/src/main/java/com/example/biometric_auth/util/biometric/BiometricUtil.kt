package com.example.biometric_auth.util.biometric

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

class BiometricUtil {
    companion object {
        fun isBiometricAvailable(context: Context): Boolean {
            val biometricManager = BiometricManager.from(context)
            return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
        }

        private fun setBiometricPromptInfo(
            title: String,
            subtitle: String,
            description: String,
            allowDeviceCredential: Boolean
        ): BiometricPrompt.PromptInfo {
            val builder = BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setDescription(description)

            // Use Device Credentials if allowed, otherwise show Cancel Button
            builder.apply {
                if (allowDeviceCredential) setDeviceCredentialAllowed(true)
                else setNegativeButtonText("Отмена")
            }

            return builder.build()
        }

        private fun initBiometricPrompt(
            activity: AppCompatActivity,
            listener: BiometricPrompt.AuthenticationCallback
        ): BiometricPrompt {
            val executor = ContextCompat.getMainExecutor(activity)
            return BiometricPrompt(activity, executor, listener)
        }

        fun showBiometricPrompt(
            title: String = "Аутентификация с помощью биометрии",
            subtitle: String = "Для продолжения подтвердите биометрические данные",
            description: String = "Введите свой отпечаток пальца или отсканируйте лицо, чтобы убедиться, что это вы!",
            activity: AppCompatActivity,
            listener: BiometricPrompt.AuthenticationCallback,
            cryptoObject: BiometricPrompt.CryptoObject? = null,
            allowDeviceCredential: Boolean = false
        ) {
            val promptInfo = setBiometricPromptInfo(
                title,
                subtitle,
                description,
                allowDeviceCredential
            )

            val biometricPrompt = initBiometricPrompt(activity, listener)

            biometricPrompt.apply {
                if (cryptoObject == null) authenticate(promptInfo)
                else authenticate(promptInfo, cryptoObject)
            }
        }
    }
}