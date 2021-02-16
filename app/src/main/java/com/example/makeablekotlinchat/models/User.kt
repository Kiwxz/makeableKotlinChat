package com.example.makeablekotlinchat.models

data class User
    (
    val uid: String,
    val userName: String,
    val email: String
) {
    constructor() : this("","","")
}
