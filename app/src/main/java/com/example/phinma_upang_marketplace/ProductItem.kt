package com.example.phinma_upang_marketplace

import ProductsFetch
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductItem : AppCompatActivity() {
    //THIS FILE CONTAINS THE CODE FOR THE PRODUCT ITEM PAGE
    //IT GETS THE PRODUCT DATA FROM THE API AND DISPLAYS IT IN A RECYCLER VIEW
    private val BASE_URL = "https://upmarketplace-com.preview-domain.com/public/api/"
    private var recyclerView: RecyclerView? = null
    private var adapter: ProductsAdapter? = null
    private var products = mutableListOf<ProductsFetch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_item)
        val authToken = intent.getStringExtra("authToken")
        recyclerView = findViewById<View>(R.id.productList) as RecyclerView
        recyclerView!!.layoutManager = GridLayoutManager(this, 2)
        adapter = ProductsAdapter(this, products, authToken!!)
        recyclerView!!.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val itemsInterface = retrofit.create(ItemsInterface::class.java)
        val retrofitData = itemsInterface.getItems(authToken)

        retrofitData.enqueue(object : Callback<List<ProductsFetch>?> {
            override fun onResponse(
                call: Call<List<ProductsFetch>?>,
                response: Response<List<ProductsFetch>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    products.addAll(responseBody)
                    adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    Log.d("ProductItem", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ProductsFetch>?>, t: Throwable) {
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
