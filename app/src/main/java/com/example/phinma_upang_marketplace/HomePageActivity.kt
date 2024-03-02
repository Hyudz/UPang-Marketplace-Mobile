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
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomnavigationview)
        bottomNavigationView.selectedItemId = R.id.home

        val cartButton :ImageButton = findViewById(R.id.shopping_cart)
        val messagesButton : ImageButton = findViewById(R.id.chat)
        val fname : String = intent.getStringExtra("fname").toString()
        val lname : String = intent.getStringExtra("lname").toString()


        val authToken = intent.getStringExtra("authToken")
        shop.setOnClickListener {
            val intent = Intent(this, ProductItem::class.java)
            intent.putExtra("authToken", authToken)
            intent.putExtra("fname", fname)
            intent.putExtra("lname", lname)
            startActivity(intent)
        }

        cartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("authToken", authToken)
            startActivity(intent)
        }

        messagesButton.setOnClickListener {
            val intent = Intent(this, Messages::class.java)
            intent.putExtra("authToken", authToken)
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
                R.id.favorite -> {
                    val intent = Intent(this, Likes::class.java)
                    intent.putExtra("authToken", authToken)
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
                    startActivity(intent)
                }
            }
            true
        }
    }

    //dinagdag ko lang to para hindi mag back sa log in page
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        moveTaskToBack(false)
    }
}