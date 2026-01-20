package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mnauhouse.R
import com.example.mnauhouse.data.repository.CartRepository
import com.example.mnauhouse.ui.viewmodel.CartViewModel

class DonateFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_donate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializace ViewModel pro košík (pokud potřebujete)
        val cartRepository = CartRepository(requireContext())
        cartViewModel = ViewModelProvider(
            this,
            CartViewModel.CartViewModelFactory(cartRepository)
        )[CartViewModel::class.java]

        // Přidejte logiku pro tlačítka a výběr částky
        // Např. při kliknutí na "Přispět" zavolat cartViewModel pro uložení
    }
}