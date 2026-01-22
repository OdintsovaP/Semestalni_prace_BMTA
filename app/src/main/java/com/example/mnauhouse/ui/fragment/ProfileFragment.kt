package com.example.mnauhouse.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mnauhouse.R
import com.example.mnauhouse.data.repository.UserRepository
import com.google.android.material.button.MaterialButton

class ProfileFragment : Fragment() {

    private lateinit var userInfoTextView: TextView
    private lateinit var balanceTextView: TextView
    private lateinit var logoutButton: MaterialButton
    private lateinit var topUpButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userRepository = UserRepository(requireContext())
        val currentUser = userRepository.getCurrentUser()

        userInfoTextView = view.findViewById(R.id.userInfoTextView)
        balanceTextView = view.findViewById(R.id.balanceTextView)
        logoutButton = view.findViewById(R.id.logoutButton)
        topUpButton = view.findViewById(R.id.topUpButton)

        if (currentUser != null) {
            // Показать кабинет
            userInfoTextView.text = "Vítejte, ${currentUser.name}!\nEmail: ${currentUser.email}"
            balanceTextView.text = "Balans: ${currentUser.balance} Kč"
            logoutButton.visibility = View.VISIBLE
            topUpButton.visibility = View.VISIBLE

            // Кнопка пополнения баланса
            topUpButton.setOnClickListener {
                showTopUpDialog(userRepository, currentUser)
            }

            // Кнопка выхода
            logoutButton.setOnClickListener {
                userRepository.logout()
                Toast.makeText(context, "Odhlášeno", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment,
                    null, androidx.navigation.NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph, true).build())
            }
        } else {
            // Перенаправить на вход
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    // Диалог для пополнения баланса
    private fun showTopUpDialog(userRepository: UserRepository, user: com.example.mnauhouse.data.model.User) {
        val editText = EditText(requireContext()).apply {
            hint = "Zadejte částku (Kč)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Doplnit bilanci")
            .setView(editText)
            .setPositiveButton("Doplnit") { _, _ ->
                val amount = editText.text.toString().toDoubleOrNull()
                if (amount != null && amount > 0) {
                    userRepository.updateBalance(amount)
                    // Обновить UI
                    balanceTextView.text = "Balans: ${user.balance + amount} Kč"
                    Toast.makeText(context, "Balans doplněn o $amount Kč", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Neplatná částka", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Zrušit", null)
            .show()
    }
}