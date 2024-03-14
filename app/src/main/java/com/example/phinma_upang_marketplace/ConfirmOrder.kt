package com.example.phinma_upang_marketplace

import GetData
import OrderRequest
import OrderResponse
import ProductsRequest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfirmOrder : AppCompatActivity() {

    private lateinit var confirmBtn : Button
    private lateinit var buyerName : TextView
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)

        val productName = intent.getStringExtra("product_name").toString()
        val productPrice = intent.getStringExtra("product_price").toString()
        val productID = intent.getStringExtra("product_id").toString()
        val sellerId = intent.getStringExtra("seller_id").toString()
        val authToken = intent.getStringExtra("authToken").toString()
        buyerName = findViewById(R.id.buyerName)
        val textView = findViewById<TextView>(R.id.textView2)

        buyerName.text = intent.getStringExtra("buyerName").toString()
        textView.text = "$productName \n$productPrice"

        Log.d("ConfirmOrder", "Product Name: $productID")

        getSeller(productID.toInt(), authToken)
        getBuyer(authToken)

        confirmBtn = findViewById(R.id.placeBtn)
        confirmBtn.setOnClickListener{
            placeOrder(productName, productPrice, productID, authToken, sellerId)
        }
    }

    fun getSeller(productID: Int, authToken: String){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ItemsInterface::class.java)
        val request = ProductsRequest(productID)
        val serviceCall = service.getSeller(request, authToken)
        Log.d("ConfirmOrder", "Product ID: $productID")
        Log.d("Token", "Auth Token: $authToken")
        Log.d("ConfirmOrder", "Service Call: $serviceCall")
        serviceCall.enqueue(object : Callback<GetData> {
            override fun onResponse(call: Call<GetData>, response: Response<GetData>) {
                if (response.isSuccessful) {
                    val firstName = response.body()?.first_name
                    val lastName = response.body()?.last_name
                    val email = response.body()?.email
                    val sellerDetails = "$firstName $lastName \n$email"
                    val textView = findViewById<TextView>(R.id.textView)
                    textView.text = sellerDetails
                    Log.d("ConfirmOrder", "Seller Details: $sellerDetails")

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ConfirmOrder", "Error Response Body: $errorBody")
                    Toast.makeText(this@ConfirmOrder, "Failed to Fetch Seller Data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetData>, t: Throwable) {
                Log.e("ConfirmOrder", "Failed to connect", t)
                Toast.makeText(this@ConfirmOrder, "Something went wrong", Toast.LENGTH_SHORT).show()

                if (t is HttpException) {
                    Log.e("ConfirmOrder", "Raw Response: ${t.response()?.errorBody()?.string()}")
                }
            }
        })
    }

    fun getBuyer(authToken: String){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ItemsInterface::class.java)
        val serviceCall = service.getBuyer(authToken)
        serviceCall.enqueue(object : Callback<GetData> {
            override fun onResponse(call: Call<GetData>, response: Response<GetData>) {
                if (response.isSuccessful) {
                    val firstName = response.body()?.first_name
                    val lastName = response.body()?.last_name
                    val email = response.body()?.email
                    val buyerDetails = "$firstName $lastName \n$email"
                    val buyerName = findViewById<TextView>(R.id.buyerName)
                    buyerName.text = buyerDetails
                    Log.d("ConfirmOrder", "Buyer Details: $buyerDetails")

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ConfirmOrder", "Error Response Body: $errorBody")
                    Toast.makeText(this@ConfirmOrder, "Failed to Fetch Buyer Data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetData>, t: Throwable) {
                Log.e("ConfirmOrder", "Failed to connect", t)
                Toast.makeText(this@ConfirmOrder, "Something went wrong", Toast.LENGTH_SHORT).show()

                if (t is HttpException) {
                    Log.e("ConfirmOrder", "Raw Response: ${t.response()?.errorBody()?.string()}")
                }
            }
        })
    }

    fun placeOrder(productName: String, productPrice: String, productID: String, authToken: String, sellerId: String){
        val product_id = productID.toInt()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val message : EditText = findViewById(R.id.messageText)
        val payment = "Cash on Delivery"

        val service = retrofit.create(ItemsInterface::class.java)
        val request = OrderRequest(product_id, message.text.toString(), payment)
        val serviceCall = service.orderedItems(request, authToken)

        Log.d("ConfirmOrder", "Product ID: $product_id")
        Log.d("Token", "Auth Token: $authToken")

        serviceCall.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@ConfirmOrder, OrderConfirmed::class.java)
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

        //start the intent
        val intent = Intent(this, OrderConfirmed::class.java)
        intent.putExtra("product_name", productName)
        intent.putExtra("product_price", productPrice)
        intent.putExtra("authToken", authToken)
        startActivity(intent)
    }
}