package com.customscopecommunity.crosshairpro

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.customscopecommunity.crosshairpro.constants.Constants.CHANNEL_ID
import com.customscopecommunity.crosshairpro.databinding.ActivitySecondMainBinding
import com.customscopecommunity.crosshairpro.newdatabase.State
import com.customscopecommunity.crosshairpro.newdatabase.StateDatabase
import com.customscopecommunity.crosshairpro.screens.MainFragment
import com.facebook.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
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

class SecondMainActivity : AppCompatActivity(), IUnityAdsListener {

    private lateinit var fanInterstitialAd: InterstitialAd

    private var countShowedAd = 0

    // to stop the  handler from the handler
    private var stopHandler = false

    // not to call the showAdButton() method multiple times in the onUnityAdsReady() method
    private var timerStart = false

    private var handler: Handler = Handler()
    private var runnable: Runnable? = null
    private var delay: Long = 3000

    ///unity ads
    private val unityGameID = "3708923"
    private val testMode = true

    //private val bannerPlacement = "bannerPro"
    private val rewardedPlacement = "crosshairRewarded"
    private val interstitialPlacement = "interstitialAdCrosshairPro"

    //private val fanInterstitialPlacementId = "1761971713959604_1761999090623533"
    private val fanInterstitialPlacementId = ""

    private lateinit var binding: ActivitySecondMainBinding

    private lateinit var adView: AdView              // admob banner
    private var initialLayoutComplete = false

    private lateinit var mFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_main)

        setSupportActionBar(toolbar)

        mFragment = MainFragment()
        addMainFragment(mFragment)

        CoroutineScope(IO).launch {
            getStateFromRoomDb()
        }

        createNotificationChannel()


        /////////////////////////////////////////////////Ads
        AudienceNetworkAds.initialize(this)
        fanInterstitialAd = InterstitialAd(this, fanInterstitialPlacementId)
        loadFanInterstitialAd()

        // Listener for rewarded and interstitial ad events
        UnityAds.addListener(this)

        //unity ads initialize
        UnityAds.initialize(applicationContext, unityGameID, testMode)

        MobileAds.initialize(this)  // admob banner


        //admob banner
        adView = AdView(this)
        banner_ad_container.addView(adView)
        banner_ad_container.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBanner()
            }
        }
        /////////////////////////////////////////////////Ads


        binding.adAnimationView.setOnClickListener {
            showRewardedVideoAd()
        }

    }


    private fun loadFanInterstitialAd() {
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

    // admob banner
    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = banner_ad_container.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
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

    private fun loadBanner() {
        //adView.adUnitId = getString(R.string.main_banner)
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        adView.adSize = adSize

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun showUnityInterstitialAd() {
        // show Unity Video Ad
        if (UnityAds.isReady(interstitialPlacement))
            UnityAds.show(this, interstitialPlacement)
    }

    private fun showRewardedVideoAd() {
        // Rewarded video ad
        if (UnityAds.isReady(rewardedPlacement)) {
            UnityAds.show(this, rewardedPlacement)

        } else {
            showUnityInterstitialAd()
        }
    }

    private fun showAdButton() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay)
            // repeating method
            if (stopHandler)
                return@Runnable

            if (UnityAds.isReady(rewardedPlacement) || UnityAds.isReady(interstitialPlacement)) {
                binding.adAnimationView.visibility = View.VISIBLE
                handler.removeCallbacks(runnable!!) // stop the repeating timer
                stopHandler = true
            }
            // repeating method
        }.also { runnable = it }, delay)
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

    /////////////////////////////////////////////////////////////Unity Video Ads Listener
    override fun onUnityAdsReady(p0: String?) {
        if (!timerStart) {
            showAdButton()
            timerStart = true
        }
    }

    override fun onUnityAdsStart(p0: String?) {
    }

    override fun onUnityAdsFinish(p0: String?, p1: UnityAds.FinishState?) {
        countShowedAd++

        if (countShowedAd <= 1) {
            binding.adAnimationView.visibility = View.GONE
            stopHandler = false
            showAdButton()
        } else {
            if (runnable != null) {
                handler.removeCallbacks(runnable!!) // stop the repeating timer
                stopHandler = true
                binding.adAnimationView.visibility = View.GONE
            }
        }
    }

    override fun onUnityAdsError(p0: UnityAds.UnityAdsError?, p1: String?) {
    }
    /////////////////////////////////////////////////////////////Unity Video Ads Listener

    override fun onPause() {
        if (runnable != null) {
            stopHandler = true
            handler.removeCallbacks(runnable!!) // stop the repeating timer
        }
        adView.pause()
        super.onPause()
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

        // showing ad button after returning to the app or returning to the SecondMainActivity from classic and premium
        if (UnityAds.isReady(rewardedPlacement) || UnityAds.isReady(interstitialPlacement)) {
            if (countShowedAd <= 1)
                binding.adAnimationView.visibility = View.VISIBLE
        }

        adView.resume()
    }

    override fun onDestroy() {
        if (runnable != null) {
            handler.removeCallbacks(runnable!!) // stop the repeating timer
        }
        fanInterstitialAd.destroy()
        UnityAds.removeListener(this)
        adView.destroy()
        super.onDestroy()
    }
}