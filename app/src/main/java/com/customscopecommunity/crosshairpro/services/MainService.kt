package com.customscopecommunity.crosshairpro.services

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.*
import android.view.View.inflate
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.layout_controller.view.*
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.widget.SeekBar
import android.view.WindowManager
import android.os.Build
import com.customscopecommunity.crosshairpro.*
import com.customscopecommunity.crosshairpro.screens.backgroundLight
import com.customscopecommunity.crosshairpro.screens.colour


private const val notificationId = 1
class MainService : Service(), View.OnClickListener {

    private lateinit var mWindowManager: WindowManager
    private lateinit var mFloatingView: View
    private lateinit var mCrosshairView: View
    private lateinit var imageView: ImageView

    private lateinit var xWindowManager: WindowManager
    private lateinit var xFloatingView: View
    private lateinit var xCollapsedView: View

    private lateinit var params: WindowManager.LayoutParams

    private var checkFun = false

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {

        val intent = Intent(this, SecondMainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.actionbar_logo)
            .setContentTitle(getString(R.string.running_notification))
            .setContentText(getString(R.string.tap_to_open))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        startForeground(notificationId, builder.build())

        afterFinishVisibility = 1

        mFloatingView = inflate(this, R.layout.layout_main_crosshair, null)
        imageView = mFloatingView.findViewById(R.id.mainServiceCrosshair)

        if (backgroundLight == 1) {
            imageView.setBackgroundResource(R.drawable.c_background)
        } else {
            imageView.setBackgroundResource(0)
        }

        if (crossNum == 100) {
            imageView.setImageResource(R.drawable.crosshair4)
            DrawableCompat.setTint(
                imageView.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        } else {

            when (crossNum) {
                51 -> imageView.setImageResource(R.drawable.crosshair1).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                52 -> imageView.setImageResource(R.drawable.crosshair2).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                53 -> imageView.setImageResource(R.drawable.crosshair3).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                54 -> imageView.setImageResource(R.drawable.crosshair4).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                55 -> imageView.setImageResource(R.drawable.crosshair5).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                56 -> imageView.setImageResource(R.drawable.crosshair6).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                57 -> imageView.setImageResource(R.drawable.crosshair7).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                58 -> imageView.setImageResource(R.drawable.crosshair8).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                59 -> imageView.setImageResource(R.drawable.crosshair9).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                60 -> imageView.setImageResource(R.drawable.crosshair10).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                61 -> imageView.setImageResource(R.drawable.crosshair11).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                62 -> imageView.setImageResource(R.drawable.crosshair12).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                63 -> imageView.setImageResource(R.drawable.crosshair13).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                64 -> imageView.setImageResource(R.drawable.crosshair14).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                65 -> imageView.setImageResource(R.drawable.crosshair15).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                66 -> imageView.setImageResource(R.drawable.crosshair16).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                67 -> imageView.setImageResource(R.drawable.crosshair17).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                68 -> imageView.setImageResource(R.drawable.crosshair18).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                69 -> imageView.setImageResource(R.drawable.crosshair19).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }
                70 -> imageView.setImageResource(R.drawable.crosshair20).also {
                    when (colour) {
                        0 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        1 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.primary
                            )
                        )
                        2 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        3 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.green
                            )
                        )
                        4 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.yellow
                            )
                        )
                        5 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.purple
                            )
                        )
                        6 -> DrawableCompat.setTint(
                            imageView.drawable, ContextCompat.getColor(
                                this,
                                R.color.blue
                            )
                        )
                    }
                }

            }

        }
        val layoutFlag: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER

        if (!checkFun) {
            controller()
            checkFun = true
        }

        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager.addView(mFloatingView, params)

        mCrosshairView = mFloatingView.findViewById(R.id.mainServiceCrosshair)

        mCrosshairView.setOnClickListener {

            if (!checkFun) {
                controller()
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mainServiceCrosshair -> {
                mCrosshairView.visibility = View.VISIBLE
            }
        }
    }

    private fun controller() {

        checkFun = true

        xFloatingView = inflate(this, R.layout.layout_controller, null)
        xWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val xlayoutFlag: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val xParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            xlayoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        xParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.END

        xWindowManager.addView(xFloatingView, xParams)

        xCollapsedView = xFloatingView.findViewById(R.id.controller)

        xCollapsedView.buttonUp.setOnClickListener {
            params.y -= 2
            mWindowManager.updateViewLayout(mFloatingView, params)
        }

        xCollapsedView.buttonDown.setOnClickListener {
            params.y += 2
            mWindowManager.updateViewLayout(mFloatingView, params)
        }

        xCollapsedView.buttonLeft.setOnClickListener {
            params.x -= 2
            mWindowManager.updateViewLayout(mFloatingView, params)
        }

        xCollapsedView.buttonRight.setOnClickListener {
            params.x += 2
            mWindowManager.updateViewLayout(mFloatingView, params)
        }

        xCollapsedView.buttonCancel.setOnClickListener {
            xWindowManager.removeView(xFloatingView)
            checkFun = false
        }

        val seekBar = xCollapsedView.findViewById(R.id.seekBar) as SeekBar

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                val scale = progress / 100.0f
                imageView.scaleX = scale
                imageView.scaleY = scale

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        xCollapsedView.setOnTouchListener(object : View.OnTouchListener {
            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0.toFloat()
            private var initialTouchY: Float = 0.toFloat()

            override fun onTouch(v: View, event: MotionEvent): Boolean {

                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = xParams.x
                        initialY = xParams.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }


                    MotionEvent.ACTION_UP -> {
                        v.performClick()
                        return true
                    }

                    MotionEvent.ACTION_MOVE -> {
                        xParams.x = initialX - (event.rawX - initialTouchX).toInt()
                        xParams.y = initialY + (event.rawY - initialTouchY).toInt()
                        xWindowManager.updateViewLayout(xFloatingView, xParams)
                        return true
                    }
                }
                return false
            }

        })

        xCollapsedView.setOnClickListener(this)

    }

    override fun onDestroy() {

        afterFinishVisibility = 5
        mWindowManager.removeView(mFloatingView)

        if (checkFun) {
            xWindowManager.removeView(xFloatingView)
        }
    }

}