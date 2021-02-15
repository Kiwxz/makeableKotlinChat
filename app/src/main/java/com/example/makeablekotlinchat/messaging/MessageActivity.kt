package com.example.makeablekotlinchat.messaging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.makeablekotlinchat.databinding.ActivityMessageBinding
import com.example.makeablekotlinchat.models.Message
import com.example.makeablekotlinchat.registration.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MessageActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        verifyUserLogin()

        binding.btnSendMessage.setOnClickListener {
            sendMessage()
        }
    }

    // Verifies that the user is logged in - if not, the user is returned to registration page
    private fun verifyUserLogin() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // Saves the sent message in Firebase under current user
    private fun sendMessage() {
        val messageContent = binding.txtChatMessage.text.toString()
        val uid = FirebaseAuth.getInstance().uid!!
        val db = FirebaseDatabase.getInstance("https://makeablekotlinchat-default-rtdb.europe-west1.firebasedatabase.app/")
        val currentUserRef = db.getReference("/users/$uid")
        val message = Message(messageContent, uid)
        currentUserRef.child("message").push().setValue(message)
        binding.txtChatMessage.setText(null)
    }
}