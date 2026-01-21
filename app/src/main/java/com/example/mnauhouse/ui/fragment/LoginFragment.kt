package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mnauhouse.R
import com.example.mnauhouse.data.repository.UserRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var registerButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userRepository = UserRepository(requireContext())

        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        loginButton = view.findViewById(R.id.loginButton)
        registerButton = view.findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val user = userRepository.findUser(email, password)
                if (user != null) {
                    userRepository.saveCurrentUser(user)
                    Toast.makeText(context, "Přihlášení úspěšné!", Toast.LENGTH_SHORT).show()
                    // Переход в личный кабинет с очисткой back stack
                    findNavController().navigate(R.id.action_loginFragment_to_profileFragment,
                        null, androidx.navigation.NavOptions.Builder()
                            .setPopUpTo(R.id.nav_graph, true).build())
                } else {
                    Toast.makeText(context, "Nesprávné údaje", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Vyplňte všechna pole", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            // Переход в регистрацию
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}