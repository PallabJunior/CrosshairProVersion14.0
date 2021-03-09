package com.customscopecommunity.crosshairpro.database.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import com.customscopecommunity.crosshairpro.constants.DatabaseConstants.SETTINGS_PREFS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingsRepository(context: Context) {

    private val dataStore = context.createDataStore(name = SETTINGS_PREFS)

    companion object {
        val SAVED_CROSSHAIR_KEY = intPreferencesKey("SAVED_CROSSHAIR_KEY")
        val SAVED_COLOR_KEY = intPreferencesKey("SAVED_COLOR_KEY")
        val SAVED_BG_STATE_KEY = booleanPreferencesKey("SAVED_BG_STATE_KEY")
        val SAVED_MILLIS_KEY = longPreferencesKey("SAVED_MILLIS_KEY")
        val SAVED_ADS_NUMBER_KEY = intPreferencesKey("SAVED_ADS_NUMBER_KEY")
    }

    suspend fun saveNumberOfAdsShowed(adsCount: Int) {
        dataStore.edit {
            it[SAVED_ADS_NUMBER_KEY] = adsCount
        }
    }

    suspend fun saveDateTimeMillis(currentMillis: Long) {
        dataStore.edit {
            it[SAVED_MILLIS_KEY] = currentMillis
        }
    }

    suspend fun saveCrosshair(crosshair: Int) {
        dataStore.edit {
            it[SAVED_CROSSHAIR_KEY] = crosshair
        }
    }

    suspend fun saveCrosshairColour(colour: Int) {
        dataStore.edit {
            it[SAVED_COLOR_KEY] = colour
        }
    }

    suspend fun saveBgLightState(isLightOn: Boolean) {
        dataStore.edit {
            it[SAVED_BG_STATE_KEY] = isLightOn
        }
    }


    val getSavedCrosshair: Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            // No type safety.
            it[SAVED_CROSSHAIR_KEY] ?: 200
        }

    val getSavedColor: Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            // No type safety.
            it[SAVED_COLOR_KEY] ?: 0
        }

    val getBgLightState: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            // No type safety.
            it[SAVED_BG_STATE_KEY] ?: false
        }

    val getSavedDateTimeMillis: Flow<Long> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            it[SAVED_MILLIS_KEY] ?: 0
        }

    val getSavedCountOfAds: Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            // No type safety.
            it[SAVED_ADS_NUMBER_KEY] ?: 0
        }


}