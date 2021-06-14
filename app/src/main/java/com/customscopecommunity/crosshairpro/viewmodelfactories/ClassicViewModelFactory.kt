package com.customscopecommunity.crosshairpro.viewmodelfactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.customscopecommunity.crosshairpro.viewmodels.ClassicViewModel

@Suppress("UNCHECKED_CAST")
class ClassicViewModelFactory(private var application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassicViewModel::class.java)) {
            return ClassicViewModel(application) as T
        }
        throw IllegalArgumentException("Unexpected error occurred")
    }
}