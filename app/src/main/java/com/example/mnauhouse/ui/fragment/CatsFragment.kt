package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
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

        // Repository + ViewModel
        val repository = CatsRepository(requireContext())
        viewModel = ViewModelProvider(
            this,
            CatsViewModel.CatsViewModelFactory(repository)
        )[CatsViewModel::class.java]

        recyclerView = view.findViewById(R.id.catsRecyclerView)

        // Jeden layout manager pro oba režimy — adaptivita je v XML
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Adapter
        adapter = CatsAdapter(emptyList()) { cat ->
            // Kliknutí na tlačítko Adoptovat → přechod na stránku adopce
            findNavController().navigate(R.id.action_catsFragment_to_adoptionFragment)
        }

        recyclerView.adapter = adapter

        // Pozorování dat
        viewModel.cats.observe(viewLifecycleOwner) { cats ->
            adapter.updateData(cats)
        }
    }
}
