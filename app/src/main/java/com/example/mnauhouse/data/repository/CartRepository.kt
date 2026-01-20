package com.example.mnauhouse.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mnauhouse.data.model.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartRepository(private val context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("cart_prefs",
        Context.MODE_PRIVATE)
    private val gson = Gson()

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    init {
        loadCartFromPrefs()
    }

    private fun loadCartFromPrefs() {
        val json = prefs.getString("cart_items", "[]")
        val type = object : TypeToken<List<CartItem>>() {}.type
        val items: List<CartItem> = gson.fromJson(json, type)
        _cartItems.value = items
    }

    private fun saveCartToPrefs(items: List<CartItem>) {
        val json = gson.toJson(items)
        prefs.edit().putString("cart_items", json).apply()
        _cartItems.value = items
    }

    fun addToCart(item: CartItem) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        val existingItem = currentItems.find { it.id == item.id }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            currentItems.add(item)
        }
        saveCartToPrefs(currentItems)
    }

    fun updateCartItem(item: CartItem) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        val index = currentItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            currentItems[index] = item
            saveCartToPrefs(currentItems)
        }
    }

    fun removeFromCart(id: String) {
        val currentItems = _cartItems.value?.filter { it.id != id } ?: emptyList()
        saveCartToPrefs(currentItems)
    }

    fun clearCart() {
        saveCartToPrefs(emptyList())
    }

    fun getCartItemCount(): Int {
        return _cartItems.value?.sumOf { it.quantity } ?: 0
    }
}