package com.example.newsapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val username = findViewById<EditText>(R.id.EmailAddress)
        val password = findViewById<EditText>(R.id.TextPassword)
        val submit = findViewById<Button>(R.id.button)
        val register = findViewById<TextView>(R.id.register)


        submit.setOnClickListener {

            val user: String = username.text.toString()
            val passWord: String = password.text.toString()

            val preferences: SharedPreferences = getSharedPreferences("MYPREFS", MODE_PRIVATE)
            val userEmail: String? = preferences.getString("Email", user)
            val userPassword: String? = preferences.getString("Password", passWord)
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString("email", userEmail)
            editor.putString("password", userPassword)
            editor.apply()

            if (username.text.toString()==userEmail && password.text.toString()==userPassword) {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()

            }
            else if (username.text.isNullOrEmpty()){
                username.error = "Enter username"
            }
            else if (password.text.isNullOrEmpty()){
                password.error = "Enter password"
            }
            else{
                Toast.makeText(this, "login failed \n Enter correct Username and password", Toast.LENGTH_LONG).show()
            }

        }

        register.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }

    }
}