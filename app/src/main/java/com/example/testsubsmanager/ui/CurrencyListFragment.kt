package com.example.testsubsmanager.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.Currency
import com.example.testsubsmanager.databinding.FragmentCurrencyListBinding
import com.example.testsubsmanager.ui.adapters.CurrencyListAdapter
import com.example.testsubsmanager.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CurrencyListFragment : DaggerFragment() {
    private lateinit var navController: NavController
    private lateinit var adapter: CurrencyListAdapter
    private lateinit var binding: FragmentCurrencyListBinding
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        adapter = CurrencyListAdapter(emptyList())

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val currencies = runBlocking { viewModel.getAllCurrencies() }
        adapter = CurrencyListAdapter(currencies.sortedBy { it.code })
        adapter.setOnItemClickListener { currency ->
            viewModel.setSelectedCurrency(currency)
            navController.navigate(R.id.action_currencyListFragment_to_addSubscriptionFragment)
        }
        adapter.notifyDataSetChanged()
        binding.recyclerView.adapter = adapter
    }

}
