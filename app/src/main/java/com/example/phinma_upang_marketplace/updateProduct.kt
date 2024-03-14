package com.example.phinma_upang_marketplace

import OrderResponse
import PostProduct
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class updateProduct : AppCompatActivity() {
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)

        val newName = findViewById<EditText>(R.id.productName)
        val newPrice = findViewById<EditText>(R.id.productPrice)
        val newDescription = findViewById<EditText>(R.id.productDescription)
        val newStock = findViewById<EditText>(R.id.productQuantity)
//        val newCategory = findViewById<EditText>(R.id.productCategory)
        val updateBtn = findViewById<Button>(R.id.updateBtn)
        val authToken = intent.getStringExtra("authToken").toString()
        val id = intent.getStringExtra("id")

        val adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val newCategory = findViewById<Spinner>(R.id.category)
        newCategory.adapter = adapter

        updateBtn.setOnClickListener {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val itemsInterface = retrofit.create(ItemsInterface::class.java)
            val request = PostProduct(newName.text.toString(), newPrice.text.toString(), newDescription.text.toString(), newStock.text.toString(), newCategory.selectedItem.toString())
            val call = itemsInterface.updateProduct(request, authToken, id!!.toInt())

            call.enqueue(object : Callback<OrderResponse> {
                override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@updateProduct, "Product Updated", Toast.LENGTH_SHORT).show()
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        finish()
                    } else {
                        Toast.makeText(this@updateProduct, "Product not Updated", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Toast.makeText(this@updateProduct, "Product not Updated", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}