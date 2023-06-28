package com.example.testsubsmanager.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testsubsmanager.databinding.FragmentAnalyticsBinding
import com.example.testsubsmanager.ui.adapters.AnalyticsListAdapter
import com.example.testsubsmanager.ui.adapters.SubscriptionAnalyticsListAdapter
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import java.util.Locale
import javax.inject.Inject

class AnalyticsFragment : DaggerFragment() {
    private lateinit var navController: NavController
    private lateinit var adapterAnalytic: AnalyticsListAdapter
    private lateinit var adapterSubscription: SubscriptionAnalyticsListAdapter
    private lateinit var binding: FragmentAnalyticsBinding
    @Inject
    lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        adapterAnalytic = AnalyticsListAdapter(emptyList())
        binding.analyticsListMonths.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapterAnalytic = AnalyticsListAdapter(viewModel.getListAnalyticModel().reversed())
        adapterAnalytic.notifyDataSetChanged()
        adapterAnalytic.setOnItemClickListener { analyticsModel ->
            viewModel.setSelectedMonth(analyticsModel)
            adapterSubscription = SubscriptionAnalyticsListAdapter(viewModel.getSelectedMonth().listSubscription.sortedBy { it.nameSub })
            binding.analyticsListSubscritpions.adapter = adapterSubscription
            binding.analyticsCurrentMonth.text = viewModel.getSelectedMonth().month.capitalize(Locale.ROOT)
        }
        binding.analyticsCurrentMonth.text = viewModel.getSelectedMonth().month.capitalize(Locale.ROOT)
        adapterSubscription = SubscriptionAnalyticsListAdapter(emptyList())
        binding.analyticsListSubscritpions.layoutManager = LinearLayoutManager(requireContext())
        val subscriptions = viewModel.getSelectedMonth().listSubscription
        adapterSubscription = SubscriptionAnalyticsListAdapter(subscriptions.sortedBy { it.nameSub })
        binding.analyticsListSubscritpions.adapter = adapterSubscription
        binding.analyticsListMonths.adapter = adapterAnalytic
    }

}