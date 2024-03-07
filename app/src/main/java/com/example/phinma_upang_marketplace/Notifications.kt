package com.example.phinma_upang_marketplace

import NotificationGet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Notifications : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: NotificationAdapter? = null
    private var notification = mutableListOf<NotificationGet>()
    private val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        val authToken = intent.getStringExtra("authToken")
        recyclerView = findViewById<View>(R.id.list_item) as RecyclerView
        recyclerView!!.layoutManager = GridLayoutManager(this, 1)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val notificationInterface = retrofit.create(NotificationInterface::class.java)
        val retrofitData = notificationInterface.getNotifications(authToken!!)

        retrofitData.enqueue(object : Callback<List<NotificationGet>?> {
            override fun onResponse(
                call: Call<List<NotificationGet>?>,
                response: Response<List<NotificationGet>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    notification.addAll(responseBody)
                    adapter = NotificationAdapter(this@Notifications, notification, authToken!!)
                    recyclerView!!.adapter = adapter
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    Log.d("ProductItem", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<NotificationGet>?>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Failed to fetch products",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("ProductItem", "onFailure" + t.message)
            }
        })
    }
}
