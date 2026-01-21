package com.example.mnauhouse.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mnauhouse.R
import com.example.mnauhouse.ui.fragment.HomeFragment.QuickAction

class QuickActionsAdapter(
    private val onItemClick: (QuickAction) -> Unit
) : RecyclerView.Adapter<QuickActionsAdapter.QuickActionViewHolder>() {

    private var quickActions = listOf<QuickAction>()

    fun submitList(actions: List<QuickAction>) {
        quickActions = actions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickActionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quick_action, parent, false)
        return QuickActionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuickActionViewHolder, position: Int) {
        holder.bind(quickActions[position])
    }

    override fun getItemCount() = quickActions.size

    inner class QuickActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.actionIcon)
        private val titleTextView: TextView = itemView.findViewById(R.id.actionTitle)

        fun bind(action: QuickAction) {
            iconImageView.setImageResource(action.iconRes)
            titleTextView.text = action.title
            itemView.setOnClickListener { onItemClick(action) }
        }
    }
}