package com.hangoverstudios.men.photo.suite.editor.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.hangoverstudios.men.photo.suite.editor.app.BuildConfig;
import com.hangoverstudios.men.photo.suite.editor.app.fragments.PreviewSavedImageFragment;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.ChangeBackgroundAdapter;
import com.hangoverstudios.men.photo.suite.editor.app.interfaces.ChangeBackgroundSaveImage;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;
import com.hangoverstudios.men.photo.suite.editor.app.views.SuitDrawView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SaveFinishActivity extends AppCompatActivity implements ChangeBackgroundSaveImage {
    //private LinearLayout home, share, setWallpaper;
    private NativeAd nativeAdGlobal;
    private String IMAGE_PATH;
    //private AdView adViewBanner10;
    //private FrameLayout adViewContainer;
    private RecyclerView backgroundRecyclerView;
    private ChangeBackgroundAdapter changeBackgroundAdapter;
    private Bitmap saveFinishedBitmap;
    private ProgressDialog progressDialog;
    private LinearLayout saveDone;
    private LinearLayout btnOnBackPressed;

    private String FROM = null;

    private View hiddenBackground;
    private View displayingBackground;

    private ImageView hiddenForegroundImage;
    private Bitmap savedBitmap;
    //private ImageView displayingForegroundImage;

    private RelativeLayout relativeSFMainSaveCanvas, relativeLayoutOne, relativeLayoutTwo, saveFinishTopBar;

    int viewWidth;
    int viewHeight;
    SuitDrawView mSuitDrawView;
    double bmRatio;
    double viewRatio;
    int actionBarHeight;
    int bottombarHeight;
    double mDensity;
    int bmWidth;
    int bmHeight;
    private ShapeableImageView imageViewPreview;
    private AppCompatImageView imgBack, imgHome, previewImg;
    private  PreviewSavedImageFragment previewSavedImageFragment;
    private  FrameLayout previewSavedImageFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  MyInterstitialAdView.getInstance().activitiesAD(this);
        setContentView(R.layout.activity_save_finish_new);

        previewSavedImageFragmentContainer = findViewById(R.id.containerPreview);
        previewSavedImageFragment = new PreviewSavedImageFragment();
        hiddenBackground = findViewById(R.id.hidden_background_view);
        hiddenForegroundImage = findViewById(R.id.hidden_foreground_img);

        displayingBackground = findViewById(R.id.displaying_background_view);

        relativeSFMainSaveCanvas = findViewById(R.id.main_sf_rel_save_canvas);
        relativeLayoutOne = findViewById(R.id.rel_layout_one);
        saveFinishTopBar = findViewById(R.id.save_finish_top_bar);
        relativeLayoutTwo = findViewById(R.id.rel_layout_two);
        imageViewPreview = findViewById(R.id.imageViewPreview);

        mDensity = getResources().getDisplayMetrics().density;
        actionBarHeight = (int) (70 * mDensity);
        bottombarHeight = (int) (60 * mDensity);
        viewWidth = getResources().getDisplayMetrics().widthPixels;
        viewHeight = getResources().getDisplayMetrics().heightPixels;
        viewRatio = (double) viewHeight / (double) viewWidth;




        ChangeBackgroundData.getChangeBackgroundData().setDisplayingBackground(displayingBackground);
        ChangeBackgroundData.getChangeBackgroundData().setSfBackgroundImageView(hiddenBackground);


        imgBack = findViewById(R.id.imageViewBack);
        imgHome = findViewById(R.id.imageViewHome);
        previewImg = findViewById(R.id.imageViewPreviewIcon);
        backgroundRecyclerView = findViewById(R.id.recycler_backgrounds);

        saveDone = findViewById(R.id.sf_save_done);
        btnOnBackPressed = findViewById(R.id.sf_linear_back);

        if (getIntent().hasExtra("FROM")) {
            this.FROM = getIntent().getStringExtra("FROM");
        }
        if (ChangeBackgroundData.getChangeBackgroundData().isBackgroundApplied() || FROM.equals("CreateCollageActivity") || FROM.equals("EditCollageActivity")||FROM.equals("MostionEffectActivity")) {
            backgroundRecyclerView.setVisibility(View.GONE);
            relativeLayoutOne.setVisibility(View.GONE);
            saveFinishTopBar.setVisibility(View.GONE);
            relativeLayoutTwo.setVisibility(View.VISIBLE);
            saveDone.setVisibility(View.GONE);
        } else {
            backgroundRecyclerView.setVisibility(View.VISIBLE);
            relativeLayoutOne.setVisibility(View.VISIBLE);
            saveFinishTopBar.setVisibility(View.VISIBLE);
            relativeLayoutTwo.setVisibility(View.GONE);
            saveDone.setVisibility(View.VISIBLE);
        }


        progressDialog = new ProgressDialog(this);
        if (getIntent().hasExtra("PATH")) {
            IMAGE_PATH = getIntent().getStringExtra("PATH");
            Bitmap bitmap = BitmapFactory.decodeFile(IMAGE_PATH);
            setSavedImageBitmap(bitmap);
        }


        if(FROM.equals("CreateCollageActivity") || FROM.equals("EditCollageActivity")) {
        }else{
            Bitmap catSavedBitmap = ChangeBackgroundData.getChangeBackgroundData().getSaveFinishedBitmap();
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
                relativeSFMainSaveCanvas.addView(mSuitDrawView);
                mSuitDrawView.switchMode(SuitDrawView.MOVING_MODE);
            }
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                Intent intent = new Intent(SaveFinishActivity.this, MainActivity.class);
                intent.putExtra("SaveFinishActivity", false);
                startActivity(intent);
            }
        });
        previewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(SaveFinishActivity.this, "magnify icon clicked..!", Toast.LENGTH_SHORT).show();
                loadFragment(previewSavedImageFragment);
            }
        });
        previewSavedImageFragmentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previewSavedImageFragment.isVisible()) {
                    //fragmentTransaction.remove(selectionFragment);
                    getFragmentManager().beginTransaction().remove((Fragment) previewSavedImageFragment).commitAllowingStateLoss();
                    previewSavedImageFragmentContainer.setVisibility(View.GONE);
                }
            }
        });
        saveDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setViewHeights(1);
                //  ChangeBackgroundData.getChangeBackgroundData().setSaveFinishedBitmap(getSavedDrawingCache());
                ChangeBackgroundData.getChangeBackgroundData().setSaveFinishedBitmap(getBitmapFromView(relativeSFMainSaveCanvas));
                new SaveFinishedImage().execute();
            }
        });
        btnOnBackPressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setBackgroundAdapter();
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void setSavedImageBitmap( Bitmap bitmap) {
        savedBitmap = bitmap;
        ChangeBackgroundData.getChangeBackgroundData().setSaveFinishedBitmap(bitmap);
        imageViewPreview.setImageBitmap(bitmap);
        hiddenForegroundImage.setImageBitmap(bitmap);
    }

    public Bitmap getSavedBitmap()
    {
        return savedBitmap;
    }

    private void setBackgroundAdapter() {

        String str = "bgph";
        ArrayList<Object> backgroundList = new ArrayList();
        int colorDefault = getResources().getColor(R.color.white);
        int colorSelected = getResources().getColor(R.color.transparent);


        for (int i = 1; i <= 10; i++) {
            backgroundList.add(SaveFinishActivity.this.getResources().getIdentifier(str + "_" + i, "drawable", SaveFinishActivity.this.getPackageName()));
        }

        changeBackgroundAdapter = new ChangeBackgroundAdapter(SaveFinishActivity.this, backgroundList, 4);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        backgroundRecyclerView.setLayoutManager(linearLayoutManager);
        backgroundRecyclerView.setAdapter(changeBackgroundAdapter);

    }

    public void shareImages(String path) {
        ArrayList<Uri> files = new ArrayList<Uri>();
        Uri uri = FileProvider.getUriForFile(SaveFinishActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File((String) path));
        files.add(uri);
        Intent shareIntent;
        shareIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + " : https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        startActivity(Intent.createChooser(shareIntent, "send"));
    }

    public void setWallpaper(String path) {
        File f1 = new File(path);
        if (f1.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(path);
            WallpaperManager m = WallpaperManager.getInstance(this);
            try {
                m.setBitmap(bmp);
                Toast.makeText(SaveFinishActivity.this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(SaveFinishActivity.this, "can't set wallpaper", Toast.LENGTH_SHORT).show();
            }
            if (isWallpaperSet()) {
                Toast.makeText(SaveFinishActivity.this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SaveFinishActivity.this, "can't set wallpaper", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isWallpaperSet() {
        boolean isWallPaperSet;
        try {
            WallpaperManager wm = WallpaperManager.getInstance(this);
            WallpaperInfo wi = wm.getWallpaperInfo();
            if (wi.getPackageName().equals(getPackageName())) {
                isWallPaperSet = true;
            } else {
                isWallPaperSet = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isWallPaperSet = false;
        }
        return isWallPaperSet;
    }

    /*private void admobAd() {
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
                        if (nativeAdGlobal != null) {
                            nativeAdGlobal.destroy();
                        }
                        nativeAdGlobal = nativeAd;
                        FrameLayout frameLayout = findViewById(R.id.native_ad_save_finish);
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

    public void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
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

    @Override
    public void onBackPressed() {
        if (previewSavedImageFragment.isVisible()) {
            //fragmentTransaction.remove(selectionFragment);
            getFragmentManager().beginTransaction().remove((Fragment) previewSavedImageFragment).commitAllowingStateLoss();
            previewSavedImageFragmentContainer.setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
            ChangeBackgroundData.getChangeBackgroundData().setBackgroundApplied(false);
        }
    }

    @Override
    public void saveImage() {
        saveDone.setVisibility(View.VISIBLE);
    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    class SaveFinishedImage extends AsyncTask<Void, Void, Void> {
        String imgPath = null;
        File finishedFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("saving...please wait.");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Bitmap bitmap = ChangeBackgroundData.getChangeBackgroundData().getSaveFinishedBitmap();
            Bitmap bitmap = getBitmapFromView(relativeSFMainSaveCanvas);
            String filename = "img_" + String.format("%d.jpg", System.currentTimeMillis());
            String path = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) ?
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/Saved Images"
                    : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images";
            //  String path = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images";
            File dir = new File(path);
            dir.mkdirs();
            File dest = new File(dir, filename);
            try {
                FileOutputStream out = new FileOutputStream(dest);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finishedFile = dest;
            IMAGE_PATH = path + "/" + filename;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (finishedFile != null && IMAGE_PATH != null) {
                // setViewHeights(0);
                saveDone.setVisibility(View.GONE);
//                displayingBackground.setVisibility(View.GONE);
//                backgroundRecyclerView.setVisibility(View.GONE);
                relativeLayoutOne.setVisibility(View.GONE);
                saveFinishTopBar.setVisibility(View.GONE);
                relativeLayoutTwo.setVisibility(View.VISIBLE);
                refreshGallery(finishedFile);
                setSavedImageBitmap(BitmapFactory.decodeFile(IMAGE_PATH));
            } else {
                Toast.makeText(SaveFinishActivity.this, "saving failed try again..", Toast.LENGTH_SHORT).show();
            }
            if (progressDialog.isShowing())
                progressDialog.dismiss();

        }
    }

    private Bitmap getSavedDrawingCache() {
        Bitmap b = Bitmap.createBitmap(relativeSFMainSaveCanvas.getDrawingCache());
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);
        return resizedBitmap;
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }
    /*private void setViewHeights(int flag)
    {
        final float scale = getResources().getDisplayMetrics().density;
        int dpWidthInPx  = (int) (displayingBackground.getLayoutParams().width * scale);
        int dpHeightInPx = 0;
        if(flag == 0)
        {
            dpHeightInPx = (int) (350 * scale);
            //dpHeightInPx = (int) (viewHeight*scale - 200);

           *//* LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
            displayingForegroundImage.setLayoutParams(layoutParams);*//*

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
            displayingForegroundImage.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
            displayingBackground.setLayoutParams(layoutParams2);

            *//*displayingBackground.getLayoutParams().height = dpToPx(500,SaveFinishActivity.this);
            displayingBackground.getLayoutParams().width = viewWidth;
            displayingForegroundImage.getLayoutParams().height = dpToPx(500,SaveFinishActivity.this);
            displayingForegroundImage.getLayoutParams().width = viewWidth;*//*
        }
        else
        {

            dpHeightInPx = (int) (400 * scale);
            //dpHeightInPx = (int) (viewHeight*scale);

            *//* LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
            displayingForegroundImage.setLayoutParams(layoutParams);*//*

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
            displayingForegroundImage.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
            displayingBackground.setLayoutParams(layoutParams2);

            *//*displayingBackground.getLayoutParams().height = viewHeight;
            displayingBackground.getLayoutParams().width = viewWidth;
            displayingForegroundImage.getLayoutParams().height = viewHeight;
            displayingForegroundImage.getLayoutParams().width = viewWidth;*//*

        }
    }*/

    private void loadFragment(Fragment fragment) {

// create a FragmentManager
        previewSavedImageFragmentContainer.setVisibility(View.VISIBLE);
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.animator.slide_up, R.animator.slide_down);
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(previewSavedImageFragmentContainer.getId(), fragment);
        fragmentTransaction.commit(); // save the changes
    }

    public void myClickHandler(View view) {
        int id = view.getId();
        if (id == R.id.save_img) {
            Toast.makeText(this, "Saved to Gallery", Toast.LENGTH_SHORT).show();
            /*try {
                Uri uriImage = FileProvider.getUriForFile(com.hangoverstudios.men.photo.suite.editor.app.activities.SaveFinishActivity.this,
                        getApplicationContext().getPackageName() + ".provider", new File(IMAGE_PATH));
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uriImage);
                intent.setPackage("com.instagram.android");
                startActivity(intent);
            } catch (Exception i) {
                Toast.makeText(this, getString(R.string.no_instagram_app), Toast.LENGTH_SHORT).show();
            }*/


        }
        /*if (id == R.id.share_imageView) {
            Toast.makeText(this, getString(R.string.saved_image_message), Toast.LENGTH_SHORT).show();
        }*/
        if (id == R.id.whats_app_share) {
//            try {
//                Uri uriImage = FileProvider.getUriForFile(com.hangoverstudios.men.photo.suite.editor.app.activities.SaveFinishActivity.this,
//                        getApplicationContext().getPackageName() + ".provider", new File(IMAGE_PATH));
//
//                Intent intent = new Intent();
//                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + " : https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_SEND);
//                intent.putExtra(Intent.EXTRA_STREAM, uriImage);
//                intent.setPackage("org.telegram.messenger");
//                startActivity(intent);
//            } catch (Exception i) {
//                Toast.makeText(this, "No Telegram app..!", Toast.LENGTH_SHORT).show();
//            }

            try {
                Uri uriImage = FileProvider.getUriForFile(SaveFinishActivity.this,
                        getApplicationContext().getPackageName() + ".provider", new File(IMAGE_PATH));

                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + " : https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uriImage);
                intent.setPackage("com.whatsapp");
                startActivity(intent);
            } catch (Exception i) {
                Toast.makeText(this, getString(R.string.no_whatsapp_app), Toast.LENGTH_SHORT).show();
            }

        }
        if (id == R.id.facebook_share) {
            //  initShareIntent("face");

            try {
                Uri uriImage = FileProvider.getUriForFile(SaveFinishActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider", new File(IMAGE_PATH));

                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + " : https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uriImage);
                intent.setPackage("com.facebook.katana");
                startActivity(intent);
            } catch (Exception i) {
                Toast.makeText(this, getString(R.string.no_facebook_app), Toast.LENGTH_SHORT).show();
            }

        }
        if (id == R.id.others_share) {
            Uri uriImage = FileProvider.getUriForFile(com.hangoverstudios.men.photo.suite.editor.app.activities.SaveFinishActivity.this,
                    getApplicationContext().getPackageName() + ".provider", new File(IMAGE_PATH));

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + " : https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
            sharingIntent.setType("image/*");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uriImage);
            startActivity(sharingIntent);
        }
    }

}