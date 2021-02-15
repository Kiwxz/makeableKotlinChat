package com.example.makeablekotlinchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.makeablekotlinchat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterUser.setOnClickListener {
            val userName = binding.txtRegisterUserName.text.toString()
            val passWord = binding.txtRegisterPassword.text.toString()
            Log.d("MainActivity","Username:" + userName)
            Log.d("MainActivity","Password: $passWord")
        }

        binding.txtAlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            Log.d("MainActivity","Show login activity")
        }
    }
}