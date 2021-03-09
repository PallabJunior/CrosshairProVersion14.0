package com.customscopecommunity.crosshairpro.viewmodelfactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.customscopecommunity.crosshairpro.viewmodels.MainViewModel
import com.customscopecommunity.crosshairpro.viewmodels.SecondMainViewModel

@Suppress("UNCHECKED_CAST")
class SecondMainViewModelFactory(private var application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SecondMainViewModel::class.java)) {
            return SecondMainViewModel(application) as T
        }
        throw IllegalArgumentException("Unexpected error occurred")
    }
}