package com.example.mnauhouse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mnauhouse.R
import com.example.mnauhouse.data.model.CartItem

class CheckoutAdapter : RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {

    private var items = listOf<CartItem>()

    fun submitList(list: List<CartItem>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checkout_item, parent, false)
        return CheckoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CheckoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.checkoutItemImage)
        private val name: TextView = itemView.findViewById(R.id.checkoutItemName)
        private val quantity: TextView = itemView.findViewById(R.id.checkoutItemQuantity)
        private val price: TextView = itemView.findViewById(R.id.checkoutItemPrice)

        fun bind(item: CartItem) {
            name.text = item.name
            quantity.text = "Množství: ${item.quantity}"
            price.text = "Cena: ${item.price * item.quantity} Kč"

            Glide.with(itemView.context)
                .load(item.image)
                .placeholder(R.drawable.img)
                .into(image)
        }
    }
}
