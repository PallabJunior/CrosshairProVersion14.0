package com.customscopecommunity.crosshairpro.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.customscopecommunity.crosshairpro.R
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.ProService
import com.customscopecommunity.crosshairpro.crossNum
import com.customscopecommunity.crosshairpro.services.PremiumService
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_classic.*


var colour: Int = 0
var backgroundLight: Int = 0
const val action: String = "activity-2-initialized"

class ClassicActivity : AppCompatActivity() {

    private lateinit var cService: Intent
    private lateinit var pService: Intent
    private lateinit var premService: Intent

    private lateinit var crosshairViews: Array<ImageView>

    private lateinit var colorControlBtns: Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_classic)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.classic_collection)

        classicBannerAd.loadAd(AdRequest.Builder().build())

        crosshairViews = arrayOf(
            classic1, classic2, classic3, classic4, classic5,
            classic6, classic7, classic8, classic9, classic10,
            classic11, classic12, classic13, classic14, classic15,
            classic16, classic17, classic18, classic19, classic20
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
        pService = Intent(this, ProService::class.java)
        premService = Intent(this, PremiumService::class.java)


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

        // for changing the colours of crosshairs by clicking the colour buttons
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
        } // done

        for (v in crosshairViews) {

            v.setOnClickListener {
                when (v) {
                    classic1 -> crossNum = 51
                    classic2 -> crossNum = 52
                    classic3 -> crossNum = 53
                    classic4 -> crossNum = 54
                    classic5 -> crossNum = 55
                    classic6 -> crossNum = 56
                    classic7 -> crossNum = 57
                    classic8 -> crossNum = 58
                    classic9 -> crossNum = 59
                    classic10 -> crossNum = 60
                    classic11 -> crossNum = 61
                    classic12 -> crossNum = 62
                    classic13 -> crossNum = 63
                    classic14 -> crossNum = 64
                    classic15 -> crossNum = 65
                    classic16 -> crossNum = 66
                    classic17 -> crossNum = 67
                    classic18 -> crossNum = 68
                    classic19 -> crossNum = 69
                    classic20 -> crossNum = 70
                }
                stopServices()
                toastShow()
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
        stopService(pService)
        stopService(premService)
    }

    private fun toastShow() {

        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(action))
        val toast = Toast.makeText(this, getString(R.string.tap_start), Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

        val handler = Handler()
        handler.postDelayed({ toast.cancel() }, 700)
    }

    private fun closeFg() {
        finish()
    }
}
