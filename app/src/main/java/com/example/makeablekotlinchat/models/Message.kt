package com.example.makeablekotlinchat.models

import com.example.makeablekotlinchat.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.message_incoming.view.*
import kotlinx.android.synthetic.main.message_outgoing.view.*

data class Message
    (
    val text: String,
    val senderId: String
) {
    constructor() : this("","")
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