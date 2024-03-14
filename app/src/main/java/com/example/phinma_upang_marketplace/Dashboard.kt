package com.example.phinma_upang_marketplace

import Product
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Dashboard : AppCompatActivity() {
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        //Toast.makeText(this, "Welcome to Dashboard", Toast.LENGTH_SHORT).show()

        val authToken = intent.getStringExtra("authToken")

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitData = retrofit.create(ItemsInterface::class.java)
        val service = retrofitData.getSellerProducts(authToken!!)
        val adapter = SellerProductsAdapter(this@Dashboard, R.layout.item_cart, mutableListOf(), authToken, "", "")
        service.enqueue(object : retrofit2.Callback<List<Product>> {
            override fun onResponse(call: retrofit2.Call<List<Product>>, response: retrofit2.Response<List<Product>>) {
                if (response.isSuccessful) {
                    adapter.clearData()
                    adapter.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Response Body: ${response.body()}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Product>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to fetch Liked Items", Toast.LENGTH_SHORT).show()
            }


        })

        val listView: ListView = findViewById(R.id.seller_products)
        listView.adapter = adapter


    }
}