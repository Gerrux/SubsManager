package com.example.testsubsmanager.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.testsubsmanager.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.example.testsubsmanager.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : DaggerFragment() {
    private lateinit var navController: NavController
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        GlobalScope.launch(Dispatchers.Main) {
            val currentDate = SimpleDateFormat("dd/MM/yyyy").format(Date())
            viewModel.fetchAndSaveCurrencyRates(currentDate)
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val currency = sharedPreferences.getString("currency", "RUB")
            currency?.let { viewModel.setDisplayedCurrency(it) }
            delay(2000)
            navController.navigate(R.id.action_splashScreenFragment_to_homeFragment)
        }

    }
}