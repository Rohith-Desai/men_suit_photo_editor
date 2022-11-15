package com.hangoverstudios.men.photo.suite.editor.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.billingclient.api.BillingClient;
//import com.android.billingclient.api.BillingClientStateListener;
//import com.android.billingclient.api.BillingResult;
//import com.android.billingclient.api.Purchase;
//import com.android.billingclient.api.PurchasesUpdatedListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.hangoverstudios.men.photo.suite.editor.app.MyBounceInterpolator;
import com.hangoverstudios.men.photo.suite.editor.app.ads.MyAppOpen;
import com.hangoverstudios.men.photo.suite.editor.app.model.InterstitialAdModel;
import com.hangoverstudios.men.photo.suite.editor.app.model.OurAdsData;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.receiver.ConnectivityReceiver;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.hangoverstudios.men.photo.suite.editor.app.billing.BillingController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

//import static com.android.billingclient.api.BillingClient.SkuType.INAPP;
import static com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods.commonMethods;

public class SplashScreen extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener/*, PurchasesUpdatedListener*/ {
    public FirebaseRemoteConfig remoteConfig;
    public static SplashScreen splashInstance;
    boolean adLoaded, timeOut = false;
    public static FirebaseAnalytics mFirebaseAnalytics;
    GifImageView loading;
    TextView startImg;
    public static final String PREF_FILE = "MEN_SUIT";
    public static final String PURCHASE_KEY = "removeads";


    private BroadcastReceiver br = new ConnectivityReceiver();
    private boolean receiverRegistered = false;
    // public BillingClient billingClient;

    List<String> allNames = new ArrayList<String>();
    BillingController bill;
    JSONObject ourAds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_dummy);
        splashInstance = this;
        FirebaseApp.initializeApp(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // loading = findViewById(R.id.splash_loading);
        // startImg = findViewById(R.id.start_img);

        CommonMethods.getInstance().setSku(getSharedPreferences("PURCHASE_PREF", MODE_PRIVATE).getString("_sku_", ""));
        CommonMethods.getInstance().setPurchaseState(getSharedPreferences("PURCHASE_PREF", MODE_PRIVATE).getInt("_purchase_state_", -1));


        if (checkInternetConnection()) {

            setProgressBar();

        }
        bill = new BillingController(this);
        bill.billingInitialization();
        if (bill.getPurchaseValueFromPref()) {
            commonMethods.disableAds();
//            remove_ads.setVisibility(View.GONE);
        }

       /* startImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isHelp = getSharedPreferences("MenSuit", MODE_PRIVATE).getBoolean("helpScreen", true);
                if (isHelp) {
                    startActivity(new Intent(SplashScreen.this, Help.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                finish();
            }
        });*/
//        String locale = getResources().getConfiguration().locale.getCountry();

    }

    private void setProgressBar() {
        long totalSeconds = 11;
        long intervalSeconds = 1;
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setMax(100);
        CountDownTimer timer = new CountDownTimer(totalSeconds * 1100, intervalSeconds * 110) {

            public void onTick(long millisUntilFinished) {
                Log.d("seconds elapsed: ", String.valueOf((totalSeconds * 1100 - millisUntilFinished) / 110));
                progressBar.setProgress((int) (totalSeconds * 1100 - millisUntilFinished) / 110);
            }

            public void onFinish() {
                TextView start_but = findViewById(R.id.start_but);
//                load.setVisibility(View.GONE);
                progressBar.setVisibility(View.INVISIBLE);
                 
                start_but.setVisibility(View.VISIBLE);
                final Animation myAnim = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bounce);

                // Use bounce interpolator with amplitude 0.2 and frequency 20
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);

                start_but.startAnimation(myAnim);

                start_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                Log.d("done!", "Time's up!");
            }

        };
        timer.start();
    }

    private boolean checkInternetConnection() {
        boolean isconnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (connectivityManager.getActiveNetworkInfo() != null) {
                isconnected = connectivityManager.getActiveNetworkInfo().isConnected();
            }
            return isconnected;
        } else {
            return isconnected;
//        isconnected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        }
    }

    private void assignDefaultValues() {
        RemoteConfigValues.getOurRemote().setServer("false");
        RemoteConfigValues.getOurRemote().setUpgradeAppVersion("1");
        RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunch("true");
        RemoteConfigValues.getOurRemote().setShowInterstitialOnExit("false");
        RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunchFBAd("false");
        RemoteConfigValues.getOurRemote().setShowInterstitialAd("true");
        RemoteConfigValues.getOurRemote().setInterstitialOnlyGoogle("true");
        RemoteConfigValues.getOurRemote().setInterstitialOnlyFB("false");
        RemoteConfigValues.getOurRemote().setAdRotationPolicy("false");
        RemoteConfigValues.getOurRemote().setAdRotationPolicyNative("false");
        RemoteConfigValues.getOurRemote().setShowNativeAd("true");
        RemoteConfigValues.getOurRemote().setShowNativeAdOnMainScreen("true");
        RemoteConfigValues.getOurRemote().setNativeOnlyGoogle("true");
        RemoteConfigValues.getOurRemote().setNativeOnlyFB("false");
        RemoteConfigValues.getOurRemote().setShowRewardVideoAd("true");
        RemoteConfigValues.getOurRemote().setShowNativeAdOnDialog("true");
        RemoteConfigValues.getOurRemote().setImaUrl("");
        RemoteConfigValues.getOurRemote().setShowOurAppInterstitials("false");
        RemoteConfigValues.getOurRemote().setOurApps("false");
        RemoteConfigValues.getOurRemote().setOurAppsOnMainScreen("false");
        RemoteConfigValues.getOurRemote().setEnableWatermark("false");
        RemoteConfigValues.getOurRemote().setEnableIAPflag("false");
        RemoteConfigValues.getOurRemote().setRemoveAds("false");
        RemoteConfigValues.getOurRemote().setIAP_NOADS("false");
        RemoteConfigValues.getOurRemote().setOpenAppAdFrequency("0");
        RemoteConfigValues.getOurRemote().setUpdateFromPlaystore("false");
        RemoteConfigValues.getOurRemote().setShowRatingLayout("false");

        try {
            JSONObject ourAds = new JSONObject("{\n" +
                    "            \"ourApp1Link\": \"https://play.google.com/store/apps/details?id=com.hangoverstudios.romantic.photo.frames.love.photo.editor\",\n" +
                    "            \"ourApp1icon\": \"https://raw.githubusercontent.com/Samarasa/appicons/master/images/gadi/newicons/romantic.png\",\n" +
                    "            \"ourApp2Link\": \"https://play.google.com/store/apps/details?id=com.hangoverstudios.photo.suit.editor\",\n" +
                    "            \"ourApp2icon\": \"https://raw.githubusercontent.com/Samarasa/appicons/master/images/gadi/newicons/photosuit.png\",\n" +
                    "            \"ourApp3Link\": \"https://play.google.com/store/apps/details?id=com.hangoverstudios.books.photo.frame.collage\",\n" +
                    "            \"ourApp3icon\": \"https://raw.githubusercontent.com/Samarasa/appicons/master/images/gadi/newicons/photobook.png\",\n" +
                    "            \"ourApp4Link\": \"https://play.google.com/store/apps/details?id=com.hangoverstudios.men.photo.suite.editor.app\",\n" +
                    "            \"ourApp4icon\": \"https://raw.githubusercontent.com/Samarasa/appicons/master/images/gadi/newicons/mensuit.png\",\n" +
                    "            \"ourApp5Link\": \"https://play.google.com/store/apps/details?id=com.hangoverstudios.photo.photocollage.photoeditor.collagemaker\",\n" +
                    "            \"ourApp5icon\": \"https://raw.githubusercontent.com/Samarasa/appicons/master/images/gadi/newicons/college.png\",\n" +
                    "            \"ourApp6Link\": \"https://play.google.com/store/apps/details?id=com.hangoverstudios.photoeditor.neonphotoeditor\",\n" +
                    "            \"ourApp6icon\": \"https://raw.githubusercontent.com/Samarasa/appicons/master/images/gadi/newicons/neon.png\",\n" +
                    "            \"ourApp7Link\": \"https://play.google.com/store/apps/details?id=com.hangoverstudios.reverse.video.effect\",\n" +
                    "            \"ourApp7icon\": \"https://raw.githubusercontent.com/Samarasa/appicons/master/images/gadi/newicons/reverse.png\",\n" +
                    "            \"ourApp8Link\": \"https://play.google.com/store/apps/details?id=com.hangoverstudios.statusdp.whatsappstatussaver.saveit\",\n" +
                    "            \"ourApp8icon\": \"https://raw.githubusercontent.com/Samarasa/appicons/master/images/gadi/newicons/saver.png\",\n" +
                    "            \"ourApp9Link\": \"https://play.google.com/store/apps/details?id=com.hangoverstudios.daily.expensemanager\",\n" +
                    "            \"ourApp9icon\": \"https://raw.githubusercontent.com/Samarasa/appicons/master/images/gadi/newicons/expense.png\"\n" +
                    "            }");
            assignValuesRemote(ourAds, "local");
        } catch (JSONException e) {
            e.printStackTrace();
        }

       /* OurAdsData.getData().setOurApp1icon("https://raw.githubusercontent.com/Samarasa/appicons/master/images/suntuz/statussaver.png");
        OurAdsData.getData().setOurApp2icon("https://raw.githubusercontent.com/Samarasa/appicons/master/images/suntuz/xoffroad.png");
        OurAdsData.getData().setOurApp3icon("https://raw.githubusercontent.com/Samarasa/appicons/master/images/suntuz/findimagedifferences.png");
        OurAdsData.getData().setOurApp4icon("https://raw.githubusercontent.com/Samarasa/appicons/master/images/suntuz/stacktwist.png");
        OurAdsData.getData().setOurApp5icon("https://raw.githubusercontent.com/Samarasa/appicons/master/images/suntuz/statussaver.png");
        OurAdsData.getData().setOurApp6icon("https://raw.githubusercontent.com/Samarasa/appicons/master/images/suntuz/funlearn.png");
        OurAdsData.getData().setOurApp7icon("https://raw.githubusercontent.com/Samarasa/appicons/master/images/suntuz/vehicleinfo.png");
        OurAdsData.getData().setOurApp8icon("https://raw.githubusercontent.com/Samarasa/appicons/master/images/suntuz/statussaver.png");
        OurAdsData.getData().setOurApp9icon("https://raw.githubusercontent.com/Samarasa/appicons/master/images/suntuz/stacktwist.png");

        OurAdsData.getData().setOurApp1Link("https://play.google.com/store/apps/details?id=com.hangoverstudios.statusdp.whatsappstatussaver.saveit");
        OurAdsData.getData().setOurApp2Link("https://play.google.com/store/apps/details?id=com.hangoverstudios.extreme.games.dangerousroaddriving.monster.truck4x4.offroad.stunt.jeep");
        OurAdsData.getData().setOurApp3Link("https://play.google.com/store/apps/details?id=com.hangoverstudios.finddifference.findimagedifferences.differencegame");
        OurAdsData.getData().setOurApp4Link("https://play.google.com/store/apps/details?id=com.manogna.helixjumpTwist.stack.breaker.twiststack.stackball3d");
        OurAdsData.getData().setOurApp5Link("https://play.google.com/store/apps/details?id=com.hangoverstudios.statusdp.whatsappstatussaver.saveit");
        OurAdsData.getData().setOurApp6Link("https://play.google.com/store/apps/developer?id=Hangover+Studios");
        OurAdsData.getData().setOurApp7Link("https://play.google.com/store/apps/developer?id=Deeku+Apps");
        OurAdsData.getData().setOurApp8Link("https://play.google.com/store/apps/details?id=com.hangoverstudios.statusdp.whatsappstatussaver.saveit");
        OurAdsData.getData().setOurApp9Link("https://play.google.com/store/apps/details?id=com.manogna.helixjumpTwist.stack.breaker.twiststack.stackball3d");*/
        loadRemoteConfig();
    }

    private void assignValuesRemote(JSONObject ourApps, String remote) {
        try {
            OurAdsData.getData().setOurApp1icon(ourApps.getString("ourApp1icon"));
            OurAdsData.getData().setOurApp2icon(ourApps.getString("ourApp2icon"));
            OurAdsData.getData().setOurApp3icon(ourApps.getString("ourApp3icon"));
            OurAdsData.getData().setOurApp4icon(ourApps.getString("ourApp4icon"));
            OurAdsData.getData().setOurApp5icon(ourApps.getString("ourApp5icon"));
            OurAdsData.getData().setOurApp6icon(ourApps.getString("ourApp6icon"));
            OurAdsData.getData().setOurApp7icon(ourApps.getString("ourApp7icon"));
            OurAdsData.getData().setOurApp8icon(ourApps.getString("ourApp8icon"));
            OurAdsData.getData().setOurApp9icon(ourApps.getString("ourApp9icon"));

            OurAdsData.getData().setOurApp1Link(ourApps.getString("ourApp1Link"));
            OurAdsData.getData().setOurApp2Link(ourApps.getString("ourApp2Link"));
            OurAdsData.getData().setOurApp3Link(ourApps.getString("ourApp3Link"));
            OurAdsData.getData().setOurApp4Link(ourApps.getString("ourApp4Link"));
            OurAdsData.getData().setOurApp5Link(ourApps.getString("ourApp5Link"));
            OurAdsData.getData().setOurApp6Link(ourApps.getString("ourApp6Link"));
            OurAdsData.getData().setOurApp7Link(ourApps.getString("ourApp7Link"));
            OurAdsData.getData().setOurApp8Link(ourApps.getString("ourApp8Link"));
            OurAdsData.getData().setOurApp9Link(ourApps.getString("ourApp9Link"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadRemoteConfig() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                timeOut = true;
            }
        }, 10000);
        remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .build();
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings);
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        long cacheExpiration = 43200;
        remoteConfig.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    remoteConfig.activateFetched();
                    String ourAdsStr = remoteConfig.getString("OurApps");
                    try {
                        ourAds = new JSONObject(ourAdsStr);
                        assignValuesRemote(ourAds, "remote");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunch("true");
//                    RemoteConfigValues.getOurRemote().setShowInterstitialOnExit("true");
//                    RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunchFBAd("true");
//                    RemoteConfigValues.getOurRemote().setShowInterstitialAd("true");
//                    RemoteConfigValues.getOurRemote().setInterstitialOnlyGoogle("true");
//                    RemoteConfigValues.getOurRemote().setInterstitialOnlyFB("false");
//                    RemoteConfigValues.getOurRemote().setAdRotationPolicy("false");
//                    RemoteConfigValues.getOurRemote().setAdRotationPolicyNative("false");
//                    RemoteConfigValues.getOurRemote().setShowNativeAd("true");
//                    RemoteConfigValues.getOurRemote().setShowNativeAdOnMainScreen("true");

                    RemoteConfigValues.getOurRemote().setServer(remoteConfig.getString("server"));
                    RemoteConfigValues.getOurRemote().setUpgradeAppVersion(remoteConfig.getString("upgradeAppVersion"));

                    RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunch(remoteConfig.getString("showInterstitialOnLaunch"));
                    RemoteConfigValues.getOurRemote().setShowInterstitialOnExit(remoteConfig.getString("showInterstitialOnExit"));
                    RemoteConfigValues.getOurRemote().setShowInterstitialOnLaunchFBAd(remoteConfig.getString("showInterstitialOnLaunchFBAd"));
                    RemoteConfigValues.getOurRemote().setShowInterstitialAd(remoteConfig.getString("showInterstitialAd"));
                    RemoteConfigValues.getOurRemote().setInterstitialOnlyGoogle(remoteConfig.getString("interstitialOnlyGoogle"));
                    RemoteConfigValues.getOurRemote().setInterstitialOnlyFB(remoteConfig.getString("interstitialOnlyFB"));
                    RemoteConfigValues.getOurRemote().setAdRotationPolicy(remoteConfig.getString("adRotationPolicy"));
                    RemoteConfigValues.getOurRemote().setAdRotationPolicyNative(remoteConfig.getString("adRotationPolicyNative"));
                    RemoteConfigValues.getOurRemote().setShowNativeAd(remoteConfig.getString("showNativeAd"));

                    RemoteConfigValues.getOurRemote().setShowNativeAdOnMainScreen(remoteConfig.getString("showNativeAdOnMainScreen"));
                    RemoteConfigValues.getOurRemote().setNativeOnlyGoogle(remoteConfig.getString("nativeOnlyGoogle"));
                    RemoteConfigValues.getOurRemote().setNativeOnlyFB(remoteConfig.getString("nativeOnlyFB"));
                    RemoteConfigValues.getOurRemote().setShowRewardVideoAd(remoteConfig.getString("showRewardVideoAd"));
                    RemoteConfigValues.getOurRemote().setShowNativeAdOnDialog(remoteConfig.getString("showNativeAdOnDialog"));
                    RemoteConfigValues.getOurRemote().setImaUrl(remoteConfig.getString("imageUrl"));
                    RemoteConfigValues.getOurRemote().setShowOurAppInterstitials(remoteConfig.getString("showOurAppInterstitials"));
                    RemoteConfigValues.getOurRemote().setOurApps(remoteConfig.getString("ourApps"));
                    //RemoteConfigValues.getOurRemote().setOurApps("true");
                    RemoteConfigValues.getOurRemote().setOurAppsOnMainScreen(remoteConfig.getString("OurAppsOnMainScreen"));
                    //RemoteConfigValues.getOurRemote().setOurAppsOnMainScreen("true");

                    RemoteConfigValues.getOurRemote().setEnableWatermark(remoteConfig.getString("EnableWatermark"));
                    RemoteConfigValues.getOurRemote().setEnableIAPflag(remoteConfig.getString("enableIAPflag"));
                    //RemoteConfigValues.getOurRemote().setEnableIAPflag(remoteConfig.getString("true"));
                    RemoteConfigValues.getOurRemote().setRemoveAds(remoteConfig.getString("removeads"));
                    RemoteConfigValues.getOurRemote().setIAP_NOADS(remoteConfig.getString("IAP_NOADS"));
                    RemoteConfigValues.getOurRemote().setOpenAppAdFrequency(remoteConfig.getString("openAppAdFrequency"));
                    RemoteConfigValues.getOurRemote().setUpdateFromPlaystore(remoteConfig.getString("updateFromPlaystore"));
                    RemoteConfigValues.getOurRemote().setShowRatingLayout(remoteConfig.getString("ShowRatingLayout"));

                    //  Log.d("updateFromPlaystore",remoteConfig.getString("updateFromPlaystore"));


//                    Log.v("SPLASH_TAG", "remote config values : "
//                            + "\n1. server : " + RemoteConfigValues.getOurRemote().getServer()
//                            + "\n2. showInterstitialOnLaunch : " + RemoteConfigValues.getOurRemote().getShowInterstitialOnLaunch()
//                            + "\n3. showInterstitialOnExit : " + RemoteConfigValues.getOurRemote().getShowInterstitialOnExit()
//                            + "\n4. showInterstitialOnLaunchFBAd : " + "not implemented.."
//                            + "\n5. showInterstitialAd : " + RemoteConfigValues.getOurRemote().getShowInterstitialAd()
//                            + "\n6. interstitialOnlyGoogle : " + RemoteConfigValues.getOurRemote().getInterstitialOnlyGoogle()
//                            + "\n7. interstitialOnlyFB : " + RemoteConfigValues.getOurRemote().getInterstitialOnlyFB()
//                            + "\n8. adRotationPolicy : " + RemoteConfigValues.getOurRemote().getAdRotationPolicy()
//                            + "\n9. adRotationPolicyNative : " + RemoteConfigValues.getOurRemote().getAdRotationPolicyNative()
//                            + "\n10. showRewardVideoAd : " + RemoteConfigValues.getOurRemote().getShowRewardVideoAd()
//                            + "\n11. showNativeAd : " + RemoteConfigValues.getOurRemote().getShowNativeAd()
//                            + "\n12. showNativeAdOnDialog : " + RemoteConfigValues.getOurRemote().getShowNativeAdOnDialog()
//                            + "\n13. showNativeAdOnMainScreen : " + RemoteConfigValues.getOurRemote().getShowNativeAdOnMainScreen()
//                            + "\n14. nativeOnlyGoogle : " + RemoteConfigValues.getOurRemote().getNativeOnlyGoogle()
//                            + "\n15. showOurAppInterstitials : " + RemoteConfigValues.getOurRemote().getShowOurAppInterstitials()
//                            + "\n16. imageUrl : " + RemoteConfigValues.getOurRemote().getImageUrl()
//                            + "\n17. ourApps : " + RemoteConfigValues.getOurRemote().getOurApps()
//                            + "\n18. OurAppsOnMainScreen : " + RemoteConfigValues.getOurRemote().getOurAppsOnMainScreen()
//                            + "\n19. EnableWatermark : " + RemoteConfigValues.getOurRemote().getEnableWatermark()
//                            + "\n20. enableIAPflag : " + RemoteConfigValues.getOurRemote().getEnableIAPflag()
//                            + "\n21. IAP_NOADS : " + RemoteConfigValues.getOurRemote().getIAP_NOADS()
//                            + "\n22. openAppAdFrequency : " + RemoteConfigValues.getOurRemote().getOpenAppAdFrequency());

                    System.out.println("IAP Flag : " + RemoteConfigValues.getOurRemote().getEnableIAPflag());
                }
            }
        });
        // default false
        // RemoteConfigValues.getOurRemote().setEnableIAPflag("true"); //
        checkData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyAppOpen.getInstance().setConnectivityListener(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(br, filter);
        receiverRegistered = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConnectivityReceiver.connectivityReceiverListener = null;
        if (receiverRegistered) {
            unregisterReceiver(br);
        }
    }

    private void loadGoogleAd() {
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Map<String, AdapterStatus> adapterStatusMap = initializationStatus.getAdapterStatusMap();
                for (String next : adapterStatusMap.keySet()) {
                    AdapterStatus adapterStatus = adapterStatusMap.get(next);
                    Log.d("MyApp", String.format("Adapter name: %s, Description: %s, Latency: %d", new Object[]{next, adapterStatus.getDescription(), Integer.valueOf(adapterStatus.getLatency())}));
                }
            }
        });

//        MobileAds.initialize(this, getString(R.string.admob_app_id));

    }

    /*public void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        if (interstitialGoogleAd != null) {
            interstitialGoogleAd.loadAd(adRequest);
        }
    }*/

    private void checkData() {

        // int localAppVersion = 33;
        if (RemoteConfigValues.getOurRemote().getServer().equals("false")) {
            if (timeOut) {
                boolean isHelp = getSharedPreferences("MenSuit", MODE_PRIVATE).getBoolean("helpScreen", true);
                if (isHelp) {
                    startActivity(new Intent(SplashScreen.this, Help.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                finish();
               /* int a = Integer.parseInt(RemoteConfigValues.getOurRemote().getUpgradeAppVersion());
                if (localAppVersion >= a) {
                   // startImg.setVisibility(View.VISIBLE);
                   // loading.setVisibility(View.INVISIBLE);
                    boolean isHelp = getSharedPreferences("MenSuit", MODE_PRIVATE).getBoolean("helpScreen", true);
                    if (isHelp) {
                        startActivity(new Intent(SplashScreen.this, Help.class));
                    } else {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    }
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, Update.class));
                    finish();
                }*/
            } else {
                holdTime();
            }
        } else {
            if (adLoaded || timeOut) {
                boolean isHelp = getSharedPreferences("MenSuit", MODE_PRIVATE).getBoolean("helpScreen", true);
                if (isHelp) {
                    startActivity(new Intent(SplashScreen.this, Help.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                finish();
               /* int a = Integer.parseInt(RemoteConfigValues.getOurRemote().getUpgradeAppVersion());
                if (localAppVersion >= a) {
                    //startImg.setVisibility(View.VISIBLE);
                   // loading.setVisibility(View.INVISIBLE);
                    boolean isHelp = getSharedPreferences("MenSuit", MODE_PRIVATE).getBoolean("helpScreen", true);
                    if (isHelp) {
                        startActivity(new Intent(SplashScreen.this, Help.class));
                    } else {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    }
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, Update.class));
                    finish();
                }*/
            } else {
                holdTime();
            }
        }
    }

    private void holdTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                checkData();
            }
        }, 3000);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            assignDefaultValues();
            if (CommonMethods.getInstance().getSku().equals("") && CommonMethods.getInstance().getPurchaseState() == -1 || CommonMethods.getInstance().getSku().equals("removeads") && CommonMethods.getInstance().getPurchaseState() != 1) {
                loadGoogleAd();
            }
        } else {
            Toast.makeText(SplashScreen.this, "Check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

//    private SharedPreferences getPreferenceObject() {
//        return getApplicationContext().getSharedPreferences(PREF_FILE, 0);
//    }
//
//    private SharedPreferences.Editor getPreferenceEditObject() {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREF_FILE, 0);
//        return pref.edit();
//    }
//
//    private boolean getPurchaseValueFromPref() {
//        return getPreferenceObject().getBoolean(PURCHASE_KEY, false);
//    }
//
//    private void savePurchaseValueToPref(boolean value) {
//        getPreferenceEditObject().putBoolean(PURCHASE_KEY, value).commit();
//    }

    /*@Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

    }*/
}
