package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mnauhouse.R
import com.example.mnauhouse.data.repository.CatsRepository
import com.example.mnauhouse.ui.adapter.CatsAdapter
import com.example.mnauhouse.ui.viewmodel.CatsViewModel
class CatsFragment : Fragment() {

    private lateinit var viewModel: CatsViewModel
    private lateinit var adapter: CatsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializace repository a ViewModel
        val repository = CatsRepository(requireContext())
        viewModel = ViewModelProvider(
            this,
            CatsViewModel.CatsViewModelFactory(repository)
        )[CatsViewModel::class.java]

        // Nastavení RecyclerView (mřížka pro kočky)
        recyclerView = view.findViewById(R.id.catsRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)  // 2 sloupce
        adapter = CatsAdapter { cat ->
            // Zpracování kliknutí na kočku (např. otevření detailu)
            // Můžete přidat dialog nebo nový fragment
        }
        recyclerView.adapter = adapter

        // Pozorování dat
        viewModel.cats.observe(viewLifecycleOwner) { cats ->
            adapter.submitList(cats)
        }
    }
}