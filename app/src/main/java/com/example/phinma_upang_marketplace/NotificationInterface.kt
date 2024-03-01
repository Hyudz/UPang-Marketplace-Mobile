package com.example.phinma_upang_marketplace

import NotificationGet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NotificationInterface {

    @GET("notifications")
    fun getNotifications(@Header("Authorization") authToken: String): Call<List<NotificationGet>>
}