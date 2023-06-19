package com.example.testsubsmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testsubsmanager.R
//import com.example.testsubsmanager.ui.adapters.SubscriptionAdapter
import com.example.testsubsmanager.databinding.FragmentHomeBinding
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class HomeFragment : DaggerFragment() {
    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

//        binding.listSubscriptions.adapter = SubscriptionAdapter()
//
//        viewModel.getAllSubscriptions().observe(viewLifecycleOwner) { subscriptions ->
//            (binding.listSubscriptions.adapter as? SubscriptionAdapter)?.submitList(subscriptions)
//        }
        binding.settingsButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        binding.addSubscriptionButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addSubscriptionFragment)
        }

    }
}