package com.example.testsubsmanager.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.testsubsmanager.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

        val currentDate = SimpleDateFormat("dd/MM/yyyy").format(Date())
        GlobalScope.launch(Dispatchers.Main) {
            withTimeoutOrNull(2000) {
                viewModel.fetchAndSaveCurrencyRates(currentDate)
            }
        }

        navController.navigate(R.id.action_splashScreenFragment_to_homeFragment)


    }
}