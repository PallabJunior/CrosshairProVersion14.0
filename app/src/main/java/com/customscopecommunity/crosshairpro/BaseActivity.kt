package com.customscopecommunity.crosshairpro

import android.animation.*
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.customscopecommunity.crosshairpro.communicate.Variables
import com.customscopecommunity.crosshairpro.constants.CurrentScreen
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView


//private const val TAG = "BASE_ACTIVITY"

open class BaseActivity : AppCompatActivity() {

    fun loadNativeAd(
        adUnitId: String,
        screen: CurrentScreen,
        isAdLoaded: (state: Boolean) -> Unit
    ) {
        val adFrame =
            if (screen == CurrentScreen.CLASSIC) {
                findViewById<FrameLayout>(R.id.native_ad_frame_classic)
            } else {
                findViewById(R.id.native_ad_frame_prem)
            }

        val builder = AdLoader.Builder(this, adUnitId)

        builder.forNativeAd { nativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            // If this callback occurs after the activity is destroyed, you must call
            // destroy and return or you may get a memory leak.
            if (isDestroyed || isFinishing || isChangingConfigurations) {
                nativeAd.destroy()
                return@forNativeAd
            }

            val adView = layoutInflater.inflate(R.layout.native_ad_big, null) as NativeAdView


            populateNativeAdView(nativeAd, adView)
            adFrame.removeAllViews()
            adFrame.addView(adView)
        }

        val videoOptions = VideoOptions.Builder()
            .setStartMuted(true)
            .build()
        val adOptions = NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .build()

        builder.withNativeAdOptions(adOptions)


        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                //Log.d(TAG, "native.onAdLoaded: called")
                isAdLoaded(true)
                // to update the adCount when returning to Home screen
                Variables.isNativeAdShowed = true
            }

//            override fun onAdFailedToLoad(p0: LoadAdError) {
//                super.onAdFailedToLoad(p0)
//                Log.d(TAG, "native.onAdFailedToLoad: $p0")
//            }
//
//            override fun onAdImpression() {
//                super.onAdImpression()
//                Log.d(TAG, "onAdImpression: called")
//            }
//
//            override fun onAdClicked() {
//                super.onAdClicked()
//                Log.d(TAG, "onAdClicked: callled")
//            }

        }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun populateNativeAdView(
        nativeAd: NativeAd,
        adView: NativeAdView
    ) {

        adView.mediaView = adView.findViewById(R.id.ad_media)
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        (adView.mediaView as MediaView).setMediaContent(nativeAd.mediaContent!!)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            (adView.bodyView as TextView).visibility = View.INVISIBLE
        } else {
            (adView.bodyView as TextView).visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            (adView.callToActionView as Button).visibility = View.INVISIBLE
        } else {
            (adView.callToActionView as Button).visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            (adView.iconView as ImageView).visibility = View.INVISIBLE
        } else {
            (adView.iconView as ImageView).setImageDrawable(nativeAd.icon!!.drawable)
            (adView.iconView as ImageView).visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)

    }


    fun View.slideView() {
        val view = this

        view.visibility = View.VISIBLE
        val layoutParams = view.layoutParams

        layoutParams.height = 1
        view.layoutParams = layoutParams
        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                Resources.getSystem().displayMetrics.widthPixels,
                View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
        )
        val height = view.measuredHeight
        val valueAnimator = ObjectAnimator.ofInt(1, height)
        valueAnimator.duration = 800
        valueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            val layoutParams2 = view.layoutParams

            if (height > value) {
                layoutParams2.height = value
                view.layoutParams = layoutParams2
            } else {
                layoutParams2.height = ViewGroup.LayoutParams.WRAP_CONTENT
                view.layoutParams = layoutParams2
            }
        }
        valueAnimator.start()
    }


    fun View.slideDown() {
        val view = this

        view.post {
            val height = view.height
            val valueAnimator = ObjectAnimator.ofInt(height, 0)
            valueAnimator.addUpdateListener { animation ->
                val value = animation.animatedValue as Int

                if (value > 0) {
                    val layoutParams = view.layoutParams
                    layoutParams.height = value
                    view.layoutParams = layoutParams
                } else {
                    view.visibility = View.GONE
                }
            }
            valueAnimator.start()
        }
    }


}