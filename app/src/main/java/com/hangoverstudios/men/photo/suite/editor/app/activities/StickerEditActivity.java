package com.hangoverstudios.men.photo.suite.editor.app.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hangoverstudios.men.photo.suite.editor.app.ads.MyInterstitialAdView;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.StickersPreviewAdapter;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.SuitsDownPreviewAdapter;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;
import com.hangoverstudios.men.photo.suite.editor.app.views.BubbleTextView;
import com.hangoverstudios.men.photo.suite.editor.app.views.StickerView;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.SplashScreen.mFirebaseAnalytics;
//import static com.hangoverstudios.men.photo.suite.editor.app.activities.CategoriesActivity.categoriesActivity;

public class StickerEditActivity extends AppCompatActivity {
    private Intent intent;
    private String from;
    private LinearLayout rotateLinearBtn, refreshLinearBtn, doneLinearBtn, saveLinearBtn, backLinearBtn;
    private ImageView selectedImage;
    int angle = 0;
    private RecyclerView stickersRecyclerView, downSuitsPreviewRecycler;
    SuitsDownPreviewAdapter suitsDownPreviewAdapter;
    private LinearLayout menStyleLayout, accessoriesLayout, makeOverLayout, newStickersLayout;
    private LinearLayout hairStyle, mustache, beard, eyeBrows, eyeBall;
    private LinearLayout glasses, cap, stoles, tie, chain, earrings;
    private LinearLayout sixPack, eightPack, chest, tattoos;
    private LinearLayout loveStick, wishStick, greetStick, expStick;

    private TextView hairStyleTxt, mustacheTxt, beardTxt, eyeBrowsTxt, eyeBallTxt;
    private TextView glassesTxt, capTxt, stolesTxt, tieTxt, chainTxt, earringsTxt;
    private TextView sixPackTxt, eightPackTxt, chestTxt, tattoosTxt;
    private TextView loveStickTxt, wishStickTxt, greetStickTxt, expStickTxt;

    private ImageView hairStyleImg, mustacheImg, beardImg, eyeBrowsImg, eyeBallImg;
    private ImageView glassesImg, capImg, stolesImg, tieImg, chainImg, earringsImg;
    private ImageView sixPackImg, eightPackImg, chestImg, tattoosImg;
    private ImageView loveStickImg, wishStickImg, greetStickImg, expStickImg;

    public RelativeLayout mContentRootView;
    private Bitmap bitmap;
    private int currentColor = Color.parseColor("#00ff00");
    private static final Random random = new Random();
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";
    public StickerView mCurrentView;
    public ArrayList<View> mViews;
    public BubbleTextView mCurrentEditTextView;
    private int[] mResources = {};
    AlertDialog OptionDialog;
    private String categoryType;
    public static StickerEditActivity stickerEditActivity;
    private AdView adViewBanner9;
    private FrameLayout adViewContainer;
    RecyclerView stickersPrevRecycler;
    StickersPreviewAdapter stickerPreviewAdapter;
    public String RESOURCE_ID_TO_COPY;
    public int INT_RESOURCE_ID_TO_COPY;
    String dResouce[];
    ArrayList<String> imagesFromStorage;
    String subCategory;
    private CategoriesActivity categoriesActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_edit_dummy);
        categoriesActivity = ChangeBackgroundData.getChangeBackgroundData().getCategoriesActivity();
        stickerEditActivity = this;
        intent = getIntent();
        from = intent.hasExtra("type")?intent.getStringExtra("type"):null;
        categoryType = intent.hasExtra("category")?intent.getStringExtra("category"):null;

        /*adViewContainer = findViewById(R.id.sticker_edit_banner_ad);
        adViewContainer.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adViewBanner9, adViewContainer, StickerEditActivity.this);
            }
        });*/
        initVars();
        setBottomLayout(categoryType);
        clickables();
        if(categoriesActivity!=null){
            bitmap = categoriesActivity.getSavedBitmap();
            selectedImage.setImageBitmap(bitmap);
            mViews = new ArrayList<>();
        }
    }

    private void clickables() {
        mContentRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                if (mCurrentView != null)
                    mCurrentView.setInEdit(false);
            }
        });
        hairStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null){
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this, mustacheImg, mustacheTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,beardImg, beardTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,hairStyleImg, hairStyleTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eyeBrowsImg, eyeBrowsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eyeBallImg, eyeBallTxt);
                    setStickerPrevAdapter("hair");
                }
            }
        });
        mustache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(categoriesActivity, "mustache clicked", Toast.LENGTH_SHORT).show();
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,mustacheImg, mustacheTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,beardImg, beardTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,hairStyleImg, hairStyleTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eyeBrowsImg, eyeBrowsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eyeBallImg, eyeBallTxt);
                    setStickerPrevAdapter("mustache");
                }
            }
        });
        beard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,mustacheImg, mustacheTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,beardImg, beardTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,hairStyleImg, hairStyleTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eyeBrowsImg, eyeBrowsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eyeBallImg, eyeBallTxt);
                    setStickerPrevAdapter("beard");
                }
            }
        });
        eyeBrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,mustacheImg, mustacheTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,beardImg, beardTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,hairStyleImg, hairStyleTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,eyeBrowsImg, eyeBrowsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eyeBallImg, eyeBallTxt);
                    setStickerPrevAdapter("eyebrows");
                }
            }
        });
        eyeBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,mustacheImg, mustacheTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,beardImg, beardTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,hairStyleImg, hairStyleTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eyeBrowsImg, eyeBrowsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,eyeBallImg, eyeBallTxt);
                    setStickerPrevAdapter("eye");
                }
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,capImg, capTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,stolesImg, stolesTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,tieImg, tieTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,chainImg, chainTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,earringsImg, earringsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,glassesImg, glassesTxt);
                    // openAlertDialog("glasses");
                    setStickerPrevAdapter("glasses");
                }
            }
        });
        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,capImg, capTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,stolesImg, stolesTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,tieImg, tieTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,chainImg, chainTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,earringsImg, earringsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,glassesImg, glassesTxt);
                    setStickerPrevAdapter("caps");
                }
            }
        });
        stoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,capImg, capTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,stolesImg, stolesTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,tieImg, tieTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,chainImg, chainTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,earringsImg, earringsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,glassesImg, glassesTxt);
                    setStickerPrevAdapter("stole");
                }
            }
        });
        tie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,capImg, capTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,stolesImg, stolesTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,tieImg, tieTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,chainImg, chainTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,earringsImg, earringsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,glassesImg, glassesTxt);
                    setStickerPrevAdapter("tie");
                }
            }
        });
        chain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,capImg, capTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,stolesImg, stolesTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,tieImg, tieTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,chainImg, chainTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,earringsImg, earringsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,glassesImg, glassesTxt);
                    setStickerPrevAdapter("chain");
                }
            }
        });
        earrings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,capImg, capTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,stolesImg, stolesTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,tieImg, tieTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,chainImg, chainTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,earringsImg, earringsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,glassesImg, glassesTxt);
                    setStickerPrevAdapter("ear");
                }
            }
        });

        sixPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,sixPackImg, sixPackTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eightPackImg, eightPackTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,chestImg, chestTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,tattoosImg, tattoosTxt);
                    setStickerPrevAdapter("packs");
                }
            }
        });
        eightPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,sixPackImg, sixPackTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,eightPackImg, eightPackTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,chestImg, chestTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,tattoosImg, tattoosTxt);
                    setStickerPrevAdapter("epack");
                }
            }
        });
        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,sixPackImg, sixPackTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eightPackImg, eightPackTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,chestImg, chestTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,tattoosImg, tattoosTxt);
                    // openAlertDialog("chest");
                    setStickerPrevAdapter("chest");
                }
            }
        });
        tattoos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,sixPackImg, sixPackTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,eightPackImg, eightPackTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,chestImg, chestTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,tattoosImg, tattoosTxt);
                    //openAlertDialog("tattoos");
                    setStickerPrevAdapter("tattoo");
                }
            }
        });


        loveStick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,loveStickImg, loveStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,wishStickImg, wishStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,greetStickImg, greetStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,expStickImg, expStickTxt);
                    setStickerPrevAdapter("lovekers");
                }
            }
        });


        wishStick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,loveStickImg, loveStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,wishStickImg, wishStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,greetStickImg, greetStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,expStickImg, expStickTxt);
                    setStickerPrevAdapter("wishkers");
                }
            }
        });

        greetStick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,loveStickImg, loveStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,wishStickImg, wishStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,greetStickImg, greetStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,expStickImg, expStickTxt);
                    setStickerPrevAdapter("greetkers");
                }
            }
        });

        expStick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,loveStickImg, loveStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,wishStickImg, wishStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,greetStickImg, greetStickTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(StickerEditActivity.this,expStickImg, expStickTxt);
                    setStickerPrevAdapter("expkers");
                }
            }
        });

        rotateLinearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (angle == 360) {
                    angle = 90;
                } else {
                    angle += 90;
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(angle);
                if(bitmap!=null){
                    Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                            matrix, true);
                    selectedImage.setImageBitmap(rotated);
                }
            }
        });
        refreshLinearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonMethods.getInstance().getBitmapEventQueue() != null)
                    CommonMethods.getInstance().getBitmapEventQueue().clear();
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                if (mCurrentView != null)
                    mCurrentView.setInEdit(false);

                bitmap = Bitmap.createBitmap(mContentRootView.getDrawingCache());
                selectedImage.setImageBitmap(bitmap);
                if(mViews!=null){
                    for (int i = 0; i < mViews.size(); i++) {
                        mContentRootView.removeView(mViews.get(i));
                    }
                    mContentRootView.removeView(mCurrentView);
                    mViews = new ArrayList<>();
                    bitmap = categoriesActivity.getSavedBitmap();

                    selectedImage.setImageBitmap(bitmap);
                    mContentRootView.buildDrawingCache();
                    mContentRootView.refreshDrawableState();
                }
                // showAdds();
            }
        });
        doneLinearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mSuitDrawView.mirrorImage();
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                if (mCurrentView != null)
                    mCurrentView.setInEdit(false);

                bitmap = Bitmap.createBitmap(mContentRootView.getDrawingCache());
                selectedImage.setImageBitmap(bitmap);
                if(mViews!=null){
                    for (int i = 0; i < mViews.size(); i++) {
                        mContentRootView.removeView(mViews.get(i));
                    }
                    mContentRootView.removeView(mCurrentView);
                }
                mViews = new ArrayList<>();
            }
        });
        saveLinearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                if (mCurrentView != null)
                    mCurrentView.setInEdit(false);
                bitmap = Bitmap.createBitmap(mContentRootView.getDrawingCache());
                selectedImage.setImageBitmap(bitmap);
                if(categoriesActivity!=null){
                    categoriesActivity.updateSavedBitmap(bitmap);
                }
                for (int i = 0; i < mViews.size(); i++) {
                    mContentRootView.removeView(mViews.get(i));
                }
                mViews = new ArrayList<>();
                mContentRootView.removeView(mCurrentView);
                // mSuitDrawView.save();
                saveImage();
            }
        });
        backLinearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
      /*  moreStickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StickerEditActivity.this,MoreResourcesActivity.class));
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyInterstitialAdView.getInstance().activitiesAD(StickerEditActivity.this);
    }

    /*private void showAdds() {
            if (isConnected) {
                interstitialAd = new InterstitialAd(this);
                if (dataHandler.get("manognaapps_showInterstitialAd") != null) {
                    if (dataHandler.get("manognaapps_showInterstitialAd").equals("true")) {
                        interstitialAd = dataHandler.getInterstitial("mInterstitialAd");
                        if (interstitialAd != null) {
                            if (interstitialAd.isLoaded()) {
                                interstitialAd.show();
                            }
                        }

                    }
                }
            }
        }*/
    private void saveImage() {
        Bitmap b = Bitmap.createBitmap(mContentRootView.getDrawingCache());
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);
        categoriesActivity.updateSavedBitmap(resizedBitmap);
//        MyInterstitialAdView.getInstance().activitiesAD(StickerEditActivity.this);

       /* File myfolder = new File(Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name));
        //getResources().getString(R.string.app_name)
        if (!myfolder.exists())
            if (!myfolder.mkdir()) {
                Toast.makeText(this, myfolder + " can't be created.", Toast.LENGTH_SHORT).show();
            }
        String fileName = getToken(5) + getDateTime() + ".png";
        File file = new File(myfolder, fileName);
        Toast.makeText(SetSuitActivity.this, myfolder.toString(),
                Toast.LENGTH_SHORT).show();
        try {
            FileOutputStream out = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        onBackPressed();
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getToken(int length) {
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return token.toString();
    }

    public static Bitmap createBitmap(Bitmap bitmap, float f) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /* private void openAlertDialog(String type) {
         stickerBuilder = new AlertDialog.Builder(this);
         LayoutInflater inflater = (this).getLayoutInflater();
         View dialogView = inflater.inflate(R.layout.recyecler_items_layout, null);
         switch (type) {
             case "hair":
                 mResources = new int[]{
                         R.drawable.hair_1,
                         R.drawable.hair_2,
                         R.drawable.hair_3,
                         R.drawable.hair_4,
                         R.drawable.more
                 };
                 break;
             case "mustache":
                 mResources = new int[]{
                         R.drawable.mustache_a,
                         R.drawable.mustache_b,
                         R.drawable.mustache_c,
                         R.drawable.mustache_d,
                         R.drawable.more
                 };
                 break;
             case "beard":
                 mResources = new int[]{
                         R.drawable.beard_a,
                         R.drawable.beard_b,
                         R.drawable.beard_c,
                         R.drawable.beard_d,
                         R.drawable.more
                 };
                 break;
             case "eyebrow":
                 mResources = new int[]{
                         R.drawable.eye_brows_1,
                         R.drawable.eye_brows_2,
                         R.drawable.eye_brows_3,
                         R.drawable.eye_brows_4,
                         R.drawable.more
                 };
                 break;
             case "eyeball":
                 mResources = new int[]{
                         R.drawable.eyeball_a,
                         R.drawable.eyeball_b,
                         R.drawable.eyeball_c,
                         R.drawable.eyeball_d,
                         R.drawable.more
                 };
                 break;
             case "glasses":
                 mResources = new int[]{
                         R.drawable.glass_2,
                         R.drawable.glass_3,
                         R.drawable.glass_4,
                         R.drawable.glass_5,
                         R.drawable.more
                 };
                 break;
             case "cap":
                 mResources = new int[]{
                         R.drawable.cap_1,
                         R.drawable.cap_2,
                         R.drawable.cap_3,
                         R.drawable.cap_4,
                         R.drawable.more
                 };
                 break;
             case "stoles":
                 mResources = new int[]{
                         R.drawable.stoles_a,
                         R.drawable.stoles_b,
                         R.drawable.stoles_c,
                         R.drawable.stoles_d,
                         R.drawable.more
                 };
                 break;
             case "tie":
                 mResources = new int[]{
                         R.drawable.tie_a,
                         R.drawable.tie_b,
                         R.drawable.tie_c,
                         R.drawable.tie_d,
                         R.drawable.more
                     };
                 break;
             case "chain":
                 mResources = new int[]{
                         R.drawable.chain_a,
                         R.drawable.chain_b,
                         R.drawable.chain_c,
                         R.drawable.chain_d,
                         R.drawable.more
                       };
                 break;
             case "earrings":
                 mResources = new int[]{
                         R.drawable.earrings_a,
                         R.drawable.earrings_b,
                         R.drawable.earrings_c,
                         R.drawable.earrings_d,
                         R.drawable.more
                      };
                 break;
             case "sixPack":
                 mResources = new int[]{
                         R.drawable.sixpack_a,
                         R.drawable.sixpack_b,
                         R.drawable.sixpack_c,
                         R.drawable.sixpack_d,
                         R.drawable.more
                               };
                 break;
             case "eightPack":
                 mResources = new int[]{
                         R.drawable.eightpack_a,
                         R.drawable.eightpack_b,
                         R.drawable.eightpack_c,
                         R.drawable.eightpack_d,
                         R.drawable.more
                                  };
                 break;
             case "chest":
                 mResources = new int[]{
                         R.drawable.chest_a,
                         R.drawable.chest_b,
                         R.drawable.chest_c,
                         R.drawable.chest_d,
                         R.drawable.more
                                     };
                 break;
             case "tattoos":
                 mResources = new int[]{
                         R.drawable.tattoos_a,
                         R.drawable.tattoos_b,
                         R.drawable.tattoos_c,
                         R.drawable.tattoos_d,
                         R.drawable.more
                              };
                 break;


         }
         RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.items_recycler);
         ItemsAdaper itemsAdaper = new ItemsAdaper(this, mResources);
         LinearLayoutManager mLinearLayout = new GridLayoutManager(this, 3);
         recyclerView.setLayoutManager(mLinearLayout);
         recyclerView.setAdapter(itemsAdaper);
         stickerBuilder.setView(dialogView);
 //        stickerBuilder.create();
         OptionDialog = stickerBuilder.create();
         OptionDialog.show();
     }*/
    public void updateStickerAdapter() {
        // stickerPreviewAdapter = new StickersPreviewAdapter(StickerEditActivity.this,mResources);
        //stickersPrevRecycler.setAdapter(stickerPreviewAdapter);
    }

    private void setBottomLayout(String category) {
        if (category.equals("menStyles")) {
            menStyleLayout.setVisibility(View.VISIBLE);
            accessoriesLayout.setVisibility(View.GONE);
            makeOverLayout.setVisibility(View.GONE);
            newStickersLayout.setVisibility(View.GONE);
            setStickerPrevAdapter("hair");
            if (ChangeBackgroundData.getChangeBackgroundData() != null)
                ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,hairStyleImg, hairStyleTxt);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "menStyles");
            bundle.putString("Activity", "StickerEditActivity");
            if (mFirebaseAnalytics != null)
                mFirebaseAnalytics.logEvent("manStylesCategory", bundle);
        } else if (category.equals("accessories")) {
            menStyleLayout.setVisibility(View.GONE);
            accessoriesLayout.setVisibility(View.VISIBLE);
            makeOverLayout.setVisibility(View.GONE);
            newStickersLayout.setVisibility(View.GONE);
            setStickerPrevAdapter("glasses");
            if (ChangeBackgroundData.getChangeBackgroundData() != null)
                ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,glassesImg, glassesTxt);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "accessories");
            bundle.putString("Activity", "StickerEditActivity");
            if (mFirebaseAnalytics != null)
                mFirebaseAnalytics.logEvent("accessoriesCategory", bundle);
        } else if (category.equals("makeOver")) {
            menStyleLayout.setVisibility(View.GONE);
            accessoriesLayout.setVisibility(View.GONE);
           // accessoriesLayout.setVisibility(View.GONE);
            newStickersLayout.setVisibility(View.GONE);
            makeOverLayout.setVisibility(View.VISIBLE);
            setStickerPrevAdapter("packs");
            if (ChangeBackgroundData.getChangeBackgroundData() != null)
                ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,sixPackImg, sixPackTxt);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "makeOver");
            bundle.putString("Activity", "StickerEditActivity");
            if (mFirebaseAnalytics != null)
                mFirebaseAnalytics.logEvent("makeOverCategory", bundle);
        }else if (category.equals("stickers")) {
            menStyleLayout.setVisibility(View.GONE);
            accessoriesLayout.setVisibility(View.GONE);
            newStickersLayout.setVisibility(View.VISIBLE);
            makeOverLayout.setVisibility(View.GONE);
            setStickerPrevAdapter("lovekers");
            if (ChangeBackgroundData.getChangeBackgroundData() != null)
                ChangeBackgroundData.getChangeBackgroundData().setColorFilter(StickerEditActivity.this,loveStickImg, loveStickTxt);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "stickers");
            bundle.putString("Activity", "StickerEditActivity");
            if (mFirebaseAnalytics != null)
                mFirebaseAnalytics.logEvent("StickerCategory", bundle);
        }
    }

    public void setCurrentEdit(StickerView stickerView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        mCurrentView = stickerView;
        stickerView.setInEdit(true);
    }

    private void initVars() {
        rotateLinearBtn = findViewById(R.id.rotate_btn_linear);
        refreshLinearBtn = findViewById(R.id.refresh_btn_linear);
        doneLinearBtn = findViewById(R.id.done_btn_linear);
        saveLinearBtn = findViewById(R.id.save_btn_linear);
        backLinearBtn = findViewById(R.id.back_btn_linear);

        selectedImage = (ImageView) findViewById(R.id.selected_image);
        stickersRecyclerView = (RecyclerView) findViewById(R.id.stickers_preview_recycler);
        stickersPrevRecycler = findViewById(R.id.stickers_preview_recycler);
        downSuitsPreviewRecycler = findViewById(R.id.down_suits_preview_recycler);
        menStyleLayout = findViewById(R.id.men_style_bottom_layout);
        accessoriesLayout = findViewById(R.id.accessories_bottom_layout);
        makeOverLayout = findViewById(R.id.makeover_bottom_layout);
        newStickersLayout = findViewById(R.id.newsticker_bottom_layout);

        hairStyle = findViewById(R.id.linear_hair_style);
        mustache = findViewById(R.id.linear_mustache);
        beard = findViewById(R.id.linear_beard);
        eyeBrows = findViewById(R.id.linear_eyebrows);
        eyeBall = findViewById(R.id.linear_eyeballs);

        glasses = findViewById(R.id.linear_sunglasses);
        cap = findViewById(R.id.linear_cap);
        stoles = findViewById(R.id.linear_stoles);
        tie = findViewById(R.id.linear_tie);
        chain = findViewById(R.id.linear_chain);
        earrings = findViewById(R.id.linear_earring);

        sixPack = findViewById(R.id.linear_six_pack);
        eightPack = findViewById(R.id.linear_eight_packs);
        chest = findViewById(R.id.linear_chest);
        tattoos = findViewById(R.id.linear_tattoos);

        loveStick = findViewById(R.id.loveStick);
        wishStick = findViewById(R.id.wishStick);
        greetStick = findViewById(R.id.greetStick);
        expStick = findViewById(R.id.expStick);

        loveStickTxt = findViewById(R.id.loveStickTxt);
        wishStickTxt = findViewById(R.id.wishStickTxt);
        greetStickTxt = findViewById(R.id.greetStickTxt);
        expStickTxt = findViewById(R.id.expStickTxt);

        loveStickImg = findViewById(R.id.loveStickImg);
        wishStickImg = findViewById(R.id.wishStickImg);
        greetStickImg = findViewById(R.id.greetStickImg);
        expStickImg = findViewById(R.id.expStickImg);


        hairStyleTxt = findViewById(R.id.hair_style_btn_txt);
        mustacheTxt = findViewById(R.id.mustache_btn_txt);
        beardTxt = findViewById(R.id.beard_btn_txt);
        eyeBrowsTxt = findViewById(R.id.eyebrows_btn_txt);
        eyeBallTxt = findViewById(R.id.eyeballs_btn_txt);
        glassesTxt = findViewById(R.id.sunglasses_btn_txt);
        capTxt = findViewById(R.id.cap_btn_txt);
        stolesTxt = findViewById(R.id.stoles_btn_txt);
        tieTxt = findViewById(R.id.tie_btn_txt);
        chainTxt = findViewById(R.id.chain_btn_txt);
        earringsTxt = findViewById(R.id.earring_btn_txt);
        sixPackTxt = findViewById(R.id.six_pack_btn_txt);
        eightPackTxt = findViewById(R.id.eight_packs_btn_txt);
        chestTxt = findViewById(R.id.chest_btn_txt);
        tattoosTxt = findViewById(R.id.tattoos_btn_txt);

        hairStyleImg = findViewById(R.id.hair_style_button_img);
        mustacheImg = findViewById(R.id.mustache_button_img);
        beardImg = findViewById(R.id.beard_button_img);
        eyeBrowsImg = findViewById(R.id.eyebrows_button_img);
        eyeBallImg = findViewById(R.id.eyeballs_button_img);
        glassesImg = findViewById(R.id.sunglasses_button_img);
        capImg = findViewById(R.id.cap_button_img);
        stolesImg = findViewById(R.id.stoles_button_img);
        tieImg = findViewById(R.id.tie_button_img);
        chainImg = findViewById(R.id.chain_button_img);
        earringsImg = findViewById(R.id.earring_button_img);

        sixPackImg = findViewById(R.id.six_pack_button_img);
        chestImg = findViewById(R.id.chest_button_img);
        eightPackImg = findViewById(R.id.eight_packs_button_img);
        tattoosImg = findViewById(R.id.tattoos_button_img);

        mContentRootView = (RelativeLayout) findViewById(R.id.rl_content_root);

        mContentRootView.setDrawingCacheEnabled(true);
        mContentRootView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mContentRootView.layout(0, 0, mContentRootView.getMeasuredWidth(), mContentRootView.getMeasuredHeight());
        mContentRootView.buildDrawingCache(true);
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

    public ArrayList<String> getImagesFromDevice(String directoryPath) {
        final ArrayList<String> tempAudioList = new ArrayList<>();
        File directory1 = new File(directoryPath + "/");
        File[] files = directory1.listFiles();
        if (isDirectoryNotEmpty(directoryPath)) {
            for (int i = 0; i < files.length; i++) {
                tempAudioList.add(files[i].getAbsolutePath());
                Log.e("Files", "FileName:" + files[i].getAbsolutePath());
            }
            return tempAudioList;
        } else {
            return null;
        }
    }

    public void setStickerPrevAdapter(String type) {
        imagesFromStorage = null;
        String PATH = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/" + getResources().getString(R.string.app_name) + "/"
                :Environment.getExternalStorageDirectory().toString()+ "/" +getResources().getString(R.string.app_name) + "/" ;
       // String PATH = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/";
        switch (type) {
            case "hair":
                subCategory = "hair";
                if (isDirectoryNotEmpty(PATH + "hair")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "hair");
                } else {
                    mResources = new int[]{
                            R.drawable.hair_one,
                            R.drawable.hair_two,
                            R.drawable.hair_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "mustache":
                subCategory = "mustache";
                if (isDirectoryNotEmpty(PATH + "mustache")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "mustache");
                } else {
                    mResources = new int[]{
                            R.drawable.mustache_one,
                            R.drawable.mustache_two,
                            R.drawable.mustache_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "beard":
                subCategory = "beard";
                if (isDirectoryNotEmpty(PATH + "beard")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "beard");
                } else {
                    subCategory = "beard";
                    mResources = new int[]{
                            R.drawable.beard_one,
                            R.drawable.beard_two,
                            R.drawable.beard_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "eyebrows":
                subCategory = "eyebrows";
                if (isDirectoryNotEmpty(PATH + "eyebrows")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "eyebrows");
                } else {
                    mResources = new int[]{
                            R.drawable.ebrows_one,
                            R.drawable.ebrows_two,
                            R.drawable.ebrows_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "eye":
                subCategory = "eye";
                if (isDirectoryNotEmpty(PATH + "eye")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "eye");
                } else {
                    mResources = new int[]{
                            R.drawable.eye_one,
                            R.drawable.eye_two,
                            R.drawable.eye_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "glasses":
                subCategory = "glasses";
                if (isDirectoryNotEmpty(PATH + "glasses")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "glasses");
                } else {
                    mResources = new int[]{
                            R.drawable.glass_one,
                            R.drawable.glass_two,
                            R.drawable.glass_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "caps":
                subCategory = "caps";
                if (isDirectoryNotEmpty(PATH + "caps")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "caps");
                } else {
                    mResources = new int[]{
                            R.drawable.cap_one,
                            R.drawable.cap_two,
                            R.drawable.cap_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "stole":
                subCategory = "stole";
                if (isDirectoryNotEmpty(PATH + "stole")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "stole");
                } else {
                    mResources = new int[]{
                            R.drawable.stole_one,
                            R.drawable.stole_two,
                            R.drawable.stole_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "tie":
                subCategory = "tie";
                if (isDirectoryNotEmpty(PATH + "tie")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "tie");
                } else {
                    mResources = new int[]{
                            R.drawable.tie_one,
                            R.drawable.bow_tie_one,
                            R.drawable.tie_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "chain":
                subCategory = "chain";
                if (isDirectoryNotEmpty(PATH + "chain")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "chain");
                } else {
                    mResources = new int[]{
                            R.drawable.chain_one,
                            R.drawable.chain_two,
                            R.drawable.chain_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "ear":
                subCategory = "ear";
                if (isDirectoryNotEmpty(PATH + "ear")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "ear");
                } else {
                    mResources = new int[]{
                            R.drawable.ear_one,
                            R.drawable.ear_two,
                            R.drawable.ear_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "packs":
                subCategory = "packs";
                if (isDirectoryNotEmpty(PATH + "packs")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "packs");
                } else {
                    mResources = new int[]{
                            R.drawable.pack_one,
                            R.drawable.pack_two,
                            R.drawable.pack_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "epack":
                subCategory = "epack";
                if (isDirectoryNotEmpty(PATH + "epack")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "epack");
                } else {
                    mResources = new int[]{
                            R.drawable.eight_one,
                            R.drawable.eight_two,
                            R.drawable.eight_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "chest":
                subCategory = "chest";
                if (isDirectoryNotEmpty(PATH + "chest")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "chest");
                } else {
                    mResources = new int[]{
                            R.drawable.chest_one,
                            R.drawable.chest_two,
                            R.drawable.chest_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "tattoo":
                subCategory = "tattoo";
                if (isDirectoryNotEmpty(PATH + "tattoo")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "tattoo");
                } else {
                    mResources = new int[]{
                            R.drawable.tatto_one,
                            R.drawable.tatto_two,
                            R.drawable.tatto_three,
                            R.drawable.more_01,
                    };
                }
                break;
            case "lovekers":
                subCategory = "lovekers";
                if (isDirectoryNotEmpty(PATH + "lovekers")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "lovekers");
                } else {
                    mResources = new int[]{
                            R.drawable.love_stickers_1,
                            R.drawable.love_stickers_2,
                            R.drawable.love_stickers_3,
                            R.drawable.more_01,
                    };
                }
                break;
            case "wishkers":
                subCategory = "wishkers";
                if (isDirectoryNotEmpty(PATH + "wishkers")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "wishkers");
                } else {
                    mResources = new int[]{
                            R.drawable.stickers_wishe_1,
                            R.drawable.stickers_wishe_2,
                            R.drawable.stickers_wishe_3,
                            R.drawable.more_01,
                    };
                }
                break;
            case "greetkers":
                subCategory = "greetkers";
                if (isDirectoryNotEmpty(PATH + "greetkers")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "greetkers");
                } else {
                    mResources = new int[]{
                            R.drawable.stickers_greet_1,
                            R.drawable.stickers_greet_2,
                            R.drawable.stickers_greet_3,
                            R.drawable.more_01,
                    };
                }
                break;
            case "expkers":
                subCategory = "expkers";
                if (isDirectoryNotEmpty(PATH + "expkers")) {
                    imagesFromStorage = getImagesFromDevice(PATH + "expkers");
                } else {
                    mResources = new int[]{
                            R.drawable.expressions_stickers_1,
                            R.drawable.expressions_stickers_2,
                            R.drawable.expressions_stickers_3,
                            R.drawable.more_01,
                    };
                }
                break;
        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        if (imagesFromStorage != null) {
            downSuitsPreviewRecycler.setVisibility(VISIBLE);
            stickersPrevRecycler.setVisibility(GONE);
            downSuitsPreviewRecycler.setLayoutManager(layoutManager);
            suitsDownPreviewAdapter = new SuitsDownPreviewAdapter(StickerEditActivity.this, imagesFromStorage, subCategory, categoryType);
            downSuitsPreviewRecycler.setAdapter(suitsDownPreviewAdapter);
        } else {
            stickersPrevRecycler.setVisibility(VISIBLE);
            downSuitsPreviewRecycler.setVisibility(GONE);
            stickersPrevRecycler.setLayoutManager(layoutManager);
            stickerPreviewAdapter = new StickersPreviewAdapter(StickerEditActivity.this, mResources, subCategory);
            stickersPrevRecycler.setAdapter(stickerPreviewAdapter);
        }
    }

    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }

    public void addStickerView(String mResource) {
        final StickerView stickerView = new StickerView(this);
        Bitmap myBitmap = BitmapFactory.decodeFile(mResource);
        stickerView.setBitmap(myBitmap, "path");
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews.remove(stickerView);
                mContentRootView.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                mCurrentView.setInEdit(false);
                mCurrentView = stickerView;
                mCurrentView.setInEdit(true);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(stickerView, lp);
        mViews.add(stickerView);
        setCurrentEdit(stickerView);
    }

    public void addStickerView(int mResource) {
        final StickerView stickerView = new StickerView(this);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mResource, options); //out of memory
        stickerView.setBitmap(bitmap, "int");
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                if(mViews!=null){
                    mViews.remove(stickerView);
                }
                mContentRootView.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                if (mCurrentEditTextView != null) {
                    mCurrentEditTextView.setInEdit(false);
                }
                mCurrentView.setInEdit(false);
                mCurrentView = stickerView;
                mCurrentView.setInEdit(true);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentRootView.addView(stickerView, lp);
        if(mViews!=null){
            mViews.add(stickerView);
        }
        setCurrentEdit(stickerView);
    }
}