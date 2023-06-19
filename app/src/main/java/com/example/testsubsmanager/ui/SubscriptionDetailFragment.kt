package com.example.testsubsmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.databinding.FragmentSubscriptionDetailBinding
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SubscriptionDetailFragment : DaggerFragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSubscriptionDetailBinding
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSubscriptionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}