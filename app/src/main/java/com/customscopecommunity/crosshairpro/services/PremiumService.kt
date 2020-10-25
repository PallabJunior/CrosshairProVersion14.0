package com.customscopecommunity.crosshairpro.services

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Toast
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.afterFinishVisibility
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_NUMBER
import com.customscopecommunity.crosshairpro.database.Position
import com.customscopecommunity.crosshairpro.database.PositionDatabase
import com.customscopecommunity.crosshairpro.newdatabase.State
import com.customscopecommunity.crosshairpro.newdatabase.StateDatabase
import kotlinx.android.synthetic.main.layout_pro_controller.view.*
import kotlinx.android.synthetic.main.layout_pro_crosshair.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PremiumService : BaseService(), View.OnClickListener {

    private val notificationId = 3

    private var isClickedForController = false

    private var mWindowManager: WindowManager? = null
    private lateinit var mFloatingView: View
    private lateinit var mCrosshairView: View
    private lateinit var imageView: ImageView

    private var xWindowManager: WindowManager? = null
    private lateinit var xFloatingView: View
    private lateinit var xCollapsedView: View

    private lateinit var params: WindowManager.LayoutParams

    private var checkFun = false

    private var isToastShowed = false

    // control the movement of the crosshair
    private var moveCount = 10

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel(notificationId)

        afterFinishVisibility = 3

        mFloatingView = View.inflate(this, R.layout.layout_pro_crosshair, null)
        imageView = mFloatingView.proServiceCrosshair

        val cNum = intent?.getIntExtra(CROSSHAIR_NUMBER, 200)

        when (cNum ?: 200) {
            200 -> addImage(R.drawable.pro1n)
            80 -> addImage(R.drawable.prem1n)
            81 -> addImage(R.drawable.prem2)
            82 -> addImage(R.drawable.prem3)
            83 -> addImage(R.drawable.prem4)
            84 -> addImage(R.drawable.prem5n)
            85 -> addImage(R.drawable.prem6n)
            86 -> addImage(R.drawable.prem7n)
            87 -> addImage(R.drawable.prem8n)
            88 -> addImage(R.drawable.prem9n)
            89 -> addImage(R.drawable.prem10n)
            90 -> addImage(R.drawable.prem11n)
            91 -> addImage(R.drawable.prem12n)
            92 -> addImage(R.drawable.prem13n)
            93 -> addImage(R.drawable.prem14n)
            94 -> addImage(R.drawable.prem15n)
            95 -> addImage(R.drawable.prem16n)
            96 -> addImage(R.drawable.prem17n)
            97 -> addImage(R.drawable.prem18n)
            98 -> addImage(R.drawable.prem19n)
            99 -> addImage(R.drawable.prem20n)
            100 -> addImage(R.drawable.prem21n)

            101 -> addImage(R.drawable.pro1n)
            102 -> addImage(R.drawable.pro2n)
            103 -> addImage(R.drawable.pro3n)
            104 -> addImage(R.drawable.pro4n)
            105 -> addImage(R.drawable.pro5n)
            106 -> addImage(R.drawable.pro6n)
            107 -> addImage(R.drawable.pro7n)
            108 -> addImage(R.drawable.pro8n)
            109 -> addImage(R.drawable.pro9n)
            110 -> addImage(R.drawable.pro10n)
            111 -> addImage(R.drawable.pro11n)
            112 -> addImage(R.drawable.pro12n)
            113 -> addImage(R.drawable.pro13n)
            114 -> addImage(R.drawable.pro14n)
            115 -> addImage(R.drawable.pro15n)
            116 -> addImage(R.drawable.pro16n)
            117 -> addImage(R.drawable.pro17n)
            118 -> addImage(R.drawable.pro18n)
            119 -> addImage(R.drawable.pro19n)
            120 -> addImage(R.drawable.pro20n)


            121 -> addImage(R.drawable.nn1)
            122 -> addImage(R.drawable.nn2)
            123 -> addImage(R.drawable.nn3)
            124 -> addImage(R.drawable.nn4)
            125 -> addImage(R.drawable.nn5)
            126 -> addImage(R.drawable.nn6)
            127 -> addImage(R.drawable.nn7)
            128 -> addImage(R.drawable.nn8)
            129 -> addImage(R.drawable.nn9)
            130 -> addImage(R.drawable.nn10)
            131 -> addImage(R.drawable.nn11)
            132 -> addImage(R.drawable.nn12)
            133 -> addImage(R.drawable.nn13)

            else -> addImage(R.drawable.pro1n)

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
        }


        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager?.addView(mFloatingView, params)
        mCrosshairView = mFloatingView.findViewById(R.id.proServiceCrosshair)


        mCrosshairView.setOnClickListener {

            if (!checkFun) {
                isClickedForController = true
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
            R.id.proServiceCrosshair -> {
                mCrosshairView.visibility = View.VISIBLE
            }
        }
    }

    private fun controller() {

        checkFun = true

        xFloatingView = View.inflate(this, R.layout.layout_pro_controller, null)
        xWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val xLayoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val xParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            xLayoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        xParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.END
        xParams.x = 15


        try {
            xWindowManager?.addView(xFloatingView, xParams)
        } catch (e: Exception) {
            checkFun = false
            if (isClickedForController) {
                isClickedForController = false
                Toast.makeText(
                    this,
                    getString(R.string.controller_not_supported),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.initial_window_token_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

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

//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//                    val scale = progress / 100.0f
//                    imageView.scaleX = scale
//                    imageView.scaleY = scale
//                } else {}

                val scale = dpToPx(progress)
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


    private fun addImage(img: Int) {
        imageView.setImageResource(img)
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
            CoroutineScope(Dispatchers.IO).launch {
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

        afterFinishVisibility = 7
        mWindowManager?.removeView(mFloatingView)

        if (checkFun) {
            xWindowManager?.removeView(xFloatingView)
        }
        super.onDestroy()
    }

}
