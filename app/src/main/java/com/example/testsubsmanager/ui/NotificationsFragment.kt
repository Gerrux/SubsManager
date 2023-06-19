package com.example.testsubsmanager.ui

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testsubsmanager.R
import com.example.testsubsmanager.ui.adapters.NotificationListAdapter
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment

class NotificationsFragment : DaggerFragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications_list, container, false)
    }
}