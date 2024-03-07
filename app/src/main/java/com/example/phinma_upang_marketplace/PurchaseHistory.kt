package com.example.phinma_upang_marketplace

import HistoryResponse
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PurchaseHistory : AppCompatActivity() {
<<<<<<< HEAD
    private val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
=======
    private val BASE_URL = "https://upmarketplace-com.preview-domain.com/public/api/"
>>>>>>> e8f22e59d0ab84885296a5facde11f79f9f0407f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authToken = intent.getStringExtra("authToken")
        Log.d("PurchaseHistory", "Auth Token: $authToken")
        val retrofitData = retrofit.create(ItemsInterface::class.java)
        val service = retrofitData.getHistory(authToken!!)
        val adapter = HistoryAdapter(this@PurchaseHistory, R.layout.item_history, mutableListOf())
        service.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(call: Call<HistoryResponse>, response: Response<HistoryResponse>) {
                if (response.isSuccessful) {
                    val historyResponse = response.body()

//                        Log.d("PurchaseHistory", "Histroy: ${historyResponse.copy().descriptions}")
                        adapter.clearData()
                        adapter.addAll(historyResponse)

                    //Log.d("PurchaseHistory", "${adapter.addAll(historyResponse)}")
                    adapter.notifyDataSetChanged()


//                    Log.d("ProductItem", "Response Body: ${response.body()}")
                } else {
                    Toast.makeText(applicationContext, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                    Log.d("ProductItem", "Unsuccessful response: ${response.code()}")
                    Log.d("ProductItem", "Unsuccessful response: ${response.errorBody()}")

                    Log.e("ProductItem", "Response Body: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {

                Toast.makeText(applicationContext, "Failed to fetch Liked Items", Toast.LENGTH_SHORT).show()
                Log.d("ProductItem", "Failed to fetch Liked Items: $t")
                Log.d("ProductItem", "Request URL: ${call.request().url()}")
                Log.d("ProductItem", "Request Method: ${call.request().method()}")
                Log.d("ProductItem", "Request Headers: ${call.request().headers()}")
                Log.d("ProductItem", "Request Body: ${call.request()}")
                Log.d("ProductItem", "Request Body: ${call.request().body()}")

            }
        })
    }
}