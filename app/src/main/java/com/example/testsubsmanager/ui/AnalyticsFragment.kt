package com.example.testsubsmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testsubsmanager.R
import com.example.testsubsmanager.databinding.FragmentAnalyticsBinding
import com.example.testsubsmanager.databinding.FragmentCurrencyListBinding
import com.example.testsubsmanager.ui.adapters.AnalyticsListAdapter
import com.example.testsubsmanager.ui.adapters.CurrencyListAdapter
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AnalyticsFragment : DaggerFragment() {
    private lateinit var navController: NavController
    private lateinit var adapter: AnalyticsListAdapter
    private lateinit var binding: FragmentAnalyticsBinding
    @Inject
    lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

}