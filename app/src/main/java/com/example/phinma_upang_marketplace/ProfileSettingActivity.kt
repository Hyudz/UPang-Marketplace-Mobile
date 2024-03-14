package com.example.phinma_upang_marketplace

import GetData
import OrderResponse
import ProfileRequest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class ProfileSettingActivity : AppCompatActivity() {
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
    private lateinit var userId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting)
        val updateProfile : Button = findViewById(R.id.profile_setting_update)
        val deleteProfile : Button = findViewById(R.id.profile_setting_delete)
        val authToken = intent.getStringExtra("authToken")

        val newGender : Spinner = findViewById(R.id.gender_spinner)
        val adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        newGender.adapter = adapter
        getId(authToken!!)
        updateProfile.setOnClickListener {
            updateProfile(authToken!!, userId.toInt(), newGender.selectedItem.toString())
        }

        deleteProfile.setOnClickListener {
            deleteProfile(authToken!!)
        }
    }

    private fun updateProfile(authToken : String, id : Int, newGender : String) {
        val newFname : EditText = findViewById(R.id.profile_setting_fname)
        val newLname : EditText = findViewById(R.id.profile_setting_lname)
        val newBDate : EditText = findViewById(R.id.birthdate)
        val newContact : EditText = findViewById(R.id.contact)
        val newAddress : EditText = findViewById(R.id.address)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ItemsInterface::class.java)
        val request = ProfileRequest(newFname.text.toString(), newLname.text.toString(), newGender ,newBDate.toString(),newContact.toString(), newAddress.toString())
        val call = service.updateProfile(request, authToken, id)
        call.enqueue(object : retrofit2.Callback<OrderResponse> {
            override fun onResponse(call: retrofit2.Call<OrderResponse>, response: retrofit2.Response<OrderResponse>) {
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

            override fun onFailure(call: retrofit2.Call<OrderResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
            }
        })


//        val intent = Intent(this, ProfileActivity::class.java)
//        startActivity(intent)
    }

    private fun deleteProfile(authToken: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ItemsInterface::class.java)
        val call = service.deleteProfile(authToken, userId.toInt())
        call.enqueue(object : retrofit2.Callback<OrderResponse> {
            override fun onResponse(call: retrofit2.Call<OrderResponse>, response: retrofit2.Response<OrderResponse>) {
                if (response.isSuccessful) {
                    Log.d("ProfileSettingActivity", response.body().toString())
                    Log.d("ProfileSettingActivity", response.message())
                    Log.d("ProfileSettingActivity", response.code().toString())
                    Log.d("ProfileSettingActivity", response.toString())
                    Log.d("ProfileSettingActivity", response.raw().toString())
                    val intent = Intent(this@ProfileSettingActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext, "Profile Deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("ProfileSettingActivity", response.body().toString())
                    Log.d("ProfileSettingActivity", response.message())
                    Log.d("ProfileSettingActivity", response.code().toString())
                    Log.d("ProfileSettingActivity", response.toString())
                    Log.d("ProfileSettingActivity", response.raw().toString())
                    Toast.makeText(applicationContext, "Error. Failed to Delete Profile", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<OrderResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getId(authToken: String){
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