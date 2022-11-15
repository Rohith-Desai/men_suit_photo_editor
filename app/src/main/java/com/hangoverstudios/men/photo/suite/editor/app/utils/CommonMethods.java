package com.hangoverstudios.men.photo.suite.editor.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;
import com.hangoverstudios.men.photo.suite.editor.app.interfaces.CallimageSelection;
import com.hangoverstudios.men.photo.suite.editor.app.interfaces.NativeaddMainListener;
import com.hangoverstudios.men.photo.suite.editor.app.interfaces.Photocollageopen;
//import com.hangoverstudios.men.photo.suite.editor.app.bgremoverlite.MainActivity;

import java.util.LinkedList;
import java.util.Queue;

public class CommonMethods {

    public static final CommonMethods commonMethods = new CommonMethods();

    private final static String RATING_PREFERENCES = "mensuitapprating";
    private Queue<String> bitmapEventQueue = new LinkedList<>();

    public int getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(int purchaseState) {
        this.purchaseState = purchaseState;
    }

    private int purchaseState = -1;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    private String sku = "";

    public int getOPEN_APP_FREQUENCY() {
        return OPEN_APP_FREQUENCY;
    }

    public void setOPEN_APP_FREQUENCY(int OPEN_APP_FREQUENCY) {
        this.OPEN_APP_FREQUENCY = OPEN_APP_FREQUENCY;
    }

    public Bitmap getBgRemovedBitmap() {
        return bgRemovedBitmap;
    }

    public void setBgRemovedBitmap(Bitmap bgRemovedBitmap) {
        this.bgRemovedBitmap = bgRemovedBitmap;
    }

    private Bitmap bgRemovedBitmap;
    private String fromDemo;

   /* public MainActivity getMainActivityObject() {
        return mainActivityObject;
    }*/

  /*  public void setMainActivityObject(MainActivity mainActivityObject) {
        this.mainActivityObject = mainActivityObject;
    }*/

   // private MainActivity mainActivityObject ;

    public Bitmap getOriginalSelectedBitmap() {
        return originalSelectedBitmap;
    }

    public void setOriginalSelectedBitmap(Bitmap originalSelectedBitmap) {
        this.originalSelectedBitmap = originalSelectedBitmap;
    }

    private Bitmap originalSelectedBitmap;

    private int OPEN_APP_FREQUENCY = 0;

    public void addToBitmapEventQueue(String record) {
        bitmapEventQueue.add(record);
    }

    public Queue<String> getBitmapEventQueue() {
        return bitmapEventQueue;
    }

    public void clearBitmapEventQueue() {
        if (bitmapEventQueue != null) {
            bitmapEventQueue.clear();
        }
    }
    public boolean ratingIsDone(Context context) {

        SharedPreferences sharedpreferences = context.getSharedPreferences(RATING_PREFERENCES, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedpreferences.edit();
        return sharedpreferences.getBoolean("rating", false);
    }

    public void ratingDone(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(RATING_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("rating", true);
        editor.apply();
    }
    public static CommonMethods getInstance() {
        return commonMethods;
    }

   /* public void onLaunchAD(Context context) {
        if (CommonMethods.getInstance().getSku().equals("") && CommonMethods.getInstance().getPurchaseState() == -1 || getSku().equals("removeads") && getPurchaseState() != 1 || getSku().equals("") && getPurchaseState() == -1) {
            if (RemoteConfigValues.getOurRemote().getShowInterstitialOnLaunch() != null
                    && RemoteConfigValues.getOurRemote().getShowInterstitialOnLaunch().equals("true")) {
                showGoogleAd(context);
            }
       }
    }

    public void onExitAD(Context context) {
        if (CommonMethods.getInstance().getSku().equals("") && CommonMethods.getInstance().getPurchaseState() == -1 || getSku().equals("removeads") && getPurchaseState() != 1) {
            if (RemoteConfigValues.getOurRemote().getShowInterstitialOnExit() != null
                    && RemoteConfigValues.getOurRemote().getShowInterstitialOnExit().equals("true")) {
                showGoogleAd(context);
           }
        }
    }*/

    public void loadBannerAd(AdView adViewBanner, FrameLayout adViewContainer, Context context) {
        if (CommonMethods.getInstance().getSku().equals("") && CommonMethods.getInstance().getPurchaseState() == -1 || getSku().equals("removeads") && getPurchaseState() != 1) {
            if (RemoteConfigValues.getOurRemote().getRemoveAds() != null)
                if (!RemoteConfigValues.getOurRemote().getRemoveAds().equals("true")) {

                    adViewBanner = new AdView(context);
                    adViewBanner.setAdUnitId(context.getString(R.string.admob_banner_id));
                    adViewContainer.removeAllViews();
                    adViewContainer.addView(adViewBanner);

                    AdSize adSize = getAdSize(context, adViewContainer);
                    adViewBanner.setAdSize(adSize);

                    AdRequest adRequest =
                            new AdRequest.Builder().build();
                    adViewBanner.loadAd(adRequest);
                }
        }

    }

   /* public void activitiesAD(Context context) {
       if (CommonMethods.getInstance().getSku().equals("") && CommonMethods.getInstance().getPurchaseState() == -1 || getSku().equals("removeads") && getPurchaseState() != 1) {
            if (RemoteConfigValues.getOurRemote().getShowInterstitialAd() != null) {
                if (RemoteConfigValues.getOurRemote().getShowInterstitialAd().equals("true")) {
                    if (RemoteConfigValues.getOurRemote().getAdRotationPolicy().equals("true")) {
                        if (isInterstitialFB()) {
                            *//*if (splashInstance.interstitialFBAd != null && splashInstance.interstitialFBAd.isAdLoaded()) {
                               // splashInstance.interstitialFBAd.show();
                            } else*//* if (splashInstance.interstitialGoogleAd != null) {
                                splashInstance.interstitialGoogleAd.show((Activity) context);
                            } else {
                                splashInstance.loadMyInterstitial();
                            }
                        } else {
                            if (splashInstance.interstitialGoogleAd != null) {
                                splashInstance.interstitialGoogleAd.show((Activity) context);
                            } *//*else if (splashInstance.interstitialFBAd != null && splashInstance.interstitialFBAd.isAdLoaded()) {
                                splashInstance.interstitialFBAd.show();
                                splashInstance.loadMyInterstitial();
                            }*//* else {
                                splashInstance.loadMyInterstitial();
                            }
                        }
                    } else {
                        if (RemoteConfigValues.getOurRemote().getInterstitialOnlyGoogle().equals("true")) {
                            showGoogleAd(context);
                        }
                    }
                }
            }
        }
    }

    private void showGoogleAd(Context context) {
        if (splashInstance.interstitialGoogleAd != null) {
            splashInstance.interstitialGoogleAd.show((Activity) context);
        } else {
            splashInstance.loadMyInterstitial();
        }
    }

    private boolean isInterstitialFB() {
        boolean interstitialFB = false;
        return interstitialFB;
    }*/

    public static AdSize getAdSize(Context context, View adContainerView) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }
        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    public void disableAds() {
//        System.out.println("sku : "+CommonMethods.getInstance().getSku()+
//                           "purchase state : "+CommonMethods.getInstance().getPurchaseState());

            RemoteConfigValues.getOurRemote().setRemoveAds("true");
            RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunch("false");
            RemoteConfigValues.getOurRemote().setShowInterstitialOnExit("false");
            RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunchFBAd("false");
            RemoteConfigValues.getOurRemote().setShowInterstitialAd("false");
            RemoteConfigValues.getOurRemote().setInterstitialOnlyFB("false");
            RemoteConfigValues.getOurRemote().setAdRotationPolicy("false");
            RemoteConfigValues.getOurRemote().setShowNativeAd("false");
            RemoteConfigValues.getOurRemote().setShowNativeAdOnMainScreen("false");
            RemoteConfigValues.getOurRemote().setShowNativeAdOnDialog("false");
            RemoteConfigValues.getOurRemote().setAdRotationPolicyNative("false");
            RemoteConfigValues.getOurRemote().setInterstitialOnlyGoogle("false");
            RemoteConfigValues.getOurRemote().setNativeOnlyFB("false");
            RemoteConfigValues.getOurRemote().setNativeOnlyGoogle("false");
            RemoteConfigValues.getOurRemote().setShowRewardVideoAd("false");
            RemoteConfigValues.getOurRemote().setShowOurAppInterstitials("false");
            RemoteConfigValues.getOurRemote().setOurApps("false");
            RemoteConfigValues.getOurRemote().setOurAppsOnMainScreen("false");
    }

    private CallimageSelection callimageSelection;

    public CallimageSelection getCallimageSelection() {
        return callimageSelection;
    }

    public void setCallimageSelection(CallimageSelection callimageSelection) {
        this.callimageSelection = callimageSelection;
    }

    private NativeaddMainListener  nativeaddMainListener;

    public NativeaddMainListener getNativeaddMainListener() {
        return nativeaddMainListener;
    }

    public void setNativeaddMainListener(NativeaddMainListener nativeaddMainListener) {
        this.nativeaddMainListener = nativeaddMainListener;
    }
    private Photocollageopen photocollageopen;

    public Photocollageopen getPhotocollageopen() {
        return photocollageopen;
    }

    public void setPhotocollageopen(Photocollageopen photocollageopen) {
        this.photocollageopen = photocollageopen;
    }

    public String getFromDemo() {
        return fromDemo;
    }

    public void setFromDemo(String fromDemo) {
        this.fromDemo = fromDemo;
    }

    private Bitmap motionbitmap;

    public Bitmap getMotionbitmap() {
        return motionbitmap;
    }

    public void setMotionbitmap(Bitmap motionbitmap) {
        this.motionbitmap = motionbitmap;
    }
}











