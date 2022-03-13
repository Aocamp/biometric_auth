package com.example.biometric_auth.registration

interface RegistrationContract {
    interface ViewContract {
        fun openMainView()
        fun showError()
        fun saveRegistrationData(
            login: String,
            email: String,
            biometric: Boolean,
            alias: String,
            encryptedPass: ByteArray?,
            iv: ByteArray?
        )
    }

    interface PresenterContract {
        fun validateField(login: String, pass: String, email: String): Boolean
        fun registrateUser(login: String, pass: String, email: String, biometric: Boolean)
        fun authUser(login: String, pass: String)
    }
}