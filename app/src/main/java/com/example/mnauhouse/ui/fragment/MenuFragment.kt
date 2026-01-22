package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mnauhouse.R
import com.example.mnauhouse.data.model.CartItem
import com.example.mnauhouse.data.model.MenuItem
import com.example.mnauhouse.data.repository.CartRepository
import com.example.mnauhouse.data.repository.MenuRepository
import com.example.mnauhouse.ui.adapter.MenuAdapter
import com.example.mnauhouse.ui.viewmodel.CartViewModel
import com.example.mnauhouse.ui.viewmodel.MenuViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MenuFragment : Fragment() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: MenuAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var chipGroup: ChipGroup

    private var selectedCategory = "all"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializace repository a ViewModel
        val menuRepository = MenuRepository(requireContext())
        val cartRepository = CartRepository(requireContext())

        viewModel = ViewModelProvider(
            this,
            MenuViewModel.MenuViewModelFactory(menuRepository)
        )[MenuViewModel::class.java]

        cartViewModel = ViewModelProvider(
            this,
            CartViewModel.CartViewModelFactory(cartRepository)
        )[CartViewModel::class.java]

        // Nastavení RecyclerView
        recyclerView = view.findViewById(R.id.menuRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MenuAdapter(
            onItemClick = { /* Zpracování kliknutí na položku */ },
            onAddToCartClick = { menuItem -> addToCart(menuItem) },
            onUpdateCartQuantity = { menuItem, newQuantity ->
                // Находим соответствующий CartItem
                val cartItem = cartViewModel.cartItems.value?.find { it.id == menuItem.id }
                if (cartItem != null) {
                    // Вызываем метод из ViewModel для обновления
                    cartViewModel.updateQuantity(cartItem, newQuantity)
                }
            }
        )
        recyclerView.adapter = adapter

        // Nastavení chipů pro kategorie
        chipGroup = view.findViewById(R.id.categoryChipGroup)
        setupCategoryChips()

        // Pozorování dat
        viewModel.menuItems.observe(viewLifecycleOwner) { items ->
            filterAndDisplayItems(items)
        }
    }

    private fun setupCategoryChips() {
        val categories = listOf("all", "hot", "cold", "desserts", "specials", "cats", "christmas")

        categories.forEach { category ->
            val chip = Chip(context).apply {
                text = getCategoryDisplayName(category)
                isCheckable = true
                isChecked = category == selectedCategory
                setOnClickListener {
                    selectedCategory = category
                    updateChipSelection()
                    viewModel.menuItems.value?.let { filterAndDisplayItems(it) }
                }
            }
            chipGroup.addView(chip)
        }
    }

    private fun updateChipSelection() {
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.isChecked = chip.text == getCategoryDisplayName(selectedCategory)
        }
    }

    private fun getCategoryDisplayName(category: String): String {
        return when (category) {
            "all" -> "Vše"
            "hot" -> "Teplé"
            "cold" -> "Studené"
            "desserts" -> "Dezerty"
            "specials" -> "Speciality"
            "cats" -> "Pro kočky"
            "christmas" -> "Vánoce"
            else -> category
        }
    }

    private fun filterAndDisplayItems(items: List<MenuItem>) {
        val filteredItems = if (selectedCategory == "all") {
            items
        } else {
            items.filter { it.category == selectedCategory }
        }
        adapter.submitList(filteredItems)
    }

    private fun addToCart(menuItem: MenuItem) {
        val cartItem = CartItem(
            id = menuItem.id,
            name = menuItem.name,
            price = menuItem.price,
            quantity = 1,
            image = menuItem.image
        )
        cartViewModel.addToCart(cartItem)
    }
}