package com.hangoverstudios.men.photo.suite.editor.app.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.hangoverstudios.men.photo.suite.editor.app.activities.LoadingAd;
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;
import com.hangoverstudios.men.photo.suite.editor.app.model.InterstitialAdModel;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.SplashScreen.splashInstance;

public class MyInterstitialAdView {
    public static final MyInterstitialAdView myInterstitialAdView = new MyInterstitialAdView();

    public static MyInterstitialAdView getInstance() {
        return myInterstitialAdView;
    }

    public void onLaunchAD(Context context) {
        if (RemoteConfigValues.getOurRemote().getShowInterstitialOnLaunch() != null
                && RemoteConfigValues.getOurRemote().getShowInterstitialOnLaunch().equals("true")) {
            showGoogleAd(context, true);
        }
    }

    public void onExitAD(Context context) {
        if (RemoteConfigValues.getOurRemote().getShowInterstitialOnExit() != null
                && RemoteConfigValues.getOurRemote().getShowInterstitialOnExit().equals("true")) {
            showGoogleAd(context, false);
        }
    }

    public void activitiesAD(Context context) {
        if (RemoteConfigValues.getOurRemote().getShowInterstitialAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowInterstitialAd().equals("true")) {
                showGoogleAd(context, false);
            }
        }
    }

    private void showGoogleAd(Context context, boolean isMainActivity) {
        context.startActivity(new Intent(context, LoadingAd.class));
    }

    private boolean isInterstitialFB() {
        boolean interstitialFB = false;
        return interstitialFB;
    }
}
