package com.example.phinma_upang_marketplace

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

class CartActivity : AppCompatActivity() {
    private val BASE_URL = "https://upmarketplace-com.preview-domain.com/public/api/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // create a retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authToken = intent.getStringExtra("authToken")

        // ANG ALAM KO INTERFACE TO EG
        val retrofitData = retrofit.create(ItemsInterface::class.java)
        val service = retrofitData.getCart(authToken!!)
        val adapter = CartAdapter(this, R.layout.item_cart,  mutableListOf())

        service.enqueue(object : Callback<List<ProductsFetch>?> {
            override fun onResponse(
                call: Call<List<ProductsFetch>?>,
                response: Response<List<ProductsFetch>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    Log.d("CartActivity", "Response: $responseBody")
                    adapter.clearData()
                    adapter.addAll(responseBody)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                    Log.d("ProductItem", "Unsuccessful response: ${response.code()}")
                    Log.d("ProductItem", "Unsuccessful response: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<ProductsFetch>?>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Failed to fetch Liked Items",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("ProductItem", "onFailure" + t.message)
            }
        })

        val listView: ListView = findViewById(R.id.cart_item)

        listView.adapter = adapter
    }
}
