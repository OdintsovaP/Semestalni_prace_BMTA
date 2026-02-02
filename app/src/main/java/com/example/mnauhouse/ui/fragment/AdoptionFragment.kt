package com.example.mnauhouse.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mnauhouse.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class AdoptionFragment : Fragment() {

    // Datová třída pro položky zdarma
    data class FreeItem(
        val title: String,
        val price: String,
        val details: List<String>
    )

    // Seznam položek zdarma
    private val freeItems = listOf(
        FreeItem(
            title = "Očkování",
            price = "1500 Kč",
            details = listOf(
                "Základní očkování proti kočičí rýmě",
                "Očkování proti panleukopenii",
                "Očkování proti vzteklině",
                "Veterinární průkaz s razítky",
                "Doporučení dalšího očkování"
            )
        ),
        FreeItem(
            title = "Kastrace",
            price = "2500 Kč",
            details = listOf(
                "Provedeno certifikovaným veterinářem",
                "Včetně pooperační péče",
                "Kontrolní návštěva zdarma",
                "Léky na zotavení v ceně",
                "Telefonická podpora 24/7"
            )
        ),
        FreeItem(
            title = "Veterinární pas",
            price = "500 Kč",
            details = listOf(
                "Mezinárodně platný pas",
                "Záznam o všech očkováních",
                "Historie zdravotních kontrol",
                "Informace o čipování",
                "Nutné pro cesty do zahraničí"
            )
        ),
        FreeItem(
            title = "Startovací balíček",
            price = "1200 Kč",
            details = listOf(
                "Kvalitní krmivo na 2 týdny",
                "Miska na jídlo a vodu",
                "Škrabadlo a hračky",
                "Podestýlka a záchod",
                "Průvodce péčí o kočku",
                "Slevový kupón na další nákupy"
            )
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_adopce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // GridLayout pro položky zdarma
        val grid = view.findViewById<GridLayout>(R.id.gridFreeItems)

        // Vytvoření karet programově
        freeItems.forEach { item ->
            val card = layoutInflater.inflate(R.layout.item_free_service, grid, false) as CardView

            val title = card.findViewById<TextView>(R.id.textItemTitle)
            val price = card.findViewById<TextView>(R.id.textItemPrice)

            title.text = item.title
            price.text = item.price

            card.setOnClickListener {
                showDetailsBottomSheet(item)
            }

            grid.addView(card)
        }

        // Tlačítka nahoře a dole
        view.findViewById<Button>(R.id.buttonCallInfo)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+420777111222")
            startActivity(intent)
        }


        view.findViewById<Button>(R.id.buttonCall)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+420777111222")
            startActivity(intent)
        }


        view.findViewById<Button>(R.id.buttonVisit)?.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=Mnau+House+Cat+Café+Pardubice")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }


        view.findViewById<Button>(R.id.buttonShelterInfo)?.setOnClickListener {
            findNavController().navigate(R.id.action_adoptionFragment_to_shelterFragment)
        }

    }

    // BottomSheetDialog s detaily služby
    private fun showDetailsBottomSheet(item: FreeItem) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottomsheet_free_item_details, null)

        val title = view.findViewById<TextView>(R.id.textDetailTitle)
        val price = view.findViewById<TextView>(R.id.textDetailPrice)
        val list = view.findViewById<TextView>(R.id.textDetailList)
        val close = view.findViewById<Button>(R.id.buttonCloseDetail)

        title.text = item.title
        price.text = item.price

        // Seznam detailů jako odrážky
        list.text = item.details.joinToString("\n• ", prefix = "• ")

        close.setOnClickListener { dialog.dismiss() }

        dialog.setContentView(view)
        dialog.show()
    }
}
