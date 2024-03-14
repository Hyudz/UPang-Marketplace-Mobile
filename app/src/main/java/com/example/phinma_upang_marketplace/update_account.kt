package com.example.phinma_upang_marketplace

import GetData
import MessageResponse
import OrderResponse
import UpdateRequest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class update_account : AppCompatActivity() {
    private val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
    private lateinit var userId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_account)

        val authToken = intent.getStringExtra("authToken")

        val updateBtn : Button = findViewById(R.id.update_account_button)
        getId(authToken!!)
        updateBtn.setOnClickListener {
            updateAccount(authToken)
        }
    }

    fun updateAccount(authToken : String) {
        val email : EditText = findViewById(R.id.update_account_email)
        val newPassword : EditText = findViewById(R.id.newPassword)
        val confirmPassword : EditText = findViewById(R.id.confirmPassword)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ItemsInterface::class.java)
        val request = UpdateRequest(email.text.toString(), newPassword.text.toString(), confirmPassword.text.toString())
        val call = service.updateAccount(request, authToken, userId)
        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    finish()
                    Toast.makeText(applicationContext, "Profile Updated", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("Profile Setting", response.errorBody().toString())
                    Log.d("Profile Setting", response.message())
                    Log.d("Profile Setting", response.code().toString())
                    Log.d("Profile Setting", response.toString())
                    Log.d("Profile Setting", response.raw().toString())
                    Log.d("Profile Setting", response.body().toString())
                    Toast.makeText(applicationContext, "Error. Failed to Update Profile", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                Log.d("Profile Setting", t.stackTraceToString())

                Log.d("Email", email.text.toString())
                Log.d("Password", newPassword.text.toString())
                Log.d("Confirm Password", confirmPassword.text.toString())
                Toast.makeText(applicationContext, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getId(authToken : String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ItemsInterface::class.java)
        val serviceCall = service.getBuyer(authToken)
        Log.d("ProfileSettingActivity", "Auth Token $authToken")
        serviceCall.enqueue(object : retrofit2.Callback<GetData> {
            override fun onResponse(call: retrofit2.Call<GetData>, response: retrofit2.Response<GetData>) {
                if (response.isSuccessful) {
                    val id = response.body()?.id
                    userId = id.toString()
                    Log.d("ProfileSettingActivity", "ID: $id")
                } else {
                    Log.d("ProfileSettingActivity", "Failed to get ID")
                }
            }

            override fun onFailure(call: retrofit2.Call<GetData>, t: Throwable) {
                Log.d("ProfileSettingActivity", "Failed to get ID")
            }
        })
    }
}