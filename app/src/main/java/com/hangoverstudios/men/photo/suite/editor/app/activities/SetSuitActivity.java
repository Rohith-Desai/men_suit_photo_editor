package com.hangoverstudios.men.photo.suite.editor.app.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hangoverstudios.men.photo.suite.editor.app.ads.MyInterstitialAdView;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.SuitAdapter;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.SuitsDownPreviewAdapter;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.SuitsPreviewAdapter;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;
import com.hangoverstudios.men.photo.suite.editor.app.views.SuitDrawView;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.SplashScreen.mFirebaseAnalytics;
//import static com.hangoverstudios.men.photo.suite.editor.app.activities.ImageCropActivity.ChangeBackgroundData.getChangeBackgroundData();

public class SetSuitActivity extends AppCompatActivity {

    private LinearLayout layoutCanvas, backToCategory, saveSuitImage, horizontalFlip;
    LinearLayout suits, blazers, traditional, formal, jacket;
    LinearLayout attitude, fashion, macho, love, border;
    LinearLayout police, military, doctor, mixed, body;
    private TextView suitsTxt, blazersTxt, traditionalTxt, formalTxt, jacketTxt;
    private TextView attitudeTxt, fashionTxt, machoTxt, loveTxt, borderTxt;
    private TextView policeTxt, militaryTxt, doctorTxt, mixedTxt, bodyTxt;
    private ImageView suitsImg, blazersImg, traditionalImg, formalImg, jacketImg;
    private ImageView attitudeImg, fashionImg, machoImg, loveImg, borderImg;
    private ImageView policeImg, militaryImg, doctorImg, mixedImg, bodyImg;
    View saveLayout;
    public int startPosition = 0;
    public static LinearLayout previousBtn, nextBtn;
    private RelativeLayout seekBarLayout;
    static Canvas CANVAS;
    public Dialog dialog1;
    private Bitmap resultingImage;
    private boolean crop;
    Bitmap bp;
    Canvas bitmapCanvas;
    Canvas canvas;
    private static final Random random = new Random();
    public static SetSuitActivity setSuitActivity;
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";
    public int[] mResources;
    public ArrayList<String> lResources;
    private RecyclerView suitsRecycler, suitPrevRecycler, downSuitsPreviewRecycler;
    private SuitAdapter suitAdapter;
    SuitsPreviewAdapter suitsPreviewAdapter;
    SuitsDownPreviewAdapter suitsDownPreviewAdapter;
    public ImageView suitsImage;
    public static int settedImage = 0;
    int count = 0;
    private Bitmap catSavedBitmap;
    Bitmap bm, bmp;
    private String CATEGORY = null;
    public static SeekBar suitZoomSeekBar;
    SuitDrawView mSuitDrawView;
    double mDensity;
    private AdView adViewBanner8;
    private FrameLayout adViewContainer;
    int viewWidth;
    int viewHeight;
    int bmWidth;
    int bmHeight;
    String SUB_CAT_TYPE = "";
    int actionBarHeight;
    int bottombarHeight;
    double bmRatio;
    double viewRatio;
    private int PROGRESS = 180;
    public static Queue<String> localEventQueue = new LinkedList<>();
    public int getPROGRESS() {
        return PROGRESS;
    }
    public void setPROGRESS(int PROGRESS) {
        this.PROGRESS = PROGRESS;
    }
    ArrayList<String> dResouce = null;
    boolean isDownload = false;
    TextView textView;
    private CategoriesActivity categoriesActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra("category")) {
            CATEGORY = getIntent().getStringExtra("category");
        }
        if (CATEGORY.equals("dresses") || CATEGORY.equals("uniforms")) {
            if (CATEGORY.equals("dresses")) {
                SUB_CAT_TYPE = "suit";
            } else {
                SUB_CAT_TYPE = "police";
            }
            setContentView(R.layout.activity_set_suit);
            //adViewContainer = findViewById(R.id.suit_1_banner_ad);
        } else {
            setPROGRESS(250);
            SUB_CAT_TYPE = "attitude";
            setContentView(R.layout.activity_set_suit_2);
           // adViewContainer = findViewById(R.id.suit_2_banner_ad);
        }
        categoriesActivity = ChangeBackgroundData.getChangeBackgroundData().getCategoriesActivity();
        /*adViewContainer.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adViewBanner8, adViewContainer, SetSuitActivity.this);
            }
        });*/
        setSuitActivity = this;
        downSuitsPreviewRecycler = (RecyclerView) findViewById(R.id.down_suits_preview_recycler);
        suitPrevRecycler = (RecyclerView) findViewById(R.id.suits_preview_recycler);
        initVars();
        clickables();
        mDensity = getResources().getDisplayMetrics().density;
        actionBarHeight = (int) (110 * mDensity);
        bottombarHeight = (int) (60 * mDensity);
        viewWidth = getResources().getDisplayMetrics().widthPixels;
        viewHeight = getResources().getDisplayMetrics().heightPixels - actionBarHeight - bottombarHeight;
        viewRatio = (double) viewHeight / (double) viewWidth;
        if(categoriesActivity!=null){
            catSavedBitmap = categoriesActivity.getSavedBitmap();
        }
        if (catSavedBitmap != null) {
            bmRatio = (double) catSavedBitmap.getHeight() / (double) catSavedBitmap.getWidth();
            if (bmRatio < viewRatio) {
                bmWidth = viewWidth;
                bmHeight = (int) (((double) viewWidth) * ((double) (catSavedBitmap.getHeight()) / (double) (catSavedBitmap.getWidth())));
            } else {
                bmHeight = viewHeight;
                bmWidth = (int) (((double) viewHeight) * ((double) (catSavedBitmap.getWidth()) / (double) (catSavedBitmap.getHeight())));
            }
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            mSuitDrawView = new SuitDrawView(this, catSavedBitmap, displayMetrics.widthPixels, displayMetrics.heightPixels, viewWidth, viewHeight);
            mSuitDrawView.setLayoutParams(new ViewGroup.LayoutParams(viewWidth, viewHeight));
            layoutCanvas.addView(mSuitDrawView);
            mSuitDrawView.switchMode(SuitDrawView.MOVING_MODE);
            if(ChangeBackgroundData.getChangeBackgroundData()!=null){
                ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this, suitsImg, suitsTxt);
            }
        }
    }

    private void initVars() {
        LinearLayout bottomSuits = findViewById(R.id.bottom_bar_suits);
        LinearLayout bottomFrames = findViewById(R.id.bottom_bar_frames);
        LinearLayout bottomUniforms = findViewById(R.id.bottom_bar_uniforms);
        suitZoomSeekBar = findViewById(R.id.suit_zoom_seekbar);
        suits = findViewById(R.id.linear_suit);
        blazers = findViewById(R.id.linear_blazer);
        traditional = findViewById(R.id.linear_traditional);
        formal = findViewById(R.id.linear_formal);
        jacket = findViewById(R.id.linear_jacket);
        attitude = findViewById(R.id.linear_attitude);
        fashion = findViewById(R.id.linear_fashion);
        macho = findViewById(R.id.linear_macho);
        love = findViewById(R.id.linear_love);
        border = findViewById(R.id.linear_border);
        police = findViewById(R.id.linear_police);
        military = findViewById(R.id.linear_military);
        doctor = findViewById(R.id.linear_doctor);
        mixed = findViewById(R.id.linear_mixed);
        body = findViewById(R.id.linear_body);
        suitsImg = findViewById(R.id.suit_button_img);
        blazersImg = findViewById(R.id.blazer_button_img);
        traditionalImg = findViewById(R.id.traditional_button_img);
        formalImg = findViewById(R.id.formal_button_img);
        jacketImg = findViewById(R.id.jacket_button_img);
        attitudeImg = findViewById(R.id.attitude_button_img);
        fashionImg = findViewById(R.id.fashion_button_img);
        machoImg = findViewById(R.id.macho_button_img);
        loveImg = findViewById(R.id.love_button_img);
        borderImg = findViewById(R.id.border_button_img);
        policeImg = findViewById(R.id.police_button_img);
        militaryImg = findViewById(R.id.military_button_img);
        doctorImg = findViewById(R.id.doctor_button_img);
        mixedImg = findViewById(R.id.mixed_button_img);
        bodyImg = findViewById(R.id.body_button_img);
        suitsTxt = findViewById(R.id.suit_btn_txt);
        blazersTxt = findViewById(R.id.blazer_btn_txt);
        traditionalTxt = findViewById(R.id.traditional_btn_txt);
        formalTxt = findViewById(R.id.formal_btn_txt);
        jacketTxt = findViewById(R.id.jacket_btn_txt);
        attitudeTxt = findViewById(R.id.attitude_btn_txt);
        fashionTxt = findViewById(R.id.fashion_btn_txt);
        machoTxt = findViewById(R.id.macho_btn_txt);
        loveTxt = findViewById(R.id.love_btn_txt);
        borderTxt = findViewById(R.id.border_btn_txt);
        policeTxt = findViewById(R.id.police_btn_txt);
        militaryTxt = findViewById(R.id.military_btn_txt);
        doctorTxt = findViewById(R.id.doctor_btn_txt);
        mixedTxt = findViewById(R.id.mixed_btn_txt);
        bodyTxt = findViewById(R.id.body_btn_txt);
        suitsImage = (ImageView) findViewById(R.id.suits_image);
        previousBtn = findViewById(R.id.previous_btn);
        previousBtn.setVisibility(GONE);
        nextBtn = findViewById(R.id.next_btn);
        dialog1 = new Dialog(this);
        saveLayout = (View) findViewById(R.id.top);
        saveLayout.setDrawingCacheEnabled(true);
        saveLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        saveLayout.layout(0, 0, saveLayout.getMeasuredWidth(), saveLayout.getMeasuredHeight());
        saveLayout.buildDrawingCache(true);
        layoutCanvas = (LinearLayout) findViewById(R.id.layout_canvas);
        suitsRecycler = (RecyclerView) findViewById(R.id.suits_recycler);
        suitAdapter = new SuitAdapter(this, mResources);
        seekBarLayout = (RelativeLayout) findViewById(R.id.seekbar_layout);
        backToCategory = findViewById(R.id.back_to_category);
        horizontalFlip = findViewById(R.id.flip);
        saveSuitImage = findViewById(R.id.save_done);
        if (catSavedBitmap != null)
            bm = catSavedBitmap;
        textView = findViewById(R.id.head_txt);
        if (getIntent().hasExtra("category")) {
            if (CATEGORY.equals("dresses")) {
                bottomSuits.setVisibility(VISIBLE);
                bottomFrames.setVisibility(GONE);
                bottomUniforms.setVisibility(GONE);
                mResources = new int[]{
                        R.drawable.suit_one, R.drawable.suit_two, R.drawable.suit_three, R.drawable.more_01
                };
                textView.setText("DRESSES");
                setSuitAdapter("suit");
                SUB_CAT_TYPE = "suit";
                if(ChangeBackgroundData.getChangeBackgroundData()!=null){
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,suitsImg, suitsTxt);
                }
                updatePrevNext(0);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "dresses");
                bundle.putString("Activity", "SetSuitActivity");
                if (mFirebaseAnalytics != null)
                    mFirebaseAnalytics.logEvent("dressesCategory", bundle);
            } else if (CATEGORY.equals("frames")) {
                bottomFrames.setVisibility(VISIBLE);
                bottomSuits.setVisibility(GONE);
                bottomUniforms.setVisibility(GONE);
                mResources = new int[]{
                        R.drawable.attitude_one, R.drawable.attitude_two, R.drawable.attitude_three, R.drawable.more_01
                };
                setSuitAdapter("attitude");
                SUB_CAT_TYPE = "attitude";
                textView.setText("FRAMES");
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,attitudeImg, attitudeTxt);
                }
                updatePrevNext(0);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "frames");
                bundle.putString("Activity", "SetSuitActivity");
                if (mFirebaseAnalytics != null)
                    mFirebaseAnalytics.logEvent("framesCategory", bundle);
            } else if (CATEGORY.equals("uniforms")) {
                bottomUniforms.setVisibility(VISIBLE);
                bottomSuits.setVisibility(GONE);
                bottomFrames.setVisibility(GONE);
                mResources = new int[]{
                        R.drawable.police_one, R.drawable.police_two, R.drawable.police_three, R.drawable.more_01
                };
                setSuitAdapter("police");
                textView.setText("UNIFORMS");
                SUB_CAT_TYPE = "police";
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,policeImg, policeTxt);
                }
                updatePrevNext(0);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "uniforms");
                bundle.putString("Activity", "SetSuitActivity");
                if (mFirebaseAnalytics != null)
                    mFirebaseAnalytics.logEvent("uniformsCategory", bundle);
            }
        }
    }

    public void updateSuitAdapter(String type, ArrayList<String> rsources) {
        if (rsources.size() > 0) {
            isDownload = true;
            dResouce = null;
            dResouce = rsources;
            suitPrevRecycler.setVisibility(GONE);
            downSuitsPreviewRecycler.setVisibility(VISIBLE);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(SetSuitActivity.this, LinearLayoutManager.HORIZONTAL, false);
            downSuitsPreviewRecycler.setLayoutManager(layoutManager);
            suitsDownPreviewAdapter = new SuitsDownPreviewAdapter(SetSuitActivity.this, rsources, type, CATEGORY);
            downSuitsPreviewRecycler.setAdapter(suitsDownPreviewAdapter);
            Bitmap myBitmap = BitmapFactory.decodeFile(rsources.get(0));
            suitsImage.setImageBitmap(myBitmap);
        }
    }

    private void setSuitAdapter(String type) {
        lResources = getImagesFromDevice(type);
        if (lResources != null && lResources.size() > 0) {
            isDownload = true;
            updateSuitAdapter(type, lResources);
        } else {
            suitPrevRecycler.setVisibility(VISIBLE);
            downSuitsPreviewRecycler.setVisibility(GONE);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            suitPrevRecycler.setLayoutManager(layoutManager);
            suitsPreviewAdapter = new SuitsPreviewAdapter(SetSuitActivity.this, mResources, type, CATEGORY);
            suitPrevRecycler.setAdapter(suitsPreviewAdapter);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mResources[startPosition]);
            suitsImage.setImageBitmap(bitmap);
            isDownload = false;
            updatePrevNext(0);
        }
    }

    public void setSuitsZoomBar(final int startPosition) {
        Log.v("test", "position : " + startPosition);
        suitZoomSeekBar.setMax(250);
        suitZoomSeekBar.setProgress(getPROGRESS());
        ArrayList<String> allImages = getImagesFromDevice(SUB_CAT_TYPE);
        if (allImages != null && allImages.size() > 0) {
            if (startPosition < allImages.size() - 1) {
                bmp = BitmapFactory.decodeFile(allImages.get(startPosition));
            } else if (startPosition == allImages.size() - 1) {
                bmp = BitmapFactory.decodeFile(allImages.get(startPosition));
            }
        } else {
            if (startPosition < mResources.length - 1){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                bmp = BitmapFactory.decodeResource(getResources(), mResources[startPosition], options);
            }
        }
        suitsImage.setImageBitmap(bmp);
        if (CATEGORY.equals("frames")) {
            setPROGRESS(265);
        } else {
            if (getPROGRESS() > 0) {
                float scale = getPROGRESS() / 100.0f;
                suitsImage.setScaleX(scale);
                suitsImage.setScaleY(scale);
                suitsImage.getLayoutParams().width = 402;
                suitsImage.getLayoutParams().height = 522;
            }
        }
        suitsImage.setImageBitmap(bmp);
        setPROGRESS(getPROGRESS());
        suitZoomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (getPROGRESS() > 0) {
                    float scale = getPROGRESS() / 100.0f;
                    suitsImage.setScaleX(scale);
                    suitsImage.setScaleY(scale);
                    suitsImage.getLayoutParams().width = 402;
                    suitsImage.getLayoutParams().height = 522;
                }
                suitsImage.setImageBitmap(bmp);
                if (progress > 0 && progress <= 50) {
                    setPROGRESS(50);
                } else {
                    setPROGRESS(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void clickables() {
        suits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> tempList = getImagesFromDevice("suit");
                SUB_CAT_TYPE = "suit";
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.suit_one, R.drawable.suit_two, R.drawable.suit_three, R.drawable.more_01
                };
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("suit", tempList);
                    }
                } else {
                    setSuitAdapter("suit");
                }
                updatePrevNext(startPosition);
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,suitsImg, suitsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,blazersImg, blazersTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,traditionalImg, traditionalTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,formalImg, formalTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,jacketImg, jacketTxt);
                }

            }
        });
        blazers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> tempList = getImagesFromDevice("blazer");
                SUB_CAT_TYPE = "blazer";
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.blazer_one, R.drawable.blazer_two, R.drawable.blazer_three, R.drawable.more_01
                };
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("blazer", tempList);
                    }
                } else {
                    setSuitAdapter("blazer");
                }
                updatePrevNext(startPosition);
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,suitsImg, suitsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,blazersImg, blazersTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,traditionalImg, traditionalTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,formalImg, formalTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,jacketImg, jacketTxt);
                }
            }
        });
        traditional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                ArrayList<String> tempList = getImagesFromDevice("tradition");
                mResources = new int[]{
                        R.drawable.trad_one, R.drawable.trad_two, R.drawable.trad_three, R.drawable.more_01
                };
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("tradition", tempList);
                    }
                } else {
                    setSuitAdapter("tradition");
                }
                SUB_CAT_TYPE = "tradition";
                updatePrevNext(startPosition);
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,suitsImg, suitsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,blazersImg, blazersTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,traditionalImg, traditionalTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,formalImg, formalTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,jacketImg, jacketTxt);
                }

            }
        });
        formal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> tempList = getImagesFromDevice("formal");
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.formal_one, R.drawable.formal_two, R.drawable.formal_three, R.drawable.more_01
                };
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("formal", tempList);
                    }
                } else {
                    setSuitAdapter("formal");
                }
                SUB_CAT_TYPE = "formal";
                updatePrevNext(startPosition);

                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,suitsImg, suitsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,blazersImg, blazersTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,traditionalImg, traditionalTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,formalImg, formalTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,jacketImg, jacketTxt);
                }
            }
        });
        jacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.jacket_one, R.drawable.jacket_two, R.drawable.jacket_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("jacket");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("jacket", tempList);
                    }
                } else {
                    setSuitAdapter("jacket");
                }
                SUB_CAT_TYPE = "jacket";
                updatePrevNext(startPosition);
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,suitsImg, suitsTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,blazersImg, blazersTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,traditionalImg, traditionalTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,formalImg, formalTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,jacketImg, jacketTxt);
                }
            }
        });

        attitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.attitude_one, R.drawable.attitude_two, R.drawable.attitude_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("attitude");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("attitude", tempList);
                    }
                } else {
                    setSuitAdapter("attitude");
                }
                SUB_CAT_TYPE = "attitude";
                updatePrevNext(startPosition);
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,attitudeImg, attitudeTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,fashionImg, fashionTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,machoImg, machoTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,loveImg, loveTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,borderImg, borderTxt);
                }
            }
        });
        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.fashion_one, R.drawable.fashion_two, R.drawable.fashion_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("fashion");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("fashion", tempList);
                    }
                } else {
                    setSuitAdapter("fashion");
                }
                SUB_CAT_TYPE = "fashion";
                updatePrevNext(startPosition);

                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,attitudeImg, attitudeTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,fashionImg, fashionTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,machoImg, machoTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,loveImg, loveTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,borderImg, borderTxt);
                }
            }
        });
        macho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.macho_one, R.drawable.macho_two, R.drawable.macho_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("macho");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("macho", tempList);
                    }
                } else {
                    setSuitAdapter("macho");
                }
                SUB_CAT_TYPE = "macho";
                updatePrevNext(startPosition);

                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,attitudeImg, attitudeTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,fashionImg, fashionTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,machoImg, machoTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,loveImg, loveTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,borderImg, borderTxt);
                }
            }
        });
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.love_one, R.drawable.love_two, R.drawable.love_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("love");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("love", tempList);
                    }
                } else {
                    setSuitAdapter("love");
                }
                SUB_CAT_TYPE = "love";
                updatePrevNext(startPosition);

                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,attitudeImg, attitudeTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,fashionImg, fashionTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,machoImg, machoTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,loveImg, loveTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,borderImg, borderTxt);
                }
            }
        });
        border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.border_one, R.drawable.border_two, R.drawable.border_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("border");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("border", tempList);
                    }
                } else {
                    setSuitAdapter("border");
                }
                SUB_CAT_TYPE = "border";
                updatePrevNext(startPosition);

                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,attitudeImg, attitudeTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,fashionImg, fashionTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,machoImg, machoTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,loveImg, loveTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,borderImg, borderTxt);
                }
            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.police_one, R.drawable.police_two, R.drawable.police_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("police");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("police", tempList);
                    }
                } else {
                    setSuitAdapter("police");
                }
                SUB_CAT_TYPE = "police";
                updatePrevNext(startPosition);

                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,militaryImg, militaryTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,doctorImg, doctorTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,mixedImg, mixedTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,bodyImg, bodyTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,policeImg, policeTxt);
                }
            }
        });
        military.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.military_one, R.drawable.military_two, R.drawable.military_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("military");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("military", tempList);
                    }
                } else {
                    setSuitAdapter("military");
                }
                SUB_CAT_TYPE = "military";
                updatePrevNext(startPosition);
                if(ChangeBackgroundData.getChangeBackgroundData()!=null){
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,militaryImg, militaryTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,doctorImg, doctorTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,mixedImg, mixedTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,bodyImg, bodyTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,policeImg, policeTxt);
                }
            }
        });
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.doctor_one, R.drawable.doctor_two, R.drawable.doctor_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("doctor");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("doctor", tempList);
                    }
                } else {
                    setSuitAdapter("doctor");
                }
                SUB_CAT_TYPE = "doctor";
                updatePrevNext(startPosition);

                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,militaryImg, militaryTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,doctorImg, doctorTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,mixedImg, mixedTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,bodyImg, bodyTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,policeImg, policeTxt);
                }
            }
        });
        mixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.mixed_one, R.drawable.mixed_two, R.drawable.mixed_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("mixed");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("mixed", tempList);
                    }
                } else {
                    setSuitAdapter("mixed");
                }
                SUB_CAT_TYPE = "mixed";
                updatePrevNext(startPosition);

                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,militaryImg, militaryTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,doctorImg, doctorTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,mixedImg, mixedTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,bodyImg, bodyTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,policeImg, policeTxt);
                }
            }
        });
        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosition = 0;
                mResources = new int[]{
                        R.drawable.body_one, R.drawable.body_two, R.drawable.body_three, R.drawable.more_01
                };
                ArrayList<String> tempList = getImagesFromDevice("body");
                if (tempList != null) {
                    if (tempList.size() > 0) {
                        updateSuitAdapter("body", tempList);
                    }
                } else {
                    setSuitAdapter("body");
                }
                SUB_CAT_TYPE = "body";
                updatePrevNext(startPosition);
                if(ChangeBackgroundData.getChangeBackgroundData()!=null){
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,militaryImg, militaryTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,doctorImg, doctorTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,mixedImg, mixedTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(SetSuitActivity.this,bodyImg, bodyTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(SetSuitActivity.this,policeImg, policeTxt);
                }
            }
        });
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startPosition > 0) {
                    startPosition--;
                    updatePrevNext(startPosition);
                }
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dResouce != null) {
                    if (startPosition < dResouce.size()) {
                        startPosition++;
                        updatePrevNext(startPosition);
                    }
                } else {
                    if (startPosition < 3) {
                        startPosition++;
                        updatePrevNext(startPosition);
                    }
                }
            }
        });
        backToCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        horizontalFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mirrorImage();
            }
        });
        saveSuitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSuitImage.setClickable(false);
                saveImage();
            }
        });
    }

    public ArrayList<String> getImagesFromDevice(String subDirectory) {
        final ArrayList<String> tempAudioList = new ArrayList<>();
        String directoryPath = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/" + getResources().getString(R.string.app_name) + "/" + subDirectory + "/"
                :Environment.getExternalStorageDirectory().toString()+ "/" +getResources().getString(R.string.app_name) + "/" + subDirectory + "/";
        //String directoryPath = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/" + subDirectory + "/";
        File directory1 = new File(directoryPath);
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

    public boolean isDirectoryNotEmpty(String directoryPath) {
        Log.e("TEST_1", "directoryPath" + directoryPath);
        try {
            File file = new File(directoryPath);
            if (file.exists() && file.isDirectory()) {
                if (file.exists() && Objects.requireNonNull(file.list()).length > 0) {
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

    public void updatePrevNext(int startPosition) {
        if (dResouce != null) {
            if (dResouce.size() > 0 && isDownload) {

                if (dResouce.size() <= 3) {
                    if (startPosition > 0) {
                        if (startPosition == dResouce.size() - 2) {
                            previousBtn.setVisibility(VISIBLE);
                            nextBtn.setVisibility(GONE);
                            Bitmap myBitmap = BitmapFactory.decodeFile(dResouce.get(startPosition));
                            suitsImage.setImageBitmap(myBitmap);
                            setSuitsZoomBar(startPosition);
                        } else {
                            previousBtn.setVisibility(VISIBLE);
                            nextBtn.setVisibility(VISIBLE);
                            Bitmap myBitmap = BitmapFactory.decodeFile(dResouce.get(startPosition));
                            suitsImage.setImageBitmap(myBitmap);
                            setSuitsZoomBar(startPosition);
                        }
                    }
                    if (dResouce.size() == 1 && startPosition == 0) {
                        previousBtn.setVisibility(GONE);
                        nextBtn.setVisibility(GONE);
                        Bitmap myBitmap = BitmapFactory.decodeFile(dResouce.get(startPosition));
                        suitsImage.setImageBitmap(myBitmap);
                        setSuitsZoomBar(startPosition);
                    } else if (startPosition == 0) {
                        previousBtn.setVisibility(GONE);
                        nextBtn.setVisibility(VISIBLE);
                        Bitmap myBitmap = BitmapFactory.decodeFile(dResouce.get(startPosition));
                        suitsImage.setImageBitmap(myBitmap);
                        setSuitsZoomBar(startPosition);
                    }
                } else {
                    if (startPosition == dResouce.size() - 1) {
                        previousBtn.setVisibility(VISIBLE);
                        nextBtn.setVisibility(GONE);
                        Bitmap myBitmap = BitmapFactory.decodeFile(dResouce.get(startPosition));
                        suitsImage.setImageBitmap(myBitmap);
                        setSuitsZoomBar(startPosition);
                    } else {
                        previousBtn.setVisibility(VISIBLE);
                        nextBtn.setVisibility(VISIBLE);
                        Bitmap myBitmap = BitmapFactory.decodeFile(dResouce.get(startPosition));
                        suitsImage.setImageBitmap(myBitmap);
                        setSuitsZoomBar(startPosition);
                    }
                    if (startPosition == 0) {
                        previousBtn.setVisibility(GONE);
                        nextBtn.setVisibility(VISIBLE);
                        Bitmap myBitmap = BitmapFactory.decodeFile(dResouce.get(startPosition));
                        suitsImage.setImageBitmap(myBitmap);
                        setSuitsZoomBar(startPosition);
                    }
                }
                Log.v("bitmapEventList", "res : " + dResouce.get(startPosition));
                localEventQueue.add(dResouce.get(startPosition));
            } else {
                if (startPosition > 0) {
                    if (startPosition == 3) {
                        previousBtn.setVisibility(VISIBLE);
                        nextBtn.setVisibility(GONE);
                        suitsImage.setImageResource(mResources[startPosition]);
                        setSuitsZoomBar(startPosition);
                    } else {
                        previousBtn.setVisibility(VISIBLE);
                        nextBtn.setVisibility(VISIBLE);
                        suitsImage.setImageResource(mResources[startPosition]);
                        setSuitsZoomBar(startPosition);
                    }
                }
                if (startPosition == 0) {
                    previousBtn.setVisibility(GONE);
                    nextBtn.setVisibility(VISIBLE);
                    suitsImage.setImageResource(mResources[startPosition]);
                    setSuitsZoomBar(startPosition);
                }
                Log.v("bitmapEventList", "res : " + mResources[startPosition]);
                localEventQueue.add("" + mResources[startPosition]);
                isDownload = false;
            }
        } else {
            if (startPosition > 0) {
                if (startPosition == 2) {
                    previousBtn.setVisibility(VISIBLE);
                    nextBtn.setVisibility(GONE);
                    suitsImage.setImageResource(mResources[startPosition]);
                    setSuitsZoomBar(startPosition);
                } else {
                    previousBtn.setVisibility(VISIBLE);
                    nextBtn.setVisibility(VISIBLE);
                    suitsImage.setImageResource(mResources[startPosition]);
                    setSuitsZoomBar(startPosition);
                }
            }
            if (startPosition == 0) {
                previousBtn.setVisibility(GONE);
                nextBtn.setVisibility(VISIBLE);
                suitsImage.setImageResource(mResources[startPosition]);
                setSuitsZoomBar(startPosition);
            }
            Log.v("bitmapEventList", "res : " + mResources[startPosition]);
            localEventQueue.add("" + mResources[startPosition]);
            isDownload = false;
        }
    }

    public void mirrorImage() {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
    }

    private void saveImage() {
       // saveLayout.setBackgroundColor();
        Bitmap b = Bitmap.createBitmap(saveLayout.getDrawingCache());
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);
        if(categoriesActivity!=null){
            categoriesActivity.updateSavedBitmap(resizedBitmap);
        }
        CommonMethods.getInstance().addToBitmapEventQueue((String) retrieveElement(localEventQueue.size() - 1, localEventQueue));
//        MyInterstitialAdView.getInstance().activitiesAD(SetSuitActivity.this);

        onBackPressed();
    }

    public static Object retrieveElement(int index, Queue q) {
        Iterator it = q.iterator();
        int count = 0;
        while (it.hasNext()) {
            Object e = it.next();
            if (count == index) {
                it.remove();
                return e;
            }
            count++;
        }
        return null;
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(getApplicationContext(), EraserActivity.class);
//        startActivity(intent);

        super.onBackPressed();
//        MyInterstitialAdView.getInstance().activitiesAD(SetSuitActivity.this);
    }

    @Override
    protected void onRestart() {
        saveSuitImage.setClickable(true);
        super.onRestart();
    }
}

