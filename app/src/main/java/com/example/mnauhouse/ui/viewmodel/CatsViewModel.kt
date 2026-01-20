package com.example.mnauhouse.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mnauhouse.data.model.Cat
import com.example.mnauhouse.data.repository.CatsRepository

class CatsViewModel(private val repository: CatsRepository) : ViewModel() {

    val cats: LiveData<List<Cat>> = repository.cats  // LiveData pro seznam koček

    // Factory pro ViewModel
    class CatsViewModelFactory(private val repository: CatsRepository) : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CatsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CatsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}