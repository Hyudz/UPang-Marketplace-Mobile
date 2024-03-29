package com.example.phinma_upang_marketplace

import LoginRequest
import LoginResponse
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var signview: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
//    val BASE_URL = "https://upmarketplace-com.preview-domain.com/public/api/"
    val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
//    val BASE_URL = "https://127.0.0.1:8000/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signview = findViewById(R.id.signupbutton)
        signview.setOnClickListener() {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        email = findViewById(R.id.Email_Login)
        password = findViewById(R.id.Password_Login)

        loginBtn = findViewById(R.id.Button_Login)
        loginBtn.setOnClickListener {
            Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show()
            loginpost()
        }
    }

    private fun loginpost() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LoginInterface::class.java)
        val request = LoginRequest(email.text.toString(), password.text.toString())
        val loginCall = service.login(request)
        loginCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        Log.d("Login", response.body().toString())
                        val authToken = "Bearer ${response.body()?.token}"
                        loginNav(authToken)
                    } else {
                        Toast.makeText(applicationContext, "Null response", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Unsuccessful response: ${response.code()}")
                    Log.d("Login", "Request URL: ${call.request().url()}")
                    Log.d("Login", "Request Method: ${call.request().method()}")
                    Log.d("Login", "Request Headers: ${call.request().headers()}")
                    Log.d("Login", "Request Body: ${call.request()}")
                    Log.d("Login", "Request Body: ${call.request().body()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("NetworkError", "Failed to make network call", t)
                Log.e("NetworkError", "Request URL: ${call.request().url()}")
                Log.e("NetworkError", "Request Method: ${call.request().method()}")
                Log.e("NetworkError", "Request Headers: ${call.request().headers()}")
                Log.e("NetworkError", "Request Body: ${call.request()}")
                Log.e("NetworkError", "Request Body: ${call.request().body()}")
                Log.d("ProductItem", "Failed to fetch Liked Items: $t")
                Log.d("ProductItem", "Request URL: ${call.request().url()}")
                Log.d("ProductItem", "Request Method: ${call.request().method()}")
                Log.d("ProductItem", "Request Headers: ${call.request().headers()}")
                Log.d("ProductItem", "Request Body: ${call.request()}")
                Log.d("ProductItem", "Request Body: ${call.request().body()}")
                Toast.makeText(applicationContext, "Network error. Please try again.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loginNav(authToken: String) {
        val intent = Intent(this, HomePageActivity::class.java)
        intent.putExtra("authToken", authToken)
        startActivity(intent)
    }

    //dinagdag ko lang to para hindi mag bumalik sa profile page pag nag log out
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        moveTaskToBack(false)
    }
}