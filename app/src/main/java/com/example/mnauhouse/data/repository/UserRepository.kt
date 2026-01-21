package com.example.mnauhouse.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.mnauhouse.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserRepository(private val context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Загрузить список пользователей
    fun getUsers(): List<User> {
        val json = prefs.getString("users", "[]")
        val type = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(json, type)
    }

    // Сохранить список пользователей
    private fun saveUsers(users: List<User>) {
        val json = gson.toJson(users)
        prefs.edit().putString("users", json).apply()
    }

    // Добавить пользователя (регистрация)
    fun addUser(user: User) {
        val users = getUsers().toMutableList()
        users.add(user)
        saveUsers(users)
    }

    // Найти пользователя по email и password (вход)
    fun findUser(email: String, password: String): User? {
        return getUsers().find { it.email == email && it.password == password }
    }

    // Сохранить текущего пользователя (после входа)
    fun saveCurrentUser(user: User) {
        val json = gson.toJson(user)
        prefs.edit().putString("current_user", json).apply()
    }

    // Загрузить текущего пользователя
    fun getCurrentUser(): User? {
        val json = prefs.getString("current_user", null)
        return if (json != null) gson.fromJson(json, User::class.java) else null
    }

    // Выйти (очистить текущего пользователя)
    fun logout() {
        prefs.edit().remove("current_user").apply()
    }
}