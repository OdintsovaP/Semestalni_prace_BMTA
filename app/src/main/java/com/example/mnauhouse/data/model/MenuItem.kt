package com.example.mnauhouse.data.model

data class MenuItem(
    val id: String,
    val name: String,
    val price: Int,
    val description: String,
    val image: String,
    val category: String,
    val emoji: String
)