package com.customscopecommunity.crosshairpro.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.customscopecommunity.crosshairpro.database.Position

open class BaseService : Service() {

    open var vValue = 0
    open var hValue = 0

    open var position: Position? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}