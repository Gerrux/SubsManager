package com.example.testsubsmanager.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testsubsmanager.R
import com.example.testsubsmanager.databinding.FragmentAddSubscriptionBinding
import com.example.testsubsmanager.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.android.support.DaggerFragment
import vadiole.colorpicker.ColorModel
import vadiole.colorpicker.ColorPickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


class AddSubscriptionFragment : DaggerFragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentAddSubscriptionBinding
    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var  colorPicker: ColorPickerDialog
    private var selectedColor: Int = Color.parseColor("#FFFFFF")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        colorPicker = ColorPickerDialog.Builder()
            .setInitialColor(selectedColor)
            .setColorModel(ColorModel.RGB)
            .setColorModelSwitchEnabled(true)
            .setButtonOkText(android.R.string.ok)
            .setButtonCancelText(android.R.string.cancel)
            .onColorSelected { color: Int ->
                selectedColor = color
                binding.etSubscriptionColor.setColorFilter(selectedColor)
                binding.etSubscriptionColor.imageTintList = ColorStateList.valueOf(selectedColor)
            }
            .create()
        binding.etSubscriptionColor.setOnClickListener {
            showColorPickerDialog(selectedColor) {
                selectedColor
            }
        }

        binding.etSubscriptionCurrency.setText(viewModel.getSelectedCurrency()?.code ?: "")

        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
            .setTitleText("SELECT A DATE")
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)

        val datePicker = datePickerBuilder.build()

        binding.etSubscriptionStartDate.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER")
            binding.etSubscriptionStartDate.clearFocus()
        }

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = Calendar.getInstance()
            selectedDate.timeInMillis = selection

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)

            binding.etSubscriptionStartDate.setText(formattedDate)
        }

        binding.etSubscriptionCurrency.setOnClickListener{
            navController.navigate(R.id.action_addSubscriptionFragment_to_currencyListFragment)
        }

        binding.cancelButton.setOnClickListener {
            navController.navigate(R.id.action_addSubscriptionFragment_to_homeFragment)
        }

        binding.saveSubscriptionButton.setOnClickListener {
            val hexColor = String.format("#%06X", 0xFFFFFF and selectedColor)
            viewModel.saveSubscription(
                subscriptionName=binding.etSubscriptionName.text.toString(),
                color=hexColor,
                price=binding.etSubscriptionPrice.text.toString().toDouble(),
                currency=binding.etSubscriptionCurrency.text.toString(),
                startDate=binding.etSubscriptionStartDate.text.toString(),
                duration=binding.etSubscriptionDuration.text.toString().toInt(),
                typeDuration=binding.etSubscriptionTypeDuration.text.toString()
            )
            navController.navigate(R.id.action_addSubscriptionFragment_to_homeFragment)
        }
    }

    private fun showColorPickerDialog(selectedColor: Int, callback: (Int) -> Unit){
        colorPicker = ColorPickerDialog.Builder()
            .setInitialColor(selectedColor)
            .setColorModel(ColorModel.RGB)
            .setColorModelSwitchEnabled(true)
            .setButtonOkText(android.R.string.ok)
            .setButtonCancelText(android.R.string.cancel)
            .onColorSelected { color: Int ->
                binding.etSubscriptionColor.setColorFilter(color)
                binding.etSubscriptionColor.imageTintList = ColorStateList.valueOf(color)
                callback(color)
            }
            .create()
        colorPicker.show(childFragmentManager, "color_picker")
    }
}