package com.example.phinma_upang_marketplace

import OrderResponse
import ProductsRequest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfirmOrder : AppCompatActivity() {

    private lateinit var confirmBtn : Button
    val BASE_URL = "https://upmarketplace-com.preview-domain.com/public/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)

        val productName = intent.getStringExtra("product_name").toString()
        val productPrice = intent.getStringExtra("product_price").toString()
        val productID = intent.getStringExtra("product_id").toString()
        val sellerId = intent.getStringExtra("seller_id").toString()
        val authToken = intent.getStringExtra("authToken").toString()

        confirmBtn = findViewById(R.id.placeBtn)
        confirmBtn.setOnClickListener{
            placeOrder(productName, productPrice, productID, authToken, sellerId)
        }
    }

    fun placeOrder(productName: String, productPrice: String, productID: String, authToken: String, sellerId: String){
        val product_id = productID.toInt()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ItemsInterface::class.java)
        val request = ProductsRequest(product_id)
        val serviceCall = service.orderedItems(request, authToken)

        Log.d("ConfirmOrder", "Product ID: $product_id")
        Log.d("Token", "Auth Token: $authToken")

        serviceCall.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@ConfirmOrder, OrderConfirmed::class.java)
                    intent.putExtra("product_name", productName)
                    intent.putExtra("product_price", productPrice)
                    intent.putExtra("authToken", authToken)
                    startActivity(intent)

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ConfirmOrder", "Error Response Body: $errorBody")
                    Toast.makeText(this@ConfirmOrder, "Order Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Log.e("ConfirmOrder", "Failed to place order", t)
                Toast.makeText(this@ConfirmOrder, "Something went wrong", Toast.LENGTH_SHORT).show()

                if (t is HttpException) {
                    Log.e("ConfirmOrder", "Raw Response: ${t.response()?.errorBody()?.string()}")
                }
            }
        })

<<<<<<< HEAD
=======
        //start the intent
>>>>>>> 073ae06f231b9ca69f8d45b82f762768289d4f06
        val intent = Intent(this, OrderConfirmed::class.java)
        intent.putExtra("product_name", productName)
        intent.putExtra("product_price", productPrice)
        intent.putExtra("authToken", authToken)
        startActivity(intent)
    }
}