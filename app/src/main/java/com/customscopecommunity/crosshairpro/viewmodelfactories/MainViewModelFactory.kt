package com.customscopecommunity.crosshairpro.viewmodelfactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.customscopecommunity.crosshairpro.viewmodels.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private var application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unexpected error occurred")
    }
}