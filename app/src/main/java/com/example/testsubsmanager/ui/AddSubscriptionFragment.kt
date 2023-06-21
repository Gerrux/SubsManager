package com.example.testsubsmanager.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.TypeDuration
import com.example.testsubsmanager.databinding.FragmentAddSubscriptionBinding
import com.example.testsubsmanager.ui.models.FormData
import com.example.testsubsmanager.viewmodels.MainViewModel
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
                binding.etSubscriptionColor.setColorFilter(color)
                binding.etSubscriptionColor.imageTintList = ColorStateList.valueOf(color)
            }
            .create()
        binding.etSubscriptionColor.setOnClickListener {
            showColorPickerDialog(selectedColor) {
                selectedColor = it
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
            saveFormData()
            navController.navigate(R.id.action_addSubscriptionFragment_to_currencyListFragment)
        }

        val options = TypeDuration.values().map { it.name }.toTypedArray()
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, options)
        binding.etSubscriptionTypeDuration.setAdapter(adapter)
        binding.etSubscriptionTypeDuration.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.hideKeyboard()
            }
        }
        binding.cancelButton.setOnClickListener {
            navController.navigate(R.id.action_addSubscriptionFragment_to_homeFragment)
        }

        binding.saveSubscriptionButton.setOnClickListener {
            try {
            val color = selectedColor
            val hexColor = String.format("#%06X", 0xFFFFFF and color)
            Log.e("SB grrx", hexColor)
            viewModel.saveSubscription(
                subscriptionName=binding.etSubscriptionName.text.toString(),
                color=hexColor,
                price=binding.etSubscriptionPrice.text.toString().toDouble(),
                startDate=binding.etSubscriptionStartDate.text.toString(),
                duration=binding.etSubscriptionDuration.text.toString().toInt(),
                typeDuration=binding.etSubscriptionTypeDuration.text.toString()
            )
            viewModel.formData = MutableLiveData()
            navController.navigate(R.id.action_addSubscriptionFragment_to_homeFragment)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Not all fields are filled in", Toast.LENGTH_LONG).show()
            }
        }
        loadFormData()
    }

    private fun loadFormData(){
        val formData = viewModel.formData.value
        binding.etSubscriptionName.setText(formData?.subscriptionName ?: "")
        val color = formData?.color
        if (color != null){
            selectedColor = Color.parseColor(color)
            binding.etSubscriptionColor.setColorFilter(selectedColor)
        } else {
            selectedColor = Color.parseColor("#FFFFFF")
        }
        binding.etSubscriptionPrice.setText(formData?.price ?: "")
        binding.etSubscriptionStartDate.setText(formData?.startDate ?: "")
        binding.etSubscriptionDuration.setText(formData?.duration ?: "1")
        binding.etSubscriptionTypeDuration.setText(formData?.typeDuration ?: "MONTHS")
    }

    private fun saveFormData(){
        val formData = FormData(
            subscriptionName = binding.etSubscriptionName.text.toString(),
            color = String.format("#%06X", 0xFFFFFF and selectedColor),
            price = binding.etSubscriptionPrice.text.toString(),
            startDate = binding.etSubscriptionStartDate.text.toString(),
            duration = binding.etSubscriptionDuration.text.toString(),
            typeDuration = binding.etSubscriptionTypeDuration.text.toString()
        )
        viewModel.formData.value = formData
    }

    private fun View.hideKeyboard(): Boolean {
        try {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) { }
        return false
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