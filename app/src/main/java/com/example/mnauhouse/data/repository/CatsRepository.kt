package com.example.mnauhouse.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mnauhouse.data.model.Cat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CatsRepository(private val context: Context) {  // Context pro přístup k assets

    private val _cats = MutableLiveData<List<Cat>>()  // Interní MutableLiveData
    val cats: LiveData<List<Cat>> = _cats  // Veřejná LiveData pro pozorování

    init {
        loadFromJson()  // Načtení dat při inicializaci
    }

    private fun loadFromJson() {
        try {
            // Otevření souboru cats.json z assets
            val jsonString = context.assets.open("cats.json").bufferedReader().use { it.readText() }
            val gson = Gson()  // Knihovna pro JSON
            // Definice typu pro seznam Cat
            val type = object : TypeToken<List<Cat>>() {}.type
            val catList: List<Cat> = gson.fromJson(jsonString, type)  // Parsování JSON
            _cats.value = catList  // Nastavení načtených dat
        } catch (e: Exception) {
            // Pokud se soubor nepodaří načíst (chyba v JSON nebo soubor neexistuje)
            _cats.value = emptyList()  // Prázdný seznam
        }
    }
}