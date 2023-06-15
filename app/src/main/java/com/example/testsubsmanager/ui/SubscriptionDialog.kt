package com.example.testsubsmanager.ui
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.testsubsmanager.R
import com.example.testsubsmanager.data.models.Subscription

class SubscriptionDialog : DialogFragment() {

    private lateinit var nameText: TextView
    private lateinit var priceText: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_subscription, null)

        nameText = view.findViewById(R.id.subscription_name_text)
        priceText = view.findViewById(R.id.subscription_price_text)

        // Получение информации о подписке и установка значений в текстовые поля
        val subscription = getSubscription()
        nameText.text = subscription.name
        priceText.text = subscription.price.toString()

        val editButton = view.findViewById<Button>(R.id.edit_subscription_button)
        val deleteButton = view.findViewById<Button>(R.id.delete_subscription_button)

        editButton.setOnClickListener {
            // Обработчик нажатия на кнопку редактирования
            dismiss()
        }

        deleteButton.setOnClickListener {
            // Обработчик нажатия на кнопку удаления
            dismiss()
        }

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .create()
    }

    private fun getSubscription(): Subscription {
        // Получение информации о подписке из базы данных или другого источника
        // Возвращение объекта Subscription

        TODO()
    }
}
