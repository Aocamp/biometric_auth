package com.example.biometric_auth.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Request {
    @POST("{end_point}")
    fun postRequest(@Path("end_point") endPoint : String, @Body body : Any) : Call<Any>

    @GET("{end_point}")
    fun getRequest(@Path("end_point") endPoint : String) : Call<Any>
}