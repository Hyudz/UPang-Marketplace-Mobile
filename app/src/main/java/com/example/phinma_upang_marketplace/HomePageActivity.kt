package com.example.phinma_upang_marketplace

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

class HomePageActivity : AppCompatActivity() {
    private lateinit var shop: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        shop = findViewById(R.id.startshopping)
        shop.setOnClickListener {
            shop()
        }

        Log.d("HomePageActivity", "User Type: ${intent.getStringExtra("authToken")}")

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomnavigationview)
        bottomNavigationView.selectedItemId = R.id.home

        val cartButton :ImageButton = findViewById(R.id.shopping_cart)
        val messagesButton : ImageButton = findViewById(R.id.chat)

        cartButton.setOnClickListener {
            cart()
        }

        messagesButton.setOnClickListener {
            messages()
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> startActivity(Intent(this, HomePageActivity::class.java))
                R.id.favorite -> {
                    val authToken = intent.getStringExtra("authToken")
                    val intent = Intent(this, Likes::class.java)
                    intent.putExtra("authToken", authToken)
                    startActivity(intent)
                }
                R.id.notification -> {
                    val authToken = intent.getStringExtra("authToken")
                    val intent = Intent(this, Notifications::class.java)
                    intent.putExtra("authToken", authToken)
                    startActivity(intent)
                }
                R.id.profile -> {
                    val authToken = intent.getStringExtra("authToken")
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("authToken", authToken)
                    startActivity(intent)
                }
            }
            true
        }
    }

    private fun shop(){
        val authToken = intent.getStringExtra("authToken")
        val intent = Intent(this, ProductItem::class.java)
        intent.putExtra("authToken", authToken)
        startActivity(intent)
    }

    //dinagdag ko lang to para hindi mag back sa log in page
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        moveTaskToBack(false)
    }

    public fun cart(){
        val authToken = intent.getStringExtra("authToken")
        val intent = Intent(this, CartActivity::class.java)
        intent.putExtra("authToken", authToken)
        startActivity(intent)
    }

    public fun messages(){
        val authToken = intent.getStringExtra("authToken")
        val intent = Intent(this, Messages::class.java)
        intent.putExtra("authToken", authToken)
        startActivity(intent)
    }
}