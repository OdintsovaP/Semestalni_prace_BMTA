package com.example.mnauhouse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mnauhouse.R
import com.example.mnauhouse.data.model.MenuItem

class MenuAdapter(
    private val onItemClick: (MenuItem) -> Unit,  // Callback pro kliknutí na položku
    private val onAddToCartClick: (MenuItem) -> Unit  // Callback pro přidání do košíku
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var menuItems = listOf<MenuItem>()  // Seznam položek menu

    // Metoda pro aktualizaci seznamu (volá se z Fragment)
    fun submitList(items: List<MenuItem>) {
        menuItems = items
        notifyDataSetChanged()  // Aktualizace zobrazení
    }

    // Vytvoření ViewHolder pro každý prvek
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)  // Načtení layout pro položku
        return MenuViewHolder(view)
    }

    // Naplnění ViewHolder daty
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuItems[position])  // Předání dat do ViewHolder
    }

    // Počet položek v seznamu
    override fun getItemCount() = menuItems.size

    // Vnitřní třída pro ViewHolder (drží reference na UI prvky)
    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.itemImage)  // Obrázek položky
        private val nameText: TextView = itemView.findViewById(R.id.itemName)  // Název
        private val descriptionText: TextView = itemView.findViewById(R.id.itemDescription)  // Popis
        private val priceText: TextView = itemView.findViewById(R.id.itemPrice)  // Cena
        private val addButton: View = itemView.findViewById(R.id.addToCartButton)  // Tlačítko "Přidat"

        fun bind(menuItem: MenuItem) {
            // Nastavení textů
            nameText.text = menuItem.name
            descriptionText.text = menuItem.description
            priceText.text = "${menuItem.price} Kč"  // Formátování ceny

            // Načtení obrázku pomocí Glide (knihovna pro obrázky)
            Glide.with(itemView.context)
                .load(menuItem.image)  // URL obrázku
                .placeholder(R.drawable.img)  // Zástupný obrázek při načítání
                .into(imageView)  // Nastavení do ImageView

            // Kliknutí na celou položku
            itemView.setOnClickListener { onItemClick(menuItem) }
            // Kliknutí na tlačítko "Přidat do košíku"
            addButton.setOnClickListener { onAddToCartClick(menuItem) }
        }
    }
}