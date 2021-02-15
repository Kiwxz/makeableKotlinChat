package com.example.makeablekotlinchat.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.makeablekotlinchat.messaging.MessageActivity
import com.example.makeablekotlinchat.models.User
import com.example.makeablekotlinchat.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
                        Log.d("RegisterActivity", "Created user with ID {${task.result?.user?.uid}")
                        saveUserToDatabase()
                        val user = auth.currentUser
                    } else {
                        // Prints error message to the user
                        // TODO: Right now it's only informative, not very pretty
                        Toast.makeText(this, "Failed:" + task.exception, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    // Sends already registered users to login screen
    private fun sendToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun saveUserToDatabase() {
        // Creates reference to database and saves user information
        val uid = FirebaseAuth.getInstance().uid!!
        val db = FirebaseDatabase.getInstance("https://makeablekotlinchat-default-rtdb.europe-west1.firebasedatabase.app/")
        val currentUserRef = db.getReference("/users/$uid")
        val userName = binding.txtRegisterUserName.text.toString()
        val userEmail = binding.txtEmail.text.toString()

        val user =
            User(uid, userName, userEmail)
        currentUserRef.setValue(user)
        // If successful, user is taken to message page
            .addOnSuccessListener {
                val intent = Intent(this, MessageActivity::class.java)
                startActivity(intent)
            }
    }
}