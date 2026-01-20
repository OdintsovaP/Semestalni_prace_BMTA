package com.example.mnauhouse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mnauhouse.R
import com.example.mnauhouse.ui.fragment.HomeFragment.Feature

class FeaturesAdapter : RecyclerView.Adapter<FeaturesAdapter.FeatureViewHolder>() {

    private var features = listOf<Feature>()

    // Metoda pro aktualizaci seznamu
    fun submitList(featureList: List<Feature>) {
        features = featureList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feature, parent, false)
        return FeatureViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bind(features[position])
    }

    override fun getItemCount() = features.size

    inner class FeatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.featureIcon)
        private val titleTextView: TextView = itemView.findViewById(R.id.featureTitle)
        private val subtitleTextView: TextView = itemView.findViewById(R.id.featureSubtitle)

        fun bind(feature: Feature) {
            iconImageView.setImageResource(feature.iconRes)  // Ikona
            titleTextView.text = feature.title  // Název
            subtitleTextView.text = feature.subtitle  // Popis
        }
    }
}