package com.hangoverstudios.men.photo.suite.editor.app.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.google.android.gms.ads.AdView;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;
import com.isseiaoki.simplecropview.CropImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.MainActivity.selectedImageBitmap;

public class ImageCropActivity extends AppCompatActivity {

    CropImageView cropView;
    public static Bitmap croppedImage;
    public LinearLayout buttonRotateRight, buttonSquareCrop, buttonFreeCrop, buttonCircleCrop, done;
    public TextView freeTxt, squareTxt, doneTxt, circleTxt, rotateTxt;
    public ImageView freeImg, squareImg, doneImg, circleImg, rotateImg;
    LinearLayout backToHome;
    //public static ImageCropActivity ChangeBackgroundData.getChangeBackgroundData();
    private AdView adViewBanner4;
    private FrameLayout adViewContainer;
    int i = 0;
    private MLImageSegmentationAnalyzer analyzer;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop_dummy);
       // ChangeBackgroundData.getChangeBackgroundData() = this;
        cropView = findViewById(R.id.cropImageView);
        setImageToCropImageView(selectedImageBitmap);
        backToHome = findViewById(R.id.back_to_home_activity);
        buttonRotateRight = findViewById(R.id.button_rotate_Right);
        buttonFreeCrop = findViewById(R.id.button_free);
        buttonSquareCrop = findViewById(R.id.button_square);
        buttonCircleCrop = findViewById(R.id.button_circle);
        freeTxt = findViewById(R.id.free_btn_txt);
        squareTxt = findViewById(R.id.square_btn_txt);
        doneTxt = findViewById(R.id.done_btn_txt);
        circleTxt = findViewById(R.id.circle_btn_txt);
        rotateTxt = findViewById(R.id.rotate_btn_txt);
        freeImg = findViewById(R.id.free_button_img);
        squareImg = findViewById(R.id.square_button_img);
        doneImg = findViewById(R.id.done_button_img);
        circleImg = findViewById(R.id.circle_button_img);
        rotateImg = findViewById(R.id.rotate_button_img);

        ChangeBackgroundData.getChangeBackgroundData().setDoneTxt(doneTxt);
        ChangeBackgroundData.getChangeBackgroundData().setDoneImg(doneImg);
        ChangeBackgroundData.getChangeBackgroundData().setFreeTxt(freeTxt);
        ChangeBackgroundData.getChangeBackgroundData().setFreeImg(freeImg);

        /*adViewContainer = findViewById(R.id.img_crop_banner_ad);
        adViewContainer.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adViewBanner4, adViewContainer, ImageCropActivity.this);
            }
        });*/

        done = findViewById(R.id.button_done);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonFreeCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeBackgroundData.getChangeBackgroundData().setColorFilter(ImageCropActivity.this,freeImg, freeTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,squareImg, squareTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,circleImg, circleTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,doneImg,doneTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,rotateImg, rotateTxt);
                startActivity(new Intent(ImageCropActivity.this, FreeImageCropActivity.class));

            }
        });

        buttonSquareCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropView.setCropMode(CropImageView.CropMode.FREE);
                ChangeBackgroundData.getChangeBackgroundData().setColorFilter(ImageCropActivity.this,squareImg, squareTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,freeImg, freeTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,circleImg, circleTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,doneImg,doneTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,rotateImg, rotateTxt);
            }
        });
        buttonCircleCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropView.setCropMode(CropImageView.CropMode.CIRCLE);
                ChangeBackgroundData.getChangeBackgroundData().setColorFilter(ImageCropActivity.this,circleImg, circleTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,freeImg, freeTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,squareImg, squareTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,doneImg,doneTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,rotateImg, rotateTxt);
            }
        });

        buttonRotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeBackgroundData.getChangeBackgroundData().setColorFilter(ImageCropActivity.this,rotateImg, rotateTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,freeImg, freeTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,squareImg, squareTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,circleImg, circleTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,doneImg,doneTxt);
                rotateBitmap(selectedImageBitmap, 90);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageCropActivity.this.pDialog.show();
                ChangeBackgroundData.getChangeBackgroundData().setColorFilter(ImageCropActivity.this,doneImg, doneTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,rotateImg, rotateTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,freeImg, freeTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,squareImg, squareTxt);
                ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(ImageCropActivity.this,circleImg, circleTxt);
                croppedImage = cropView.getCroppedBitmap();
                CommonMethods.getInstance().setOriginalSelectedBitmap(croppedImage);
                //startActivity(new Intent(ImageCropActivity.this, BGRemover.class));
               // startActivity(new Intent(ImageCropActivity.this,com.hangoverstudios.men.photo.suite.editor.app.bgremoverlite.MainActivity.class));
                analyzer();
            }
        });
        init();

    }
    public void init() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.pDialog = progressDialog;
        progressDialog.setMessage("Loading...");
        this.pDialog.setCancelable(false);
    }

    private void setImageToCropImageView(Bitmap selectedImageBitmap1) {
        if (selectedImageBitmap1 != null) {
            cropView.setImageBitmap(selectedImageBitmap1);
            selectedImageBitmap = selectedImageBitmap1;
        }
    }

    public void rotateBitmap(Bitmap bitmap, float angle) {
        if (angle == 360) {
            angle = 0;
        }
        if(bitmap!=null){
            bitmap = createBitmap(bitmap, (float) angle);
            setImageToCropImageView(bitmap);
        }
    }

    public static Bitmap createBitmap(Bitmap bitmap, float f) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        if(bitmap!=null){
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }else{
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void analyzer() {
        if(this.croppedImage!=null){
            this.analyzer = MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(new MLImageSegmentationSetting.Factory().setExact(false).setAnalyzerType(0).setScene(0).create());
            this.analyzer.asyncAnalyseFrame(new MLFrame.Creator().setBitmap(this.croppedImage).create()).addOnSuccessListener(new OnSuccessListener<MLImageSegmentation>() {

                public void onSuccess(MLImageSegmentation mLImageSegmentation) {
                    if (mLImageSegmentation != null) {
                        ImageCropActivity.this.displaySuccess(mLImageSegmentation);
                    } else {
                        ImageCropActivity.this.displayFailure();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(Exception exc) {
                    ImageCropActivity.this.displayFailure();
                }
            });
        }
    }

    private void displaySuccess(MLImageSegmentation mLImageSegmentation) {
        if (this.croppedImage == null) {
            displayFailure();
            return;
        }
        Bitmap foreground = mLImageSegmentation.getForeground();
        if (foreground != null) {
            croppedImage = foreground;
            showImage();
        } else {
            Log.d("sk","sk");
            displayFailure();
        }
        ImageCropActivity.this.pDialog.dismiss();

    }

    @SuppressLint("WrongConstant")
    private void displayFailure() {

        Toast.makeText(getApplicationContext(), "Fail", 0).show();
    }

    public void openNext(Bitmap bitmap2) {
        // reqimage.setImageBitmap(bitmap2);
        CommonMethods.getInstance().setBgRemovedBitmap(bitmap2);
//        startActivity(new Intent(ImageCropActivity.this, EraserActivity.class));
        Intent intent = new Intent(ImageCropActivity.this,EraserActivity.class);
        intent.putExtra("IMGAEFROM","IMAGECROP");
        startActivity(intent);
       /* if (bitmap2 != null) {
            bitmap2.setHasAlpha(true);
            long currentTimeMillis = System.currentTimeMillis();
            String str = "_" + currentTimeMillis + ".";
            File file = new File(getCacheDir(), "Cutouts");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                File file2 = new File(file, str);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            @SuppressLint("WrongConstant") Toast makeText = Toast.makeText(this, "Please edit photo before save..", 0);
            makeText.setGravity(16, 0, 0);
            makeText.show();
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MLImageSegmentationAnalyzer mLImageSegmentationAnalyzer = this.analyzer;
        if (mLImageSegmentationAnalyzer != null) {
            try {
                mLImageSegmentationAnalyzer.stop();
            } catch (IOException e) {
                Log.e("Auto crop", "Stop failed: " + e.getMessage());
            }
        }
    }


    public void showImage() {
        Log.d("surya","surya");

        openNext(this.croppedImage);

    }


}
