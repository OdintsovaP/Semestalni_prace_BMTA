package com.example.mnauhouse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mnauhouse.R
import com.example.mnauhouse.data.model.Cat

class CatsAdapter(
    private var cats: List<Cat>,
    private val onAdoptClick: (Cat) -> Unit
) : RecyclerView.Adapter<CatsAdapter.CatViewHolder>() {

    inner class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.catImage)
        val name: TextView = view.findViewById(R.id.catName)
        val info: TextView = view.findViewById(R.id.catInfo)
        val description: TextView = view.findViewById(R.id.catDescription)
        val button: Button = view.findViewById(R.id.adoptButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]

        holder.name.text = cat.name
        holder.info.text = "${cat.age} • ${cat.breed}"
        holder.description.text = cat.personality

        Glide.with(holder.itemView)
            .load(cat.image)
            .placeholder(R.drawable.animal)
            .into(holder.image)

        holder.button.setOnClickListener {
            onAdoptClick(cat)
        }
    }

    override fun getItemCount(): Int = cats.size

    fun updateData(newCats: List<Cat>) {
        cats = newCats
        notifyDataSetChanged()
    }
}
