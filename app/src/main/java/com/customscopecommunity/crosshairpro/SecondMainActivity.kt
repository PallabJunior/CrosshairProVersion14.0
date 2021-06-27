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
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.customscopecommunity.crosshairpro.communicate.Variables
import com.customscopecommunity.crosshairpro.communicate.Variables.isMaxAdReached
import com.customscopecommunity.crosshairpro.communicate.Variables.isServiceRunningOnStart
import com.customscopecommunity.crosshairpro.constants.AdUnitIds.MED_INTERSTITIAL_UNIT
import com.customscopecommunity.crosshairpro.constants.AdUnitIds.SPN_AD_LINK
import com.customscopecommunity.crosshairpro.constants.Constants.CHANNEL_ID
import com.customscopecommunity.crosshairpro.constants.Constants.MORE_APPS_URL
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_second_main.*
import kotlinx.android.synthetic.main.bottom_sheet_spn_ad.*
import kotlinx.android.synthetic.main.sponsored_ad_layout.*
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

//private const val TAG = "SECOND_MAIN_ACTIVITY"

class SecondMainActivity : BaseActivity() {

    private var rotationAngle = 0f
    private var medInterstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd? = null
    private var countAdsShowed = 0
    private val maxAdNumber = 10
    private var isAdInitialized = false
    private lateinit var binding: ActivitySecondMainBinding
    private lateinit var mFragment: Fragment
    private lateinit var secondMainVm: SecondMainViewModel
    private lateinit var bottomSheet: BottomSheetBehavior<LinearLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_main)

        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        val vmFactory = SecondMainViewModelFactory(application)
        secondMainVm = ViewModelProvider(this, vmFactory).get(SecondMainViewModel::class.java)

        mFragment = MainFragment()
        addMainFragment(mFragment)


        handleBottomSheet()

        initializeSponsoredAd()
        initializeAds()

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

    private fun disableButtons() {
        (mFragment as MainFragment).disableMainButtons()
    }

    private fun enableButtons() {
        (mFragment as MainFragment).enableMainButtons()
    }

    private fun handleBottomSheet() {
        bottomSheet = BottomSheetBehavior.from(bottom_sheet_parent)
        bottomSheet.isDraggable = false

        collapse_ad.setOnClickListener {
            if (bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED) {
                rotationAngle = 0f
                collapse_ad.animate().rotation(rotationAngle).setDuration(200).start()
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                rotationAngle = 180f
                collapse_ad.animate().rotation(rotationAngle).setDuration(200).start()
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        bottomSheet.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        rotationAngle = 0f
                        collapse_ad.animate().rotation(rotationAngle).setDuration(200).start()
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        rotationAngle = 180f
                        collapse_ad.animate().rotation(rotationAngle).setDuration(200).start()
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }
        })
    }

    private fun initializeAds() {
        secondMainVm.readSavedAdImpression.observe(this, {
            countAdsShowed = it
            // making sure that ad initialize only one time(isAdInitialized) &
            // user can see only the specified number(below in the `if` condition) of ads in 24 hours
            if (it < maxAdNumber && !isAdInitialized) {
                //Log.d(TAG, "initializeAds: called")
                isAdInitialized = true
                MobileAds.initialize(this) {
                    // to load the first native ad in the setting activity
                    // on the first open after timer period is over
                    isMaxAdReached = false
                    loadAdmobInterstitialMediationAd()
                }
            } else {
                if (it >= maxAdNumber) {
                    //Log.d(TAG, "initializeAds: max ad number reached")
                    isMaxAdReached = true
                }
            }

        })
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

        val minutes = 30
        val timePeriod = (minutes * 60 * 1000) // time in millis
        // Check time elapsed
        return System.currentTimeMillis() >= savedMillis + timePeriod
    }

    private fun loadAdmobInterstitialMediationAd() {
        val adRequest = AdRequest.Builder().build()

        com.google.android.gms.ads.interstitial.InterstitialAd.load(
            this, MED_INTERSTITIAL_UNIT, adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    //Log.d(TAG, "int.onAdFailedToLoad: $adError")
                    medInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd) {
                    //Log.d(TAG, "int.onAdLoaded: called")
                    medInterstitialAd = interstitialAd
                    medInterstitialAdListener()
                }
            })
    }

    private fun medInterstitialAdListener() {
        medInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                (mFragment as MainFragment).startRequiredService()
                medInterstitialAd = null
                // reloading ad
                loadAdmobInterstitialMediationAd()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                medInterstitialAd = null
            }

            override fun onAdShowedFullScreenContent() {
                medInterstitialAd = null
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
            R.id.rateMe -> RATE_ME_URL.openUrl()
            R.id.menu_more_apps -> MORE_APPS_URL.openUrl()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun String.openUrl() {
        val uri = Uri.parse(this)
        val launch = Intent(Intent.ACTION_VIEW, uri)
        startActivity(launch)
    }


    private fun setStateOnUi(isRunning: Boolean?) {
        // handle button visibility in fragment if the user is returning from the game
        if (isRunning == true && crossNum != 500) {
            isServiceRunningOnStart = true
            (mFragment as MainFragment).setButtonsVisibility()
        }

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

    private fun initializeSponsoredAd() {
        spn_banner_container.setOnClickListener {
            SPN_AD_LINK.openUrl()
        }

        btn_install_spn_ad.setOnClickListener {
            SPN_AD_LINK.openUrl()
        }

    }


    private fun showMedInterstitialAd() {
        if (medInterstitialAd != null) {
            medInterstitialAd?.show(this)
        } else {
            (mFragment as MainFragment).startRequiredService()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Variables.isNativeAdShowed) {
            Variables.isNativeAdShowed = false
            countAdImpression()
        }

        if (canShowFanAd) {
            canShowFanAd = false
            showMedInterstitialAd()
        }
    }

    override fun onDestroy() {
        medInterstitialAd = null
        super.onDestroy()
    }
}