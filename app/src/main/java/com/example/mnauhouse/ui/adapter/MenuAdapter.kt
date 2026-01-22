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
    private val onUpdateCartQuantity: (MenuItem, Int) -> Unit,  // Новый callback для обновления количества
    private var cartItems: List<CartItem> = emptyList()  // Данные корзины
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var menuItems = listOf<MenuItem>()

    fun submitList(items: List<MenuItem>) {
        menuItems = items
        notifyDataSetChanged()
    }

    fun updateCartItems(cart: List<CartItem>) {
        // Обновить данные корзины и перерисовать
        (this as MenuAdapter).cartItems = cart
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

    override fun getItemCount() = menuItems.size

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

            Glide.with(itemView.context)
                .load(menuItem.image)
                .placeholder(R.drawable.img)
                .into(imageView)

            // Найти товар в корзине
            val cartItem = cartItems.find { it.id == menuItem.id }
            val quantity = cartItem?.quantity ?: 0

            if (quantity > 0) {
                // Товар в корзине — показать количество и кнопки + и -
                addButton.visibility = View.GONE
                quantityText.visibility = View.VISIBLE
                minusButton.visibility = View.VISIBLE
                plusButton.visibility = View.VISIBLE
                quantityText.text = quantity.toString()
            } else {
                // Товар не в корзине — показать кнопку добавления
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
                    onUpdateCartQuantity(menuItem, 0)  // Удалить из корзины
                }
            }
        }
    }
}