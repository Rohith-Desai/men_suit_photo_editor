package com.hangoverstudios.men.photo.suite.editor.app.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.hangoverstudios.men.photo.suite.editor.app.ads.MyInterstitialAdView;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
//import com.hangoverstudios.men.photo.suite.editor.app.bgremoverlite.MyUtils;
//import com.hangoverstudios.men.photo.suite.editor.app.photoeraser.BitmapListData;
//import com.hangoverstudios.men.photo.suite.editor.app.photoeraser.ImageUtils;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;
import com.hangoverstudios.men.photo.suite.editor.app.views.CropView;
import com.hangoverstudios.men.photo.suite.editor.app.views.HoverView;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.io.FileOutputStream;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.SplashScreen.mFirebaseAnalytics;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.FreeImageCropActivity.FreeImageCropActivity;
import static com.hangoverstudios.men.photo.suite.editor.app.views.CropView.croppedBitmap;
import static com.hangoverstudios.men.photo.suite.editor.app.views.HoverView.savedBitmap;
import static com.huawei.hms.feature.dynamic.b.v;

public class EraserActivity extends Activity implements OnClickListener {
    private ContentResolver mContentResolver;
    private Bitmap mBitmap;

    String filePath;

    public static EraserActivity eraserActivity;
    MainActivity mainActivity;


    public HoverView mHoverView;
    double mDensity;
    int viewWidth;
    int viewHeight;
    int bmWidth;
    int bmHeight;
    int actionBarHeight;
    int bottombarHeight;
    double bmRatio;
    double viewRatio;
    private CardView ErRsCardContainer;
    public LinearLayout magicWind, eraser, eraserDone, mirror, zoom;
    public TextView magicWindTxt, eraserTxt, eraserDoneTxt, mirrorTxt, zoomTxt;
    public ImageView magicWindImg, eraserImg, eraserDoneImg, mirrorImg, zoomImg;
    ImageView eraserSubButton, unEraserSubButton;
    ImageView brushSize1Button, brushSize2Button, brushSize3Button, brushSize4Button;
    ImageView magicRemoveButton, magicRestoreButton;
    ImageView undoButton, redoButton;
    ImageView colorButton, magicAImg;
    public SeekBar magicSeekBar, brushSizeSeekBar, brushOffsetSeekBar;
    public RelativeLayout eraserLayout, magicLayout, zoomLayout;
    RelativeLayout mLayout;
    LinearLayout backToImgCrop;
    private Bitmap resultingImage;
    private boolean crop;
    private AdView adViewBanner5;
    private FrameLayout adViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eraser_dummy);
        mContentResolver = getContentResolver();
        eraserActivity = this;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (getIntent().hasExtra("cmgFrom")) {
            if (getIntent().getStringExtra("cmgFrom").equals("category")) {
                if (ChangeBackgroundData.getChangeBackgroundData().getCategoriesActivity() != null) {
                    mBitmap = ChangeBackgroundData.getChangeBackgroundData().getCategoriesActivity().getSavedBitmap();//null
                }
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "bgEraser");
                bundle.putString("Activity", "EraserActivity");
                if (mFirebaseAnalytics != null)
                    mFirebaseAnalytics.logEvent("bgEraserCategory", bundle);
            }
        } else {
            if (croppedBitmap != null) {
                getBitmap();
                mBitmap = resultingImage;
//                if(MyUtils.getBgRemovedBitmap != null) {
//                    mBitmap = (Bitmap) MyUtils.getBgRemovedBitmap;
//                }
            } else {
                //mBitmap = croppedImage;
//                if(MyUtils.getBgRemovedBitmap != null) {
//                    mBitmap = (Bitmap) MyUtils.getBgRemovedBitmap;
//                }
                mBitmap = CommonMethods.getInstance().getBgRemovedBitmap();
            }
        }

        /*adViewContainer = findViewById(R.id.eraser_banner_ad);
        adViewContainer.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adViewBanner5, adViewContainer, EraserActivity.this);
            }
        });*/
        mLayout = findViewById(R.id.mainLayout);
        mDensity = getResources().getDisplayMetrics().density;
        actionBarHeight = (int) (110 * mDensity);
        bottombarHeight = (int) (60 * mDensity);
        viewWidth = getResources().getDisplayMetrics().widthPixels;
        viewHeight = getResources().getDisplayMetrics().heightPixels - actionBarHeight - bottombarHeight;
        viewRatio = (double) viewHeight / (double) viewWidth;
        if (mBitmap != null) {
            bmRatio = (double) mBitmap.getHeight() / (double) mBitmap.getWidth();
            if (bmRatio < viewRatio) {
                bmWidth = viewWidth;
                bmHeight = (int) (((double) viewWidth) * ((double) (mBitmap.getHeight()) / (double) (mBitmap.getWidth())));
            } else {
                bmHeight = viewHeight;
                bmWidth = (int) (((double) viewHeight) * ((double) (mBitmap.getWidth()) / (double) (mBitmap.getHeight())));
            }
            mBitmap = Bitmap.createScaledBitmap(mBitmap, bmWidth, bmHeight, false);
            mHoverView = new HoverView(this, mBitmap, bmWidth, bmHeight, viewWidth, viewHeight);
            mHoverView.setLayoutParams(new LayoutParams(viewWidth, viewHeight));
            mLayout.addView(mHoverView);
        }
        backToImgCrop = findViewById(R.id.back_to_img_crop);
        backToImgCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initButton();
    }

    private void getBitmap() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            crop = extras.getBoolean("crop");
        }
        int widthOfscreen = 0;
        int heightOfScreen = 0;

        DisplayMetrics dm = new DisplayMetrics();
        try {
            getWindowManager().getDefaultDisplay().getMetrics(dm);
        } catch (Exception ex) {
        }
        widthOfscreen = dm.widthPixels;
        heightOfScreen = dm.heightPixels;


        Bitmap bitmap2 = croppedBitmap;

        resultingImage = Bitmap.createBitmap(widthOfscreen,
                heightOfScreen, bitmap2.getConfig());

        Canvas canvas = new Canvas(resultingImage);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Path path = new Path();
        for (int i = 0; i < CropView.points.size(); i++) {
            path.lineTo(CropView.points.get(i).x, CropView.points.get(i).y);
        }
        canvas.drawPath(path, paint);
        if (crop) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        }
        canvas.drawBitmap(bitmap2, 0, 0, paint);
    }

    public void initButton() {

        ErRsCardContainer = findViewById(R.id.er_rs_cv_container);

        magicWind = findViewById(R.id.button_magic);
        eraser = findViewById(R.id.button_eraser);
        eraserDone = findViewById(R.id.button_eraser_done);
        mirror = findViewById(R.id.button_mirror);
        zoom = findViewById(R.id.button_zoom);

        magicWind.setOnClickListener(this);
        eraser.setOnClickListener(this);
        eraserDone.setOnClickListener(this);
        mirror.setOnClickListener(this);
        zoom.setOnClickListener(this);

        magicWindTxt = findViewById(R.id.magic_btn_txt);
        eraserTxt = findViewById(R.id.eraser_btn_txt);
        eraserDoneTxt = findViewById(R.id.eraser_done_btn_txt);
        mirrorTxt = findViewById(R.id.mirror_btn_txt);
        zoomTxt = findViewById(R.id.zoom_btn_txt);

        magicWindImg = findViewById(R.id.magic_button_img);
        eraserImg = findViewById(R.id.eraser_button_img);
        eraserDoneImg = findViewById(R.id.eraser_done_button_img);
        mirrorImg = findViewById(R.id.mirror_button_img);
        zoomImg = findViewById(R.id.zoom_button_img);

        magicAImg = findViewById(R.id.magic_button_img);

        eraserSubButton = findViewById(R.id.erase_sub_button);
        eraserSubButton.setOnClickListener(this);
        unEraserSubButton = findViewById(R.id.unerase_sub_button);
        unEraserSubButton.setOnClickListener(this);

        brushSizeSeekBar = findViewById(R.id.brush_siz_seekbar);
        brushSizeSeekBar.incrementProgressBy(20);
        brushSizeSeekBar.setMax(200);
        brushSizeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar != null) {
                    if (mHoverView != null) {
                        mHoverView.setEraseBrushSize(seekBar.getProgress());
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

        brushOffsetSeekBar = findViewById(R.id.brush_offset_seekbar);
        brushOffsetSeekBar.incrementProgressBy(20);
        brushOffsetSeekBar.setMax(200);
        brushOffsetSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar != null) {
                    if (mHoverView != null) {
                        mHoverView.setPointerOffset(seekBar.getProgress());
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
        magicSeekBar = findViewById(R.id.magic_seekbar);
        magicSeekBar.setProgress(15);
        magicSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mHoverView != null) {
                    mHoverView.setMagicThreshold(seekBar.getProgress());
                    if (mHoverView.getMode() == mHoverView.MAGIC_MODE)
                        mHoverView.magicEraseBitmap();//null
                    else if (mHoverView.getMode() == mHoverView.MAGIC_MODE_RESTORE)
                        mHoverView.magicRestoreBitmap();
                    mHoverView.invalidateView();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
        });
        magicRemoveButton = findViewById(R.id.magic_remove_button);
        magicRemoveButton.setOnClickListener(this);
        magicRestoreButton = findViewById(R.id.magic_restore_button);
        magicRestoreButton.setOnClickListener(this);
        undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(this);
        undoButton.setColorFilter(getResources().getColor(R.color.white));

        redoButton = findViewById(R.id.redoButton);
        redoButton.setOnClickListener(this);
        redoButton.setColorFilter(getResources().getColor(R.color.white));
        updateRedoButton();
        eraserLayout = findViewById(R.id.eraser_layout);
        magicLayout = findViewById(R.id.magicWand_layout);
        zoomLayout = findViewById(R.id.zoom_layout);

        colorButton = findViewById(R.id.colorButton);
        colorButton.setOnClickListener(this);

        if (mHoverView != null)
            mHoverView.switchMode(HoverView.ERASE_MODE);
        resetSubEraserButtonState();
        eraserSubButton.setSelected(true);
    }

    public void resetSeekBar() {
        magicSeekBar.setProgress(0);
        if (mHoverView != null) {
            mHoverView.setMagicThreshold(0);
        }
    }

    int currentColor = 0;

    public void setBackGroundColor(int color) {
        switch (color) {
            case 0:
                mLayout.setBackgroundResource(R.drawable.bg);
                colorButton.setBackgroundResource(R.drawable.white_drawable);
                break;
            case 1:
                mLayout.setBackgroundColor(Color.WHITE);
                colorButton.setBackgroundResource(R.drawable.black_drawable);
                break;
            case 2:
                mLayout.setBackgroundColor(Color.BLACK);
                colorButton.setBackgroundResource(R.drawable.transparent_drawable);
                break;

            default:
                break;
        }

        currentColor = color;
    }

    public void resetSubEraserButtonState() {
        eraserSubButton.setSelected(false);
        unEraserSubButton.setSelected(false);
    }

    public void resetSubMagicButtonState() {
        magicRemoveButton.setSelected(false);
        magicRestoreButton.setSelected(false);
    }

    public void updateUndoButton() {
        if (mHoverView != null) {
            if (mHoverView.checkUndoEnable()) {
                undoButton.setEnabled(true);
                undoButton.setAlpha(1.0f);
            } else {
                undoButton.setEnabled(false);
                undoButton.setAlpha(0.3f);
            }
        }
    }

    public void updateRedoButton() {
        if (mHoverView != null) {
            if (mHoverView.checkRedoEnable()) {
                redoButton.setEnabled(true);
                redoButton.setAlpha(1.0f);
            } else {
                redoButton.setEnabled(false);
                redoButton.setAlpha(0.3f);
            }
        }
    }

    @Override
    public void onClick(View v) {
        updateUndoButton();
        updateRedoButton();
        switch (v.getId()) {
            case R.id.button_eraser:
                zoomLayout.setVisibility(View.GONE);
                if (mHoverView != null) {
                    mHoverView.switchMode(mHoverView.ERASE_MODE);  //null
                }
                if (eraserLayout.getVisibility() == View.VISIBLE) {
                    eraserLayout.setVisibility(View.GONE);
                } else {
                    eraserLayout.setVisibility(View.VISIBLE);
                }
                magicLayout.setVisibility(View.GONE);
                ErRsCardContainer.setVisibility(View.VISIBLE);
                resetSubEraserButtonState();
                eraserSubButton.setSelected(true);
                if (ChangeBackgroundData.getChangeBackgroundData() != null) {
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(EraserActivity.this, eraserImg, eraserTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, magicWindImg, magicWindTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, eraserDoneImg, eraserDoneTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, mirrorImg, mirrorTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, zoomImg, zoomTxt);
                }
                break;
            case R.id.button_magic:
                zoomLayout.setVisibility(View.GONE);
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.MAGIC_MODE);//null
                }
                if (magicLayout.getVisibility() == View.VISIBLE) {
                    magicLayout.setVisibility(View.GONE);
                } else {
                    magicLayout.setVisibility(View.VISIBLE);
                }
                eraserLayout.setVisibility(View.GONE);
                ErRsCardContainer.setVisibility(View.VISIBLE);
                resetSubMagicButtonState();
                resetSeekBar();
                magicRemoveButton.setSelected(true);
                if (ChangeBackgroundData.getChangeBackgroundData() != null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, eraserImg, eraserTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(EraserActivity.this, magicWindImg, magicWindTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, eraserDoneImg, eraserDoneTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, mirrorImg, mirrorTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, zoomImg, zoomTxt);
                }
                break;
            case R.id.button_mirror:
                zoomLayout.setVisibility(View.GONE);
                ErRsCardContainer.setVisibility(View.GONE);
                findViewById(R.id.eraser_layout).setVisibility(View.GONE);
                findViewById(R.id.magicWand_layout).setVisibility(View.GONE);
                if (mHoverView != null) {
                    mHoverView.mirrorImage();  //OutOfMemoryError
                }
                if (ChangeBackgroundData.getChangeBackgroundData() != null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, eraserImg, eraserTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, magicWindImg, magicWindTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, eraserDoneImg, eraserDoneTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(EraserActivity.this, mirrorImg, mirrorTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, zoomImg, zoomTxt);
                }
                break;
            case R.id.button_zoom:
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.MOVING_MODE);
                }
                findViewById(R.id.magicWand_layout).setVisibility(View.GONE);
                findViewById(R.id.zoom_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.eraser_layout).setVisibility(View.GONE);
                ErRsCardContainer.setVisibility(View.GONE);

                if (ChangeBackgroundData.getChangeBackgroundData() != null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, eraserImg, eraserTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, magicWindImg, magicWindTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, eraserDoneImg, eraserDoneTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, mirrorImg, mirrorTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(EraserActivity.this, zoomImg, zoomTxt);
                }
                break;

            case R.id.erase_sub_button:
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.ERASE_MODE);//null
                }
                resetSubEraserButtonState();
                eraserSubButton.setSelected(true);
                break;
            case R.id.unerase_sub_button:
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.UNERASE_MODE);//null
                }
                resetSubEraserButtonState();
                unEraserSubButton.setSelected(true);
                break;
            case R.id.magic_remove_button:
                resetSubMagicButtonState();
                magicRemoveButton.setSelected(true);
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.MAGIC_MODE);
                }
                resetSeekBar();
                break;

            case R.id.magic_restore_button:
                resetSubMagicButtonState();
                magicRestoreButton.setSelected(true);
                if (mHoverView != null) {
                    mHoverView.switchMode(HoverView.MAGIC_MODE_RESTORE);
                }
                resetSeekBar();
                break;

            case R.id.button_eraser_done:
                if (ChangeBackgroundData.getChangeBackgroundData() != null) {
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, eraserImg, eraserTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, magicWindImg, magicWindTxt);
                    ChangeBackgroundData.getChangeBackgroundData().setColorFilter(EraserActivity.this, eraserDoneImg, eraserDoneTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, mirrorImg, mirrorTxt);
                    ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, zoomImg, zoomTxt);
                }
                if (mHoverView != null) {
                    mHoverView.saveDrawnBitmap();
                    if (CommonMethods.getInstance().getFromDemo() != null) {
                        if (!CommonMethods.getInstance().getFromDemo().equals("")) {
                            if (CommonMethods.getInstance().getFromDemo().equals("fromEraser")) {
                                Intent intent = new Intent(getApplicationContext(), bg_eraser_save.class);
//                                intent.putExtra("category", "dresses");
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                                startActivity(intent);
                            }
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                            startActivity(intent);
                        }
                    }
                }


//                    case R.id.bg_eraser:
//                        Intent intent = new Intent(getApplicationContext(), bg_eraser_save.class);
//                        startActivity(intent);
//                        break;
//
//                    case R.id.bg_changer:
//                        Intent i = new Intent(getApplicationContext(), CategoriesActivity.class);
//                        startActivity(i);
//                        break;
//
//                    case R.id.suit_styles:
//                        Intent intent1 = new Intent(getApplicationContext(), CategoriesActivity.class);
//                        startActivity(intent1);
//                        break;
//                String value = getIntent().getStringExtra("IMGAEFROM");
//                if (value != null) {
//                    if (value.equals("IMAGECROP")) {
//                        Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
//                        intent.putExtra("FROM",value);
//                        startActivity(intent);
//                    }
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
//                    startActivity(intent);
//                }
//                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
//                startActivity(intent);
                break;

            case R.id.colorButton:
                setBackGroundColor((currentColor + 1) % 3);
                break;

            case R.id.undoButton:
                findViewById(R.id.eraser_layout).setVisibility(View.GONE);
                findViewById(R.id.magicWand_layout).setVisibility(View.GONE);
                if (mHoverView != null) {
                    mHoverView.undo();
                    if (mHoverView.checkUndoEnable()) {
                        undoButton.setEnabled(true);
                        undoButton.setAlpha(1.0f);
                    } else {
                        undoButton.setEnabled(false);
                        undoButton.setAlpha(0.3f);
                    }
                }
                updateRedoButton();
                break;
            case R.id.redoButton:
                findViewById(R.id.eraser_layout).setVisibility(View.GONE);
                findViewById(R.id.magicWand_layout).setVisibility(View.GONE);
                if (mHoverView != null) {
                    mHoverView.redo();//null
                }
                updateUndoButton();
                updateRedoButton();
                break;
        }
    }


    public void showSaveWorkDialog(final Bitmap bitmap) {

//        Intent intent = new Intent(getApplicationContext(), bg_eraser_save.class);
//        startActivity(intent);

//        final Dialog dialog = new Dialog(EraserActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setContentView(R.layout.save_work_dialog);
//        TextView msgTxt = dialog.findViewById(R.id.dialog_msg);
//        TextView msgSubTxt = dialog.findViewById(R.id.dialog_msg_sub);
//        msgTxt.setText("Background Erased");
//        msgSubTxt.setText("Wanna Add background? \nClick Next or Save the Image");
//        TextView dialogNext = dialog.findViewById(R.id.dialog_next);
//        dialogNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // save(bitmap);
//                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
//                startActivity(intent);
//                dialog.dismiss();
//            }
//        });
//        TextView dialogSaveWork = dialog.findViewById(R.id.dialog_save_work);
//        dialogSaveWork.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveWork(bitmap);
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
    }

    private void saveWork(Bitmap bitmap) {
        String filename = "img_" + String.format("%d.jpg", System.currentTimeMillis());
        String path = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) ?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/PrevSavedImages"
                : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/PrevSavedImages";
        // String path = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/PrevSavedImages";
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

        Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
        startActivity(intent);
//        MyInterstitialAdView.getInstance().activitiesAD(CategoriesActivity.this);
        finish();
    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

//    private void save(Bitmap bitmap)
//    {
//        int a = ImageUtils.dpToPx((Context) BGRemover.this, 42);
//        bitmap = ImageUtils.resizeBitmap(bitmap, orgBitWidth + a + a, orgBitHeight + a + a);
//        int i = a + a;
//        bitmap = Bitmap.createBitmap(bitmap, a, a, bitmap.getWidth() - i, bitmap.getHeight() - i);
//        bitmap = Bitmap.createScaledBitmap(bitmap, orgBitWidth, orgBitHeight, true);
//        bitmap = ImageUtils.maskBitmap(orgBitmap, bitmap);
//        if (bitmap == null) {
//            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
//        } else {
//            BitmapListData.m11922b().setListValue(bitmap.copy(bitmap.getConfig(), true));
//            savedBitmap = bitmap;
//
//            savedBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.4), (int) (bitmap.getHeight() * 0.4), true);
//            Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
//            startActivity(intent);
////                            startActivityForResult(new Intent(this, AdvanceEditActivity.class),1111);
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (FreeImageCropActivity != null && ChangeBackgroundData.getChangeBackgroundData() != null) {
            ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, FreeImageCropActivity.freeDoneImg, FreeImageCropActivity.freeDoneTxt);
            ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, FreeImageCropActivity.freeRotateImg, FreeImageCropActivity.freeRotateTxt);
        }
        if (ChangeBackgroundData.getChangeBackgroundData().getDoneImg() != null && ChangeBackgroundData.getChangeBackgroundData().getDoneTxt() != null) {
            ChangeBackgroundData.getChangeBackgroundData().removeColorFilter(EraserActivity.this, ChangeBackgroundData.getChangeBackgroundData().getDoneImg(), ChangeBackgroundData.getChangeBackgroundData().getDoneTxt());
        }
//        MyInterstitialAdView.getInstance().activitiesAD(EraserActivity.this);
        // setResult(Activity.RESULT_CANCELED);
        // if(CommonMethods.getInstance().getMainActivityObject() != null) CommonMethods.getInstance().getMainActivityObject().finish();
    }

}
