package com.example.mnauhouse.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mnauhouse.MainActivity
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

        val repository = CatsRepository(requireContext())
        viewModel = ViewModelProvider(
            this,
            CatsViewModel.CatsViewModelFactory(repository)
        )[CatsViewModel::class.java]

        recyclerView = view.findViewById(R.id.catsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = CatsAdapter(emptyList()) { cat ->
            // Přepnutí na záložku Adopce
            (activity as MainActivity).bottomNavigationView.selectedItemId = R.id.adoptionFragment
        }

        recyclerView.adapter = adapter

        viewModel.cats.observe(viewLifecycleOwner) { cats ->
            adapter.updateData(cats)
        }
    }
}
