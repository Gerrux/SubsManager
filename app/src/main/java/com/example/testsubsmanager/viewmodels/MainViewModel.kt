package com.example.testsubsmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testsubsmanager.database.AppDatabaseRepository
import com.example.testsubsmanager.database.dto.Subscription
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(private val repository: AppDatabaseRepository) : ViewModel() {

    private val subscriptionsLiveData = MutableLiveData<List<Subscription>>()
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    fun getAllSubscriptions(): LiveData<List<Subscription>> {
        ioScope.launch {
            val subscriptions = repository.getAllSubscriptions()
            subscriptionsLiveData.postValue(subscriptions.value)
        }
        return subscriptionsLiveData
    }

}
