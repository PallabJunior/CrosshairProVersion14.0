package com.customscopecommunity.crosshairpro

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.customscopecommunity.crosshairpro.databinding.ActivitySecondMainBinding
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.BannerView.IListener
import com.unity3d.services.banners.UnityBannerSize
import kotlinx.android.synthetic.main.activity_second_main.*


const val CHANNEL_ID = "crosshair"
var crossNum: Int = 200
var afterFinishVisibility: Int = 0
const val systemAlertWindowPermission = 2084

var isCrosshairSelected = false

var showUnityVideoAd = false          // var showUnityVideoAd = false
private var showUnityVideoAdAgain = true            // interstitial and rewarded ad

class SecondMainActivity : AppCompatActivity() {

    ///unity ads
    private val unityGameID = "3708923"
    private val testMode = true
    private val bannerPlacement = "bannerPro"
    private val rewardedPlacement = "crosshairRewarded"
    private val interstitialPlacement = "interstitialAdCrosshairPro"


    // Interstitial and rewarded ad listener
    private val rewardedUnityAdsListener = UnityVideoAdsListener()

    // Listener for banner ad events
    private val bannerListener = UnityBannerListener()
    private lateinit var topBanner: BannerView

    private lateinit var binding: ActivitySecondMainBinding

    //private lateinit var adView: AdView              // admob banner

    //private var initialLayoutComplete = false

    // admob banner
//    private val adSize: AdSize
//        get() {
//            val display = windowManager.defaultDisplay
//            val outMetrics = DisplayMetrics()
//            display.getMetrics(outMetrics)
//
//            val density = outMetrics.density
//
//            var adWidthPixels = banner_ad_container.width.toFloat()
//            if (adWidthPixels == 0f) {
//                adWidthPixels = outMetrics.widthPixels.toFloat()
//            }
//
//            val adWidth = (adWidthPixels / density).toInt()
//            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        isCrosshairSelected = false

        //unity ads initialize
        UnityAds.initialize(this, unityGameID, testMode)

        // Listener for rewarded and interstitial ad events
        UnityAds.addListener(rewardedUnityAdsListener)

        if (UnityAds.isInitialized()) {
            showUnityBannerAd()
        } else {
            val handler = Handler()
            handler.postDelayed({
                showUnityBannerAd()
            }, 3000)
        }

//        MobileAds.initialize(this)  // admob banner
//
//        //admob banner
//        adView = AdView(this)
//        banner_ad_container.addView(adView)
//        banner_ad_container.viewTreeObserver.addOnGlobalLayoutListener {
//            if (!initialLayoutComplete) {
//                initialLayoutComplete = true
//                loadBanner()
//            }
//        }
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


//    private fun loadBanner() {
//        //adView.adUnitId = getString(R.string.main_banner)
//        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
//
//        adView.adSize = adSize
//
//        val adRequest = AdRequest.Builder().build()
//        adView.loadAd(adRequest)
//    }


    public override fun onPause() {
        //adView.pause()
        super.onPause()
        showUnityVideoAd = false
    }

    public override fun onResume() {
        super.onResume()
        //adView.resume()
        //showUnityInterstitialAd()
        showRewardedVideoAd()

    }

    public override fun onDestroy() {
        //adView.destroy()
        super.onDestroy()
    }

    private fun showUnityInterstitialAd() {
        // show Unity Video Ad
        if (UnityAds.isReady(interstitialPlacement) && showUnityVideoAd && showUnityVideoAdAgain) {
            UnityAds.show(this, interstitialPlacement)

            showUnityVideoAdAgain = false
        }
    }

    private fun showRewardedVideoAd() {
        // Rewarded video ad
        if (UnityAds.isReady(rewardedPlacement) && showUnityVideoAd && showUnityVideoAdAgain) {
            UnityAds.show(this, rewardedPlacement)
            showUnityVideoAdAgain = false
        } else {
            // Interstitial Video ad
            showUnityInterstitialAd()
        }
    }

    private fun showUnityBannerAd() {
        topBanner = BannerView(this, bannerPlacement, UnityBannerSize(320, 50))
        topBanner.listener = bannerListener
        topBanner.load()
        banner_ad_container.addView(topBanner)
    }

//    private fun destroyUnityBannerAd() {
//        topBannerView.removeAllViews()
//    }


    //////////// unity rewarded video ad listener
    class UnityVideoAdsListener : IUnityAdsListener {

        override fun onUnityAdsReady(placementId: String?) {
        }

        override fun onUnityAdsStart(placementId: String?) {
        }

        override fun onUnityAdsFinish(placementId: String?, p1: UnityAds.FinishState?) {
        }

        override fun onUnityAdsError(p0: UnityAds.UnityAdsError?, p1: String?) {
        }
    }
    //////////// unity rewarded video ad listener

    // Implement the Listener interface methods for unity banner ad
    class UnityBannerListener : IListener {
        override fun onBannerLoaded(bannerAdView: BannerView) {
        }

        override fun onBannerFailedToLoad(bannerAdView: BannerView, errorInfo: BannerErrorInfo) {
        }

        override fun onBannerClick(bannerAdView: BannerView) {
        }

        override fun onBannerLeftApplication(bannerAdView: BannerView) {
        }
    }
}