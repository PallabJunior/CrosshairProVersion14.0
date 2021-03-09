package com.customscopecommunity.crosshairpro.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.customscopecommunity.crosshairpro.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClassicViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepository =
        (application.applicationContext as MainApplication).settingsRepository

    fun saveCrosshairColour(colour: Int): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveCrosshairColour(colour)
        }
    }

    fun saveBgLightState(isLightOn: Boolean): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveBgLightState(isLightOn)
        }
    }
}