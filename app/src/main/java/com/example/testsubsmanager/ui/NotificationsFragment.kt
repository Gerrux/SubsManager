package com.example.testsubsmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.testsubsmanager.R
import com.example.testsubsmanager.adapters.NotificationListAdapter
import com.example.testsubsmanager.viewmodels.NotificationViewModel

class NotificationsFragment : Fragment() {

    private lateinit var viewModel: NotificationViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications_list, container, false)
        recyclerView = view.findViewById(R.id.notificationList)
        adapter = NotificationListAdapter(listOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel = ViewModelProvider(this)[NotificationViewModel::class.java]
        viewModel.notifications.observe(viewLifecycleOwner) { notifications ->
            adapter = NotificationListAdapter(notifications)
            recyclerView.adapter = adapter
        }
        return view
    }
}