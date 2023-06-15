package com.example.testsubsmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.Preference
import com.example.testsubsmanager.R


class SettingsFragment: Fragment() {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction()
            .replace(R.id.settings_container, PreferenceFragment())
            .commit()

        navController = Navigation.findNavController(view)

        val backButton = view.findViewById(R.id.back_settings_button) as Button
        backButton.setOnClickListener {
            navController.navigate(R.id.action_settingsFragment_to_homeFragment)
        }
    }
}