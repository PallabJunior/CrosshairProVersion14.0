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
import androidx.lifecycle.ViewModelProvider
import com.customscopecommunity.crosshairpro.constants.AdUnitIds.MED_INTERSTITIAL_UNIT
import com.customscopecommunity.crosshairpro.constants.Constants.CHANNEL_ID
import com.customscopecommunity.crosshairpro.constants.Constants.PRIVACY_POLICY_URL
import com.customscopecommunity.crosshairpro.constants.Constants.RATE_ME_URL
import com.customscopecommunity.crosshairpro.databinding.ActivitySecondMainBinding
import com.customscopecommunity.crosshairpro.newdatabase.State
import com.customscopecommunity.crosshairpro.newdatabase.StateDatabase
import com.customscopecommunity.crosshairpro.screens.MainFragment
import com.customscopecommunity.crosshairpro.viewmodelfactories.SecondMainViewModelFactory
import com.customscopecommunity.crosshairpro.viewmodels.SecondMainViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
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

    private var medInterstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd? = null
    private var countAdsShowed = 0
    private var isAdInitialized = false

    private lateinit var binding: ActivitySecondMainBinding

    private lateinit var mFragment: Fragment

    private lateinit var secondMainVm: SecondMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_main)


        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        val vmFactory = SecondMainViewModelFactory(application)
        secondMainVm = ViewModelProvider(this, vmFactory).get(SecondMainViewModel::class.java)

        mFragment = MainFragment()
        addMainFragment(mFragment)

        secondMainVm.readSavedAdImpression.observe(this, {
            countAdsShowed = it


            // making sure that ad initialize only one time(isAdInitialized) &
            // user can see only the specified number(below in the `if` condition) of ads in 24 hours
            if (it < 10 && !isAdInitialized) {
                isAdInitialized = true
                MobileAds.initialize(this) {
                    loadAdmobInterstitialMediationAd()
                    loadAdmobBannerMediationAd()
                }
            }

        })

        secondMainVm.readSavedDateTimeMillis.observe(this, {
            if (isTimeLimitOver(it)) {
                // resetting number of ad count and time millis once time limit is over
                secondMainVm.saveNumberOfAdImpression(0)
                secondMainVm.saveCurrentDateTimeMillis(0)
            }
        })

        CoroutineScope(IO).launch {
            getStateFromRoomDb()
        }

        createNotificationChannel()


    }

    private fun countAdImpression() {
        countAdsShowed++
        secondMainVm.saveNumberOfAdImpression(countAdsShowed)

        updateTimeMillis()
    }

    private fun updateTimeMillis() {
        if (countAdsShowed == 1) {
            // upload the time when the first interstitial ad is showed
            secondMainVm.saveCurrentDateTimeMillis(System.currentTimeMillis())
        }
    }


    private fun isTimeLimitOver(savedMillis: Long): Boolean {
        if (savedMillis == 0L) {
            // gets called on first install or on start up when no ads showed in that day
            return false
        }

        // Check time elapsed
        return System.currentTimeMillis() >= savedMillis + 24 * 60 * 60 * 1000
    }

    private fun loadAdmobInterstitialMediationAd() {
        val adRequest = AdRequest.Builder().build()

        com.google.android.gms.ads.interstitial.InterstitialAd.load(
            this, MED_INTERSTITIAL_UNIT, adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    medInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd) {
                    medInterstitialAd = interstitialAd
                    medInterstitialAdListener()
                }

            })
    }

    private fun loadAdmobBannerMediationAd() {
        val adRequest = AdRequest.Builder().build()
        val adView: AdView = binding.bannerAdView
        adView.loadAd(adRequest)
        bannerAdListener(adView)
    }

    private fun bannerAdListener(bannerView: AdView) {
        bannerView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                countAdImpression()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
            }

            override fun onAdOpened() {
            }

            override fun onAdClicked() {
            }

            override fun onAdLeftApplication() {
            }

            override fun onAdClosed() {
            }
        }
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
            R.id.policy -> PRIVACY_POLICY_URL.openUrl()
            R.id.rateMe -> RATE_ME_URL.openRateMeUrl()
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
        // State will be null at first launch
        val refState: State? = StateDatabase(applicationContext).getStateDao().getAllStates()
        val serviceState: Boolean? = refState?.isRunning

        sendStateToMainThread(serviceState)
    }

    private fun addMainFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun medInterstitialAdListener() {
        medInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                (mFragment as MainFragment).startRequiredService()
                medInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError?) {
            }

            override fun onAdShowedFullScreenContent() {
                medInterstitialAd = null
            }
        }
    }


    override fun onResume() {
        super.onResume()
        binding.bannerAdView.resume()

        if (isSightSelected && canShowFanAd) {
            isSightSelected = false
            canShowFanAd = false

            showMedInterstitialAd()
        }
    }

    private fun showMedInterstitialAd() {
        if (medInterstitialAd != null) {
            medInterstitialAd?.show(this)
            countAdImpression()
        } else {
            (mFragment as MainFragment).startRequiredService()
        }
    }

    override fun onDestroy() {
        binding.bannerAdView.destroy()
        medInterstitialAd = null
        super.onDestroy()
    }
}