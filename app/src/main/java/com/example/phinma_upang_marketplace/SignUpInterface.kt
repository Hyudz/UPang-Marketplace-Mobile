package com.example.phinma_upang_marketplace

import SignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpInterface {
    @POST("register")
    fun signup(@Body signupRequest: SignUpRequest): Call<SignUpRequest>
}