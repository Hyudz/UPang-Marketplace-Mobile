package com.example.phinma_upang_marketplace

import GetData
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomePageActivity : AppCompatActivity() {
    private lateinit var shop: Button
    private lateinit var usertype: String
    private lateinit var fname: String
    private lateinit var lname: String
    private val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        shop = findViewById(R.id.startshopping)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomnavigationview)
        bottomNavigationView.selectedItemId = R.id.home

        val authToken = intent.getStringExtra("authToken")
        getUser(authToken!!)
        shop.setOnClickListener {
            val intent = Intent(this, ProductItem::class.java)
            intent.putExtra("authToken", authToken)
            intent.putExtra("fname", fname)
            intent.putExtra("lname", lname)
            startActivity(intent)
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomePageActivity::class.java)
                    intent.putExtra("authToken", authToken)
                    intent.putExtra("fname", fname)
                    intent.putExtra("lname", lname)
                    startActivity(intent)
                }
                R.id.cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    intent.putExtra("authToken", authToken)
                    intent.putExtra("fname", fname)
                    intent.putExtra("lname", lname)
                    startActivity(intent)
                }
                R.id.notification -> {
                    val intent = Intent(this, Notifications::class.java)
                    intent.putExtra("authToken", authToken)
                    startActivity(intent)
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("authToken", authToken)
                    Log.d("HomePageActivity", "Auth Token: $authToken")
                    intent.putExtra("fname", fname)
                    intent.putExtra("lname", lname)
                    intent.putExtra("usertype", usertype)
                    startActivity(intent)
                }
            }
            true
        }
    }

    fun getUser(authToken : String){

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
                    fname = response.body()!!.first_name
                    lname = response.body()!!.last_name
                    Log.d("ProfileSettingActivity", "User Type: $usertype")
                } else {
                    Toast.makeText( this@HomePageActivity, "Error. Please check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<GetData>, t: Throwable) {
                Toast.makeText(this@HomePageActivity, "Failed to fetch Liked Items", Toast.LENGTH_SHORT).show()
            }
        })
    }


    //dinagdag ko lang to para hindi mag back sa log in page
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        moveTaskToBack(false)
    }
}