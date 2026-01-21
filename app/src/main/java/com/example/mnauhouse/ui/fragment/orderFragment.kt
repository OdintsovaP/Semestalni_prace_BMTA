package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
import com.google.android.material.button.MaterialButton

class OrderFragment : Fragment() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: MenuAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var checkoutButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
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
        recyclerView = view.findViewById(R.id.orderRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MenuAdapter(
            onItemClick = { /* Klik na položku */ },
            onAddToCartClick = { menuItem -> addToCart(menuItem) }
        )
        recyclerView.adapter = adapter

        // Tlačítko pro přechod k objednávce
        checkoutButton = view.findViewById(R.id.checkoutButton)
        checkoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_orderFragment_to_checkoutFragment)
        }

        // Pozorování dat
        viewModel.menuItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
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