package com.customscopecommunity.crosshairpro

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.customscopecommunity.crosshairpro.constants.Constants.CHANNEL_ID
import com.customscopecommunity.crosshairpro.databinding.ActivitySecondMainBinding
import com.customscopecommunity.crosshairpro.newdatabase.State
import com.customscopecommunity.crosshairpro.newdatabase.StateDatabase
import com.customscopecommunity.crosshairpro.screens.MainFragment
import com.facebook.ads.*
import com.facebook.ads.AdSize
import kotlinx.android.synthetic.main.activity_second_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


var crossNum: Int = 500
var afterFinishVisibility: Int = 0
const val systemAlertWindowPermission = 2084
var firstOpen = true // To show the toast message on first open because of slow creation of service
var canShowFanAd = false
var isSightSelected = false

class SecondMainActivity : AppCompatActivity() {

    private lateinit var fanInterstitialAd: InterstitialAd
    private lateinit var fanBannerAdView: AdView

    private val fanInterstitialPlacementId = "1761971713959604_1761999090623533"
    private val fanBannerPlacementId = "1761971713959604_1813897582100350"

    private lateinit var binding: ActivitySecondMainBinding

    private lateinit var mFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_main)

        // FAN
        AudienceNetworkAds.initialize(this)

        setSupportActionBar(toolbar)

        mFragment = MainFragment()
        addMainFragment(mFragment)

        CoroutineScope(IO).launch {
            getStateFromRoomDb()
        }

        createNotificationChannel()

        //AdSettings.addTestDevice("91af5e10-a182-42a4-84f6-d8038263d98f")  /// required for emalutors the id will be different

        loadFanInterstitialAd()
        loadFanBannerAd()


    }

    private fun loadFanBannerAd() {
        fanBannerAdView = AdView(this, fanBannerPlacementId, AdSize.BANNER_HEIGHT_50)

        banner_ad_container.addView(fanBannerAdView)
        fanBannerAdView.loadAd()
    }


    private fun loadFanInterstitialAd() {
        //VID_HD_16_9_46S_APP_INSTALL#YOUR_PLACEMENT_ID
        fanInterstitialAd = InterstitialAd(this, fanInterstitialPlacementId)

        fanInterstitialAd.loadAd(
            fanInterstitialAd.buildLoadAdConfig()
                .withAdListener(fanInterstitialAdListener())
                .build()
        )
    }

    private fun showFanInterstitialAd(): Boolean {
        return if (fanInterstitialAd.isAdLoaded && !fanInterstitialAd.isAdInvalidated) {
            fanInterstitialAd.show()
            true
        } else
            false

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
            R.id.policy -> getString(R.string.policy_url).openUrl()
            R.id.rateMe -> getString(R.string.rate_me_url).openRateMeUrl()
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


    private fun setStateOnUi(isRunning: Boolean?) {
        // handle button visibility in fragment if the user is returning from the game
        if (isRunning == true && crossNum != 500)
            (mFragment as MainFragment).setButtonsVisibility()

    }

    private suspend fun sendStateToMainThread(state: Boolean?) {
        withContext(Main) {
            setStateOnUi(state)
        }
    }

    private suspend fun getStateFromRoomDb() {
        val refState: State? = StateDatabase(applicationContext).getStateDao().getAllStates()
        val serviceState: Boolean? = refState?.isRunning

        sendStateToMainThread(serviceState)
    }

    private fun addMainFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun fanInterstitialAdListener() = object : InterstitialAdListener {
        override fun onInterstitialDisplayed(ad: Ad) {
        }

        override fun onInterstitialDismissed(ad: Ad) {
            (mFragment as MainFragment).startRequiredService()

            fanInterstitialAd.destroy()
        }

        override fun onError(ad: Ad, adError: AdError) {
        }

        override fun onAdLoaded(ad: Ad) {
        }

        override fun onAdClicked(ad: Ad) {
        }

        override fun onLoggingImpression(ad: Ad) {
        }
    }

    override fun onResume() {
        super.onResume()

        if (isSightSelected && canShowFanAd) {
            isSightSelected = false
            canShowFanAd = false
            if (!showFanInterstitialAd()) {
                (mFragment as MainFragment).startRequiredService()
            }
        }

    }

    override fun onDestroy() {
        fanInterstitialAd.destroy()
        fanBannerAdView.destroy()
        super.onDestroy()
    }
}