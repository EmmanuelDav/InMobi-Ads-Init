package com.example.inmobiadstest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inmobi.ads.AdMetaInfo
import com.inmobi.ads.InMobiAdRequestStatus
import com.inmobi.ads.InMobiInterstitial
import com.inmobi.ads.listeners.InterstitialAdEventListener
import com.inmobi.sdk.InMobiSdk
import org.json.JSONException
import org.json.JSONObject


class InterstitialAds() : AppCompatActivity() {
    companion object {
        val TAG: String = javaClass.name
    }

    private var mInterstitialAd: InMobiInterstitial? = null
    private var mLoadAdButton: Button? = null
    private var mShowAdButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val consent = JSONObject()

        try {
            // Provide correct consent value to sdk which is obtained by User
            consent.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG)
        InMobiSdk.init(this, "5e976de4730046a48ccd397ed5ad298f", consent) { error ->
            if (error == null) {
                Log.d(TAG, "InMobi SDK Initialization Success")
                mLoadAdButton!!.setOnClickListener{
                    if (null == mInterstitialAd) {
                        setupInterstitial()
                    } else {
                        mInterstitialAd!!.load()
                    }

                }
            } else {
                Log.e(TAG, "InMobi SDK Initialization failed: " + error.message)
                mLoadAdButton!!.setOnClickListener{
                    Toast.makeText(applicationContext, "InMobi SDK is not initialized." +
                            "Check logs for more information", Toast.LENGTH_LONG).show()

                }
            }
        }
        setContentView(R.layout.activity_interstitial_ads)
        mLoadAdButton = findViewById<Button>(R.id.button_load_ad)
        mShowAdButton = findViewById<Button>(R.id.button_show_ad)
        mShowAdButton!!.setOnClickListener{
            mInterstitialAd!!.show();
        }
        setupInterstitial();
    }


    override fun onResume() {
        super.onResume()
        adjustButtonVisibility()
    }

    private fun adjustButtonVisibility() {
        mLoadAdButton!!.visibility = View.VISIBLE
        mShowAdButton!!.visibility = View.GONE
    }

    private fun setupInterstitial() {
        mInterstitialAd = InMobiInterstitial(this, 1601880166897, object : InterstitialAdEventListener() {

            override fun onAdLoadSucceeded(inMobiInterstitial: InMobiInterstitial,
                                           adMetaInfo: AdMetaInfo) {
                Log.d(TAG, "onAdLoadSuccessful with bid " + adMetaInfo.bid)
                if (inMobiInterstitial.isReady) {
                    if (mShowAdButton != null) {
                        mShowAdButton!!.visibility = View.VISIBLE
                    }
                } else {
                    Log.d(TAG, "onAdLoadSuccessful inMobiInterstitial not ready")
                }
            }

            override fun onAdLoadFailed(inMobiInterstitial: InMobiInterstitial, inMobiAdRequestStatus: InMobiAdRequestStatus) {
                Log.d(TAG, "Unable to load interstitial ad (error message: " +
                        inMobiAdRequestStatus.message)
            }

            override fun onAdFetchSuccessful(inMobiInterstitial: InMobiInterstitial, adMetaInfo: AdMetaInfo) {
                Log.d(TAG, "onAdFetchSuccessful with bid " + adMetaInfo.bid)
            }

            override fun onAdClicked(inMobiInterstitial: InMobiInterstitial, map: Map<Any, Any>) {
                Log.d(TAG, "onAdClicked " + map.size)
            }

            override fun onAdWillDisplay(inMobiInterstitial: InMobiInterstitial) {
                Log.d(TAG, "onAdWillDisplay")
            }

            override fun onAdDisplayed(inMobiInterstitial: InMobiInterstitial,
                                       adMetaInfo: AdMetaInfo) {
                Log.d(TAG, "onAdDisplayed")
            }

            override fun onAdDisplayFailed(inMobiInterstitial: InMobiInterstitial) {
                Log.d(TAG, "onAdDisplayFailed")
            }

            override fun onAdDismissed(inMobiInterstitial: InMobiInterstitial) {
                Log.d(TAG, "onAdDismissed")
            }

            override fun onUserLeftApplication(inMobiInterstitial: InMobiInterstitial) {
                Log.d(TAG, "onUserWillLeaveApplication")
            }

            override fun onRewardsUnlocked(inMobiInterstitial: InMobiInterstitial,
                                           map: Map<Any, Any>) {
                Log.d(TAG, "onRewardsUnlocked $map")
            }
        })
        mInterstitialAd!!.show()
    }
}