package com.hangoverstudios.men.photo.suite.editor.app.activities;



import static com.hangoverstudios.men.photo.suite.editor.app.activities.EraserActivity.eraserActivity;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.SplashScreen.mFirebaseAnalytics;
import static com.hangoverstudios.men.photo.suite.editor.app.views.HoverView.savedBitmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hangoverstudios.men.photo.suite.editor.app.BuildConfig;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.ads.MyInterstitialAdView;
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.views.HoverView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class bg_eraser_save extends AppCompatActivity {


    ImageView afterCrop, afterCropDup;
    LinearLayout manStyles;
    private static Bitmap SAVED_BITMAP;
    private Bitmap PREV_SAVED_BITMAP = null;
    LinearLayout backToCrop;
    RelativeLayout relImageLayout;
    private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";
    private static final Random random = new Random();
    private String filename;
    String filePath;
    int viewWidth;
    int viewHeight;
    LinearLayout home;
    TextView  save, catDone;
    Bitmap RESULT_BITMAP;
    RewardedAd mRewardedVideoAd;
    boolean isLoading;
    private static boolean canRewarded = false;
    private static boolean rewardVideoComplete = false;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;
    TextView waterMarkTxt;
    private String prevSavedImagePath;
    private ProgressDialog progressDialog;
    private LinearLayout changeBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg_eraser_save);

        MyInterstitialAdView.getInstance().activitiesAD(bg_eraser_save.this);

//        dummy_img = findViewById(R.id.dummy_img);
//        imageViewPreview = findViewById(R.id.imageViewPreview);
//
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            IMAGE_PATH = getIntent().getStringExtra("PATH");
//            Bitmap bitmap = (Bitmap) BitmapFactory.decodeFile(IMAGE_PATH);
//            dummy_img.setImageBitmap(bitmap);
//        }

        ChangeBackgroundData.getChangeBackgroundData().setbg_eraser_save(this);
        afterCrop = findViewById(R.id.afterCrop);
        afterCropDup = findViewById(R.id.dup_afterCrop);

        save = findViewById(R.id.save_img);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setClickable(false);
                saveWork(SAVED_BITMAP);
                Toast.makeText(bg_eraser_save.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        progressDialog = new ProgressDialog(this);

        if (getIntent().hasExtra("prevImagePath")) {
            prevSavedImagePath = getIntent().getStringExtra("prevImagePath");
            PREV_SAVED_BITMAP = BitmapFactory.decodeFile(prevSavedImagePath);

//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 8;
//            PREV_SAVED_BITMAP = BitmapFactory.decodeFile(prevSavedImagePath,options);

            SAVED_BITMAP = PREV_SAVED_BITMAP;
        } else {
            SAVED_BITMAP = savedBitmap;
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        viewWidth = displayMetrics.widthPixels;
        viewHeight = displayMetrics.heightPixels;
        afterCrop.getLayoutParams().width = viewWidth;
        afterCrop.getLayoutParams().height = viewHeight;

        if (PREV_SAVED_BITMAP != null) {
            afterCrop.setImageBitmap(PREV_SAVED_BITMAP);
            afterCropDup.setImageBitmap(PREV_SAVED_BITMAP);
        } else {
            afterCrop.setImageBitmap(SAVED_BITMAP);
            afterCropDup.setImageBitmap(SAVED_BITMAP);
        }

        relImageLayout = findViewById(R.id.cr1);
        ViewGroup.LayoutParams params = relImageLayout.getLayoutParams();
        if (RESULT_BITMAP != null) {
            params.width = viewWidth;
            params.height = viewHeight;
        }
        relImageLayout.setLayoutParams(params);
        waterMarkTxt = findViewById(R.id.water_mark_txt);
        backToCrop = findViewById(R.id.linear_cat_back);
        backToCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        catDone = findViewById(R.id.linear_cat_done);
        filename = "img_" + String.format("%d.jpg", System.currentTimeMillis());
        catDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RemoteConfigValues.getOurRemote().getEnableWatermark() != null) {
                    if (RemoteConfigValues.getOurRemote().getEnableWatermark().equals("true")) {
                        showWaterMarkDialog();
                    } else {
                        new bg_eraser_save.SaveImage().execute();
                    }
                }
            }
        });

        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bg_eraser_save.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        changeBackground = findViewById(R.id.linear_background);

        manStyles = findViewById(R.id.linear_man_styles);
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialog = alertDialogBuilder.create();
//        setClickListeners();

        if (CommonMethods.getInstance().getFromDemo() != null) {
            if (!CommonMethods.getInstance().getFromDemo().equals("")) {
                if (CommonMethods.getInstance().getFromDemo().equals("fromDresses")) {
                    Intent intent = new Intent(bg_eraser_save.this, SetSuitActivity.class);
                    intent.putExtra("category", "dresses");
                    startActivity(intent);
                }else if (CommonMethods.getInstance().getFromDemo().equals("fromManStyles")) {
                    Intent intent = new Intent(bg_eraser_save.this, StickerEditActivity.class);
                    intent.putExtra("category", "menStyles");
                    startActivity(intent);
                }else if (CommonMethods.getInstance().getFromDemo().equals("fromEffects")) {
                    startActivity(new Intent(bg_eraser_save.this, EffectsActivity.class));
                }else if (CommonMethods.getInstance().getFromDemo().equals("fromBackground")) {
                    startActivity(new Intent(bg_eraser_save.this, ChangeBackground.class));
                }else if (CommonMethods.getInstance().getFromDemo().equals("fromEraser")) {
//                    Intent intent = new Intent(CategoriesActivity.this, EraserActivity.class);
//                    intent.putExtra("cmgFrom", "category");
//                    startActivity(intent);
                }else if (CommonMethods.getInstance().getFromDemo().equals("fromMakeOver")) {
                    Intent intent = new Intent(bg_eraser_save.this, StickerEditActivity.class);
                    intent.putExtra("category", "makeOver");
                    startActivity(intent);
                }else if (CommonMethods.getInstance().getFromDemo().equals("fromFrames")) {
                    Intent intent = new Intent(bg_eraser_save.this, SetSuitActivity.class);
                    intent.putExtra("category", "frames");
                    startActivity(intent);
                }else if (CommonMethods.getInstance().getFromDemo().equals("fromAccess")) {
                    Intent intent = new Intent(bg_eraser_save.this, StickerEditActivity.class);
                    intent.putExtra("category", "accessories");
                    startActivity(intent);
                }
            }
        }
    }

    private void saveWork(Bitmap bitmap) {
        String filename = "img_" + String.format("%d.jpg", System.currentTimeMillis());
        String path = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/" + getResources().getString(R.string.app_name) + "/PrevSavedImages"
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/PrevSavedImages";
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
        refreshGallery(dest);
    }

    public void updateSavedBitmap(Bitmap bitmap) {
        SAVED_BITMAP = bitmap;
        afterCrop.setImageBitmap(SAVED_BITMAP);
        afterCropDup.setImageBitmap(SAVED_BITMAP);
    }

    public Bitmap getSavedBitmap() {
        return SAVED_BITMAP;
    }

//    private void setClickListeners() {
//
//        changeBackground.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(bg_eraser_save.this, ChangeBackground.class));
//            }
//        });
//    }


    private void startRewardVideoAd() {
        alertDialog.setMessage("please wait..until Ad is load...");
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();

        if (mRewardedVideoAd == null) {
            isLoading = true;
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(
                    this,
                    getString(R.string.admob_reward_id),
                    adRequest,
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            mRewardedVideoAd = null;
                            isLoading = false;
                            Toast.makeText(bg_eraser_save.this, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            alertDialog.dismiss();
                            showRewardedVideo();
                            mRewardedVideoAd = rewardedAd;
                            isLoading = false;
                        }
                    });
        }

    }

    private void showRewardedVideo() {
        if (mRewardedVideoAd == null) {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
            return;
        }

        mRewardedVideoAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        mRewardedVideoAd = null;
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        if (rewardVideoComplete && canRewarded) {
                            new bg_eraser_save.SaveImage().execute();
                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "noWaterMark");
                            bundle.putString("Activity", "CategoriesActivity");
                            if (mFirebaseAnalytics != null)
                                mFirebaseAnalytics.logEvent("noWaterMarkSave", bundle);
                        } else {
                            rewardVideoComplete = false;
                            canRewarded = false;
                        }
                        mRewardedVideoAd = null;
                    }
                });
        Activity activityContext = bg_eraser_save.this;
        mRewardedVideoAd.show(
                activityContext,
                new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        canRewarded = true;
                        rewardVideoComplete = true;
                    }
                });
    }

    public File save() {
        ArrayList<String> finalEventList = new ArrayList<>(CommonMethods.getInstance().getBitmapEventQueue());
        ArrayList<String> trimmedFinalEventList = new ArrayList<>();
        for (int i = 0; i < finalEventList.size(); i++) {
            trimmedFinalEventList.add(finalEventList.get(i).substring(finalEventList.get(i).lastIndexOf("/") + 1));
        }
        Log.v("bitmapEventList", "final assets : " + trimmedFinalEventList.toString());
        Bundle bundle = new Bundle();
        for (int i = 0; i < trimmedFinalEventList.size(); i++) {
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, trimmedFinalEventList.get(i));
            bundle.putString("Activity", "CategoriesActivity");
            if (mFirebaseAnalytics != null)
                mFirebaseAnalytics.logEvent("finalSaveBitmapImage", bundle);
        }
        CommonMethods.getInstance().clearBitmapEventQueue();
        Bitmap bitmap = saveDrawnBitmap(relImageLayout);
        RESULT_BITMAP = bitmap;
        String path = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) ?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/Saved Images"
                : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images";
        //String path = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images";
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
        refreshGallery(dest);
        filePath = path + "/" + filename;
        return dest;
    }

    public void showWaterMarkDialog() {
        final Dialog dialog = new Dialog(bg_eraser_save.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.water_mark_dialog);


        TextView msgTxt = dialog.findViewById(R.id.dialog_msg);
        TextView msgSubTxt = dialog.findViewById(R.id.dialog_msg_sub);
        msgTxt.setText("Save with no water mark");
        msgSubTxt.setText("Are you sure want to save with 'water mark' or 'no water mark'.\n To get no water mark proceed to reward video.");

        TextView dialogOk = dialog.findViewById(R.id.dialog_ok);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RemoteConfigValues.getOurRemote().getShowRewardVideoAd() != null
                        && RemoteConfigValues.getOurRemote().getShowRewardVideoAd().equals("true")) {
                    waterMarkTxt.setVisibility(View.INVISIBLE);
                    startRewardVideoAd();
                } else {
                    waterMarkTxt.setVisibility(View.INVISIBLE);
                    new bg_eraser_save.SaveImage().execute();
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "noWaterMark");
                    bundle.putString("Activity", "CategoriesActivity");
                    if (mFirebaseAnalytics != null)
                        mFirebaseAnalytics.logEvent("noWaterMarkSave", bundle);
                }
                dialog.dismiss();
            }
        });
        TextView dialogCancel = dialog.findViewById(R.id.dialog_cancel);
        dialogCancel.setVisibility(View.VISIBLE);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyInterstitialAdView.getInstance().activitiesAD(CategoriesActivity.this);
                MyInterstitialAdView.getInstance().activitiesAD(bg_eraser_save.this);
                waterMarkTxt.setVisibility(View.VISIBLE);
                new bg_eraser_save.SaveImage().execute();
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "waterMark");
                bundle.putString("Activity", "CategoriesActivity");
                if (mFirebaseAnalytics != null)
                    mFirebaseAnalytics.logEvent("waterMarkSave", bundle);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public Bitmap saveDrawnBitmap(RelativeLayout relativeLayout) {
        Bitmap saveBitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(saveBitmap);
        relativeLayout.draw(cv);
        return saveBitmap;
    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getIntent().putExtra("cmgFrom", "category");
        scanMedia();
        if (ChangeBackgroundData.getChangeBackgroundData() != null && eraserActivity != null) {
            ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(bg_eraser_save.this, eraserActivity.eraserDoneImg, eraserActivity.eraserDoneTxt);
            ChangeBackgroundData.getChangeBackgroundData().setColorFilter(bg_eraser_save.this, eraserActivity.eraserImg, eraserActivity.eraserTxt);
        }

        if (eraserActivity != null && eraserActivity.mHoverView != null) {
            eraserActivity.zoomLayout.setVisibility(View.GONE);
            eraserActivity.eraserLayout.setVisibility(View.VISIBLE);
            eraserActivity.mHoverView.switchMode(HoverView.ERASE_MODE);
            eraserActivity.resetSubEraserButtonState();
            eraserActivity.eraserSubButton.setSelected(true);
        }
    }

    public void scanMedia() {
        File targetPath = new File(Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images");    //path where gif will be stored
        String finalPath = targetPath.getAbsolutePath();
        MediaScannerConnection.scanFile(this,
                new String[]{finalPath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    class SaveImage extends AsyncTask<Void, Void, Void> {
        String imgPath = null;
        File saveFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("saving...please wait.");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Bitmap bitmap = ChangeBackgroundData.getChangeBackgroundData().getSaveFinishedBitmap();
            saveFile = save();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (saveFile != null && filePath != null) {
                Intent intent = new Intent(bg_eraser_save.this, SaveFinishActivity.class);
                intent.putExtra("PATH", filePath);
                intent.putExtra("FROM", "CategoriesActivity");
                startActivity(intent);
//        MyInterstitialAdView.getInstance().activitiesAD(CategoriesActivity.this);
                //finish();
            } else {
                Toast.makeText(bg_eraser_save.this, "saving failed try again..", Toast.LENGTH_SHORT).show();
            }
            if (progressDialog.isShowing())
                progressDialog.dismiss();

        }
    }

    @Override
    protected void onRestart() {
        save.setClickable(true);
        super.onRestart();
    }
}