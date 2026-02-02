package com.example.mnauhouse.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.mnauhouse.R

class ShelterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shelter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Zavolat útulku
        view.findViewById<Button>(R.id.buttonCallShelter)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+420777888999") // ← сюда вставь реальный номер
            startActivity(intent)
        }

        // Otevřít v mapách
        view.findViewById<Button>(R.id.buttonOpenMap)?.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=Kočičí+útulek+Pardubice,+Sluneční+12")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }
}
