package com.example.makeablekotlinchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.makeablekotlinchat.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        // Registers clicks on button - creates new user if e-mail is unique and password is at least 6 characters
        binding.btnRegisterUser.setOnClickListener {
            registerNewUser()
        }

        binding.txtAlreadyHaveAccount.setOnClickListener {
            sendToLoginScreen()
        }
    }

    private fun registerNewUser() {
        val email = binding.txtEmail.text.toString().trim()
        val password = binding.txtRegisterPassword.text.toString().trim()

        // Catches a few of the most common mistakes
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter e-mail and password to continue", Toast.LENGTH_SHORT)
                .show()
        } else if (password.length < 6) {
            Toast.makeText(this, "Password must contain at least 6 characters", Toast.LENGTH_SHORT).show()
        } else {
            // Firebase authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully created user", Toast.LENGTH_SHORT).show()
                        Log.d("MainActivity", "Created user with ID {${task.result?.user?.uid}")
                        val user = auth.currentUser
                    } else {
                        // Prints error message to the user
                        // TODO: Right now it's only informative, not very pretty
                        Toast.makeText(this, "Failed:" + task.getException(), Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    // Sends already registered users to login screen
    private fun sendToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        Log.d("MainActivity","Show login activity")
    }

    /*
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        Log.d("MainActivity", "Current user is " + currentUser?.email)
    } */
}