package com.example.mnauhouse.data.model

data class User(
    val id: String,  // Уникальный ID (можно использовать UUID)
    val email: String,
    val password: String,  // В реальном приложении хэшировать!
    val name: String
)