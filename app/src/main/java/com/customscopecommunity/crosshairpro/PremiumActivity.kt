package com.customscopecommunity.crosshairpro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.customscopecommunity.crosshairpro.adapter.PremiumAdapter
import com.customscopecommunity.crosshairpro.screens.action
import com.customscopecommunity.crosshairpro.services.MainService
import com.customscopecommunity.crosshairpro.services.PremiumService
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
import kotlinx.android.synthetic.main.activity_premium.*
import java.util.*

class PremiumActivity : AppCompatActivity() {

    private var imageList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_premium)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        // unity Ads
        val myAdsListener = UnityAdsListener()
        UnityAds.addListener(myAdsListener)

        initialize()
    }

    private fun initialize() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.pro_collection)

        val gridLayoutManager = GridLayoutManager(this, 3)
        premium_recycler_view.layoutManager = gridLayoutManager
        premium_recycler_view.setHasFixedSize(true)                        // later add

        imageList.add(R.drawable.prem1n)
        imageList.add(R.drawable.prem2)
        imageList.add(R.drawable.prem3)
        imageList.add(R.drawable.prem4)
        imageList.add(R.drawable.prem5n)
        imageList.add(R.drawable.prem6n)
        imageList.add(R.drawable.prem7n)
        imageList.add(R.drawable.prem8n)
        imageList.add(R.drawable.prem9n)
        imageList.add(R.drawable.prem10n)
        imageList.add(R.drawable.prem11n)
        imageList.add(R.drawable.prem12n)
        imageList.add(R.drawable.prem13n)
        imageList.add(R.drawable.prem14n)
        imageList.add(R.drawable.prem15n)
        imageList.add(R.drawable.prem16n)
        imageList.add(R.drawable.prem17n)
        imageList.add(R.drawable.prem18n)
        imageList.add(R.drawable.prem19n)
        imageList.add(R.drawable.prem20n)
        imageList.add(R.drawable.prem21n)

        imageList.add(R.drawable.pro1n)
        imageList.add(R.drawable.pro2n)
        imageList.add(R.drawable.pro3n)
        imageList.add(R.drawable.pro4n)
        imageList.add(R.drawable.pro5n)
        imageList.add(R.drawable.pro6n)
        imageList.add(R.drawable.pro7n)
        imageList.add(R.drawable.pro8n)
        imageList.add(R.drawable.pro9n)
        imageList.add(R.drawable.pro10n)
        imageList.add(R.drawable.pro11n)
        imageList.add(R.drawable.pro12n)
        imageList.add(R.drawable.pro13n)
        imageList.add(R.drawable.pro14n)
        imageList.add(R.drawable.pro15n)
        imageList.add(R.drawable.pro16n)
        imageList.add(R.drawable.pro17n)
        imageList.add(R.drawable.pro18n)
        imageList.add(R.drawable.pro19n)
        imageList.add(R.drawable.pro20n)

        imageList.add(R.drawable.nn1)
        imageList.add(R.drawable.nn2)
        imageList.add(R.drawable.nn3)
        imageList.add(R.drawable.nn4)
        imageList.add(R.drawable.nn5)
        imageList.add(R.drawable.nn6)
        imageList.add(R.drawable.nn7)
        imageList.add(R.drawable.nn8)
        imageList.add(R.drawable.nn9)
        imageList.add(R.drawable.nn10)
        imageList.add(R.drawable.nn11)
        imageList.add(R.drawable.nn12)
        imageList.add(R.drawable.nn13)

        premium_recycler_view.adapter = PremiumAdapter(this, imageList)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun stopServices() {
        stopService(Intent(this, MainService::class.java))
        stopService(Intent(this, PremiumService::class.java))
    }

    private fun showToast() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(action))
        val toast =
            Toast.makeText(this, getString(R.string.tap_start), Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

        val handler = Handler()
        handler.postDelayed({ toast.cancel() }, 700)
    }

    // Implement the IUnityAdsListener interface methods:
    inner class UnityAdsListener : IUnityAdsListener {
        override fun onUnityAdsReady(placementId: String?) {
        }

        override fun onUnityAdsStart(placementId: String?) {
        }

        override fun onUnityAdsFinish(placementId: String?, finishState: UnityAds.FinishState?) {
            showUnityVideoAd = false
            stopServices()
            showToast()
            onBackPressed()
        }

        override fun onUnityAdsError(error: UnityAds.UnityAdsError?, message: String?) {
        }
    }
}