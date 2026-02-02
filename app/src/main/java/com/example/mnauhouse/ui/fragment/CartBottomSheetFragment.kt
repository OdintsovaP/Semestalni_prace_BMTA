package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mnauhouse.MainActivity
import com.example.mnauhouse.R
import com.example.mnauhouse.data.repository.CartRepository
import com.example.mnauhouse.ui.adapter.CartAdapter
import com.example.mnauhouse.ui.viewmodel.CartViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class CartBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var orderButton: MaterialButton
    private lateinit var clearButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartRepository = CartRepository(requireContext())
        cartViewModel = ViewModelProvider(
            requireActivity(),
            CartViewModel.CartViewModelFactory(cartRepository)
        )[CartViewModel::class.java]

        recyclerView = view.findViewById(R.id.cartRecyclerView)
        emptyTextView = view.findViewById(R.id.emptyCartTextView)
        orderButton = view.findViewById(R.id.orderButton)
        clearButton = view.findViewById(R.id.clearButton)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CartAdapter(
            onUpdateQuantity = { cartItem, newQuantity ->
                if (newQuantity > 0) {
                    cartItem.quantity = newQuantity
                    cartViewModel.updateCartItem(cartItem)
                } else {
                    cartViewModel.removeFromCart(cartItem.id)
                }
            }
        )

        recyclerView.adapter = adapter

        orderButton.setOnClickListener {
            if (cartViewModel.cartItems.value?.isNotEmpty() == true) {
                val navController = (activity as MainActivity).navController
                navController.navigate(R.id.checkoutFragment)
                dismiss()
            } else {
                Toast.makeText(context, "Košík je prázdný", Toast.LENGTH_SHORT).show()
            }
        }

        clearButton.setOnClickListener {
            cartViewModel.clearCart()
            Toast.makeText(context, "Košík byl vyprázdněn", Toast.LENGTH_SHORT).show()
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            if (items.isEmpty()) {
                recyclerView.visibility = View.GONE
                emptyTextView.visibility = View.VISIBLE
                orderButton.isEnabled = false
                clearButton.isEnabled = false
            } else {
                recyclerView.visibility = View.VISIBLE
                emptyTextView.visibility = View.GONE
                orderButton.isEnabled = true
                clearButton.isEnabled = true
                adapter.submitList(items)
            }
        }
    }
}
