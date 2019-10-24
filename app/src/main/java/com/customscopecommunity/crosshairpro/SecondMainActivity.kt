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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_second_main.*


const val CHANNEL_ID = "crosshair"
//To communicate with the services
var crossNum: Int = 0
var afterFinishVisibility: Int = 0
private const val policyUrl: String = "https://sites.google.com/view/crosshairpro"

class SecondMainActivity : AppCompatActivity() {

    //dataBinding
    private lateinit var binding: ActivitySecondMainBinding

    private var systemAlertWindowPermission = 2084


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_main)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        //setting the app icon in the action bar
        val actionBar = supportActionBar
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setLogo(R.drawable.actionbar_logo)
        actionBar?.setDisplayUseLogoEnabled(true)


        if (!Settings.canDrawOverlays(this)) {
            askPermission()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        //initializing google ads
        MobileAds.initialize(this, "ca-app-pub-8201262723803857~4410500643")
        //loading banner ad
        bannerAd.loadAd(AdRequest.Builder().build())


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
// Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            channel.setSound(null, null)
            channel.enableVibration(false)
            channel.enableLights(false)
            // Register the channel with the system
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
        }

        return super.onOptionsItemSelected(item)
    }

    private fun String.openUrl() {
        val uri = Uri.parse(this)
        val launch = Intent(Intent.ACTION_VIEW, uri)
        startActivity(launch)
    }


}