package com.example.mnauhouse.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mnauhouse.R
import java.util.Calendar

class ReservationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val inputName = view.findViewById<EditText>(R.id.inputName)
        val inputPhone = view.findViewById<EditText>(R.id.inputPhone)
        val inputDate = view.findViewById<EditText>(R.id.inputDate)
        val inputTime = view.findViewById<EditText>(R.id.inputTime)
        val buttonReserve = view.findViewById<Button>(R.id.buttonReserve)

        val calendar = Calendar.getInstance()

        // Výběr data
        inputDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    inputDate.setText("$day.${month + 1}.$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Výběr času
        inputTime.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                { _, hour, minute ->
                    val formatted = String.format("%02d:%02d", hour, minute)
                    inputTime.setText(formatted)
                },
                12,
                0,
                true
            ).show()
        }

        // Potvrzení rezervace
        buttonReserve.setOnClickListener {
            val name = inputName.text.toString()
            val phone = inputPhone.text.toString()
            val date = inputDate.text.toString()
            val time = inputTime.text.toString()

            if (name.isEmpty() || phone.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(requireContext(), "Vyplňte všechna pole", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(
                requireContext(),
                "Rezervace potvrzena:\n$name\n$date $time",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
