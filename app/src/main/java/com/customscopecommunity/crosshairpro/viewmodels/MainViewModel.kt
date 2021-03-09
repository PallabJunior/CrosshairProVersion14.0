package com.customscopecommunity.crosshairpro.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.customscopecommunity.crosshairpro.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepository =
        (application.applicationContext as MainApplication).settingsRepository

    val readSavedCrosshair = settingsRepository.getSavedCrosshair.asLiveData()
    val readSavedColour = settingsRepository.getSavedColor.asLiveData()
    val readSavedBgState = settingsRepository.getBgLightState.asLiveData()

    fun saveCurrentCrosshair(crosshair: Int): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveCrosshair(crosshair)
        }
    }

}