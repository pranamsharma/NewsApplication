package com.example.newsapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val userName: EditText = findViewById(R.id.Name)
        val email: EditText = findViewById(R.id.EmailAddress)
        val mobile: EditText = findViewById(R.id.phone_number)
        val password: EditText = findViewById(R.id.TextPassword)
        val signup = findViewById<Button>(R.id.SignButton)
        signup.setOnClickListener {


            val preferences: SharedPreferences = getSharedPreferences("MYPREFS", MODE_PRIVATE)
            val newUser: String = userName.text.toString()
            val newPassword: String = password.text.toString()
            val newEmail: String = email.text.toString()
            val newMobile: String = mobile.text.toString()
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString("User Name",newUser)
            editor.putString("Email",newEmail)
            editor.putString("Mobile Number",newMobile)
            editor.putString("Password",newPassword)
            editor.apply()

            if (userName.text.isNullOrEmpty()){
                userName.error = "Enter Your Name"
            }
            else if (password.text.isNullOrEmpty()){
                password.error = "Enter Password"
            }
            else if (email.text.isNullOrEmpty()){
                email.error = "Enter Email Id"
            }
            else if(mobile.text.isNullOrEmpty()){
                mobile.error="Enter Phone number"
            }
            else {
                Toast.makeText(this,
                    "Your are registered successfully,Login to Continue",
                    Toast.LENGTH_LONG).show()
                startActivity(Intent(this, SecondActivity::class.java))
            }
        }
    }
}