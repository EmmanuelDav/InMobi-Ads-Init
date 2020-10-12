package com.example.inmobiadstest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.listeners.BannerAdEventListener;
import com.inmobi.sdk.InMobiSdk;
import com.inmobi.sdk.SdkInitializationListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private InMobiBanner mBannerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSONObject consentObject = new JSONObject();
        try {
            // Provide correct consent value to sdk which is obtained by User
            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
            // Provide 0 if GDPR is not applicable and 1 if applicable
            consentObject.put("gdpr", "0");
            // Provide user consent in IAB format
            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_IAB, "<< consent in IAB format >> ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InMobiSdk.init(this, "5e976de4730046a48ccd397ed5ad298f", consentObject, new SdkInitializationListener() {
            @Override
            public void onInitializationComplete(@Nullable Error error) {
                if (null != error) {
                    Log.e("admob_debugger", "InMobi Init failed -" + error.getMessage());
                } else {
                    Log.d("admob_debugger", "InMobi Init Successful");
                }
            }
        });
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        setContentView(R.layout.activity_main);
        InMobiBanner bannerAd = new InMobiBanner(this, 1601160124922l);
        RelativeLayout adContainer = findViewById(R.id.ads_container);
        float density = getResources().getDisplayMetrics().density;
        RelativeLayout.LayoutParams bannerLp = new RelativeLayout.LayoutParams((int) (320 * density), (int) (50 * density));
        adContainer.addView(bannerAd, bannerLp);
        bannerAd.load();
        findViewById(R.id.Button).setOnClickListener(view ->{
            startActivity(new Intent(this, InterstitialAds.class));
        });
    }

}