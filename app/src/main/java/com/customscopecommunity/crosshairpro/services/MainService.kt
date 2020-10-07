package com.customscopecommunity.crosshairpro.services

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.inflate
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.afterFinishVisibility
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_BG
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_COLOUR
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_NUMBER
import com.customscopecommunity.crosshairpro.database.Position
import com.customscopecommunity.crosshairpro.database.PositionDatabase
import com.customscopecommunity.crosshairpro.newdatabase.State
import com.customscopecommunity.crosshairpro.newdatabase.StateDatabase
import kotlinx.android.synthetic.main.layout_main_crosshair.view.*
import kotlinx.android.synthetic.main.layout_pro_controller.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val notificationId = 1

class MainService : BaseService(), View.OnClickListener {

    private var mWindowManager: WindowManager? = null
    private lateinit var mFloatingView: View
    private lateinit var mCrosshairView: View
    private lateinit var imageView: ImageView
    private lateinit var imageViewBg: ImageView

    private var xWindowManager: WindowManager? = null
    private lateinit var xFloatingView: View
    private lateinit var xCollapsedView: View

    private lateinit var params: WindowManager.LayoutParams

    private var checkFun = false

    private var isToastShowed = false

    // control the movement of the crosshair
    private var moveCount = 10

    private var cColour: Int? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel(notificationId)

        afterFinishVisibility = 1

        mFloatingView = inflate(this, R.layout.layout_main_crosshair, null)

        imageView = mFloatingView.mainServiceCrosshair
        imageViewBg = mFloatingView.mainBg

        val cNum = intent?.getIntExtra(CROSSHAIR_NUMBER, 1)
        val bgLight = intent?.getIntExtra(CROSSHAIR_BG, 0)
        cColour = intent?.getIntExtra(CROSSHAIR_COLOUR, 0)

        if (bgLight == 1) {
            imageView.setBackgroundResource(R.drawable.c_background)
        } else {
            imageView.setBackgroundResource(0)
        }

        when (cNum!!) {
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
                updateLayout()
                makeCrosshairVisible()
            } else {
                val verticalP = position!!.vPosition
                val horizontalP = position!!.hPosition

                params.y = verticalP
                params.x = horizontalP

                updateLayout()
                makeCrosshairVisible()

                vValue = verticalP
                hValue = horizontalP
            }
        }

        if (!checkFun) {
            controller()
            checkFun = true
        }

        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager?.addView(mFloatingView, params)
        mCrosshairView = mFloatingView.findViewById(R.id.mainServiceCrosshair)

        mCrosshairView.setOnClickListener {

            if (!checkFun) {
                controller()
            }
        }

        saveRunningState()

        return START_STICKY
    }

    private fun makeCrosshairVisible() {
        try {
            imageView.visibility = View.VISIBLE
        } catch (e: Exception) {
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

        val xlayoutType: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val xParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            xlayoutType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        xParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.END
        xParams.x = 15

        xWindowManager?.addView(xFloatingView, xParams)

        xCollapsedView = xFloatingView.findViewById(R.id.proController)

        xCollapsedView.apply {

            show_move_count.text = moveCount.toString()

            proButtonUp.setOnClickListener {
                params.y -= moveCount
                vValue -= moveCount
                updateLayout()
            }
            proButtonDown.setOnClickListener {
                params.y += moveCount
                vValue += moveCount
                updateLayout()
            }
            proButtonLeft.setOnClickListener {
                params.x -= moveCount
                hValue -= moveCount
                updateLayout()
            }
            proButtonRight.setOnClickListener {
                params.x += moveCount
                hValue += moveCount
                updateLayout()
            }
            proButtonCancel.setOnClickListener {
                xWindowManager?.removeView(xFloatingView)
                checkFun = false
                if (!isToastShowed) {
                    showToast()
                    isToastShowed = true
                }
            }

            // Decrease move count
            btn_decrease.setOnClickListener {
                --moveCount

                if (moveCount <= 1)
                    moveCount = 1

                show_move_count.text = moveCount.toString()
            }

            // Increase move count
            btn_increase.setOnClickListener {
                moveCount++

                if (moveCount >= 30)
                    moveCount = 30

                show_move_count.text = moveCount.toString()
            }
        }


        val seekBar = xCollapsedView.findViewById(R.id.proSeekBar) as SeekBar

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {

                val scale = dpToPx(progress)
                val newParams = FrameLayout.LayoutParams(scale, scale)
                imageView.layoutParams = newParams
                imageViewBg.layoutParams = newParams

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


        ////// Move the whole controller
        xCollapsedView.setOnTouchListener(object : View.OnTouchListener {
            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0.toFloat()
            private var initialTouchY: Float = 0.toFloat()

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View, event: MotionEvent): Boolean {

                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = xParams.x
                        initialY = xParams.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                    }

                    MotionEvent.ACTION_MOVE -> {
                        xParams.x = initialX - (event.rawX - initialTouchX).toInt()
                        xParams.y = initialY + (event.rawY - initialTouchY).toInt()
                        xWindowManager?.updateViewLayout(xFloatingView, xParams)
                    }

                    else -> return false
                }
                return true
            }
        })
    }

    private fun addCrosshair(img: Int) {
        imageView.setImageResource(img)

        setCrosshairColour()
    }

    private fun setCrosshairColour() {

        when (cColour) {
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
        try {
            mWindowManager?.updateViewLayout(mFloatingView, params)

        } catch (e: IllegalArgumentException) {
        }
    }

    private fun saveRunningState() {
        if (applicationContext != null) {
            CoroutineScope(Dispatchers.IO).launch {

                // refState == null on very first launch
                val refState: State? =
                    StateDatabase(applicationContext).getStateDao().getAllStates()

                val mState = State(true)

                if (refState == null) {
                    StateDatabase(applicationContext).getStateDao().addState(mState)
                } else {
                    mState.id = refState.id
                    StateDatabase(applicationContext).getStateDao().updateState(mState)
                }
            }
        }
    }


    private fun savePosition() {
        if (applicationContext != null) {
            CoroutineScope(Dispatchers.Main).launch {
                val mPosition = Position(vValue, hValue)

                if (position == null) {
                    PositionDatabase(applicationContext).getPositionDao().addPosition(mPosition)
                } else {
                    mPosition.id = position!!.id
                    PositionDatabase(applicationContext).getPositionDao().updatePosition(mPosition)
                }
            }
        }
    }


    override fun onDestroy() {

        savePosition()

        afterFinishVisibility = 5

        mWindowManager?.removeView(mFloatingView)

        if (checkFun) {
            xWindowManager?.removeView(xFloatingView)
        }

        super.onDestroy()
    }

}