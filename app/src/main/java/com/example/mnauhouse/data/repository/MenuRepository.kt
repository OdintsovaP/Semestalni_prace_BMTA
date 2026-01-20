package com.example.mnauhouse.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mnauhouse.data.model.MenuItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MenuRepository(private val context: Context) {  // Přidán context jako parametr pro přístup k assets

    private val _menuItems = MutableLiveData<List<MenuItem>>()
    val menuItems: LiveData<List<MenuItem>> = _menuItems

    init {
        loadFromJson()  // Načtení dat z JSON při inicializaci
    }

    private fun loadFromJson() {
        try {
            // Otevření souboru menu.json z assets
            val jsonString = context.assets.open("menu.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            // Definice typu pro seznam MenuItem
            val type = object : TypeToken<List<MenuItem>>() {}.type
            val items: List<MenuItem> = gson.fromJson(jsonString, type)
            _menuItems.value = items  // Nastavení načtených dat
        } catch (e: Exception) {
            // Pokud se soubor nepodaří načíst (např. chyba v JSON), použijte prázdný seznam
            _menuItems.value = emptyList()
        }
    }
}