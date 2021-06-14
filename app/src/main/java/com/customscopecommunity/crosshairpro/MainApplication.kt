package com.customscopecommunity.crosshairpro

import android.app.Application
import com.customscopecommunity.crosshairpro.database.datastore.SettingsRepository

class MainApplication : Application() {

    val settingsRepository by lazy { SettingsRepository(this) }

}