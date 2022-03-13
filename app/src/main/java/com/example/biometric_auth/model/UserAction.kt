package com.example.biometric_auth.model

data class UserAction(private val username : String,
                      private val password : String,
                      private val action: String)
