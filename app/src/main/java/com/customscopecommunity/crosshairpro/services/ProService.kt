package com.customscopecommunity.crosshairpro.services

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.core.app.NotificationCompat
import com.customscopecommunity.crosshairpro.*
import kotlinx.android.synthetic.main.layout_pro_controller.view.*

private const val notificationId = 2

class ProService : Service(), View.OnClickListener {

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

        afterFinishVisibility = 2

        mFloatingView = View.inflate(this, R.layout.layout_pro_crosshair, null)
        imageView = mFloatingView.findViewById(R.id.proServiceCrosshair)

        when (crossNum) {
            0 -> addImage(R.drawable.pro1n)
            1 -> addImage(R.drawable.pro2n)
            2 -> addImage(R.drawable.pro3n)
            3 -> addImage(R.drawable.pro4n)
            4 -> addImage(R.drawable.pro5n)
            5 -> addImage(R.drawable.pro6n)
            6 -> addImage(R.drawable.pro7n)
            7 -> addImage(R.drawable.pro8n)
            8 -> addImage(R.drawable.pro9n)
            9 -> addImage(R.drawable.pro10n)
            10 -> addImage(R.drawable.pro11n)
            11 -> addImage(R.drawable.pro12n)
            12 -> addImage(R.drawable.pro13n)
            13 -> addImage(R.drawable.pro14n)
            14 -> addImage(R.drawable.pro15n)
            15 -> addImage(R.drawable.pro16n)
            16 -> addImage(R.drawable.pro17n)
            17 -> addImage(R.drawable.pro18n)
            18 -> addImage(R.drawable.pro19n)
            19 -> addImage(R.drawable.pro20n)

            else -> addImage(R.drawable.pro12n)
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
        }

        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager.addView(mFloatingView, params)

        mCrosshairView = mFloatingView.findViewById(R.id.proServiceCrosshair)


        mCrosshairView.setOnClickListener {

            if (!checkFun) {
                controller()
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.proServiceCrosshair -> {
                mCrosshairView.visibility = View.VISIBLE
            }
        }
    }

    private fun controller() {

        checkFun = true

        xFloatingView = View.inflate(this, R.layout.layout_pro_controller, null)
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

                val scale = progress + 25

                val newParams = LinearLayout.LayoutParams(scale, scale)
                imageView.layoutParams = newParams

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

    private fun addImage(img: Int) {
        imageView.setImageResource(img)
    }

    private fun updateLayout() {
        mWindowManager.updateViewLayout(mFloatingView, params)
    }

    override fun onDestroy() {

        afterFinishVisibility = 6
        mWindowManager.removeView(mFloatingView)

        if (checkFun) {
            xWindowManager.removeView(xFloatingView)
        }
    }

}
