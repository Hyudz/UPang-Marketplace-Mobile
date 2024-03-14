package com.example.phinma_upang_marketplace

import GetData
import LogoutResponse
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {
    private lateinit var logoutBtn : Button
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profileName : TextView = findViewById(R.id.profileName)
        val fname = intent.getStringExtra("fname")
        val lname = intent.getStringExtra("lname")
        val fullName = "$fname $lname"
        val authToken = intent.getStringExtra("authToken")
        profileName.text = fullName

        logoutBtn = findViewById(R.id.logoutButton)
        logoutBtn.setOnClickListener {
            logout()
        }

        val editProfile : Button = findViewById(R.id.editProfileButton)
        editProfile.setOnClickListener {
            editProfile(authToken!!)
        }

        val updateAccount : Button = findViewById(R.id.updateAccountButton)
        updateAccount.setOnClickListener {
            val intent = Intent(this, update_account::class.java)
            intent.putExtra("authToken", authToken)
            startActivity(intent)
        }

//        val dashboardBtn : Button = findViewById(R.id.sellerDashboardButton)
//        dashboardBtn.setOnClickListener {
//            sellerDashboard()
//        }

        val purchaseHistoryBtn : Button = findViewById(R.id.purchaseHistoryButton)
        purchaseHistoryBtn.setOnClickListener {
            getUser(authToken!!)
        }
    }

    fun editProfile(authToken: String) {
        val intent = Intent(this, ProfileSettingActivity::class.java)
        intent.putExtra("authToken", authToken)
        startActivity(intent)
    }

    fun sellerDashboard() {
        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
    }

    fun purchaseHistory(authToken: String, usertype : String) {
        val intent = Intent(this, PurchaseHistory::class.java)
        intent.putExtra("authToken", authToken)
        intent.putExtra("usertype", usertype)
        Log.d("ProfileActivity", "Auth $authToken")
        startActivity(intent)
    }

    fun logout() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LoginInterface::class.java)
           val logoutCall = service.logout(intent.getStringExtra("authToken")!!)
        logoutCall.enqueue(object : Callback<LogoutResponse> {
            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        Toast.makeText(applicationContext, "Logout Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Null response", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                Log.e("NetworkError", "Failed to make network call", t)
                Toast.makeText(applicationContext, "Network error. Please try again.", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun getUser(authToken : String){
        var usertype : String
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitData = retrofit.create(ItemsInterface::class.java)
        val service = retrofitData.getBuyer(authToken)
        service.enqueue(object : retrofit2.Callback<GetData> {
            override fun onResponse(call: retrofit2.Call<GetData>, response: retrofit2.Response<GetData>) {
                if (response.isSuccessful) {
                    usertype = response.body()!!.user_type
                    purchaseHistory(authToken, usertype)
                    Log.d("ProfileSettingActivity", "User Type: $usertype")
                } else {
                    //Toast.makeText(this, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<GetData>, t: Throwable) {
                //Toast.makeText(this, "Failed to fetch Liked Items", Toast.LENGTH_SHORT).show()
            }
        })
    }



}