package com.example.phinma_upang_marketplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD
import android.util.Log
import android.widget.Button
import android.widget.Toast
=======
>>>>>>> 073ae06f231b9ca69f8d45b82f762768289d4f06
import androidx.activity.OnBackPressedCallback

class OrderConfirmed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmed)

<<<<<<< HEAD
        val backHome = findViewById<Button>(R.id.return_homepage_button)
        val authToken = intent.getStringExtra("authToken")
        Log.d("OrderConfirmed", "Auth Token: $authToken")

        backHome.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.putExtra("authToken", authToken)
            startActivity(intent)
            finish()
        }

=======
>>>>>>> 073ae06f231b9ca69f8d45b82f762768289d4f06
        // override the back button to avoid going back to the previous activity which is placing an order to avoid issue
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@OrderConfirmed, HomePageActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
<<<<<<< HEAD
                intent.putExtra("authToken", authToken)
=======
>>>>>>> 073ae06f231b9ca69f8d45b82f762768289d4f06
                startActivity(intent)
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }
}