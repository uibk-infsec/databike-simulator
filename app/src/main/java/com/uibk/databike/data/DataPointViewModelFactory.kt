package com.uibk.databike.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DataPointViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataPointViewModel(application) as T
    }
}