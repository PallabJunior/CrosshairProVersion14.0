package com.customscopecommunity.crosshairpro.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.customscopecommunity.crosshairpro.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SecondMainViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepository =
        (application.applicationContext as MainApplication).settingsRepository


    val readSavedDateTimeMillis = settingsRepository.getSavedDateTimeMillis.asLiveData()
    val readSavedAdImpression = settingsRepository.getSavedCountOfAds.asLiveData()

    fun saveNumberOfAdImpression(num: Int): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveNumberOfAdsShowed(num)
        }
    }

    fun saveCurrentDateTimeMillis(currentMillis: Long): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveDateTimeMillis(currentMillis)
        }
    }
}