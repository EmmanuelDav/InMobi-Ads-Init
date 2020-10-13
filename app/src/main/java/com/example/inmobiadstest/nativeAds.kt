package com.example.inmobiadstest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inmobi.ads.AdMetaInfo
import com.inmobi.ads.InMobiAdRequestStatus
import com.inmobi.ads.InMobiInterstitial
import com.inmobi.ads.InMobiNative
import com.inmobi.ads.listeners.NativeAdEventListener
import com.inmobi.sdk.InMobiSdk
import org.json.JSONException
import org.json.JSONObject

open class nativeAds : AppCompatActivity() {

    companion object {
        val TAG: String = javaClass.name
    }

    private val mInMobiNative: ArrayList<InMobiNative> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val consent = JSONObject()
        try {
            consent.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG)
        InMobiSdk.init(this, "5e976de4730046a48ccd397ed5ad298f", consent) { error ->
            if (error == null) {

            } else {
                Log.e(InterstitialAds.TAG, "InMobi SDK Initialization failed: " + error.message)
            }
        }
        setContentView(R.layout.activity_native_ads)
        val nativeAd = InMobiNative(this, 1600869074061, object : NativeAdEventListener() {
            override fun onAdLoadSucceeded(p0: InMobiNative, p1: AdMetaInfo) {

            }

            override fun onAdLoadFailed(p0: InMobiNative, p1: InMobiAdRequestStatus) {
                Log.d(TAG, "Ads Failed Because " + p1.message)
            }
        })
        nativeAd.load()
        mInMobiNative.add(nativeAd)
    }
}