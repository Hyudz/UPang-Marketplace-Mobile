package com.example.phinma_upang_marketplace

import OrderResponse
import ProductsFetch
import ProductsRequest
import RemoveRequest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewProducts : AppCompatActivity() {
    private lateinit var product_name: TextView
    private lateinit var product_price: TextView
    private lateinit var product_description: TextView
    private lateinit var product_image: ImageView
    private lateinit var product_id: String
    private lateinit var buyBtn : Button
    private lateinit var likeBtn : Button
    private val BASE_URL = "https://upmarketplace-com.preview-domain.com/public/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_products)

        buyBtn = findViewById(R.id.buyBtn)
        product_name = findViewById(R.id.textProductName)
        product_price = findViewById(R.id.textProductPrice)
        product_description = findViewById(R.id.productDescription)
        val product_image = findViewById<ImageView>(R.id.productImage)
        val productPath = intent.getStringExtra("image")

        Glide.with(this).load(productPath).into(product_image)

        val authToken = intent.getStringExtra("authToken")
        Log.d("ViewProducts", "Auth Token: $authToken")

        product_name.text = intent.getStringExtra("product_name")
        product_price.text = intent.getStringExtra("product_price")
        product_description.text = intent.getStringExtra("product_description")
        product_id = intent.getStringExtra("productId").toString()
        val seller_id = intent.getStringExtra("sellerId")

        buyBtn.setOnClickListener{
            val intent = Intent(this, ConfirmOrder::class.java)
            intent.putExtra("authToken", authToken)
            intent.putExtra("product_id", product_id)
            intent.putExtra("product_name", product_name.text.toString())
            intent.putExtra("product_price", product_price.text.toString())
            intent.putExtra("seller_id", seller_id)
            intent.putExtra("product_image", productPath)

            startActivity(intent)
        }

        likeBtn = findViewById(R.id.likeBtn)
        likeBtn.setOnClickListener{
            addLike(product_id, authToken!!)
        }
    }

    fun addLike(productId: String, authToken: String){
        //THIS FUNCTION ADDS A PRODUCT TO THE USER'S LIKED ITEMS
        val itemsInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Log.d("ViewProducts", "Product ID: "+productId.toInt())
        Log.d("ViewProducts", "Token: $authToken")

        val service = itemsInterface.create(ItemsInterface::class.java)
        val request = ProductsRequest(productId.toInt())
        val likeCall = service.addLike(request , authToken)

        likeCall.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Something went wrong with the server", Toast.LENGTH_SHORT).show()
                    Log.d("ViewProducts", "Unsuccessful response: ${response.code()} \n ${response.errorBody()?.string()} \n ${response.message()} \n ${response.raw()}")
                    Log.d("ViewProducts", "Unsuccessful response:" + response.body())
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Failed to add to liked items", Toast.LENGTH_SHORT).show()
                Log.d("ViewProducts", "onFailure" + t.message)
            }
        })
    }
}