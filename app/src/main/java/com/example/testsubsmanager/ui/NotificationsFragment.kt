package com.example.testsubsmanager.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testsubsmanager.R
import com.example.testsubsmanager.databinding.FragmentNotificationsListBinding
import com.example.testsubsmanager.ui.adapters.NotificationListAdapter
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NotificationsFragment : DaggerFragment() {
    private lateinit var notificationList: RecyclerView
    private lateinit var navController: NavController
    private lateinit var adapter: NotificationListAdapter
    private lateinit var binding: FragmentNotificationsListBinding
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsListBinding.inflate(inflater, container, false)
        return binding.root
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        adapter = NotificationListAdapter(emptyList())

        binding.notificationList.layoutManager = LinearLayoutManager(requireContext())
        val notifications = runBlocking { viewModel.getAllNotifications() }
        if (notifications.isNotEmpty()) {
            adapter = NotificationListAdapter(notifications.sortedBy { it.date })
        } else {
            binding.textNotificationsEmpty.text = resources.getString(R.string.no_notification)
        }

    }
}