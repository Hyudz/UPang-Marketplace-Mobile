package com.example.phinma_upang_marketplace

import ProductsFetch
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Likes : AppCompatActivity() {
    private val BASE_URL = "https://upmarketplace-com.preview-domain.com/public/api/"
    private var recyclerView: RecyclerView? = null
    private var adapter: LikesAdapter? = null
    private var like = mutableListOf<ProductsFetch>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likes)

        val authToken = intent.getStringExtra("authToken")
        recyclerView = findViewById<View>(R.id.likesList) as RecyclerView
        recyclerView!!.layoutManager = GridLayoutManager(this, 2)
        adapter = LikesAdapter(this, like, authToken!!)
        recyclerView!!.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val itemsInterface = retrofit.create(ItemsInterface::class.java)
        val retrofitData = itemsInterface.getLikes(authToken)
        Log.d("Likes", "Token: $authToken")

        retrofitData.enqueue(object : Callback<List<ProductsFetch>?> {
            override fun onResponse(
                call: Call<List<ProductsFetch>?>,
                response: Response<List<ProductsFetch>?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    like.addAll(responseBody)

                    Log.d("Likes", "Like: $like")
                    Log.d("Likes", "Response: $responseBody")
                    adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                    Log.d("ProductItem", "Unsuccessful response: ${response.code()}")
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
    }
}