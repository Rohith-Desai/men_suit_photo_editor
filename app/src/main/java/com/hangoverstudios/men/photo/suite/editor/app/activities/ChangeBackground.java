package com.hangoverstudios.men.photo.suite.editor.app.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.hangoverstudios.men.photo.suite.editor.app.ads.MyInterstitialAdView;
import com.hangoverstudios.men.photo.suite.editor.app.model.PhotoData;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.ChangeBackgroundAdapter;
import com.hangoverstudios.men.photo.suite.editor.app.interfaces.ChangeBackgroundSaveImage;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;
import com.hangoverstudios.men.photo.suite.editor.app.views.HoverView;
import com.hangoverstudios.men.photo.suite.editor.app.views.SuitDrawView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.EraserActivity.eraserActivity;
import static com.hangoverstudios.men.photo.suite.editor.app.views.HoverView.savedBitmap;
//import static com.hangoverstudios.men.photo.suite.editor.app.activities.CategoriesActivity.categoriesActivity;

public class ChangeBackground extends AppCompatActivity implements ChangeBackgroundSaveImage {


    public static final int COLOR_BACKGROUND = 0;
    public static final int CUSTOM_BACKGROUND = 1;
    public static final int IMAGE_BACKGROUND = 2;

    private LinearLayout linearBackToCategory;
    private LinearLayout linearSaveDone;
    private RelativeLayout relativeMainSaveCanvas;
    private ImageView foregroundImageView;
    private View backgroundImageView;
    //public static ChangeBackground changeBackground;

    private RecyclerView imageRecyclerViewGrid;
    private RecyclerView imageRecyclerView;
    private TextView colorBackgroundTxt;
    private TextView customBackgroundTxt;
    private TextView imageBackgroundTxt;
    private FrameLayout adContainerView;
    private ChangeBackgroundAdapter changeBackgroundAdapter;
    private AlertDialog dialog;
    public final int PICK_IMAGE = 101;
    private final int CAMERA_REQUEST = 201;
    private Uri imageUri;
    private Bitmap selectedImageBitmap;
    private AdView adViewBanner9;
    CategoriesActivity categoriesActivity;

    private PhotoData photoData;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);

        categoriesActivity = ChangeBackgroundData.getChangeBackgroundData().getCategoriesActivity();
        //changeBackground = this;
        photoData = new PhotoData(this);

        /*adContainerView = findViewById(R.id.background_banner_ad);
        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adViewBanner9, adContainerView, ChangeBackground.this);
            }
        });*/
        initializeViews();
    }

    private void initializeViews() {

        linearBackToCategory = findViewById(R.id.linear_back_to_categories);
        linearSaveDone = findViewById(R.id.background_save_done);
        relativeMainSaveCanvas = findViewById(R.id.main_rel_save_canvas);
        foregroundImageView = findViewById(R.id.foreground_img);
        backgroundImageView = findViewById(R.id.background_img);
        ChangeBackgroundData.getChangeBackgroundData().setBackgroundImageView(backgroundImageView);
        imageRecyclerViewGrid = findViewById(R.id.image_recycler_view_2);
        imageRecyclerView = findViewById(R.id.image_recycler_view);
        colorBackgroundTxt = findViewById(R.id.color_background);
        customBackgroundTxt = findViewById(R.id.custom_background);
        imageBackgroundTxt = findViewById(R.id.image_background);

        ChangeBackgroundData.getChangeBackgroundData().setImageRecyclerView(imageRecyclerView);


        mDensity = getResources().getDisplayMetrics().density;
        actionBarHeight = (int) (70 * mDensity);
        bottombarHeight = (int) (60 * mDensity);
        viewWidth = getResources().getDisplayMetrics().widthPixels;
        viewHeight = getResources().getDisplayMetrics().heightPixels;
        //viewHeight = getResources().getDisplayMetrics().heightPixels - actionBarHeight - bottombarHeight;
        viewRatio = (double) viewHeight / (double) viewWidth;

        /*relativeMainSaveCanvas.setDrawingCacheEnabled(true);
        relativeMainSaveCanvas.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        relativeMainSaveCanvas.layout(0, 0, relativeMainSaveCanvas.getMeasuredWidth(), relativeMainSaveCanvas.getMeasuredHeight());
        relativeMainSaveCanvas.buildDrawingCache(true);*/
        Bitmap catSavedBitmap = null;
        if (categoriesActivity != null) {
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
            relativeMainSaveCanvas.addView(mSuitDrawView);
            mSuitDrawView.switchMode(SuitDrawView.MOVING_MODE);
        }

        if (categoriesActivity != null) {
            Drawable d = new BitmapDrawable(getResources(), categoriesActivity.getSavedBitmap());
//            foregroundImageView.setImageDrawable(d);
        }

        linearBackToCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linearSaveDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearSaveDone.setClickable(false);
                saveImage();
            }
        });

        colorBackgroundTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                colorBackgroundTxt.setBackground(ContextCompat.getDrawable(ChangeBackground.this, R.drawable.change_background_btn_back_right));
                customBackgroundTxt.setBackground(ContextCompat.getDrawable(ChangeBackground.this, R.drawable.change_background_btn_back_white));
                imageBackgroundTxt.setBackground(ContextCompat.getDrawable(ChangeBackground.this, R.drawable.change_background_btn_back_white));


                colorBackgroundTxt.setTextColor(getResources().getColor(R.color.white));
                customBackgroundTxt.setTextColor(getResources().getColor(R.color.black));
                imageBackgroundTxt.setTextColor(getResources().getColor(R.color.black));

                linearSaveDone.setVisibility(View.VISIBLE);
                imageRecyclerView.setVisibility(View.VISIBLE);
                imageRecyclerViewGrid.setVisibility(View.GONE);
                setAdapter(COLOR_BACKGROUND);

            }
        });

        customBackgroundTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customBackgroundTxt.setBackground(ContextCompat.getDrawable(ChangeBackground.this, R.drawable.change_backhround_btn_back));
                colorBackgroundTxt.setBackground(ContextCompat.getDrawable(ChangeBackground.this, R.drawable.change_background_btn_back_white));
                imageBackgroundTxt.setBackground(ContextCompat.getDrawable(ChangeBackground.this, R.drawable.change_background_btn_back_white));


                linearSaveDone.setVisibility(View.VISIBLE);
                customBackgroundTxt.setTextColor(getResources().getColor(R.color.white));
                colorBackgroundTxt.setTextColor(getResources().getColor(R.color.black));
                imageBackgroundTxt.setTextColor(getResources().getColor(R.color.black));
                imageRecyclerView.setVisibility(View.GONE);
                imageRecyclerViewGrid.setVisibility(View.GONE);
                imagePicker();
            }
        });

        imageBackgroundTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageBackgroundTxt.setBackground(ContextCompat.getDrawable(ChangeBackground.this, R.drawable.change_background_btn_back_left));
                customBackgroundTxt.setBackground(ContextCompat.getDrawable(ChangeBackground.this, R.drawable.change_background_btn_back_white));
                colorBackgroundTxt.setBackground(ContextCompat.getDrawable(ChangeBackground.this, R.drawable.change_background_btn_back_white));


                imageBackgroundTxt.setTextColor(getResources().getColor(R.color.white));
                customBackgroundTxt.setTextColor(getResources().getColor(R.color.black));
                colorBackgroundTxt.setTextColor(getResources().getColor(R.color.black));

                linearSaveDone.setVisibility(View.VISIBLE);
                imageRecyclerViewGrid.setVisibility(View.VISIBLE);
                imageRecyclerView.setVisibility(View.GONE);
                setAdapter(IMAGE_BACKGROUND);
                setGridImageAdapter();

            }
        });

        setGridImageAdapter();
    }

 /*   public void saveImage() {
        Bitmap b = Bitmap.createBitmap(relativeMainSaveCanvas.getDrawingCache());
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);
        if (categoriesActivity != null) {
            categoriesActivity.updateSavedBitmap(resizedBitmap);
        }
        // CommonMethods.getInstance().addToBitmapEventQueue((String) retrieveElement(localEventQueue.size() - 1, localEventQueue));
//        MyInterstitialAdView.getInstance().activitiesAD(SetSuitActivity.this);
        onBackPressed();
    }*/

    private void imagePicker() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        final View customLayout = getLayoutInflater().inflate(R.layout.image_picker_card, null);
        ImageView galleryPic = customLayout.findViewById(R.id.galleryPic);
        ImageView cameraPic = customLayout.findViewById(R.id.cameraPic);
        ImageView closeDialog = customLayout.findViewById(R.id.dialog_close_btn);
        builder.setView(customLayout);
        galleryPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermission()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE);
                } else {
                    requestStoragePermission();
                }
                if (dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCameraPermission() && checkStoragePermission()) {
                    launchCamera();
                } else {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else if (!checkStoragePermission()) {
                        requestStoragePermission();
                    }
                }
                if (dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void launchCamera() {
        if (checkCameraPermission())
            takePhoto();
        else
            requestCameraPermission();
    }

    public boolean checkStoragePermission() {
        int result = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void requestStoragePermission() {
        int PERMISSION_STORAGE_REQUEST_CODE = 200;
        ActivityCompat.requestPermissions((Activity) this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE_REQUEST_CODE);
    }

    public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            return false;
        }
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestCameraPermission() {
        int PERMISSION_CAMERA_REQUEST_CODE = 201;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST_CODE);
    }

    private void setAdapter(int type) {
        String str = "bgph";
        ArrayList<Object> backgroundList = new ArrayList();
        int colorDefault = getResources().getColor(R.color.white);
        int colorSelected = getResources().getColor(R.color.transparent);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(layoutManager);

        if (type == IMAGE_BACKGROUND) {
            for (int i = 1; i <= 10; i++) {
                backgroundList.add(ChangeBackground.this.getResources().getIdentifier(str + "_" + i, "drawable", ChangeBackground.this.getPackageName()));
            }
            changeBackgroundAdapter = new ChangeBackgroundAdapter(ChangeBackground.this, backgroundList, IMAGE_BACKGROUND);
            imageRecyclerView.setAdapter(changeBackgroundAdapter);
        } else if (type == COLOR_BACKGROUND) {
            String colors[] = new String[]{"#FFFFFF", "#EFDECD", "#CD4A4A", "#CC6666", "#BC5D58", "#FF5349", "#FD5E53", "#FD7C6E", "#FDBCB4", "#FF6E4A", "#FFA089", "#EA7E5D", "#B4674D", "#A5694F", "#FF7538", "#FF7F49", "#DD9475", "#FF8243", "#FFA474", "#9F8170", "#CD9575", "#EFCDB8", "#D68A59", "#DEAA88", "#FAA76C", "#FFCFAB", "#FFBD88", "#FDD9B5", "#FFA343", "#EFDBC5", "#FFB653", "#E7C697", "#8A795D", "#FAE7B5", "#FFCF48", "#FCD975", "#FDDB6D", "#FCE883", "#F0E891", "#ECEABE", "#BAB86C", "#FDFC74", "#FDFC74", "#FFFF99", "#C5E384", "#B2EC5D", "#87A96B", "#A8E4A0", "#1DF914", "#76FF7A", "#71BC78", "#6DAE81", "#9FE2BF", "#1CAC78", "#30BA8F", "#45CEA2", "#3BB08F", "#1CD3A2", "#17806D", "#158078", "#1FCECB", "#78DBE2", "#77DDE7", "#80DAEB", "#414A4C", "#199EBD", "#1CA9C9", "#1DACD6", "#9ACEEB", "#1A4876", "#1974D2", "#2B6CC4", "#1F75FE", "#C5D0E6", "#B0B7C6", "#5D76CB", "#A2ADD0", "#979AAA", "#ADADD6", "#7366BD", "#7442C8", "#7851A9", "#9D81BA", "#926EAE", "#CDA4DE", "#8F509D", "#C364C5", "#FB7EFD", "#FC74FD", "#8E4585", "#FF1DCE", "#FF1DCE", "#FF48D0", "#E6A8D7", "#C0448F", "#6E5160", "#DD4492", "#FF43A4", "#F664AF", "#FCB4D5", "#FFBCD9", "#F75394", "#FFAACC", "#E3256B", "#FDD7E4", "#CA3767", "#DE5D83", "#FC89AC", "#F780A1", "#C8385A", "#EE204D", "#FF496C", "#EF98AA", "#FC6C85", "#FC2847", "#FF9BAA", "#CB4154", "#EDEDED", "#DBD7D2", "#CDC5C2", "#95918C", "#232323"};
            // String colors[] = new String[]{"FFFFFF", "EFDECD", "CD4A4A", "CC6666", "BC5D58", "FF5349", "FD5E53", "FD7C6E", "FDBCB4", "FF6E4A", "FFA089", "EA7E5D", "B4674D", "A5694F", "FF7538", "FF7F49", "DD9475", "FF8243", "FFA474", "9F8170", "CD9575", "EFCDB8", "D68A59", "DEAA88", "FAA76C", "FFCFAB", "FFBD88", "FDD9B5", "FFA343", /*"#EFDBC5", "#FFB653", "#E7C697", "#8A795D", "#FAE7B5", "#FFCF48", "#FCD975", "#FDDB6D", "#FCE883", "#F0E891", "#ECEABE", "#BAB86C", "#FDFC74", "#FDFC74", "#FFFF99", "#C5E384", "#B2EC5D", "#87A96B", "#A8E4A0", "#1DF914", "#76FF7A", "#71BC78", "#6DAE81", "#9FE2BF", "#1CAC78", "#30BA8F", "#45CEA2", "#3BB08F", "#1CD3A2", "#17806D", "#158078", "#1FCECB", "#78DBE2", "#77DDE7", "#80DAEB", "#414A4C", "#199EBD", "#1CA9C9", "#1DACD6", "#9ACEEB", "#1A4876", "#1974D2", "#2B6CC4", "#1F75FE", "#C5D0E6", "#B0B7C6", "#5D76CB", "#A2ADD0", "#979AAA", "#ADADD6", "#7366BD", "#7442C8", "#7851A9", "#9D81BA", "#926EAE", "#CDA4DE", "#8F509D", "#C364C5", "#FB7EFD", "#FC74FD", "#8E4585", "#FF1DCE", "#FF1DCE", "#FF48D0", "#E6A8D7", "#C0448F", "#6E5160", "#DD4492", "#FF43A4", "#F664AF", "#FCB4D5", "#FFBCD9", "#F75394", "#FFAACC", "#E3256B", "#FDD7E4", "#CA3767", "#DE5D83", "#FC89AC", "#F780A1", "#C8385A", "#EE204D", "#FF496C", "#EF98AA", "#FC6C85", "#FC2847", "#FF9BAA", "#CB4154", "#EDEDED", "#DBD7D2", "#CDC5C2", "#95918C", "#232323"*/};
            for (int i = 0; i < colors.length; i++) {
                backgroundList.add(colors[i]);
            }
           /* colorRecyclerView.setAdapter(new ColorPickerAdapter(new CollageImageAdapter.CurrentCollageIndexChangedListener() {
                @Override
                public void onIndexChanged(int color) {
                    backgroundImageView.setBackgroundColor(color);
                }
            }, colorDefault, colorSelected));*/
            changeBackgroundAdapter = new ChangeBackgroundAdapter(ChangeBackground.this, backgroundList, COLOR_BACKGROUND);
            imageRecyclerView.setAdapter(changeBackgroundAdapter);
        }
    }

    private void setGridImageAdapter() {

        String str = "bgph";
        ArrayList<Object> backgroundList = new ArrayList();
        int colorDefault = getResources().getColor(R.color.white);
        int colorSelected = getResources().getColor(R.color.transparent);


        for (int i = 1; i <= 10; i++) {
            backgroundList.add(ChangeBackground.this.getResources().getIdentifier(str + "_" + i, "drawable", ChangeBackground.this.getPackageName()));
        }

        changeBackgroundAdapter = new ChangeBackgroundAdapter(ChangeBackground.this, backgroundList, 3);

        LinearLayoutManager gridLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 1;
//            }
//        });

        imageRecyclerViewGrid.setLayoutManager(gridLayoutManager);
        imageRecyclerViewGrid.setHasFixedSize(true);
        imageRecyclerViewGrid.setItemViewCacheSize(20);
        imageRecyclerViewGrid.setDrawingCacheEnabled(true);
        imageRecyclerViewGrid.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        imageRecyclerViewGrid.setAdapter(changeBackgroundAdapter);
    }

    private void takePhoto() {
        File photo = null;
        try {
            photo = createImageFiles();
            Uri photoURI = FileProvider.getUriForFile(ChangeBackground.this, getApplicationContext().getPackageName() + ".provider", photo);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //isGalleryAd = true;
            if (requestCode == PICK_IMAGE) {
                //TODO: action
                if (data != null) {
                    imageUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        selectedImageBitmap = bitmap;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == CAMERA_REQUEST) {
                /*Bitmap bitmapFromMedia = null;
                File path = new File(Environment.getExternalStorageDirectory()
                        + "/Android/data/"
                        + getApplicationContext().getPackageName()
                        + "/temp data");
                if (!path.exists()) path.mkdirs();
                File imageFile = new File(path, "image_capture.jpg");

                Uri stringUri = Uri.fromFile(imageFile);

                try {
                    bitmapFromMedia = MediaStore.Images.Media.getBitmap(ChangeBackground.this.getContentResolver(), stringUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                Bitmap b = BitmapFactory.decodeFile(photoData.getUriPath());
               // Matrix matrix = new Matrix();
                //matrix.postRotate(90);
                if (b != null) {
                   // b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    selectedImageBitmap = b;
                }
            }

            if (selectedImageBitmap != null) {
                Drawable d = new BitmapDrawable(getResources(), selectedImageBitmap);
                backgroundImageView.setBackground(d);
                if(dialog!=null){
                    dialog.dismiss();
                }

            }

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        MyInterstitialAdView.getInstance().activitiesAD(ChangeBackground.this);
    }


    @Override
    public void saveImage() {
        ChangeBackgroundData.getChangeBackgroundData().setBackgroundApplied(true);
//        Bitmap b = Bitmap.createBitmap(relativeMainSaveCanvas.getDrawingCache());
        Bitmap b = getBitmapFromView(relativeMainSaveCanvas);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);
        if (categoriesActivity != null) {
            categoriesActivity.updateSavedBitmap(resizedBitmap);
        }
        // CommonMethods.getInstance().addToBitmapEventQueue((String) retrieveElement(localEventQueue.size() - 1, localEventQueue));
//        MyInterstitialAdView.getInstance().activitiesAD(SetSuitActivity.this);
        onBackPressed();
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

    @Override
    protected void onRestart() {
        linearSaveDone.setClickable(true);
        super.onRestart();
    }
}