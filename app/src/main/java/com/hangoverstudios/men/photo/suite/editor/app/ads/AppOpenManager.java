package com.hangoverstudios.men.photo.suite.editor.app.ads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.hangoverstudios.men.photo.suite.editor.app.R;

import java.util.Date;

public class AppOpenManager implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private static final String LOG_TAG = "AppOpenManager";
    /* access modifiers changed from: private */
    public static boolean isShowingAd = false;
    public static boolean isGalleryAd = false;
    /* access modifiers changed from: private */
    public AppOpenAd appOpenAd = null;
    private Activity currentActivity;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    /* access modifiers changed from: private */
    public long loadTime = 0;
    private final MyAppOpen myApplication;
    private AdRequest request;
    private SharedPreferences sharedPreferences;

    public AppOpenManager(MyAppOpen myApplication2) {
        this.myApplication = myApplication2;
        myApplication2.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    public void fetchAd() {
        if (!isAdAvailable()) {
            this.loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {
                    System.out.println(loadAdError.getCode());
                }

                public void onAppOpenAdLoaded(AppOpenAd appOpenAd) {
                    AppOpenAd unused = AppOpenManager.this.appOpenAd = appOpenAd;
                    long unused2 = AppOpenManager.this.loadTime = new Date().getTime();
                }
            };
            AdRequest adRequest = getAdRequest();
            this.request = adRequest;
            AppOpenAd.load((Context) this.myApplication, currentActivity.getString(R.string.admob_app_open), adRequest, 1, this.loadCallback);
        }
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public boolean isAdAvailable() {
        return this.appOpenAd != null;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        this.currentActivity = activity;
    }

    public void onActivityStarted(Activity activity) {
        this.currentActivity = activity;
    }

    public void onActivityResumed(Activity activity) {
        this.currentActivity = activity;
    }

    public void onActivityPaused(Activity activity) {
        this.currentActivity = activity;
    }

    public void onActivityStopped(Activity activity) {
        this.currentActivity = activity;
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.currentActivity = activity;
    }

    public void onActivityDestroyed(Activity activity) {
        this.currentActivity = null;
    }

    public void showAdIfAvailable() {
        if (isShowingAd || !isAdAvailable()) {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
            return;
        }
        // if(isGalleryAd){
        /*if (RemoteConfigValues.getOurRemote().getOpenAppAdFrequency() != null) {
            try {
                Log.v("Test","OPEN_APP_FREQUENCY : "+CommonMethods.getInstance().getOPEN_APP_FREQUENCY());
                Log.v("Test","OPEN_APP_FREQUENCY fetched : "+Integer.parseInt(RemoteConfigValues.getOurRemote().getOpenAppAdFrequency()));
                if (CommonMethods.getInstance().getOPEN_APP_FREQUENCY() >= Integer.parseInt(RemoteConfigValues.getOurRemote().getOpenAppAdFrequency())) {
                    Log.d(LOG_TAG, "Will show ad.");
                    CommonMethods.getInstance().setOPEN_APP_FREQUENCY(0);
                }
                else{
                    Log.v("Test","OPEN_APP_FREQUENCY : AppOpenAd skipped..");
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }*/
        /*} else {
            isGalleryAd = false;
        }*/

        FullScreenContentCallback fullScreenContentCallback =
                new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Set the reference to null so isAdAvailable() returns false.
                        AppOpenManager.this.appOpenAd = null;
                        isShowingAd = false;
                        fetchAd();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        isShowingAd = true;
                    }
                };

        appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
        appOpenAd.show(currentActivity);

    }


    private boolean wasLoadTimeLessThanNHoursAgo(long j) {
        return new Date().getTime() - this.loadTime < j * 3600000;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        showAdIfAvailable();
        Log.d(LOG_TAG, "onStart");

    }
}
