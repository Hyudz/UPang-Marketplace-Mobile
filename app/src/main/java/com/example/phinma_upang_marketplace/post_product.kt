package com.example.phinma_upang_marketplace

import PostProduct
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class post_product : AppCompatActivity() {
    val BASE_URL = "https://upmarketplace-com.preview-domain.com/public/api/"
    private val pickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_product)

        val chooseImageButton: Button = findViewById(R.id.chooseImageButton)
        val postBtn: Button = findViewById(R.id.postProductBtn)

        chooseImageButton.setOnClickListener {
            openGallery()
        }

        postBtn.setOnClickListener {
            postProduct()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(galleryIntent)
    }

    private fun postProduct() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productName = findViewById<EditText>(R.id.postproductName)
        val productPrice = findViewById<EditText>(R.id.postproductPrice)
        val productDescription = findViewById<EditText>(R.id.postproductDescription)
        val productCategory = findViewById<EditText>(R.id.postProductCategory)
        val productQuantity = findViewById<EditText>(R.id.postProductQuantity)
        val token = intent.getStringExtra("authToken")

        val service = retrofit.create(ItemsInterface::class.java)
        val request = PostProduct(productName.text.toString(), productPrice.text.toString(), productDescription.text.toString(),  productQuantity.text.toString())
        val postProductCall = service.postProduct(request, token.toString())
        postProductCall.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@post_product, "Product posted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@post_product, "Failed to post product", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@post_product, "Failed to post product", Toast.LENGTH_SHORT).show()
            }
        })
    }
}