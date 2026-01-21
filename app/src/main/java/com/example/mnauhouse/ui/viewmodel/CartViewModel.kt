package com.example.mnauhouse.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.mnauhouse.data.model.CartItem
import com.example.mnauhouse.data.repository.CartRepository

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    val cartItems: LiveData<List<CartItem>> = repository.cartItems
    val allCartItems: LiveData<List<CartItem>> = cartItems  // Для CheckoutFragment
    val cartItemCount: LiveData<Int> = cartItems.map { items -> items.sumOf { it.quantity } }

    fun addToCart(item: CartItem) {
        repository.addToCart(item)
    }

    fun updateCartItem(item: CartItem) {
        repository.updateCartItem(item)
    }

    fun removeFromCart(id: String) {
        repository.removeFromCart(id)
    }

    fun clearCart() {
        repository.clearCart()
    }

    fun getTotalPrice(): LiveData<Int> {
        return cartItems.map { items -> items.sumOf { it.price * it.quantity } }
    }

    class CartViewModelFactory(private val repository: CartRepository) : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CartViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}