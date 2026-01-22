package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mnauhouse.R
import com.example.mnauhouse.data.repository.CartRepository
import com.example.mnauhouse.ui.adapter.CartAdapter
import com.example.mnauhouse.ui.viewmodel.CartViewModel
import com.google.android.material.button.MaterialButton

class CheckoutFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalTextView: TextView
    private lateinit var payLaterButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartRepository = CartRepository(requireContext())
        cartViewModel = ViewModelProvider(
            this,
            CartViewModel.CartViewModelFactory(cartRepository)
        )[CartViewModel::class.java]

        // Nastavení RecyclerView pro košík
        recyclerView = view.findViewById(R.id.checkoutRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CartAdapter(
            onUpdateQuantity = { cartItem, newQuantity ->
                cartItem.quantity = newQuantity
                cartViewModel.updateCartItem(cartItem)
            },
            onRemoveItem = { cartItem ->
                cartViewModel.removeFromCart(cartItem.id)
            }
        )
        recyclerView.adapter = adapter

        totalTextView = view.findViewById(R.id.totalTextView)
        payLaterButton = view.findViewById(R.id.payLaterButton)
        payLaterButton.setOnClickListener {
            Toast.makeText(context, "Objednávka potvrzena! Zaplatíte při vyzvednutí.", Toast.LENGTH_SHORT).show()
            cartViewModel.clearCart()  // Очистить корзину после заказа
            // Можно добавить переход обратно в HomeFragment
        }

        // Pozorování dat
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
            val total = items.sumOf { it.price * it.quantity }
            totalTextView.text = "Celkem: $total Kč"
        }
    }
}