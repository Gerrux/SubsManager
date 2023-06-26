package com.example.testsubsmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testsubsmanager.R
import com.example.testsubsmanager.databinding.FragmentSettingsBinding
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class SettingsFragment: DaggerFragment(), PreferenceFragment.SettingsChangeListener {
    private lateinit var navController: NavController
    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferenceFragment = PreferenceFragment()
        preferenceFragment.settingsChangeListener = this

        childFragmentManager.beginTransaction()
            .replace(R.id.settings_container, preferenceFragment)
            .commit()

        navController = Navigation.findNavController(view)

        binding.backSettingsButton.setOnClickListener {
            navController.navigate(R.id.action_settingsFragment_to_homeFragment)
        }
    }

    override fun onSettingsChanged(key: String?) {
        viewModel.settingsChanged(key, requireContext())
    }
}