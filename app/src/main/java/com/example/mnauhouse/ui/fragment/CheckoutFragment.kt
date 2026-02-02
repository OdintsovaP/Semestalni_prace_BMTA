package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mnauhouse.R
import com.example.mnauhouse.data.repository.CartRepository
import com.example.mnauhouse.ui.adapter.CartAdapter
import com.example.mnauhouse.ui.adapter.CheckoutAdapter
import com.example.mnauhouse.ui.viewmodel.CartViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

class CheckoutFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: CheckoutAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalTextView: TextView
    private lateinit var payLaterButton: MaterialButton
    private lateinit var payNowButton: MaterialButton

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
            requireActivity(),
            CartViewModel.CartViewModelFactory(cartRepository)
        )[CartViewModel::class.java]

        recyclerView = view.findViewById(R.id.checkoutRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = CheckoutAdapter()
        recyclerView.adapter = adapter

        totalTextView = view.findViewById(R.id.totalTextView)
        payLaterButton = view.findViewById(R.id.payLaterButton)
        payNowButton = view.findViewById(R.id.payNowButton)

        payLaterButton.setOnClickListener {
            cartViewModel.clearCart()
            findNavController().navigate(R.id.homeFragment)
        }

        payNowButton.setOnClickListener {
            showPaymentBottomSheet()
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
            val total = items.sumOf { it.price * it.quantity }
            totalTextView.text = "Celkem: $total Kč"
        }
    }

    private fun showPaymentBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottomsheet_payment, null)

        val cancel = view.findViewById<MaterialButton>(R.id.buttonCancelPayment)
        val confirm = view.findViewById<MaterialButton>(R.id.buttonConfirmPayment)

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        confirm.setOnClickListener {
            dialog.dismiss()
            cartViewModel.clearCart()
            findNavController().navigate(R.id.homeFragment)
        }

        dialog.setContentView(view)
        dialog.show()
    }
}
