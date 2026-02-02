package com.example.mnauhouse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mnauhouse.R
import com.example.mnauhouse.data.model.CartItem

class CartAdapter(
    private val onUpdateQuantity: (CartItem, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems = listOf<CartItem>()

    fun submitList(items: List<CartItem>) {
        cartItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount() = cartItems.size

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.cartItemImage)
        private val nameText: TextView = itemView.findViewById(R.id.cartItemName)
        private val quantityText: TextView = itemView.findViewById(R.id.cartItemQuantity)
        private val priceText: TextView = itemView.findViewById(R.id.cartItemPrice)
        private val minusButton: ImageButton = itemView.findViewById(R.id.minusButton)
        private val plusButton: ImageButton = itemView.findViewById(R.id.plusButton)

        fun bind(cartItem: CartItem) {

            nameText.text = cartItem.name
            quantityText.text = "${cartItem.quantity}×"
            priceText.text = "${cartItem.price * cartItem.quantity} Kč"

            Glide.with(itemView.context)
                .load(cartItem.image)
                .placeholder(R.drawable.img)
                .into(imageView)

            // Zvýšení množství
            plusButton.setOnClickListener {
                val newQuantity = cartItem.quantity + 1
                onUpdateQuantity(cartItem, newQuantity)
            }

            // Snížení množství nebo odstranění položky
            minusButton.setOnClickListener {
                val newQuantity = cartItem.quantity - 1
                if (newQuantity > 0) {
                    onUpdateQuantity(cartItem, newQuantity)
                } else {
                    onUpdateQuantity(cartItem, 0)
                }
            }
        }
    }
}
