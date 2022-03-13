package com.example.biometric_auth.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiService {
    private const val BASE_URL = "http://sapsun322.pythonanywhere.com/"
    private var service: Request? = null

    @Synchronized
    fun getInstance() : Request {
        if (service == null) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                    .setLenient()
                    .create()))
                .build()
            service = retrofit.create(Request::class.java)
        }
        return service!!
    }

}