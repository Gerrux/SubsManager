package com.example.testsubsmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.TypeDuration
import com.example.testsubsmanager.databinding.FragmentAddSubscriptionBinding
import com.example.testsubsmanager.databinding.FragmentHomeBinding
import com.example.testsubsmanager.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddSubscriptionFragment : DaggerFragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentAddSubscriptionBinding
    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        bottomNavigation = activity?.findViewById(R.id.bNav)!!
        bottomNavigation.visibility = View.GONE

        binding.cancelButton.setOnClickListener {
            showBottomNavigationView()
            navController.navigate(R.id.action_addSubscriptionFragment_to_homeFragment)
        }
    }

    private fun showBottomNavigationView() {
        bottomNavigation.visibility = View.VISIBLE
    }
}