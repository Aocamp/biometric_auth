package com.example.biometric_auth.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.biometric_auth.R
import com.example.biometric_auth.databinding.ActivityAuthBinding
import com.example.biometric_auth.main.MainActivity
import com.example.biometric_auth.model.UserData
import com.example.biometric_auth.util.Constants
import com.example.biometric_auth.util.SharedPrefUtil
import com.example.biometric_auth.util.UserDataUtil
import com.google.gson.Gson

class AuthActivity : AppCompatActivity(), AuthContract.ViewContract {
    private var binding: ActivityAuthBinding? = null
    private var presenter: AuthContract.PresenterContract? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(LayoutInflater.from(this))
        setContentView(binding!!.root)
        presenter = AuthPresenter(this)
        binding!!.auth.setOnClickListener {
            presenter!!.authUser(
                binding!!.login.text.toString(),
                binding!!.pass.text.toString()
            )
        }

        binding!!.authWithBiometric.setOnClickListener {
            presenter!!.showBiometricAuthPromt(this)
        }

        presenter!!.checkForBiometric(
            this,
            SharedPrefUtil(this).getBooleanValue(Constants.USE_BIOMETRIC)
        )
    }

    override fun openMainView() {
        startActivity(Intent(this, MainActivity().javaClass).putExtra(Constants.AUTH, true))
        finish()
    }

    override fun showError() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: CharSequence) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showBiometricAuthOption(show: Boolean) {
        binding!!.biometricLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun getUserData(): UserData? {
        return UserDataUtil.getUserData(this)
    }
}