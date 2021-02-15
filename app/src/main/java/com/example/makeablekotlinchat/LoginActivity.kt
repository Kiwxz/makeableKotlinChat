package com.example.makeablekotlinchat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.makeablekotlinchat.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_login)

        binding.btnLogin.setOnClickListener {
            val userName = binding.txtUserName.text.toString()
            val passWord = binding.txtPassword.text.toString()
            Log.d("MainActivity","Username:" + userName)
            Log.d("MainActivity","Password: $passWord")
        }

        binding.txtBackToRegistration.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Log.d("LoginActivity","Back to registration")
        }
    }
}