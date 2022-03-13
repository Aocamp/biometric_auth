package com.example.biometric_auth.registration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.biometric_auth.R
import com.example.biometric_auth.databinding.ActivityRegistrationBinding
import com.example.biometric_auth.main.MainActivity
import com.example.biometric_auth.util.Constants
import com.example.biometric_auth.util.SharedPrefUtil
import com.example.biometric_auth.util.biometric.BiometricUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson

class RegistrationActivity : AppCompatActivity(), RegistrationContract.ViewContract {
    private lateinit var presenter: RegistrationContract.PresenterContract
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        presenter = RegistrationPresenter(this)
        binding.register.setOnClickListener {
            if (presenter.validateField(
                    binding.login.text.toString(),
                    binding.pass.text.toString(),
                    binding.email.text.toString()
                )
            ) {
                if (BiometricUtil.isBiometricAvailable(this)) {
                    MaterialAlertDialogBuilder(this)
                        .setMessage("Использовать биометрию при авторизации?")
                        .setPositiveButton("Да") { dialog, which ->
                            registerUser(true)
                            dialog.dismiss()
                        }
                        .setNegativeButton("Нет") { dialog, which ->
                            registerUser(false)
                            dialog.dismiss()
                        }
                        .setCancelable(false)
                        .create().show()

                } else {
                    registerUser(false)
                }
            }
        }
    }

    private fun registerUser(useBiometric: Boolean) {
        presenter.registrateUser(
            binding.login.text.toString(),
            binding.pass.text.toString(),
            binding.email.text.toString(),
            useBiometric
        )
    }

    override fun openMainView() {
        startActivity(Intent(this, MainActivity().javaClass).putExtra(Constants.AUTH, true))
        finish()
    }

    override fun showError() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun saveRegistrationData(
        login: String,
        email: String,
        biometric: Boolean,
        alias: String,
        encryptedPass: ByteArray?,
        iv: ByteArray?
    ) {
        val sharedPref = SharedPrefUtil(this)
        sharedPref.saveValue(Constants.USER_REGISTERED, true)
        sharedPref.saveValue(Constants.USE_BIOMETRIC, biometric)
        sharedPref.saveValue(Constants.USER_NAME, login)
        sharedPref.saveValue(Constants.ALIAS, alias)
        sharedPref.saveValue(Constants.SECRET, Gson().toJson(encryptedPass!!))
        sharedPref.saveValue(Constants.IV, Gson().toJson(iv!!))
    }
}