package com.example.mnauhouse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mnauhouse.R
import com.example.mnauhouse.data.model.Cat

class CatsAdapter(
    private val onItemClick: (Cat) -> Unit  // Callback pro kliknutí na kočku
) : RecyclerView.Adapter<CatsAdapter.CatViewHolder>() {

    private var cats = listOf<Cat>()

    // Metoda pro aktualizaci seznamu
    fun submitList(catList: List<Cat>) {
        cats = catList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cat, parent, false)  // Layout pro položku kočky
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(cats[position])
    }

    override fun getItemCount() = cats.size

    inner class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.catImage)
        private val nameText: TextView = itemView.findViewById(R.id.catName)
        private val breedText: TextView = itemView.findViewById(R.id.catBreed)
        private val ageText: TextView = itemView.findViewById(R.id.catAge)

        fun bind(cat: Cat) {
            nameText.text = cat.name
            breedText.text = cat.breed
            ageText.text = cat.age

            // Načtení obrázku pomocí Glide
            Glide.with(itemView.context)
                .load(cat.image)
                .placeholder(R.drawable.img)
                .into(imageView)

            itemView.setOnClickListener { onItemClick(cat) }  // Kliknutí na kočku
        }
    }
}