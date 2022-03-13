package com.example.biometric_auth.model

data class Register(private val username : String,
                    private val password : String,
                    private val email : String,
                    private val biometric : Boolean)
