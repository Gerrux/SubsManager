package com.example.testsubsmanager.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.databinding.FragmentAddSubscriptionBinding
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
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val backgroundColor = Color.parseColor(viewModel.getSelectedSubscription()?.color)
        val textColor = getContrastingColor(backgroundColor)
        binding.subscriptionHeader.background.setTint(backgroundColor)
        binding.subscriptionName.text = viewModel.getSelectedSubscription()?.nameSub ?: ""
        binding.subscriptionName.setTextColor(textColor)
        binding.subscriptionPrice.text = buildString {
            append("${viewModel.getSelectedSubscription()?.price.toString()} ")
            append(
                (viewModel.getSelectedSubscription()?.currency?.code
                ?: "")
            )
        }
        binding.subscriptionPrice.setTextColor(textColor)

        binding.startDateSubscription.text = viewModel.getSelectedSubscription()?.startDate.toString()
        binding.durationSubscription.text = viewModel.getSelectedSubscription()?.duration.toString()
        binding.durationTypeSubscription.text = viewModel.getSelectedSubscription()?.typeDuration?.name
            ?: ""


        binding.backButton.setOnClickListener {
            navController.navigate(R.id.action_subscriptionDetailFragment_to_homeFragment)
        }
        binding.editSubscriptionButton.setOnClickListener {
            navController.navigate(R.id.action_subscriptionDetailFragment_to_homeFragment) // To Edit fragment
        }
    }

    private fun getContrastingColor(background: Int): Int {
        val red = Color.red(background)
        val green = Color.green(background)
        val blue = Color.blue(background)
        val luminance = (red * 0.299 + green * 0.587 + blue * 0.114) / 255
        return if (luminance > 0.5) {
            Color.BLACK
        } else {
            Color.WHITE
        }
    }
}