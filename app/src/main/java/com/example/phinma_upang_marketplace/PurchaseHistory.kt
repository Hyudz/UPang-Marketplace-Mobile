package com.example.phinma_upang_marketplace

import HistoryResponse
import ProductsFetch
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PurchaseHistory : AppCompatActivity() {
    private val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
    //private val BASE_URL = "https://upmarketplace-com.preview-domain.com/public/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)
        val authToken = intent.getStringExtra("authToken")
        val usertype = intent.getStringExtra("usertype")
        if (usertype == "buyer") {
            underBuyer(usertype, authToken!!)
        } else {
            underSeller(usertype!!, authToken!!)
        }
    }

    fun underBuyer(usertype: String, authToken: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitData = retrofit.create(ItemsInterface::class.java)
        val service = retrofitData.getHistory(authToken)
        val adapter = HistoryAdapter(this@PurchaseHistory, R.layout.item_history, mutableListOf(), authToken, usertype!!)
        service.enqueue(object : Callback<List<HistoryResponse>> {
            override fun onResponse(call: Call<List<HistoryResponse>>, response: Response<List<HistoryResponse>>) {
                if (response.isSuccessful) {
                    adapter.clearData()
                    adapter.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                    Log.d("PurchaseHistory", "Response Body: ${response.body()}")
                } else {
                    Toast.makeText(applicationContext, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<HistoryResponse>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to fetch Liked Items", Toast.LENGTH_SHORT).show()
            }
        })

        val listView: ListView = findViewById(R.id.purchase_history)
        listView.adapter = adapter

    }

    fun underSeller(usertype: String, authToken: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitData = retrofit.create(ItemsInterface::class.java)
        val service = retrofitData.getSellerHistory(authToken)
        val adapter = HistoryAdapter2(this@PurchaseHistory, R.layout.item_history, mutableListOf(), authToken, usertype)
        service.enqueue(object : Callback<List<ProductsFetch>> {
            override fun onResponse(call: Call<List<ProductsFetch>>, response: Response<List<ProductsFetch>>) {
                if (response.isSuccessful) {
                    adapter.clearData()
                    adapter.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                    Log.d("PurchaseHistory", "Response Body: ${response.body()}")
                } else {
                    Toast.makeText(applicationContext, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ProductsFetch>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to fetch Liked Items", Toast.LENGTH_SHORT).show()
            }
        })

        val listView: ListView = findViewById(R.id.purchase_history)
        listView.adapter = adapter

    }
}