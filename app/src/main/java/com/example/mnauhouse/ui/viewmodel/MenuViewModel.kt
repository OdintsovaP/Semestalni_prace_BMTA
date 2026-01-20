package com.example.mnauhouse.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mnauhouse.data.model.MenuItem
import com.example.mnauhouse.data.repository.MenuRepository

class MenuViewModel(private val repository: MenuRepository) : ViewModel() {

    val menuItems: LiveData<List<MenuItem>> = repository.menuItems

    // Factory pro ViewModel
    class MenuViewModelFactory(private val repository: MenuRepository) : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MenuViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}