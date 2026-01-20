package com.example.mnauhouse.data.model

data class CartItem(
    val id: String,
    val name: String,
    val price: Int,
    var quantity: Int,
    val image: String
)