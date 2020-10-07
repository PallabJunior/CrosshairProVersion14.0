package com.customscopecommunity.crosshairpro.screens

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.constants.Constants.ACTION
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_BG
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_COLOUR
import com.customscopecommunity.crosshairpro.constants.Constants.CROSSHAIR_NUMBER
import com.customscopecommunity.crosshairpro.crossNum
import com.customscopecommunity.crosshairpro.firstOpen
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.PremiumService
import kotlinx.android.synthetic.main.fragment_classic.*


var colour: Int = 0
var backgroundLight: Int = 0

class ClassicActivity : AppCompatActivity() {

    private lateinit var cService: Intent
    private lateinit var premService: Intent

    private lateinit var crosshairViews: Array<ImageView>

    private lateinit var colorControlBtns: Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_classic)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        crosshairViews = arrayOf(
            classic1, classic2, classic3, classic4, classic5,
            classic6, classic7, classic8, classic9, classic10,
            classic11, classic12, classic13, classic14, classic15,
            classic16, classic17, classic18, classic19, classic20,
            classic21, classic22, classic23, classic24
        )

        colorControlBtns = arrayOf(
            changeRed, changeWhite, changeGreen,
            changeYellow, changePurple, changeBlue
        )

        if (backgroundLight == 1) {
            lightSwitch.isChecked = true

            for (views in crosshairViews) {
                views.setBackgroundResource(R.drawable.c_background)
            }
        }

        cService = Intent(this, MainService::class.java)
        premService = Intent(this, PremiumService::class.java)

        // setting colours of the crosshairs when coming back to this activity
        when (colour) {
            0 -> {
                for (views in crosshairViews)
                    setColour(views, R.color.white)
            }
            1 -> {
                for (views in crosshairViews)
                    setColour(views, R.color.primary)
            }
            2 -> {
                for (views in crosshairViews)
                    setColour(views, R.color.white)
            }
            3 -> {
                for (views in crosshairViews)
                    setColour(views, R.color.green)
            }
            4 -> {
                for (views in crosshairViews)
                    setColour(views, R.color.yellow)
            }
            5 -> {
                for (views in crosshairViews)
                    setColour(views, R.color.purple)
            }
            6 -> {
                for (views in crosshairViews)
                    setColour(views, R.color.blue)
            }


        }


        lightSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                backgroundLight = 1

                for (views in crosshairViews) {
                    views.setBackgroundResource(R.drawable.c_background)
                }

            } else {
                backgroundLight = 2

                for (views in crosshairViews) {
                    views.setBackgroundResource(0)
                }
            }
        }

        for (colorBtn in colorControlBtns) {
            colorBtn.setOnClickListener {
                when (colorBtn) {
                    changeRed -> {
                        for (views in crosshairViews)
                            setColour(views, R.color.primary)
                        colour = 1
                    }
                    changeWhite -> {
                        for (views in crosshairViews)
                            setColour(views, R.color.white)
                        colour = 2
                    }
                    changeGreen -> {
                        for (views in crosshairViews)
                            setColour(views, R.color.green)
                        colour = 3
                    }
                    changeYellow -> {
                        for (views in crosshairViews)
                            setColour(views, R.color.yellow)
                        colour = 4
                    }
                    changePurple -> {
                        for (views in crosshairViews)
                            setColour(views, R.color.purple)
                        colour = 5
                    }
                    changeBlue -> {
                        for (views in crosshairViews)
                            setColour(views, R.color.blue)
                        colour = 6
                    }

                }
            }
        }

        for (v in crosshairViews) {

            v.setOnClickListener {
                when (v) {
                    classic1 -> crossNum = 1
                    classic2 -> crossNum = 2
                    classic3 -> crossNum = 3
                    classic4 -> crossNum = 4
                    classic5 -> crossNum = 5
                    classic6 -> crossNum = 6
                    classic7 -> crossNum = 7
                    classic8 -> crossNum = 8
                    classic9 -> crossNum = 9
                    classic10 -> crossNum = 10
                    classic11 -> crossNum = 11
                    classic12 -> crossNum = 12
                    classic13 -> crossNum = 13
                    classic14 -> crossNum = 14
                    classic15 -> crossNum = 15
                    classic16 -> crossNum = 16
                    classic17 -> crossNum = 17
                    classic18 -> crossNum = 18
                    classic19 -> crossNum = 19
                    classic20 -> crossNum = 20
                    classic21 -> crossNum = 21
                    classic22 -> crossNum = 22
                    classic23 -> crossNum = 23
                    classic24 -> crossNum = 24
                }
                stopServices()
                changeButtonsVisibility()
                startService()
                closeFg()
            }
        }
    }

    private fun setColour(colorCrosshairView: ImageView, color: Int) {

        DrawableCompat.setTint(
            colorCrosshairView.drawable,
            ContextCompat.getColor(
                this,
                color
            )
        )
    }

    private fun stopServices() {
        stopService(cService)
        stopService(premService)
    }

    private fun startService() {

        if (firstOpen) {
            Toast.makeText(applicationContext, getString(R.string.please_wait), Toast.LENGTH_SHORT)
                .show()
            firstOpen = false
        }

        cService.apply {
            putExtra(CROSSHAIR_NUMBER, crossNum)
            putExtra(CROSSHAIR_BG, backgroundLight)
            putExtra(CROSSHAIR_COLOUR, colour)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(cService)
        } else {
            startService(cService)
        }
    }

    private fun changeButtonsVisibility() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(ACTION))
    }

    private fun closeFg() {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}