package com.example.phinma_upang_marketplace

import LoginRequest
import LoginResponse
import LogoutResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginInterface {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("logout")
    fun logout(@Header("Authorization") authToken: String): Call<LogoutResponse>

}