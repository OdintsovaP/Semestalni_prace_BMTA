package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mnauhouse.R
import com.example.mnauhouse.ui.adapter.QuickActionsAdapter
import com.example.mnauhouse.ui.adapter.FeaturesAdapter

class HomeFragment : Fragment() {

    private lateinit var quickActionsRecyclerView: RecyclerView
    private lateinit var featuresRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Nastavení RecyclerView pro rychlé akce (tlačítka)
        quickActionsRecyclerView = view.findViewById(R.id.quickActionsRecyclerView)
        quickActionsRecyclerView.layoutManager = GridLayoutManager(context, 2)  // 2 sloupce
        val quickActions = listOf(
            QuickAction("Menu", R.drawable.coffee, "menuFragment"),
            QuickAction("Objednat", R.drawable.ic_cart, "menuFragment"),
            QuickAction("Naše kočky", R.drawable.animal, "catsFragment"),
            QuickAction("Adopce", R.drawable.heart, "adoptionFragment"),
            QuickAction("Rezervace stolu", R.drawable.ic_calendar, null),  // Žádná navigace
            QuickAction("Vánoční nabídka", R.drawable.ic_gift, "menuFragment")
        )
        val quickActionsAdapter = QuickActionsAdapter { action ->
            action.navDestination?.let { destination ->
                findNavController().navigate(destination)  // Navigace
            }
        }
        quickActionsAdapter.submitList(quickActions)
        quickActionsRecyclerView.adapter = quickActionsAdapter

        // Nastavení RecyclerView pro funkce
        featuresRecyclerView = view.findViewById(R.id.featuresRecyclerView)
        featuresRecyclerView.layoutManager = GridLayoutManager(context, 2)
        val features = listOf(
            Feature("Vánoční akce", "Slevy a speciální nabídky", R.drawable.ic_gift),
            Feature("15 koček", "Čeká na vás", R.drawable.animal),
            Feature("Útulná atmosféra", "Zahřejte se", R.drawable.coffee),
            Feature("Adopce možná", "Najděte přítele", R.drawable.heart)
        )
        val featuresAdapter = FeaturesAdapter()
        featuresAdapter.submitList(features)
        featuresRecyclerView.adapter = featuresAdapter
    }

    // Datové třídy pro adaptéry
    data class QuickAction(val title: String, val iconRes: Int, val navDestination: String?)
    data class Feature(val title: String, val subtitle: String, val iconRes: Int)
}