package com.customscopecommunity.crosshairpro.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_classic)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.classic_collection)

        classicBannerAd.loadAd(AdRequest.Builder().build())

        if (backgroundLight == 1) {
            lightSwitch.isChecked = true

            classic1.setBackgroundResource(R.drawable.c_background)
            classic2.setBackgroundResource(R.drawable.c_background)
            classic3.setBackgroundResource(R.drawable.c_background)
            classic4.setBackgroundResource(R.drawable.c_background)
            classic5.setBackgroundResource(R.drawable.c_background)
            classic6.setBackgroundResource(R.drawable.c_background)
            classic7.setBackgroundResource(R.drawable.c_background)
            classic8.setBackgroundResource(R.drawable.c_background)
            classic9.setBackgroundResource(R.drawable.c_background)
            classic10.setBackgroundResource(R.drawable.c_background)
            classic11.setBackgroundResource(R.drawable.c_background)
            classic12.setBackgroundResource(R.drawable.c_background)
            classic13.setBackgroundResource(R.drawable.c_background)
            classic14.setBackgroundResource(R.drawable.c_background)
            classic15.setBackgroundResource(R.drawable.c_background)
            classic16.setBackgroundResource(R.drawable.c_background)
            classic17.setBackgroundResource(R.drawable.c_background)
            classic18.setBackgroundResource(R.drawable.c_background)
            classic19.setBackgroundResource(R.drawable.c_background)
            classic20.setBackgroundResource(R.drawable.c_background)

        }

        cService = Intent(this, MainService::class.java)
        pService = Intent(this, ProService::class.java)
        premService = Intent(this, PremiumService::class.java)


        lightSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                backgroundLight = 1

                classic1.setBackgroundResource(R.drawable.c_background)
                classic2.setBackgroundResource(R.drawable.c_background)
                classic3.setBackgroundResource(R.drawable.c_background)
                classic4.setBackgroundResource(R.drawable.c_background)
                classic5.setBackgroundResource(R.drawable.c_background)
                classic6.setBackgroundResource(R.drawable.c_background)
                classic7.setBackgroundResource(R.drawable.c_background)
                classic8.setBackgroundResource(R.drawable.c_background)
                classic9.setBackgroundResource(R.drawable.c_background)
                classic10.setBackgroundResource(R.drawable.c_background)
                classic11.setBackgroundResource(R.drawable.c_background)
                classic12.setBackgroundResource(R.drawable.c_background)
                classic13.setBackgroundResource(R.drawable.c_background)
                classic14.setBackgroundResource(R.drawable.c_background)
                classic15.setBackgroundResource(R.drawable.c_background)
                classic16.setBackgroundResource(R.drawable.c_background)
                classic17.setBackgroundResource(R.drawable.c_background)
                classic18.setBackgroundResource(R.drawable.c_background)
                classic19.setBackgroundResource(R.drawable.c_background)
                classic20.setBackgroundResource(R.drawable.c_background)
            } else {
                backgroundLight = 2

                classic1.setBackgroundResource(0)
                classic2.setBackgroundResource(0)
                classic3.setBackgroundResource(0)
                classic4.setBackgroundResource(0)
                classic5.setBackgroundResource(0)
                classic6.setBackgroundResource(0)
                classic7.setBackgroundResource(0)
                classic8.setBackgroundResource(0)
                classic9.setBackgroundResource(0)
                classic10.setBackgroundResource(0)
                classic11.setBackgroundResource(0)
                classic12.setBackgroundResource(0)
                classic13.setBackgroundResource(0)
                classic14.setBackgroundResource(0)
                classic15.setBackgroundResource(0)
                classic16.setBackgroundResource(0)
                classic17.setBackgroundResource(0)
                classic18.setBackgroundResource(0)
                classic19.setBackgroundResource(0)
                classic20.setBackgroundResource(0)
            }
        }

        changeRed.setOnClickListener {

            DrawableCompat.setTint(
                classic1.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic2.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic3.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic4.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic5.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic6.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic7.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic8.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic9.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic10.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic11.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic12.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic13.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic14.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic15.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic16.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic17.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic18.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic19.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )
            DrawableCompat.setTint(
                classic20.drawable, ContextCompat.getColor(
                    this,
                    R.color.primary
                )
            )

            colour = 1
        }
        changeWhite.setOnClickListener {

            DrawableCompat.setTint(
                classic1.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic2.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic3.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic4.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic5.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic6.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic7.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic8.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic9.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic10.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic11.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic12.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic13.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic14.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic15.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic16.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic17.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic18.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic19.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            DrawableCompat.setTint(
                classic20.drawable, ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )

            colour = 2
        }
        changeGreen.setOnClickListener {

            DrawableCompat.setTint(
                classic1.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic2.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic3.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic4.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic5.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic6.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic7.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic8.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic9.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic10.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic11.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic12.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic13.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic14.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic15.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic16.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic17.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic18.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic19.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )
            DrawableCompat.setTint(
                classic20.drawable, ContextCompat.getColor(
                    this,
                    R.color.green
                )
            )

            colour = 3
        }
        changeYellow.setOnClickListener {

            DrawableCompat.setTint(
                classic1.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic2.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic3.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic4.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic5.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic6.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic7.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic8.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic9.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic10.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic11.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic12.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic13.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic14.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic15.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic16.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic17.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic18.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic19.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )
            DrawableCompat.setTint(
                classic20.drawable, ContextCompat.getColor(
                    this,
                    R.color.yellow
                )
            )

            colour = 4
        }
        changePurple.setOnClickListener {

            DrawableCompat.setTint(
                classic1.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic2.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic3.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic4.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic5.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic6.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic7.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic8.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic9.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic10.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic11.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic12.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic13.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic14.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic15.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic16.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic17.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic18.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic19.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )
            DrawableCompat.setTint(
                classic20.drawable, ContextCompat.getColor(
                    this,
                    R.color.purple
                )
            )

            colour = 5
        }
        changeBlue.setOnClickListener {

            DrawableCompat.setTint(
                classic1.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic2.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic3.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic4.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic5.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic6.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic7.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic8.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic9.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic10.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic11.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic12.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic13.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic14.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic15.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic16.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic17.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic18.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic19.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )
            DrawableCompat.setTint(
                classic20.drawable, ContextCompat.getColor(
                    this,
                    R.color.blue
                )
            )

            colour = 6
        }
        classic1.setOnClickListener {
            crossNum = 51
            stopServices()
            toastShow()
            closeFg()
        }
        classic2.setOnClickListener {
            crossNum = 52
            stopServices()
            toastShow()
            closeFg()
        }
        classic3.setOnClickListener {
            crossNum = 53
            stopServices()
            toastShow()
            closeFg()
        }
        classic4.setOnClickListener {
            crossNum = 54
            stopServices()
            toastShow()
            closeFg()
        }
        classic5.setOnClickListener {
            crossNum = 55
            stopServices()
            toastShow()
            closeFg()
        }
        classic6.setOnClickListener {
            crossNum = 56
            stopServices()
            toastShow()
            closeFg()
        }
        classic7.setOnClickListener {
            crossNum = 57
            stopServices()
            toastShow()
            closeFg()
        }
        classic8.setOnClickListener {
            crossNum = 58
            stopServices()
            toastShow()
            closeFg()
        }
        classic9.setOnClickListener {
            crossNum = 59
            stopServices()
            toastShow()
            closeFg()
        }
        classic10.setOnClickListener {
            crossNum = 60
            stopServices()
            toastShow()
            closeFg()
        }
        classic11.setOnClickListener {
            crossNum = 61
            stopServices()
            toastShow()
            closeFg()
        }
        classic12.setOnClickListener {
            crossNum = 62
            stopServices()
            toastShow()
            closeFg()
        }
        classic13.setOnClickListener {
            crossNum = 63
            stopServices()
            toastShow()
            closeFg()
        }
        classic14.setOnClickListener {
            crossNum = 64
            stopServices()
            toastShow()
            closeFg()
        }
        classic15.setOnClickListener {
            crossNum = 65
            stopServices()
            toastShow()
            closeFg()
        }
        classic16.setOnClickListener {
            crossNum = 66
            stopServices()
            toastShow()
            closeFg()
        }
        classic17.setOnClickListener {
            crossNum = 67
            stopServices()
            toastShow()
            closeFg()
        }
        classic18.setOnClickListener {
            crossNum = 68
            stopServices()
            toastShow()
            closeFg()
        }
        classic19.setOnClickListener {
            crossNum = 69
            stopServices()
            toastShow()
            closeFg()
        }
        classic20.setOnClickListener {
            crossNum = 70
            stopServices()
            toastShow()
            closeFg()
        }

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
