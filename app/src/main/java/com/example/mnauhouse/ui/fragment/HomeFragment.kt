package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mnauhouse.MainActivity
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
            QuickAction("Menu", R.drawable.coffee, R.id.menuFragment),
            QuickAction("Objednat", R.drawable.ic_cart, R.id.orderFragment),  // Изменено: ведет в OrderFragment
            QuickAction("Naše kočky", R.drawable.animal, R.id.catsFragment),
            QuickAction("Adopce", R.drawable.heart, R.id.adoptionFragment),
            QuickAction("Rezervace stolu", R.drawable.ic_calendar, null),  // Диалог
            QuickAction("Vánoční nabídka", R.drawable.ic_gift, R.id.menuFragment)
        )
        val quickActionsAdapter = QuickActionsAdapter { action ->
            if (action.navDestinationId != null) {
                val bottomNavigationView = (activity as MainActivity).bottomNavigationView
                if (action.navDestinationId == R.id.orderFragment) {
                    // Для OrderFragment используйте navigate (не в нижней навигации)
                    findNavController().navigate(R.id.orderFragment)
                } else {
                    // Для остальных — синхронизируйте с нижней навигацией
                    bottomNavigationView.selectedItemId = action.navDestinationId
                }
            } else {
                // Для "Rezervace stolu" показываем диалог
                showReservationDialog()
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
    data class QuickAction(val title: String, val iconRes: Int, val navDestinationId: Int?)
    data class Feature(val title: String, val subtitle: String, val iconRes: Int)

    // Метод для диалога бронирования
    private fun showReservationDialog() {
        findNavController().navigate(R.id.reservationFragment)
    }

}