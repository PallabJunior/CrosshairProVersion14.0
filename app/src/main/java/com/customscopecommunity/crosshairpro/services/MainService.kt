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
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.widget.SeekBar
import android.view.WindowManager
import android.os.Build
import com.customscopecommunity.crosshairpro.*
import com.customscopecommunity.crosshairpro.screens.backgroundLight
import com.customscopecommunity.crosshairpro.screens.colour
import kotlinx.android.synthetic.main.layout_pro_controller.view.*


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

        when (crossNum) {
            51 -> addCrosshair(R.drawable.crosshair1)
            52 -> addCrosshair(R.drawable.crosshair2)
            53 -> addCrosshair(R.drawable.crosshair3)
            54 -> addCrosshair(R.drawable.crosshair4)
            55 -> addCrosshair(R.drawable.crosshair5)
            56 -> addCrosshair(R.drawable.crosshair6)
            57 -> addCrosshair(R.drawable.crosshair7)
            58 -> addCrosshair(R.drawable.crosshair8)
            59 -> addCrosshair(R.drawable.crosshair9)
            60 -> addCrosshair(R.drawable.crosshair10)
            61 -> addCrosshair(R.drawable.crosshair11)
            62 -> addCrosshair(R.drawable.crosshair12)
            63 -> addCrosshair(R.drawable.crosshair13)
            64 -> addCrosshair(R.drawable.crosshair14)
            65 -> addCrosshair(R.drawable.crosshair15)
            66 -> addCrosshair(R.drawable.crosshair16)
            67 -> addCrosshair(R.drawable.crosshair17)
            68 -> addCrosshair(R.drawable.crosshair18)
            69 -> addCrosshair(R.drawable.crosshair19)
            70 -> addCrosshair(R.drawable.crosshair20)

            else -> addCrosshair(R.drawable.crosshair4)

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

        xFloatingView = inflate(this, R.layout.layout_pro_controller, null)
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

        xCollapsedView = xFloatingView.findViewById(R.id.proController)

        xCollapsedView.apply {
            proButtonUp.setOnClickListener {
                params.y -= 2
                updateLayout()
            }
            proButtonDown.setOnClickListener {
                params.y += 2
                updateLayout()
            }
            proButtonLeft.setOnClickListener {
                params.x -= 2
                updateLayout()
            }
            proButtonRight.setOnClickListener {
                params.x += 2
                updateLayout()
            }
            proButtonCancel.setOnClickListener {
                xWindowManager.removeView(xFloatingView)
                checkFun = false
            }
        }


        val seekBar = xCollapsedView.findViewById(R.id.proSeekBar) as SeekBar

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                val scale = progress / 100.0f
                imageView.scaleX = scale
                imageView.scaleY = scale

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        val opacitySeekBar: SeekBar = xCollapsedView.findViewById(R.id.opacity_seekbar)
        opacitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                imageView.imageAlpha = progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    private fun addCrosshair(img: Int) {
        imageView.setImageResource(img)

        setCrosshairColour()
    }

    private fun setCrosshairColour() {

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

    private fun updateLayout() {
        mWindowManager.updateViewLayout(mFloatingView, params)
    }

    override fun onDestroy() {

        afterFinishVisibility = 5
        mWindowManager.removeView(mFloatingView)

        if (checkFun) {
            xWindowManager.removeView(xFloatingView)
        }
    }

}