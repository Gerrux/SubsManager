package com.example.testsubsmanager.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.testsubsmanager.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dagger.android.support.DaggerFragment


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : DaggerFragment() {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        navController.navigate(R.id.action_splashScreenFragment_to_homeFragment)
    }
}