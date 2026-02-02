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
import com.example.mnauhouse.data.model.MenuItem

class MenuAdapter(
    private val onItemClick: (MenuItem) -> Unit,
    private val onAddToCartClick: (MenuItem) -> Unit,
    private val onUpdateCartQuantity: (MenuItem, Int) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var menuItems: List<MenuItem> = emptyList()
    private var cartItems: List<CartItem> = emptyList()

    fun submitList(items: List<MenuItem>) {
        menuItems = items
        notifyDataSetChanged()
    }

    fun updateCartItems(cart: List<CartItem>) {
        cartItems = cart
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.itemImage)
        private val nameText: TextView = itemView.findViewById(R.id.itemName)
        private val descriptionText: TextView = itemView.findViewById(R.id.itemDescription)
        private val priceText: TextView = itemView.findViewById(R.id.itemPrice)
        private val addButton: ImageButton = itemView.findViewById(R.id.addToCartButton)
        private val quantityText: TextView = itemView.findViewById(R.id.quantityText)
        private val minusButton: ImageButton = itemView.findViewById(R.id.minusButton)
        private val plusButton: ImageButton = itemView.findViewById(R.id.plusButton)

        fun bind(menuItem: MenuItem) {
            nameText.text = menuItem.name
            descriptionText.text = menuItem.description
            priceText.text = "${menuItem.price} Kč"

            // --- УНИВЕРСАЛЬНАЯ ЛОГИКА ЗАГРУЗКИ КАРТИНОК ---
            val context = itemView.context
            val imagePath = menuItem.image

            if (imagePath.startsWith("http")) {
                // Если это ссылка, грузим из интернета
                Glide.with(context)
                    .load(imagePath)
                    .placeholder(R.drawable.coffee)
                    .error(R.drawable.coffee)
                    .into(imageView)
            } else {
                // Иначе, ищем картинку в drawable
                val resourceId = context.resources.getIdentifier(imagePath, "drawable", context.packageName)
                Glide.with(context)
                    .load(if (resourceId == 0) R.drawable.coffee else resourceId)
                    .placeholder(R.drawable.coffee)
                    .error(R.drawable.coffee)
                    .into(imageView)
            }
            // --- КОНЕЦ ИЗМЕНЕНИЙ ---

            val cartItem = cartItems.find { it.id == menuItem.id }
            val quantity = cartItem?.quantity ?: 0

            if (quantity > 0) {
                addButton.visibility = View.GONE
                quantityText.visibility = View.VISIBLE
                minusButton.visibility = View.VISIBLE
                plusButton.visibility = View.VISIBLE
                quantityText.text = quantity.toString()
            } else {
                addButton.visibility = View.VISIBLE
                quantityText.visibility = View.GONE
                minusButton.visibility = View.GONE
                plusButton.visibility = View.GONE
            }

            itemView.setOnClickListener { onItemClick(menuItem) }
            addButton.setOnClickListener { onAddToCartClick(menuItem) }
            plusButton.setOnClickListener { onUpdateCartQuantity(menuItem, quantity + 1) }
            minusButton.setOnClickListener {
                if (quantity > 1) {
                    onUpdateCartQuantity(menuItem, quantity - 1)
                } else {
                    onUpdateCartQuantity(menuItem, 0)
                }
            }
        }
    }
}
