package com.customscopecommunity.crosshairpro.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.View.inflate
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.customscopecommunity.crosshairpro.*
import com.customscopecommunity.crosshairpro.database.Position
import com.customscopecommunity.crosshairpro.database.PositionDatabase
import com.customscopecommunity.crosshairpro.screens.backgroundLight
import com.customscopecommunity.crosshairpro.screens.colour
import kotlinx.android.synthetic.main.layout_pro_controller.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val notificationId = 1

class MainService : BaseService(), View.OnClickListener {

    private lateinit var mWindowManager: WindowManager
    private lateinit var mFloatingView: View
    private lateinit var mCrosshairView: View
    private lateinit var imageView: ImageView

    private lateinit var xWindowManager: WindowManager
    private lateinit var xFloatingView: View
    private lateinit var xCollapsedView: View

    private lateinit var params: WindowManager.LayoutParams

    private var checkFun = false

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
            1 -> addCrosshair(R.drawable.crosshair1)
            2 -> addCrosshair(R.drawable.crosshair2)
            3 -> addCrosshair(R.drawable.crosshair3)
            4 -> addCrosshair(R.drawable.crosshair4)
            5 -> addCrosshair(R.drawable.crosshair5)
            6 -> addCrosshair(R.drawable.crosshair6)
            7 -> addCrosshair(R.drawable.crosshair7)
            8 -> addCrosshair(R.drawable.crosshair8)
            9 -> addCrosshair(R.drawable.crosshair9)
            10 -> addCrosshair(R.drawable.crosshair10)
            11 -> addCrosshair(R.drawable.crosshair11)
            12 -> addCrosshair(R.drawable.crosshair12)
            13 -> addCrosshair(R.drawable.crosshair13)
            14 -> addCrosshair(R.drawable.crosshair14)
            15 -> addCrosshair(R.drawable.crosshair15)
            16 -> addCrosshair(R.drawable.crosshair16)
            17 -> addCrosshair(R.drawable.crosshair17)
            18 -> addCrosshair(R.drawable.crosshair18)
            19 -> addCrosshair(R.drawable.crosshair19)
            20 -> addCrosshair(R.drawable.crosshair20)

            21 -> addCrosshair(R.drawable.new_classic_1)
            22 -> addCrosshair(R.drawable.new_classic_2)
            23 -> addCrosshair(R.drawable.new_classic_3)
            24 -> addCrosshair(R.drawable.new_classic_4)

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

        CoroutineScope(Dispatchers.Main).launch {

            position = PositionDatabase(applicationContext).getPositionDao().getAllPositions()

            if (position == null) {
                params.gravity = Gravity.CENTER
            } else {
                val verticalP = position!!.vPosition
                val horizontalP = position!!.hPosition

                params.y = verticalP
                params.x = horizontalP
                updateLayout()

                vValue = verticalP
                hValue = horizontalP
            }
        }

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
                vValue -= 2
                updateLayout()
            }
            proButtonDown.setOnClickListener {
                params.y += 2
                vValue += 2
                updateLayout()
            }
            proButtonLeft.setOnClickListener {
                params.x -= 2
                hValue -= 2
                updateLayout()
            }
            proButtonRight.setOnClickListener {
                params.x += 2
                hValue += 2
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

        CoroutineScope(Dispatchers.Main).launch {
            val mPosition = Position(vValue, hValue)

            if (position == null) {
                PositionDatabase(applicationContext).getPositionDao().addPosition(mPosition)
            } else {
                mPosition.id = position!!.id
                PositionDatabase(applicationContext).getPositionDao().updatePosition(mPosition)
            }
        }

        afterFinishVisibility = 5
        mWindowManager.removeView(mFloatingView)

        if (checkFun) {
            xWindowManager.removeView(xFloatingView)
        }
    }

}