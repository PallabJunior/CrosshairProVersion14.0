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
import com.customscopecommunity.crosshairpro.databinding.ActivitySecondMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.activity_second_main.*

const val CHANNEL_ID = "crosshair"
var crossNum: Int = 200
var afterFinishVisibility: Int = 0
internal lateinit var mRewardedVideoAd: RewardedVideoAd
internal lateinit var mInterstitialAd: InterstitialAd
const val systemAlertWindowPermission = 2084

class SecondMainActivity : AppCompatActivity(), RewardedVideoAdListener {

    private lateinit var binding: ActivitySecondMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_main)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        MobileAds.initialize(this)

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this
//        mRewardedVideoAd.loadAd(getString(R.string.rewarded_video_ad), AdRequest.Builder().build())
        mRewardedVideoAd.loadAd(
            "ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build()
        )

        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = getString(R.string.interstitial_ad)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        first_banner_ad.loadAd(AdRequest.Builder().build())
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

    override fun onRewardedVideoAdClosed() {
    }

    override fun onRewardedVideoAdLeftApplication() {
    }

    override fun onRewardedVideoAdLoaded() {
    }

    override fun onRewardedVideoAdOpened() {
    }

    override fun onRewardedVideoCompleted() {
    }

    override fun onRewarded(p0: RewardItem?) {
    }

    override fun onRewardedVideoStarted() {
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
    }
}