package com.example.makeablekotlinchat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.makeablekotlinchat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.txtBackToRegistration.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Log.d("LoginActivity","Back to registration")
        }
    }

    private fun loginUser() {
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("LoginActivity", "User with UID {${task.result?.user?.uid} is now logged in ")
                    Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                } else {
                    // Prints error message to the user
                    // TODO: Right now it's only informative, not very pretty
                    Toast.makeText(this, "Failed:" + task.getException(), Toast.LENGTH_SHORT).show()
                }
            }
    }
}