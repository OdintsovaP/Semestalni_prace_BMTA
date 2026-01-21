package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mnauhouse.R
import com.example.mnauhouse.data.repository.UserRepository
import com.google.android.material.button.MaterialButton

class ProfileFragment : Fragment() {

    private lateinit var userInfoTextView: TextView
    private lateinit var logoutButton: MaterialButton

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
        logoutButton = view.findViewById(R.id.logoutButton)

        if (currentUser != null) {
            // Показать кабинет
            userInfoTextView.text = "Vítejte, ${currentUser.name}!\nEmail: ${currentUser.email}"
            logoutButton.visibility = View.VISIBLE
            logoutButton.setOnClickListener {
                userRepository.logout()
                Toast.makeText(context, "Odhlášeno", Toast.LENGTH_SHORT).show()
                // Переход в LoginFragment
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment,
                    null, androidx.navigation.NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph, true).build())
            }
        } else {
            // Перенаправить на вход
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
}