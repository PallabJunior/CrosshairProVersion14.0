package com.customscopecommunity.crosshairpro

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.customscopecommunity.crosshairpro.databinding.ActivitySecondMainBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_second_main.*


const val CHANNEL_ID = "crosshair"
var crossNum: Int = 0
var afterFinishVisibility: Int = 0
private const val policyUrl: String = "https://sites.google.com/view/crosshairpro"
private const val rateMeUrl: String =
    "https://play.google.com/store/apps/details?id=com.customscopecommunity.crosshairpro"

internal lateinit var mInterstitialAd: InterstitialAd
private const val APP_UNIT_ID = "ca-app-pub-8201262723803857~4410500643"
//private const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-8201262723803857/2113390152"  //real
private const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
class SecondMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondMainBinding

    private var systemAlertWindowPermission = 2084

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_main)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        if (!Settings.canDrawOverlays(this)) {
            askPermission()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        MobileAds.initialize(this, APP_UNIT_ID)

        bannerAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = INTERSTITIAL_AD_UNIT_ID
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }


    }

    private fun askPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(intent, systemAlertWindowPermission)
    }


    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            channel.setSound(null, null)
            channel.enableVibration(false)
            channel.enableLights(false)
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    override fun onCreateOptionsMenu(policyMenu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.policy_menu, policyMenu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.policy -> policyUrl.openUrl()
            R.id.rateMe -> rateMeUrl.openRateMeUrl()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun String.openUrl() {
        val uri = Uri.parse(this)
        val launch = Intent(Intent.ACTION_VIEW, uri)
        startActivity(launch)
    }

    private fun String.openRateMeUrl() {
        val uri = Uri.parse(this)
        val launch = Intent(Intent.ACTION_VIEW, uri)
        startActivity(launch)
    }

}