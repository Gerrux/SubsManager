package com.example.testsubsmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testsubsmanager.R
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class SettingsFragment: DaggerFragment(), PreferenceFragment.SettingsChangeListener {
    private lateinit var navController: NavController
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferenceFragment = PreferenceFragment()
        preferenceFragment.settingsChangeListener = this

        childFragmentManager.beginTransaction()
            .replace(R.id.settings_container, preferenceFragment)
            .commit()

        navController = Navigation.findNavController(view)

        val backButton = view.findViewById(R.id.back_settings_button) as Button
        backButton.setOnClickListener {
            navController.navigate(R.id.action_settingsFragment_to_homeFragment)
        }
    }

    override fun onSettingsChanged(key: String?) {
        viewModel.settingsChanged(key, requireContext())
    }
}