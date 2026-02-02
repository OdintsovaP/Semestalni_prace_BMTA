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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import android.widget.TextView

class OrderFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: MenuAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var goToCartButton: MaterialButton
    private lateinit var totalTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuRepository = MenuRepository(requireContext())
        val cartRepository = CartRepository(requireContext())

        menuViewModel = ViewModelProvider(
            this,
            MenuViewModel.MenuViewModelFactory(menuRepository)
        )[MenuViewModel::class.java]

        cartViewModel = ViewModelProvider(
            requireActivity(),
            CartViewModel.CartViewModelFactory(cartRepository)
        )[CartViewModel::class.java]

        recyclerView = view.findViewById(R.id.orderRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        totalTextView = view.findViewById(R.id.orderTotalTextView)

        adapter = MenuAdapter(
            onItemClick = { },
            onAddToCartClick = { menuItem -> addToCart(menuItem) },
            onUpdateCartQuantity = { menuItem, newQuantity ->
                val cartItem = cartViewModel.cartItems.value?.find { it.id == menuItem.id }
                if (cartItem != null) {
                    cartViewModel.updateQuantity(cartItem, newQuantity)
                } else if (newQuantity > 0) {
                    addToCart(menuItem, newQuantity)
                }
            }
        )
        recyclerView.adapter = adapter

        goToCartButton = view.findViewById(R.id.goToCartButton)
        goToCartButton.setOnClickListener {
            val bottomSheet = CartBottomSheetFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        menuViewModel.menuItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            adapter.updateCartItems(items)
            val total = items.sumOf { it.price * it.quantity }
            totalTextView.text = "Celkem: $total Kč"
        }
    }

    private fun addToCart(menuItem: MenuItem, quantity: Int = 1) {
        val cartItem = CartItem(
            id = menuItem.id,
            name = menuItem.name,
            price = menuItem.price,
            quantity = quantity,
            image = menuItem.image
        )
        cartViewModel.addToCart(cartItem)
    }
}
