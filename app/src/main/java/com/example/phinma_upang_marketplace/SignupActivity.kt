package com.example.phinma_upang_marketplace

import SignUpRequest
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.GrammaticalInflectionManagerCompat.GrammaticalGender
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var usertypeSpinner: Spinner
    private lateinit var genderSpinner: Spinner
    private lateinit var signupBtn : Button

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var firstName : EditText
    private lateinit var lastName : EditText
    private lateinit var passwordConfirm : EditText

    private val BASE_URL = "https://marketplacebackup-036910b2ff5f.herokuapp.com/api/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        usertypeSpinner = findViewById(R.id.usertype)
        genderSpinner = findViewById(R.id.gender)
        signupBtn = findViewById(R.id.Button_Signup)

        email = findViewById(R.id.Email_Signup)
        password = findViewById(R.id.Password_Signup)
        firstName = findViewById(R.id.firstname)
        lastName = findViewById(R.id.lastname)
        password = findViewById(R.id.Password_Signup)
        passwordConfirm = findViewById(R.id.password_confirm)

        // Kotlin code for the spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.usertype, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        usertypeSpinner.adapter = adapter

        val adapter2 = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter2

        signupBtn.setOnClickListener {
            signup()
        }

    }

    // This function is called when the user taps the Sign Up button
    private fun signup(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SignUpInterface::class.java)
        val request = SignUpRequest(
            firstName.text.toString(),
            lastName.text.toString(),
            email.text.toString(),
            password.text.toString(),
            passwordConfirm.text.toString(),
            usertypeSpinner.selectedItem.toString(),
            genderSpinner.selectedItem.toString(),
            "2022-12-12",
            )

        service.signup(request).enqueue(object : Callback<SignUpRequest> {
            override fun onResponse(call: Call<SignUpRequest>, response: Response<SignUpRequest>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        Toast.makeText(applicationContext, "Account created successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Null response", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignUpRequest>, t: Throwable) {
                Toast.makeText(applicationContext, "Network error. Please try again.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}