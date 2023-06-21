package com.example.testsubsmanager.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testsubsmanager.R
//import com.example.testsubsmanager.ui.adapters.SubscriptionAdapter
import com.example.testsubsmanager.databinding.FragmentHomeBinding
import com.example.testsubsmanager.ui.adapters.CurrencyListAdapter
import com.example.testsubsmanager.ui.adapters.SubscriptionListAdapter
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class HomeFragment : DaggerFragment() {
    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var adapter: SubscriptionListAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)



        adapter = SubscriptionListAdapter(emptyList())
        binding.listSubscriptions.layoutManager = LinearLayoutManager(requireContext())
        val subscriptions = runBlocking { viewModel.getAllSubscriptions() }
        binding.amountSubscriptions.text = viewModel.getPrettyTotalMonthlyCost(subscriptions)
        adapter = SubscriptionListAdapter(subscriptions.sortedBy { it.nameSub })
        adapter.notifyDataSetChanged()
        binding.listSubscriptions.adapter = adapter

        binding.settingsButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        binding.addSubscriptionButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addSubscriptionFragment)
        }

    }
}