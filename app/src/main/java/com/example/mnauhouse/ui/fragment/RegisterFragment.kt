package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mnauhouse.R
import com.example.mnauhouse.data.model.User
import com.example.mnauhouse.data.repository.UserRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.UUID

class RegisterFragment : Fragment() {

    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var registerButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userRepository = UserRepository(requireContext())

        nameEditText = view.findViewById(R.id.nameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        registerButton = view.findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val existingUser = userRepository.getUsers().find { it.email == email }
                if (existingUser == null) {
                    val user = User(UUID.randomUUID().toString(), email, password, name)
                    userRepository.addUser(user)
                    Toast.makeText(context, "Registrace úspěšná!", Toast.LENGTH_SHORT).show()
                    // Переход обратно в LoginFragment
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Email už existuje", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Vyplňte všechna pole", Toast.LENGTH_SHORT).show()
            }
        }
    }
}