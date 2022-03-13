package com.example.biometric_auth.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.biometric_auth.R
import com.example.biometric_auth.auth.AuthActivity
import com.example.biometric_auth.databinding.ActivityMainBinding
import com.example.biometric_auth.registration.RegistrationActivity
import com.example.biometric_auth.util.Constants
import com.example.biometric_auth.util.SharedPrefUtil


class MainActivity : AppCompatActivity(), MainContract.ViewContract {
    private lateinit var presenter: MainContract.PresenterContract
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MainPresenter(this)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        if (intent.getBooleanExtra(Constants.AUTH, false)) {
            setContentView(binding.root)
            presenter.connectSocket(this)
            binding.lock.setOnClickListener { presenter.sendAction("lock") }
            binding.unlock.setOnClickListener { presenter.sendAction("unlock") }
        } else {
            presenter.checkIfUserRegistered(SharedPrefUtil(this))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disconnectFromSocket()
    }

    override fun goToAuth() {
        startActivity(Intent(this, AuthActivity().javaClass))
        finish()
    }

    override fun goToRegistration() {
        startActivity(Intent(this, RegistrationActivity().javaClass))
        finish()
    }

    override fun connectionMissed() {
        Toast.makeText(this, R.string.connection_missed, Toast.LENGTH_SHORT).show()
    }
}