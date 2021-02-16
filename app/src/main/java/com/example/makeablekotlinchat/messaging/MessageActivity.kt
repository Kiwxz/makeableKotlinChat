package com.example.makeablekotlinchat.messaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.makeablekotlinchat.R
import com.example.makeablekotlinchat.databinding.ActivityMessageBinding
import com.example.makeablekotlinchat.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.message_incoming.view.*
import kotlinx.android.synthetic.main.message_outgoing.view.*

class MessageActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityMessageBinding
    private val db = FirebaseDatabase.getInstance("https://makeablekotlinchat-default-rtdb.europe-west1.firebasedatabase.app/")
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val chatlogReference = db.getReference("/messages")
    private val currentUser = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvwMessages.adapter = adapter
        fetchMessagesFromFirebase()

        binding.btnSendMessage.setOnClickListener {
            sendMessage()
        }
    }

    // Saves the sent message in Firebase under current user
    private fun sendMessage() {
        val messageContent = binding.txtChatMessage.text.toString()
        val newMessage = Message(messageContent, currentUser?: "")
        chatlogReference.push().setValue(newMessage)
        binding.txtChatMessage.setText(null)
    }

    // Listens for changes in messages on Firebase. If a new message is added, the eventlistener
    // checks whether it's from another user and adds this to the adapter
    private fun fetchMessagesFromFirebase() {
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val message = dataSnapshot.getValue(Message::class.java)
                if (message == null) {
                    return
                } else if (message.senderId == currentUser) {
                    adapter.add(SendMessageItem(message))
                } else {
                    adapter.add(ReceivedMessageItem(message))
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        chatlogReference.addChildEventListener(childEventListener)
    }
}

// Makes sure the correct layout is set for sent and received messages
// Adds text to the message bubbles
class SendMessageItem(val message: Message): Item<GroupieViewHolder>() {
    override fun getLayout() = R.layout.message_outgoing

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.txtOutgoingMessage.text = message.text
    }
}

class ReceivedMessageItem(val message: Message): Item<GroupieViewHolder>() {

    override fun getLayout() = R.layout.message_incoming

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.txtIncomingMessage.text = message.text
    }
}



