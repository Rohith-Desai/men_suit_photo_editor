package com.hangoverstudios.men.photo.suite.editor.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.MainActivity.selectedImageBitmap;
import static com.hangoverstudios.men.photo.suite.editor.app.views.CropView.croppedBitmap;

import android.graphics.Matrix;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;
import com.hangoverstudios.men.photo.suite.editor.app.views.CropView;
import com.google.android.gms.ads.AdView;

public class FreeImageCropActivity extends AppCompatActivity {
    private void sendBitmap() {
        Intent intent = new Intent(FreeImageCropActivity.this, EraserActivity.class);
        intent.putExtra("crop", true);
        startActivity(intent);
    }

    private RelativeLayout relativeLayoutAutoCutBg, relLayCutBg, relRotateLay;
    private ImageView cutImageBg, viewShowImg, viewOverImg;
    private CropView cropView;
    private Bitmap bitmap;
    private int width, height;
    private int widthPixels, heightPixels;
    RelativeLayout.LayoutParams layoutParams;
    private LinearLayout buttonRotateRight, buttonReset, done, backToMainCrop;
    public TextView freeBackTxt, freeRotateTxt, freeResetTxt, freeDoneTxt;
    public ImageView freeBackImg, freeRotateImg, freeResetImg, freeDoneImg;
    SeekBar freeCropSelectionOffsetSeekBar;
    public static FreeImageCropActivity FreeImageCropActivity;
    private AdView adViewBanner6;
    private FrameLayout adViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_image_crop_view_dummy);
        FreeImageCropActivity = this;
        /*adViewContainer = findViewById(R.id.free_crop_banner_ad);
        adViewContainer.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adViewBanner6, adViewContainer, FreeImageCropActivity.this);
            }
        });*/
        initVars();
        clickables();
        bitmap = selectedImageBitmap;
        if (bitmap != null) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            widthPixels = displayMetrics.widthPixels;
            heightPixels = displayMetrics.heightPixels;
            float f = getResources().getDisplayMetrics().density;
            int i = widthPixels - ((int) f);
            int i2 = heightPixels - ((int) (f * 60.0f));

            if (width < i) {
                if (height < i2) {
                    while (width < i - 20 && height < i2) {
                        double d = (double) width;
                        Double.isNaN(d);
                        width = (int) (d * 1.1d);
                        d = (double) height;
                        Double.isNaN(d);
                        height = (int) (d * 1.1d);
                    }
                    bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    layoutParams = (RelativeLayout.LayoutParams) relativeLayoutAutoCutBg.getLayoutParams();
                    layoutParams.height = bitmap.getHeight();
                    layoutParams.width = bitmap.getWidth();
                    relativeLayoutAutoCutBg.setLayoutParams(layoutParams);
                    cropView = new CropView(this, bitmap);
                    relativeLayoutAutoCutBg.addView(cropView);
                    loadBitmap();
                }
            }
            while (true) {
                double d;
                if (width <= i && height <= i2) {
                    break;
                }
                d = (double) this.width;
                Double.isNaN(d);
                this.width = (int) (d * 0.9d);
                d = (double) this.height;
                Double.isNaN(d);
                this.height = (int) (d * 0.9d);
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
            layoutParams = (RelativeLayout.LayoutParams) relativeLayoutAutoCutBg.getLayoutParams();
            layoutParams.height = bitmap.getHeight();
            layoutParams.width = bitmap.getWidth();
            relativeLayoutAutoCutBg.setLayoutParams(layoutParams);
            cropView = new CropView(this, bitmap);
            relativeLayoutAutoCutBg.addView(cropView);
            loadBitmap();
        }
        buttonReset = findViewById(R.id.free_button_reset);
        backToMainCrop = findViewById(R.id.linear_free_back_button_img);
        done = findViewById(R.id.free_button_done);
        buttonRotateRight = findViewById(R.id.free_button_rotate_Right);

        freeBackTxt = findViewById(R.id.free_back_btn_txt);
        freeRotateTxt = findViewById(R.id.free_rotate_btn_txt);
        freeResetTxt = findViewById(R.id.free_reset_btn_txt);
        freeDoneTxt = findViewById(R.id.free_done_btn_txt);

        freeBackImg = findViewById(R.id.free_back_button_img);
        freeRotateImg = findViewById(R.id.free_rotate_button_img);
        freeResetImg = findViewById(R.id.free_reset_button_img);
        freeDoneImg = findViewById(R.id.free_done_button_img);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null){
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(FreeImageCropActivity.this, freeDoneImg, freeDoneTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(FreeImageCropActivity.this,freeRotateImg, freeRotateTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(FreeImageCropActivity.this,freeResetImg, freeResetTxt);
                    sendBitmap();
                }
            }
        });
        buttonRotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(FreeImageCropActivity.this,freeRotateImg, freeRotateTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(FreeImageCropActivity.this,freeResetImg, freeResetTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(FreeImageCropActivity.this,freeDoneImg, freeDoneTxt);
                    rotateImage(90);
                }
            }
        });
        backToMainCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(FreeImageCropActivity.this,freeBackImg, freeBackTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(FreeImageCropActivity.this,freeDoneImg, freeDoneTxt);
                    onBackPressed();
                }
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeBackgroundData.getChangeBackgroundData()!=null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(FreeImageCropActivity.this,freeRotateImg, freeRotateTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(FreeImageCropActivity.this,freeResetImg, freeResetTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(FreeImageCropActivity.this,freeDoneImg, freeDoneTxt);
                    loadBitmap();
                }
            }
        });

        freeCropSelectionOffsetSeekBar = findViewById(R.id.free_crop_offset_seek_bar);
        freeCropSelectionOffsetSeekBar.incrementProgressBy(20);
        freeCropSelectionOffsetSeekBar.setMax(200);
        freeCropSelectionOffsetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar!=null){
                    if(cropView!=null){
                        cropView.setFreeCropPointerOffset(seekBar.getProgress());
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
        });
    }

    private void clickables() {
        cutImageBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relLayCutBg.setVisibility(View.GONE);
            }
        });
    }

    private void initVars() {
        relativeLayoutAutoCutBg = findViewById(R.id.RelLayAutoCutBg);
        relLayCutBg = findViewById(R.id.RelLayCutBg);
        cutImageBg = findViewById(R.id.CutImageBg);
        viewShowImg = findViewById(R.id.ViewShowImg);
        viewOverImg = findViewById(R.id.ViewOverImg);

    }

    private void rotateImage(float angle) {
        if (angle == 360) {
            angle = 0;
        }
        bitmap = createBitmap(bitmap, angle);
        if(bitmap!=null){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayoutAutoCutBg.getLayoutParams();
            layoutParams.height = bitmap.getHeight();
            layoutParams.width = bitmap.getWidth();
            relativeLayoutAutoCutBg.setLayoutParams(layoutParams);
            cropView = new CropView(this, bitmap);
            relativeLayoutAutoCutBg.addView(cropView);
        }
    }

    public static Bitmap createBitmap(Bitmap bitmap, float f) {
        if(bitmap!=null){
            Matrix matrix = new Matrix();
            matrix.postRotate(f);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }else{
            return null;
        }
    }

    private void loadBitmap() {
        viewOverImg.setImageBitmap(null);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayoutAutoCutBg.getLayoutParams();
        if(bitmap!=null){
            layoutParams.height = bitmap.getHeight();
            layoutParams.width = bitmap.getWidth();
            relativeLayoutAutoCutBg.setLayoutParams(layoutParams);
            cropView = new CropView(this, bitmap);
            this.relativeLayoutAutoCutBg.addView(cropView);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        croppedBitmap = null;
        if(ChangeBackgroundData.getChangeBackgroundData().getFreeImg()!=null && ChangeBackgroundData.getChangeBackgroundData().getFreeTxt()!=null){
            ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(FreeImageCropActivity.this,ChangeBackgroundData.getChangeBackgroundData().getFreeImg(), ChangeBackgroundData.getChangeBackgroundData().getFreeTxt());
        }
    }
}

