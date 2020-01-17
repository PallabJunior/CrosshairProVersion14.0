package com.customscopecommunity.crosshairpro.services

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.core.app.NotificationCompat
import com.customscopecommunity.crosshairpro.*
import kotlinx.android.synthetic.main.layout_pro_controller.view.*

private const val notificationId = 3
class PremiumService : Service(), View.OnClickListener {

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

        afterFinishVisibility = 3

        mFloatingView = View.inflate(this, R.layout.layout_pro_crosshair, null)
        imageView = mFloatingView.findViewById(R.id.proServiceCrosshair)


        when (crossNum) {
            200 -> imageView.setImageResource(R.drawable.pro1n)
            80 -> imageView.setImageResource(R.drawable.prem1n)
            81 -> imageView.setImageResource(R.drawable.prem2)
            82 -> imageView.setImageResource(R.drawable.prem3)
            83 -> imageView.setImageResource(R.drawable.prem4)
            84 -> imageView.setImageResource(R.drawable.prem5n)
            85 -> imageView.setImageResource(R.drawable.prem6n)
            86 -> imageView.setImageResource(R.drawable.prem7n)
            87 -> imageView.setImageResource(R.drawable.prem8n)
            88 -> imageView.setImageResource(R.drawable.prem9n)
            89 -> imageView.setImageResource(R.drawable.prem10n)
            90 -> imageView.setImageResource(R.drawable.prem11n)
            91 -> imageView.setImageResource(R.drawable.prem12n)
            92 -> imageView.setImageResource(R.drawable.prem13n)
            93 -> imageView.setImageResource(R.drawable.prem14n)
            94 -> imageView.setImageResource(R.drawable.prem15n)
            95 -> imageView.setImageResource(R.drawable.prem16n)
            96 -> imageView.setImageResource(R.drawable.prem17n)
            97 -> imageView.setImageResource(R.drawable.prem18n)
            98 -> imageView.setImageResource(R.drawable.prem19n)
            99 -> imageView.setImageResource(R.drawable.prem20n)
            100 -> imageView.setImageResource(R.drawable.prem21n)

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

        xCollapsedView.proButtonUp.setOnClickListener {
            params.y -= 2
            mWindowManager.updateViewLayout(mFloatingView, params)
        }

        xCollapsedView.proButtonDown.setOnClickListener {
            params.y += 2
            mWindowManager.updateViewLayout(mFloatingView, params)
        }

        xCollapsedView.proButtonLeft.setOnClickListener {
            params.x -= 2
            mWindowManager.updateViewLayout(mFloatingView, params)
        }

        xCollapsedView.proButtonRight.setOnClickListener {
            params.x += 2
            mWindowManager.updateViewLayout(mFloatingView, params)
        }

        xCollapsedView.proButtonCancel.setOnClickListener {
            xWindowManager.removeView(xFloatingView)
            checkFun = false
        }

        val seekBar = xCollapsedView.findViewById(R.id.proSeekBar) as SeekBar


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {

                val scale = progress + 25

                val newParams = LinearLayout.LayoutParams(scale, scale)
                imageView.layoutParams = newParams

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

        afterFinishVisibility = 7
        mWindowManager.removeView(mFloatingView)

        if (checkFun) {
            xWindowManager.removeView(xFloatingView)
        }
    }

}
