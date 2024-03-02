package com.example.phinma_upang_marketplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback

class OrderConfirmed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmed)

        val backHome = findViewById<Button>(R.id.return_homepage_button)
        val authToken = intent.getStringExtra("authToken")
        Log.d("OrderConfirmed", "Auth Token: $authToken")

        backHome.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.putExtra("authToken", authToken)
            startActivity(intent)
            finish()
        }

        // override the back button to avoid going back to the previous activity which is placing an order to avoid issue
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@OrderConfirmed, HomePageActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.putExtra("authToken", authToken)
                startActivity(intent)
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }
}