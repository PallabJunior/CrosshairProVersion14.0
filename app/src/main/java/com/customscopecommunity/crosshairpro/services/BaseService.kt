package com.customscopecommunity.crosshairpro.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.SecondMainActivity
import com.customscopecommunity.crosshairpro.constants.Constants.CHANNEL_ID
import com.customscopecommunity.crosshairpro.database.Position

const val ACTION_CONTROLLER = "ACTION_CONTROLLER"

open class BaseService : Service() {

    open var vValue = 0
    open var hValue = 0

    open var position: Position? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun showToast() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            val toast =
                Toast.makeText(this, getString(R.string.tap_on_crosshair), Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    fun createNotificationChannel(notificationId: Int) {
        //broadCast receiver
        val broadCastReceive = Intent()
        broadCastReceive.action = ACTION_CONTROLLER
        val pendingIntentYes =
            PendingIntent.getBroadcast(this, 0, broadCastReceive, PendingIntent.FLAG_UPDATE_CURRENT)
        //broadCast receiver

        val intent = Intent(this, SecondMainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_logo)
            .setContentTitle(getString(R.string.running_notification))
            .setContentText(getString(R.string.tap_to_open))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.ic_notification_logo,
                getString(R.string.controller),
                pendingIntentYes
            )
            .setAutoCancel(false)

        startForeground(notificationId, builder.build())

    }

    fun dpToPx(dp: Int) = (dp * Resources.getSystem().displayMetrics.density).toInt()


}