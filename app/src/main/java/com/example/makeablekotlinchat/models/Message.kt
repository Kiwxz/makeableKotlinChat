package com.example.makeablekotlinchat.models

data class Message
    (
    val text: String,
    val senderId: String
) {
    constructor() : this("","")
}