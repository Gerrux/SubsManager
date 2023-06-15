package com.example.testsubsmanager.ui

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.testsubsmanager.R

class PreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val aboutDevelopersPref = findPreference<Preference>("about_developers_pref")
        aboutDevelopersPref?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            showAboutDevelopersDialog()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(
        sharedPreferences: SharedPreferences?,
        key: String?
    ) {
        when (key) {
            "notifications" -> {
                val notificationsPref = findPreference<SwitchPreferenceCompat>(key)
                val isEnabled = notificationsPref?.isChecked ?: false
                // Обработка изменения настройки
            }
            "username" -> {
                val usernamePref = findPreference<EditTextPreference>(key)
                val username = usernamePref?.text ?: ""
                // Обработка изменения настройки
            }
            "theme" -> {
                val themePref = findPreference<ListPreference>(key)
                val theme = themePref?.value ?: ""
                // Обработка изменения настройки
            }
            "currency" -> {
                val currencyPref = findPreference<ListPreference>(key)
                val currency = currencyPref?.value ?: ""
                // Обработка изменения настройки
            }
        }
    }

    private fun showAboutDevelopersDialog() {
        Log.d(TAG, "showAboutDevelopersDialog() called")
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("About Developers")
        dialogBuilder.setMessage("This app was developed by Ilya Kalinin and Daniil Pokrishkin.")
        dialogBuilder.setPositiveButton("OK", null)
        dialogBuilder.show()
    }
}