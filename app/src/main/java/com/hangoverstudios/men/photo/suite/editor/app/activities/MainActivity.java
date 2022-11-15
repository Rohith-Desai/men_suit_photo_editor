package com.hangoverstudios.men.photo.suite.editor.app.activities;

import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.CategoryIconAdapter;
import com.hangoverstudios.men.photo.suite.editor.app.ads.MyInterstitialAdView;
import com.hangoverstudios.men.photo.suite.editor.app.interfaces.CallimageSelection;
import com.hangoverstudios.men.photo.suite.editor.app.interfaces.NativeaddMainListener;
import com.hangoverstudios.men.photo.suite.editor.app.interfaces.Photocollageopen;
import com.hangoverstudios.men.photo.suite.editor.app.mask.MaskEffect;
import com.hangoverstudios.men.photo.suite.editor.app.model.InterstitialAdModel;
import com.hangoverstudios.men.photo.suite.editor.app.model.OurAdsData;
import com.hangoverstudios.men.photo.suite.editor.app.service.AlarmEvngReceiver;
import com.hangoverstudios.men.photo.suite.editor.app.service.AlarmReceiver;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.ImageAdaptermain;
import com.hangoverstudios.men.photo.suite.editor.app.billing.BillingController;
import com.hangoverstudios.men.photo.suite.editor.app.billing.UpdateBilling;
import com.hangoverstudios.men.photo.suite.editor.app.model.PhotoData;
import com.hangoverstudios.men.photo.suite.editor.app.views.WrapContentViewPager;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//import static com.android.billingclient.api.BillingClient.SkuType.INAPP;

import static com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods.commonMethods;
//import static com.hangoverstudios.men.photo.suite.editor.app.activities.SplashScreen.splashInstance;


public class MainActivity extends AppCompatActivity implements UpdateBilling, InterstitialAdModel.OnInterstitialAdStateListener, CallimageSelection, NativeaddMainListener, Photocollageopen, View.OnClickListener {

    LinearLayout savedImages, layout_promotion, drip_effect;
    ImageView selectPhoto,slide_show, collageEditor, view_all, ourAppsRel, rateUsmain, mask_effect, motion_effect;
    public static final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 2;
    private Uri imageUri;
    private Bitmap bitmap;
    public static Bitmap selectedImageBitmap;
    CardView bg_eraser, bg_changer, suit_styles;

    public static MainActivity mainActivity;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 201;
    private static final int PERMISSION__STORAGE_REQUEST_CODE = 200;
    private static final int PERMISSION_COLLAGE_EDITOR = 11;
    ImageView share, rate, privacyPolicy;
    ImageView oneImg, twoImg, threeImg, fourImg, fiveImg, sixImg, sevenImg, eightImg, nineImg;
    private AdView adViewBanner2;
    private FrameLayout adViewContainer;
    boolean showAd = true;
    private NativeAd nativeAd;
    private String URL = "http://hangoverstudios.com/games/privacy.html";
    Dialog dialog;
    boolean camera = false;
    private TextView appName;
    ScrollView scrollView;


    boolean savedGallery = false;
    RelativeLayout rating_layout;
    ImageView starOne, starTwo, starThree, starFour, starFive;
    LinearLayout remove_ads;
    //private BillingClient billingClient;
    String PRODUCT_ID = "removeads";
    public static final String PREF_FILE = "MEN_SUIT";
    public static final String PURCHASE_KEY = "removeads";
    int OPEN_APP_FREQUENCY;
    RecyclerView favRec;
    RelativeLayout noimages, forimagestext;
    ArrayList<String> imagesListmain;
    ImageAdaptermain imageAdaptermain;
    TextView moreOurApps;
    int s, size;
    // SelectImageFragment galleryFragment;
    String currentPhotoPath;
    BillingController bill;
    private PhotoData photoData;
    String currentPhotoPaths;
    RecyclerView CategoryFeatures;
    ArrayList<String> categoryIcons;
    ArrayList<String> categoryName;
    ImageView ourAppsImages;
    private int[] imageArray;
    private int currentIndex;
    private int startIndex;
    private int endIndex;
    boolean isActivityActive = true;
    HorizontalScrollView horizontalScrollView;
    private ReviewManager reviewManager;


    private AppUpdateManager appUpdateManager;
    private static final int IMMEDIATE_APP_UPDATE_REQ_CODE = 124;

    WrapContentViewPager viewPager;
    private int[] layouts;

    private ViewPagerAdapterMain viewPagerAdapterMain;
    String fromOption = "";

    private MLImageSegmentationAnalyzer analyzer;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_updated_screen);


        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

        if (RemoteConfigValues.getOurRemote().getUpdateFromPlaystore() != null) {
            if (RemoteConfigValues.getOurRemote().getUpdateFromPlaystore().equals("true")) {
                Log.d("updateFromPlaystore", "true");
                checkUpdate();

            }

        }

        init();

        reviewManager = ReviewManagerFactory.create(this);
        InterstitialAdModel.getInstance().setInterstitialAdListener(this);
        // horizontalScrollView=findViewById(R.id.horizontal_scroll_main);
        // favRec = findViewById(R.id.favRec);
        //forimagestext = findViewById(R.id.forimagestext);
        // noimages = findViewById(R.id.noimages);
        ImageView view_all =  findViewById(R.id.view_all);
        slide_show = findViewById(R.id.select_photo);

        AnimationDrawable animationDrawable = (AnimationDrawable) slide_show.getDrawable();
        animationDrawable.start();

        mainActivity = this;
        remove_ads = findViewById(R.id.remove_ads);

        photoData = new PhotoData(this);

        bg_eraser = findViewById(R.id.bg_eraser);
        bg_changer = findViewById(R.id.bg_changer);
        suit_styles = findViewById(R.id.suit_styles);

        bg_eraser.setOnClickListener(this);
        bg_changer.setOnClickListener(this);
        suit_styles.setOnClickListener(this);

        OPEN_APP_FREQUENCY = CommonMethods.getInstance().getOPEN_APP_FREQUENCY();
        // CommonMethods.getInstance().setOPEN_APP_FREQUENCY(++OPEN_APP_FREQUENCY);

        if (RemoteConfigValues.getOurRemote().getEnableIAPflag() != null) {
            if (RemoteConfigValues.getOurRemote().getEnableIAPflag().equals("true")) {
                remove_ads.setVisibility(View.VISIBLE);
            } else if (RemoteConfigValues.getOurRemote().getEnableIAPflag().equals("false")) {
//                remove_ads.setVisibility(View.GONE);
            }
        }
        CommonMethods.getInstance().setCallimageSelection((CallimageSelection) this);
        CommonMethods.getInstance().setNativeaddMainListener((NativeaddMainListener) this);
        CommonMethods.getInstance().setPhotocollageopen((Photocollageopen) this);
//        if (RemoteConfigValues.getOurRemote().getIAP_NOADS() != null) {
//            if (RemoteConfigValues.getOurRemote().getIAP_NOADS().equals("true")) {
//                remove_ads.setVisibility(View.VISIBLE);
//            } else if (RemoteConfigValues.getOurRemote().getIAP_NOADS().equals("false")) {
//                remove_ads.setVisibility(View.GONE);
//            }
//        }
//        System.out.println(" IN MAIN : \n sku : " + CommonMethods.getInstance().getSku() +
//                "purchase state : " + CommonMethods.getInstance().getPurchaseState());

//        if (CommonMethods.getInstance().getSku().equals("removeads") && CommonMethods.getInstance().getPurchaseState() == 1) {
//            // commonMethods.disableAds();
//            remove_ads.setVisibility(View.GONE);
//        }

        bill = new BillingController(this, this);
        bill.billingInitialization();
        if (bill.getPurchaseValueFromPref()) {
            commonMethods.disableAds();
            remove_ads.setVisibility(View.GONE);
            // adViewContainer.setVisibility(View.GONE);
            //remove_ads.setVisibility(View.GONE);
//            HomeActivity.this.recreate();
        }

        remove_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (CommonMethods.getInstance().getSku().equals("removeads") && CommonMethods.getInstance().getPurchaseState() != 1) {
                bill.showPurchaseDialog();
                //  }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "you already own this product..", Toast.LENGTH_SHORT).show();
//                }
            }
        });


//        bg_eraser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                onBackPressed();
//                if (CommonMethods.getInstance().getCallimageSelection()!=null){
//                    CommonMethods.getInstance().getCallimageSelection().callSelectphoto();
//                    CommonMethods.getInstance().setFromDemo("");
//                }
//            }
//        });
//
//        bg_changer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (CommonMethods.getInstance().getCallimageSelection()!=null){
//                    CommonMethods.getInstance().getCallimageSelection().callSelectphoto();
//                    CommonMethods.getInstance().setFromDemo("fromBackground");
//                }
//            }
//        });
//
//        suit_styles.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (CommonMethods.getInstance().getCallimageSelection()!=null){
//                    CommonMethods.getInstance().getCallimageSelection().callSelectphoto();
//                    CommonMethods.getInstance().setFromDemo("fromDresses");
//                }
//            }
//        });

//        starOne = findViewById(R.id.starOne);
//        starTwo = findViewById(R.id.starTwo);
//        starThree = findViewById(R.id.starThree);
//        starFour = findViewById(R.id.starFour);
//        starFive = findViewById(R.id.starFive);
//        rating_layout = findViewById(R.id.rating_layout);
//        mask_effect = findViewById(R.id.mask_effect);
        // drip_effect = findViewById(R.id.drip_effect);

//        mask_effect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fromOption = "maskEffect";
//                selectPhoto("toMask");
//            }
//        });
      /*  drip_effect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DripEffect.class));
//                fromOption = "editImage";
//                selectPhoto("toEdit");
            }
        });*/
//        motion_effect = findViewById(R.id.motion_effect);
//        motion_effect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                fromOption = "motionEffect";
//                selectPhoto("toMotion");
//
//            }
//        });


//        if (RemoteConfigValues.getOurRemote().getShowRatingLayout() != null) {
//            if (RemoteConfigValues.getOurRemote().getShowRatingLayout().equals("true")) {
//                if (!CommonMethods.getInstance().ratingIsDone(MainActivity.this)) {
//                    rating_layout.setVisibility(View.VISIBLE);
//                } else {
//                    rating_layout.setVisibility(View.GONE);
//                }
//            } else {
//                rating_layout.setVisibility(View.GONE);
//            }
//        }
//        starFive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ratingPlaystore();
//            }
//        });
//        starFour.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ratingPlaystore();
//            }
//        });
//        starThree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                messageToMail();
//            }
//        });
//        starTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                messageToMail();
//            }
//        });
//        starOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                messageToMail();
//            }
//        });

        if (getIntent().hasExtra("showAd")) {
            showAd = getIntent().getBooleanExtra("showAd", false);
        }

        if (getIntent().hasExtra("SaveFinishActivity")) {
            showAd = getIntent().getBooleanExtra("SaveFinishActivity", false);
        }

        if (showAd) {
            MyInterstitialAdView.getInstance().onLaunchAD(MainActivity.this);
        }
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },2000);*/

        // appName = findViewById(R.id.main_app_name);
        //scrollView = findViewById(R.id.ads_scroll);
       /* if (CommonMethods.getInstance().getSku().equals("") && CommonMethods.getInstance().getPurchaseState() == -1 || CommonMethods.getInstance().getSku().equals("removeads") && CommonMethods.getInstance().getPurchaseState() != 1) {
            if (RemoteConfigValues.getOurRemote().getShowNativeAd() != null) {
                if (RemoteConfigValues.getOurRemote().getShowNativeAd().equals("true")) {
                    if (RemoteConfigValues.getOurRemote().getAdRotationPolicyNative().equals("true")) {
                        appName.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        admobAd();
                    } else {


                        if (RemoteConfigValues.getOurRemote().getNativeOnlyGoogle().equals("true")) {
                            appName.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                            admobAd();
                        }
                    }
                }
            }
        } else {
            appName.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
        }*/
        /*adViewContainer = findViewById(R.id.main_banner_ad);
        adViewContainer.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adViewBanner2, adViewContainer, MainActivity.this);
            }
        });*/
        selectPhoto = findViewById(R.id.select_photo);
//        savedImages = findViewById(R.id.saved_images);
//        savedImages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                savedGallery = true;
//                if (checkStoragePermission()) {
//                    Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
//                    intent.putExtra("prev_save", false);
//                    startActivity(intent);
//                } else {
//                    requestStoragePermission(0);
//                }
//            }
//        });
        // collageEditor = findViewById(R.id.collage_photo_maker);
       /* collageEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 23) {
                    startActivity(new Intent(MainActivity.this, FrameSelectionActivity.class));
                    // openCollage(false, false, false);
                } else {
                    if (checkStoragePermission()) {
                        startActivity(new Intent(MainActivity.this, FrameSelectionActivity.class));
                        //If you have already permitted the permission
                        //  openCollage(false, false, false);
                    } else {
                        requestStoragePermission(1);
                    }
                }
            }
        });*/

        dialog = new Dialog(this);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fromOption = "editImage";
                selectPhoto("toEdit");
                CommonMethods.getInstance().setFromDemo("");
            }
        });
//        rate = findViewById(R.id.rate_main);
//        share = findViewById(R.id.share_main);
//        privacyPolicy = findViewById(R.id.privacy_policy_main);
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shareApp();
//            }
//        });
//        rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ratingIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.hangoverstudios.men.photo.suite.editor.app"));
//                if (ratingIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(ratingIntent);
//                }
//            }
//        });
//        privacyPolicy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                }
//            }
//        });
//        layout_promotion = findViewById(R.id.enable_our_apps);
//        moreOurApps = findViewById(R.id.more_our_apps);
//        if (RemoteConfigValues.getOurRemote().getOurAppsOnMainScreen() != null) {
//            if (RemoteConfigValues.getOurRemote().getOurAppsOnMainScreen().equals("true")) {
//                loadOurApps();
//                layout_promotion.setVisibility(View.VISIBLE);
//            } else {
//                layout_promotion.setVisibility(View.GONE);
//            }
//        }

//        moreOurApps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5818413742592962651"));
//                startActivity(myIntent);
//            }
//        });
 /*       hang_vehicle = findViewById(R.id.hang_vehicle);
        hang_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.hangoverstudios.vehicleinfo"));
                if (appIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(appIntent);
                }
            }
        });

        scan_vehicle = findViewById(R.id.scan_vehicle);
        scan_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.hs.rtovehicledetail.vahan.vehicleregistrationdetails.rtoapp"));
                if (appIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(appIntent);
                }
            }
        });*/
        //rateUsmain=findViewById(R.id.rate_usmain);
       /* rateUsmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkStoragePermission()) {
                    OPEN_APP_FREQUENCY = CommonMethods.getInstance().getOPEN_APP_FREQUENCY();
                    CommonMethods.getInstance().setOPEN_APP_FREQUENCY(++OPEN_APP_FREQUENCY);
                    Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                    intent.putExtra("prev_save", true);
                    startActivity(intent);
                } else {
                    requestStoragePermission(0);
                }

            }
        });*/
        view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedGallery = true;
                if (checkStoragePermission()) {
                    Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                    intent.putExtra("prev_save", false);
                    startActivity(intent);
                } else {
                    requestStoragePermission(0);
                }
            }
        });
        categoryIcons = new ArrayList<>();
        categoryName = new ArrayList<>();
        categoryName.clear();
        categoryIcons.clear();
        categoryIcons.add("shairstyle_icon");
        categoryIcons.add("sdress_icon");
        // categoryIcons.add("cat_uniform_icon_2");

        categoryIcons.add("seffect_icon");
        categoryIcons.add("sbackground_icon");
        categoryIcons.add("sblend_icon");
        categoryIcons.add("sbgremove_icon");
        //  categoryIcons.add("sphotocollege_icon");
        categoryIcons.add("sframe_icon");
        // categoryIcons.add("cat_accessories_icon_2");

        // categoryIcons.add("cat_bg_eraser_icon_2");
        // categoryIcons.add("cat_makeover_icon_2");
        // categoryIcons.add("cat_frames_icon_2");


        categoryName.add("Man Styles");
        categoryName.add("Dresses");
        //  categoryName.add("Uniform");
        categoryName.add("Effects");
        categoryName.add("Background");
        categoryName.add("Blend Effect");
        //  categoryName.add("BG Eraser");
        categoryName.add("BG Remove");
        //  categoryName.add("Photo Collage");
        categoryName.add("Frames");
//        ourAppsRel = findViewById(R.id.ourapps_relative);
//        ourAppsRel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5818413742592962651"));
//                startActivity(myIntent);
//
//            }
//        });

//        CategoryFeatures = findViewById(R.id.category_features);

//        prepareCategoryIcons();
//        ourAppsImages = findViewById(R.id.ourapps_img);
        imageArray = new int[13];
        imageArray[0] = R.drawable.app_icon_no_1;
        imageArray[1] = R.drawable.app_icon_no_2;
        imageArray[2] = R.drawable.app_icon_no_3;
        imageArray[3] = R.drawable.app_icon_no_4;
        imageArray[4] = R.drawable.app_icon_no_5;
        imageArray[5] = R.drawable.app_icon_no_6;
        imageArray[6] = R.drawable.app_icon_no_7;
        imageArray[7] = R.drawable.app_icon_no_8;
        imageArray[8] = R.drawable.app_icon_no_9;
        imageArray[9] = R.drawable.app_icon_no_10;
        imageArray[10] = R.drawable.app_icon_no_11;
        imageArray[11] = R.drawable.app_icon_no_12;
        imageArray[12] = R.drawable.app_icon_no_13;


        startIndex = 0;
        endIndex = 12;
//        nextImage();


        layouts = new int[]{
                R.layout.dummy_native_view,
                R.layout.main_nativead_real};


        viewPager = (WrapContentViewPager) findViewById(R.id.native_pager);
        viewPagerAdapterMain = new ViewPagerAdapterMain();
        viewPager.setAdapter(viewPagerAdapterMain);

        CommonMethods.getInstance().setFromDemo("");

        SharedPreferences prefs = getSharedPreferences("Notifications_Cal_M_Suit", MODE_PRIVATE);
        boolean startServ = prefs.getBoolean("STARTSERVICE", true);
        if (startServ) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("current time:  " + c.getTime());
            long old = c.getTimeInMillis();
            c.add(Calendar.DATE, 1);  // number of days to add
            c.set(Calendar.HOUR_OF_DAY, 10);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            long newT = c.getTimeInMillis();
            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent receiverIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, receiverIntent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 7);
            if (alarmMgr != null) {
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntent);
            }

            //Evening Alarm

            AlarmManager alarmMgrEvng = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent receiverIntentEvng = new Intent(getApplicationContext(), AlarmEvngReceiver.class);
            PendingIntent alarmIntentEvng = PendingIntent.getBroadcast(getApplicationContext(), 0, receiverIntentEvng, 0);

            Calendar calendarEvng = Calendar.getInstance();
            calendarEvng.setTimeInMillis(System.currentTimeMillis());
            calendarEvng.set(Calendar.HOUR_OF_DAY, 19);
            if (alarmMgrEvng != null) {
                alarmMgrEvng.setRepeating(AlarmManager.RTC_WAKEUP, calendarEvng.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntentEvng);
            }

            SharedPreferences.Editor editor = getSharedPreferences("Notifications_Cal_M_Suit", MODE_PRIVATE).edit();
            editor.putBoolean("STARTSERVICE", false);
            editor.apply();


        }


    }

    public void init() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.pDialog = progressDialog;
        progressDialog.setMessage("Loading...!");
        this.pDialog.setCancelable(false);
    }

    private void checkUpdate() {

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                startUpdateFlow(appUpdateInfo);
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                startUpdateFlow(appUpdateInfo);
            }

        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, MainActivity.IMMEDIATE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }


//    public void nextImage() {
//        //ourAppsImages.setImageResource(imageArray[currentIndex]);
//        if (isActivityActive) {
//            Glide.with(MainActivity.this)
//                    .load(imageArray[currentIndex])
//                    .circleCrop()
//                    .into(ourAppsImages);
//            Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
//            ourAppsImages.startAnimation(rotateimage);
//            currentIndex++;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (currentIndex > endIndex) {
//                        currentIndex--;
//                        previousImage();
//                    } else {
//                        nextImage();
//                    }
//
//                }
//            }, 1500); // here 1000(1 second) interval to change from current  to next image
//
//        }
//
//
//    }

//    public void previousImage() {
//        //ourAppsImages.setImageResource(imageArray[currentIndex]);
//        if (isActivityActive) {
//            Glide.with(MainActivity.this)
//                    .load(imageArray[currentIndex])
//                    .circleCrop()
//                    .into(ourAppsImages);
//            Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
//            ourAppsImages.startAnimation(rotateimage);
//            currentIndex--;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (currentIndex < startIndex) {
//                        currentIndex++;
//                        nextImage();
//                    } else {
//                        previousImage(); // here 1000(1 second) interval to change from current  to previous image
//                    }
//                }
//            }, 1500);
//
//        }
//
//
//    }

//    private void prepareCategoryIcons() {
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        CategoryFeatures.setLayoutManager(layoutManager);
//        CategoryFeatures.setAdapter(new CategoryIconAdapter(MainActivity.this, categoryIcons, categoryName));
//    }

    private void setImageAdapter(final ArrayList<String> imageFilesList, boolean selectAll) {
        imageAdaptermain = new ImageAdaptermain(MainActivity.this, imageFilesList, selectAll);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        /*gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });*/
        //favRec.setLayoutManager(layoutManager);
        // favRec.setAdapter(imageAdaptermain);
    }

    public boolean isDirectoryNotEmpty(String directoryPath) {
        Log.e("TEST_1", "directoryPath" + directoryPath);
        try {
            File file = new File(directoryPath);
            if (file.exists() && file.isDirectory()) {
                if (file.exists() && file.list().length > 0) {
                    System.out.println("Directory is not empty!");
                    return true;
                } else {
                    System.out.println("Directory is empty!");
                    return false;
                }
            } else {
                System.out.println("This is not a directory");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<String> getImagesFromDevice() {
        final ArrayList<String> tempAudioList = new ArrayList<>();

        String directoryPath = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) ?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/Saved Images"
                : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images";

        // String directoryPath = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images/";
        if (isDirectoryNotEmpty(directoryPath)) {
            File directory1 = new File(directoryPath);
            File[] files = directory1.listFiles();
            Arrays.sort(files, new Comparator() {
                public int compare(Object o1, Object o2) {

                    if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                        return -1;
                    } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }
            });

            for (int i = 0; i < files.length; i++) {
                tempAudioList.add(files[i].getAbsolutePath());
                Log.e("Files", "FileName:" + files[i].getAbsolutePath());
            }
            return tempAudioList;
        } else {
            return null;
        }
    }

    public void messageToMail() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.appfeedback);
        alertDialog.setMessage(R.string.appfeedback_text);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                CommonMethods.getInstance().ratingDone(MainActivity.this);
                openMail();
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();

    }

    private void openMail() {
        try {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"hangoverstudios.mobile@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " Feedback ");
            email.putExtra(Intent.EXTRA_TEXT, R.string.appfeedback_text);
            email.setType("message/rfc822");
            email.setPackage("com.google.android.gm");
            startActivity(email);
        } catch (Exception i) {
            Toast.makeText(this, "Gmail app not found..!", Toast.LENGTH_SHORT).show();
        }
    }
    /*private void openFeedback() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("App Feedback");
        alertDialog.setMessage("Please give us your feedback we will improve ");
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                *//*Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","hangoverstudios.mobile@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject:Men Suit Editor Feedback");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));*//*
                CommonMethods.getInstance().ratingDone(MainActivity.this);

                Intent intent = new Intent(Intent.ACTION_VIEW);

                Uri data = Uri.parse("mailto:hangoverstudios.mobile@gmail.com?subject= Men Suit Editor Feedback ");
                intent.setData(data);
                PackageManager packageManager = getPackageManager();
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent);
                }
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }*/

   /* public void openCollage(boolean isblur, boolean isScrapBook, boolean isShape) {
        if (isblur) {
            imagePicker();
        } else {
            galleryFragment = CollageHelper.addGalleryFragment(MainActivity.this, R.id.gallery_fragment_container, true, null);
            galleryFragment.setCollageSingleMode(isblur);
            galleryFragment.setIsScrapbook(isScrapBook);
            galleryFragment.setIsShape(isShape);
            if (!isScrapBook) {
                galleryFragment.setLimitMax(SelectImageFragment.MAX_COLLAGE);
            }
        }
    }*/

    private void imagePicker() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        final View customLayout = getLayoutInflater().inflate(R.layout.image_picker_card, null);
        ImageView galleryPic = customLayout.findViewById(R.id.galleryPic);
        ImageView cameraPic = customLayout.findViewById(R.id.cameraPic);
        builder.setView(customLayout);
        galleryPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermission()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE);
                } else {
                    requestStoragePermission(0);
                }
                dialog.dismiss();
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
                /*if (checkCameraPermission() && checkStoragePermission()) {
                    launchCamera();
                } else {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else if (!checkStoragePermission()) {
                        requestStoragePermission();
                    }
                }*/
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void ratingPlaystore() {
        CommonMethods.getInstance().ratingDone(MainActivity.this);
        Intent ratingIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
        startActivity(ratingIntent);
        /*Task <ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Getting the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown.
                });
            }
        });*/
    }

    public void selectPhoto(String from) {
        //startActivity(new Intent(MainActivity.this,com.hangoverstudios.men.photo.suite.editor.app.bgremoverlite.MainActivity.class));
        String str = "firstpage_select";
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.gallery_dialog);
        LinearLayout galleryImage = dialog.findViewById(R.id.linear_gallery);
        LinearLayout preSavedImage = dialog.findViewById(R.id.linear_crop_saved);
        if (from.equals("toEdit")) {
            preSavedImage.setVisibility(View.VISIBLE);
        } else {
            preSavedImage.setVisibility(View.GONE);
        }
        final LinearLayout cameraImage = dialog.findViewById(R.id.linear_camera);
        ImageView closeImage = (ImageView) dialog.findViewById(R.id.close);
        galleryImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                camera = false;
                if (checkStoragePermission()) {
                    OPEN_APP_FREQUENCY = CommonMethods.getInstance().getOPEN_APP_FREQUENCY();
                    CommonMethods.getInstance().setOPEN_APP_FREQUENCY(++OPEN_APP_FREQUENCY);
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE);
                } else {
                    requestStoragePermission(0);
                }
                dialog.dismiss();
            }
        });
        cameraImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                camera = true;
                if (checkCameraPermission() && checkStoragePermission()) {
                    takePhoto();
                } else {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else if (!checkStoragePermission()) {
                        requestStoragePermission(0);
                    }
                }
                dialog.dismiss();
            }
        });
        preSavedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera = false;
                if (checkStoragePermission()) {
                    OPEN_APP_FREQUENCY = CommonMethods.getInstance().getOPEN_APP_FREQUENCY();
                    CommonMethods.getInstance().setOPEN_APP_FREQUENCY(++OPEN_APP_FREQUENCY);
                    Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                    intent.putExtra("prev_save", true);
                    startActivity(intent);
                } else {
                    requestStoragePermission(0);
                }
                dialog.dismiss();
            }
        });
        closeImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void launchCamera() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Toast.makeText(MainActivity.this, "Something went wrong...please try again!", Toast.LENGTH_SHORT).show();
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(MainActivity.this.getApplicationContext(), getApplicationContext().getPackageName() + ".provider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            }



            /*File path = new File(Environment.getExternalStorageDirectory()
                    + "/Android/data/"
                    + getApplicationContext().getPackageName()
                    + "/temp data");
            if (!path.exists()) path.mkdirs();
            File image = new File(path, "men_suit_image_capture_temp.jpg");
            Uri imageUri = FileProvider.getUriForFile(MainActivity.this.getApplicationContext(), getApplicationContext().getPackageName() + ".provider", image);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);*/
            OPEN_APP_FREQUENCY = CommonMethods.getInstance().getOPEN_APP_FREQUENCY();
            CommonMethods.getInstance().setOPEN_APP_FREQUENCY(++OPEN_APP_FREQUENCY);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );


        File path = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/temp data");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, "men_suit_image_capture_" + timeStamp + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public boolean checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION__STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean readAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    // getData();
                    if (showAd) {
                        MyInterstitialAdView.getInstance().onLaunchAD(MainActivity.this);
                    }
                    if (readAccepted && writeAccepted) {
                        if (camera) {
                            // launchCamera();
                        } else {
                            if (savedGallery) {
                                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                                intent.putExtra("prev_save", false);
                                startActivity(intent);
                            } else {
                                //Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                // galleryIntent.setType("image/*");
                                // startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE);
                            }

                        }
                    } else {
                        showStoragePermisionDialog();
                    }

                }
                break;
            case PERMISSION_CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission(0);
                    } else {
                        takePhoto();
                    }
                } else {
                    showCamPermisionDialog();
                }
                break;

            case PERMISSION_COLLAGE_EDITOR:
                if (permissions.length > 0) {
                    if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
                        //openCollage(false, false, false);
                        //  startActivity(new Intent(MainActivity.this, FrameSelectionActivity.class));
                        // openCollage(false, false, false);
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check Permissions", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void showCamPermisionDialog() {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.permission_request_msg));
        dialog.setTitle(getString(R.string.permission_request));
        dialog.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        requestCameraPermission();

                    }
                });
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    public boolean checkStoragePermission() {
        int result = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void requestStoragePermission(int tag) {
        if (tag == 0)
            ActivityCompat.requestPermissions((Activity) this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PERMISSION__STORAGE_REQUEST_CODE);
        else
            ActivityCompat.requestPermissions((Activity) this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PERMISSION_COLLAGE_EDITOR);

    }

    private void showStoragePermisionDialog() {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.permission_request_msg));
        dialog.setTitle(getString(R.string.permission_request));
        dialog.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        android.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMMEDIATE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        } else if (resultCode == Activity.RESULT_OK) {
            //isGalleryAd = true;
            if (requestCode == PICK_IMAGE) {
                //TODO: action
                if (data != null) {
                    imageUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        selectedImageBitmap = bitmap;
                        //CommonMethods.getInstance().setOriginalSelectedBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } /*else if (requestCode == CAMERA_REQUEST) {


//                Bitmap takenImage = BitmapFactory.decodeFile(currentPhotoPath);
//
//                selectedImageBitmap = takenImage;


                Bitmap bitmapFromMedia = null;
//                File path = new File(Environment.getExternalStorageDirectory()
//                        + "/Android/data/"
//                        + getApplicationContext().getPackageName()
//                        + "/temp data");
//                if (!path.exists()) path.mkdirs();
//                File imageFile = new File(path, "men_suit_image_capture_temp.jpg");

                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                try {
                    Uri image_uri = Uri.fromFile(new File(currentPhotoPath));
                    bitmapFromMedia = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), image_uri);
                    if (bitmapFromMedia != null) {
                        bitmapFromMedia = Bitmap.createBitmap(bitmapFromMedia, 0, 0, bitmapFromMedia.getWidth(), bitmapFromMedia.getHeight(), matrix, true);
                        selectedImageBitmap = bitmapFromMedia;
                    }
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Something went wrong...please try again!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }*/ else if (requestCode == CAMERA_REQUEST) {
//            global.setBitmap(convertBitmap(imageUri, true));
                Bitmap b = null;
            /*if (imageUri != null) {
                b = convertBitmap(imageUri, true);
            }*/

//                File path = new File(Environment.getExternalStorageDirectory()
//                        + "/root/Android/data/"
//                        + getApplicationContext().getPackageName()
//                        + "/temp data");
//                if (!path.exists()) path.mkdirs();
//                File imageFile = new File(path, "men_suit_image_capture.jpg");

                Uri stringUri = null;

                //                    stringUri = Uri.fromFile(new File(currentPhotoPath));
//                    b = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), stringUri);
                imageUri = Uri.fromFile(new File(photoData.getUriPath()));
                b = BitmapFactory.decodeFile(photoData.getUriPath());
                selectedImageBitmap = b;

            }
            if (selectedImageBitmap != null) {
                if (fromOption.equals("editImage")) {
                    startActivity(new Intent(MainActivity.this, ImageCropActivity.class));
                } else if (fromOption.equals("maskEffect")) {
                    startActivity(new Intent(MainActivity.this, MaskEffect.class));
                } else {
                    MainActivity.this.pDialog.show();
                    analyzer();
                }
            } else {
                Toast.makeText(MainActivity.this, "Something went wrong...please try again!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();

        }
    }

    private void analyzer() {
        this.analyzer = MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(new MLImageSegmentationSetting.Factory().setExact(false).setAnalyzerType(0).setScene(0).create());
        this.analyzer.asyncAnalyseFrame(new MLFrame.Creator().setBitmap(selectedImageBitmap).create()).addOnSuccessListener(new OnSuccessListener<MLImageSegmentation>() {

            public void onSuccess(MLImageSegmentation mLImageSegmentation) {
                if (mLImageSegmentation != null) {
                    MainActivity.this.displaySuccess(mLImageSegmentation);
                } else {
                    MainActivity.this.displayFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(Exception exc) {
                MainActivity.this.displayFailure();
            }
        });
    }

    private void displaySuccess(MLImageSegmentation mLImageSegmentation) {
        if (this.selectedImageBitmap == null) {
            displayFailure();
            return;
        }
        Bitmap foreground = mLImageSegmentation.getForeground();
        if (foreground != null) {
            selectedImageBitmap = foreground;
//            showImage();
        } else {
            Log.d("sk", "sk");
            displayFailure();
        }
        // StickersDesignActivity.this.pDialog.dismiss();
        MainActivity.this.pDialog.dismiss();

    }

//    public void showImage() {
//
//        if (selectedImageBitmap != null) {
//            if (CommonMethods.getInstance() != null) {
//                CommonMethods.getInstance().setMotionbitmap(selectedImageBitmap);
//            }
//
//            Intent intent = new Intent(MainActivity.this, MostionEffectActivity.class);
//            intent.putExtra("item_type", imageUri.toString());
//            startActivity(intent);
//
//        }
//
//
//    }


    @SuppressLint("WrongConstant")
    private void displayFailure() {

        Toast.makeText(getApplicationContext(), "Fail", 0).show();
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_exit);

        TextView exit_no = dialog.findViewById(R.id.exit_no);
        TextView exit_yes = dialog.findViewById(R.id.exit_yes);

        exit_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        exit_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnim;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void exit() {
        super.onBackPressed();
    }

    public void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String shareMessage = "\n" + getResources().getString(R.string.app_name) + "\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }


    public void launchCamera2() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            File path = new File(Environment.getExternalStorageDirectory()
//                    + "/root/Android/data/"
//                    + getApplicationContext().getPackageName()
//                    + "/temp data");
//            if (!path.exists()) path.mkdirs();
//            File image = new File(path, "men_suit_image_capture_.jpg");


            File folder = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES + getApplicationContext().getPackageName() + "/temp data");
            folder.mkdir();
            File file = new File(folder, "men_suit_image_capture_.jpg");
            try {
                file.createNewFile();
            } catch (IOException e) {
                Toast.makeText(this.getApplicationContext(),
                        "createNewFile:" + e.toString(),
                        Toast.LENGTH_LONG).show();
            }

            currentPhotoPath = file.getAbsolutePath();

//            Uri imageUri = FileProvider.getUriForFile(SingleFrameEditActivity.this.getApplicationContext(), getApplicationContext().getPackageName() + ".provider", image);
            Uri imageUri = FileProvider.getUriForFile(MainActivity.this, getApplicationContext().getPackageName() + ".provider", file);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    private void takePhoto() {
        File photo = null;
        try {
            photo = createImageFiles();
            Uri photoURI = FileProvider.getUriForFile(MainActivity.this, getApplicationContext().getPackageName() + ".provider", photo);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            photoData.saveUriPath(Uri.fromFile(photo).getPath());
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAMERA_REQUEST);
            }

        } catch (IOException e) {
            //TODO warn the user the photo fail
        }


    }

    private File createImageFiles() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        // currentPhotoPaths = image.getAbsolutePath();
        return image;
    }

   /* private void takePhoto() {
        File photo = null;
        try {
            photo = createImageFiles();
            Uri photoURI = FileProvider.getUriForFile(MainActivity.this, getApplicationContext().getPackageName() + ".provider", photo);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            photoData.saveUriPath(Uri.fromFile(photo).getPath());
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAMERA_REQUEST);
            }

        } catch (IOException e) {
            //TODO warn the user the photo fail
        }


    }*/



   /* private void admobAd() {

        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.admob_native_id));
        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        boolean isDestroyed = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            isDestroyed = isDestroyed();
                        }
                        if (isDestroyed || isFinishing() || isChangingConfigurations()) {
                            nativeAd.destroy();
                            return;
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (MainActivity.this.nativeAd != null) {
                            MainActivity.this.nativeAd.destroy();
                        }
                        MainActivity.this.nativeAd = nativeAd;
                        FrameLayout frameLayout = findViewById(R.id.native_ad_main);
                        NativeAdView adView =
                                (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                        populateNativeAdView(nativeAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                });

        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(true).build();

        com.google.android.gms.ads.nativead.NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                    }
                                })
                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }

    public static void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        } else {
        }
    }*/


    /*@Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        //assert purchases != null;
       //Log.v("MAIN_TAG", "onPurchasesUpdated..first line : "+purchases.size());
//if item newly purchased
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            Log.v("MAIN_TAG", "newly purchased item..");
            handlePurchases(purchases);
        }
        //if item already purchased then check and reflect changes
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            Log.v("MAIN_TAG", "item already owned..first line  ");
            Purchase.PurchasesResult queryAlreadyPurchasesResult = billingClient.queryPurchases(INAPP);
            List<Purchase> alreadyPurchases = queryAlreadyPurchasesResult.getPurchasesList();
            if (alreadyPurchases != null) {
                //Log.v("TAG", "purchased products : "+alreadyPurchases.toString());
                Log.v("MAIN_TAG", "item already owned.. : "+alreadyPurchases.toString());
                handlePurchases(alreadyPurchases);
            }
        }
        //if purchase cancelled
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(getApplicationContext(), "Purchase Canceled", Toast.LENGTH_SHORT).show();
        }
        // Handle any other error msgs
        else {
            Toast.makeText(getApplicationContext(), "Error " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void handlePurchases(List<Purchase> purchases) {
        Log.v("MAIN_TAG","first line in handlePurchases..");
        for (Purchase purchase : purchases) {
            Log.v("MAIN_TAG","first line in for each..");
            //if item is purchased
            if (PRODUCT_ID.equals(purchase.getSku()) && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                Log.v("MAIN_TAG","first line in PURCHASED..: "+purchase.getSku());
                if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
                    // Invalid purchase
                    // show error to user
                    Toast.makeText(getApplicationContext(), "Error : Invalid Purchase", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //else item is purchased and also acknowledged
                        // Grant entitlement to the user on item purchase
                        // restart activity
                        // if (purchases.get(0).getSku().equals("removeads") && purchases.get(0).getPurchaseState() == 1) {
                        //savePurchaseValueToPref(true);
//                        Toast.makeText(getApplicationContext(), "Item Purchased", Toast.LENGTH_SHORT).show();
                        Log.v("MAIN_TAG","purchase.isAcknowledged() : "+purchase.isAcknowledged());
                        Log.v("MAIN_TAG"," success : "+purchases.size());


                        CommonMethods.getInstance().setSku(purchases.get(0).getSku());
                        CommonMethods.getInstance().setPurchaseState(purchases.get(0).getPurchaseState());

                        getSharedPreferences("PURCHASE_PREF", MODE_PRIVATE).edit().putString("_sku_", purchases.get(0).getSku()).apply();
                        getSharedPreferences("PURCHASE_PREF", MODE_PRIVATE).edit().putInt("_purchase_state_", purchases.get(0).getPurchaseState()).apply();

//                            getSharedPreferences("PURCHASE_PREF", MODE_PRIVATE).edit().putString("_sku_", "faghfsagh").apply();
                        // getSharedPreferences("PURCHASE_PREF", MODE_PRIVATE).edit().putInt("_purchase_state_", 1).apply();
//
//                            CommonMethods.getInstance().setSku("faghfsagh");
                        //CommonMethods.getInstance().setPurchaseState(1);

                    if (!purchase.isAcknowledged()) {
                        Log.v("MAIN_TAG","purchase.isAcknowledged() : "+purchase.isAcknowledged());
                        AcknowledgePurchaseParams acknowledgePurchaseParams =
                                AcknowledgePurchaseParams.newBuilder()
                                        .setPurchaseToken(purchase.getPurchaseToken())
                                        .build();
                        billingClient.acknowledgePurchase(acknowledgePurchaseParams, ackPurchase);
                    }

                        Log.v("log_tag", "productId : " + purchases.get(0).getSku() +
                                ", purchaseState : " + purchases.get(0).getPurchaseState() +
                                ", acknowledged : " + purchases.get(0).isAcknowledged()
                        );
                        remove_ads.setVisibility(View.GONE);
                        // this.recreate();
                        finish();
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        //  }
                }
                // else purchase is valid
                //if item is purchased and not acknowledged
            }
            //if purchase is pending
            else if (PRODUCT_ID.equals(purchase.getSku()) && purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
                Toast.makeText(getApplicationContext(),
                        "Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT).show();
            }
            //if purchase is unknown
            else if (PRODUCT_ID.equals(purchase.getSku()) && purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE) {
                //savePurchaseValueToPref(false);

                Toast.makeText(getApplicationContext(), "Purchase Status Unknown", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

//    AcknowledgePurchaseResponseListener ackPurchase = new AcknowledgePurchaseResponseListener() {
//        @Override
//        public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
//            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                //if purchase is acknowledged
//                // Grant entitlement to the user. and restart activity
//                // savePurchaseValueToPref(true);
//                Toast.makeText(getApplicationContext(), "Item Purchased", Toast.LENGTH_SHORT).show();
//                MainActivity.this.recreate();
//            }
//        }
//    };

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

    private void showPurchaseDialog() {
        //check if service is already connected
       /* if (billingClient.isReady()) {
            initiatePurchase();
        }
        //else reconnect service
        else {
            billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(this).build();
            billingClient.startConnection(new BillingClientStateListener() {
                @Override
                public void onBillingSetupFinished(BillingResult billingResult) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        initiatePurchase();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onBillingServiceDisconnected() {
                }
            });
        }*/
    }

    private void initiatePurchase() {

        /*List<String> skuList = new ArrayList<>();
        skuList.add(PRODUCT_ID);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {

                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            if (skuDetailsList != null && skuDetailsList.size() > 0) {
                                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                        .setSkuDetails(skuDetailsList.get(0))
                                        .build();
                                billingClient.launchBillingFlow(MainActivity.this, flowParams);
                            } else {
                                //try to add item/product id "purchase" inside managed product in google play console
                                Toast.makeText(getApplicationContext(), "Purchase Item not Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    " Error " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }

    @Override
    public void onStart() {
        super.onStart();

       /* if (checkStoragePermission()) {
            if (getImagesFromDevice() != null) {
                imagesListmain = getImagesFromDevice();
                s = imagesListmain.size();
                if (s != size) {

                    if (imagesListmain.size() > 0) {
                        // noImagesToShow.setVisibility(View.GONE);
                        favRec.setVisibility(View.VISIBLE);
                        forimagestext.setVisibility(View.VISIBLE);
                        noimages.setVisibility(View.GONE);
                        setImageAdapter(imagesListmain, false);
                    } else {



                    }

                }

            } else {
                favRec.setVisibility(View.GONE);
                forimagestext.setVisibility(View.GONE);
                noimages.setVisibility(View.VISIBLE);
            }

        } else {

        }*/


    }

   /* public void getData() {
        String path = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) ?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/Saved Images"
                : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images";

        // isDirectoryNotEmpty(Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images")
        if (isDirectoryNotEmpty(path)) {
            imagesListmain = getImagesFromDevice();
            if (imagesListmain != null && imagesListmain.size() > 0) {
                // noImagesToShow.setVisibility(View.GONE);
                size = imagesListmain.size();
                favRec.setVisibility(View.VISIBLE);
                forimagestext.setVisibility(View.VISIBLE);
                noimages.setVisibility(View.GONE);
            } else {
                //  noImagesToShow.setVisibility(View.VISIBLE);

                favRec.setVisibility(View.GONE);
                forimagestext.setVisibility(View.GONE);
                noimages.setVisibility(View.VISIBLE);
            }
            setImageAdapter(imagesListmain, false);
        } else {
            //  noImagesToShow.setVisibility(View.VISIBLE);

            favRec.setVisibility(View.GONE);
            forimagestext.setVisibility(View.GONE);
            noimages.setVisibility(View.VISIBLE);
        }
    }*/

    @Override
    public void updateUI() {
        commonMethods.disableAds();
        remove_ads.setVisibility(View.GONE);
    }

    @Override
    public void stateInterstitialAdChanged() {
        if (InterstitialAdModel.getInstance().getInterstitialAdState()) {
            // Toast.makeText(MainActivity.this, "Interstitial Ad closed.." + CustomModel.getInstance().getState(), Toast.LENGTH_SHORT).show();
            setData();
        }
    }

    private void setData() {
        if (!checkStoragePermission()) {
            // savedGallery = true;
            requestStoragePermission(0);
        } else {
            //getData();
        }
    }


    void loadOurApps() {
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(MainActivity.this));

        if (OurAdsData.getData().getOurApp1Link() == null || OurAdsData.getData().getOurApp2Link() == null ||
                OurAdsData.getData().getOurApp3Link() == null || OurAdsData.getData().getOurApp4Link() == null ||
                OurAdsData.getData().getOurApp5Link() == null || OurAdsData.getData().getOurApp6Link() == null ||
                OurAdsData.getData().getOurApp7Link() == null || OurAdsData.getData().getOurApp8Link() == null ||
                OurAdsData.getData().getOurApp9Link() == null
        ) {
            return;
        }

        if (OurAdsData.getData().getOurApp1icon() == null || OurAdsData.getData().getOurApp2icon() == null ||
                OurAdsData.getData().getOurApp3icon() == null || OurAdsData.getData().getOurApp4icon() == null ||
                OurAdsData.getData().getOurApp5icon() == null || OurAdsData.getData().getOurApp6icon() == null ||
                OurAdsData.getData().getOurApp7icon() == null || OurAdsData.getData().getOurApp8icon() == null ||
                OurAdsData.getData().getOurApp9icon() == null
        ) {
            return;
        }

        if (!OurAdsData.getData().getOurApp1Link().equals("")) {
            oneImg = findViewById(R.id.one);

            Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp1icon()).centerCrop().into(new BitmapImageViewTarget(oneImg) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    oneImg.setImageDrawable(circularBitmapDrawable);
                }
            });
//            Glide.with(this).load(OurAdsData.getData().getOurApp1icon()).thumbnail(0.5f)
//                    .crossFade()
//
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(oneImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp1icon(), oneImg);
            if (oneImg != null) {
                oneImg.setVisibility(View.VISIBLE);
                oneImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp1Link()));
                        startActivity(myIntent);
                    }
                });
            }
        }

        if (!OurAdsData.getData().getOurApp2Link().equals("")) {
            twoImg = findViewById(R.id.two);
            Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp2icon()).centerCrop()
                    .into(new BitmapImageViewTarget(twoImg) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            twoImg.setImageDrawable(circularBitmapDrawable);
                        }
                    });
//            Glide.with(this).load(OurAdsData.getData().getOurApp2icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(twoImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp2icon(), twoImg);
            if (twoImg != null) {
                twoImg.setVisibility(View.VISIBLE);
                twoImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp2Link()));
                        startActivity(myIntent);
                    }
                });
            }
        }


        if (!OurAdsData.getData().getOurApp3Link().equals("")) {
            threeImg = findViewById(R.id.three);
            Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp3icon()).centerCrop()
                    .into(new BitmapImageViewTarget(threeImg) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            threeImg.setImageDrawable(circularBitmapDrawable);
                        }
                    });
//            Glide.with(this).load(OurAdsData.getData().getOurApp3icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(threeImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp3icon(), threeImg);
            if (threeImg != null) {
                threeImg.setVisibility(View.VISIBLE);
                threeImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp3Link()));
                        startActivity(myIntent);
                    }
                });
            }
        }

        if (!OurAdsData.getData().getOurApp4Link().equals("")) {
            fourImg = findViewById(R.id.four);
            Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp4icon()).centerCrop()
                    .into(new BitmapImageViewTarget(fourImg) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            fourImg.setImageDrawable(circularBitmapDrawable);
                        }
                    });
//            Glide.with(this).load(OurAdsData.getData().getOurApp4icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(fourImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp4icon(), fourImg);
            if (fourImg != null) {
                fourImg.setVisibility(View.VISIBLE);
                fourImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp4Link()));
                        startActivity(myIntent);
                    }
                });
            }
        }

        if (!OurAdsData.getData().getOurApp5Link().equals("")) {
            fiveImg = findViewById(R.id.five);
            Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp5icon()).centerCrop()
                    .into(new BitmapImageViewTarget(fiveImg) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            fiveImg.setImageDrawable(circularBitmapDrawable);
                        }
                    });
//            Glide.with(this).load(OurAdsData.getData().getOurApp5icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(fiveImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp5icon(), fiveImg);
            if (fiveImg != null) {
                fiveImg.setVisibility(View.VISIBLE);
                fiveImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp5Link()));
                        startActivity(myIntent);
                    }
                });
            }
        }

        if (!OurAdsData.getData().getOurApp6Link().equals("")) {
            sixImg = findViewById(R.id.six);
            Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp6icon()).centerCrop()
                    .into(new BitmapImageViewTarget(sixImg) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            sixImg.setImageDrawable(circularBitmapDrawable);
                        }
                    });
//            Glide.with(this).load(OurAdsData.getData().getOurApp6icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(sixImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp6icon(), sixImg);
            if (sixImg != null) {
                sixImg.setVisibility(View.VISIBLE);
                sixImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp6Link()));
                        startActivity(myIntent);
                    }
                });
            }
        }

        if (!OurAdsData.getData().getOurApp7Link().equals("")) {
            sevenImg = findViewById(R.id.seven);
            Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp7icon()).centerCrop()
                    .into(new BitmapImageViewTarget(sevenImg) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            sevenImg.setImageDrawable(circularBitmapDrawable);
                        }
                    });
//            Glide.with(this).load(OurAdsData.getData().getOurApp7icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(sevenImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp7icon(), sevenImg);
            if (sevenImg != null) {
                sevenImg.setVisibility(View.VISIBLE);
                sevenImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp7Link()));
                        startActivity(myIntent);
                    }
                });
            }
        }

        if (!OurAdsData.getData().getOurApp8Link().equals("")) {
            eightImg = findViewById(R.id.eight);
            Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp8icon()).centerCrop()
                    .into(new BitmapImageViewTarget(eightImg) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            eightImg.setImageDrawable(circularBitmapDrawable);
                        }
                    });
//            Glide.with(this).load(OurAdsData.getData().getOurApp8icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(eightImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp8icon(), eightImg);
            if (eightImg != null) {
                eightImg.setVisibility(View.VISIBLE);
                eightImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp8Link()));
                        startActivity(myIntent);
                    }
                });
            }
        }

        if (!OurAdsData.getData().getOurApp9Link().equals("")) {
            nineImg = findViewById(R.id.nine);
            Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp9icon()).centerCrop()
                    .into(new BitmapImageViewTarget(nineImg) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            nineImg.setImageDrawable(circularBitmapDrawable);
                        }
                    });
//            Glide.with(this).load(OurAdsData.getData().getOurApp9icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(nineImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp9icon(), nineImg);
            if (nineImg != null) {
                nineImg.setVisibility(View.VISIBLE);
                nineImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp9Link()));
                        startActivity(myIntent);
                    }
                });
            }
        }


    }

    @Override
    public void callSelectphoto() {
        fromOption = "editImage";
        selectPhoto("toEdit");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isActivityActive = false;
        MLImageSegmentationAnalyzer mLImageSegmentationAnalyzer = this.analyzer;
        if (mLImageSegmentationAnalyzer != null) {
            try {
                mLImageSegmentationAnalyzer.stop();
            } catch (IOException e) {
                Log.e("Auto crop", "Stop failed: " + e.getMessage());
            }
        }

    }

    @Override
    public void MainnativeadListenert() {
        if (isActivityActive) {
            // horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            viewPager.setCurrentItem(1);

        }

    }

    @Override
    public void OpenphotocollageListiner() {

       /* if (Build.VERSION.SDK_INT < 23) {
            startActivity(new Intent(MainActivity.this, FrameSelectionActivity.class));
            // openCollage(false, false, false);
        } else {
            if (checkStoragePermission()) {
                startActivity(new Intent(MainActivity.this, FrameSelectionActivity.class));
                //If you have already permitted the permission
                //  openCollage(false, false, false);
            } else {
                requestStoragePermission(1);
            }
        }*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bg_eraser:
                fromOption = "editImage";
                selectPhoto("toEdit");
                CommonMethods.getInstance().setFromDemo("fromEraser");
                break;

            case R.id.bg_changer:
                fromOption = "editImage";
                selectPhoto("toEdit");
                CommonMethods.getInstance().setFromDemo("fromBackground");
                break;

            case R.id.suit_styles:
                fromOption = "editImage";
                selectPhoto("toEdit");
                CommonMethods.getInstance().setFromDemo("fromDresses");
                break;
        }
    }

    public class ViewPagerAdapterMain extends PagerAdapter {

        private LayoutInflater layoutInflater;


        public ViewPagerAdapterMain() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }


}
