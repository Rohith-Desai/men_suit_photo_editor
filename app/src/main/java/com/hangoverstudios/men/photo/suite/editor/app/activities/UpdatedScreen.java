package com.hangoverstudios.men.photo.suite.editor.app.activities;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.model.PhotoData;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.views.WrapContentViewPager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdatedScreen extends AppCompatActivity {

    CardView bg_eraser, bg_changer, suit_styles;
    ImageView slide_show, view_all;
    private PhotoData photoData;
    private int[] layouts;
    boolean savedGallery = false;
    public static final int PICK_IMAGE = 1;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 201;
    private static final int PERMISSION__STORAGE_REQUEST_CODE = 200;
    private static final int PERMISSION_COLLAGE_EDITOR = 11;
    private static final int CAMERA_REQUEST = 2;
    Dialog dialog;
    int OPEN_APP_FREQUENCY;
    String fromOption = "";
    boolean camera = false;
    WrapContentViewPager viewPager;
    private ViewPagerAdapterMain viewPagerAdapterMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_screen);

        bg_eraser = findViewById(R.id.bg_eraser);
        bg_changer = findViewById(R.id.bg_changer);
        suit_styles = findViewById(R.id.suit_styles);
        slide_show = findViewById(R.id.select_photo);
        view_all = findViewById(R.id.view_all);

        AnimationDrawable animationDrawable = (AnimationDrawable) slide_show.getDrawable();
        animationDrawable.start();

        dialog = new Dialog(this);
        slide_show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fromOption = "editImage";
                selectPhoto("toEdit");
                CommonMethods.getInstance().setFromDemo("");
            }
        });

        view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedGallery = true;
                if (checkStoragePermission()) {
                    Intent intent = new Intent(UpdatedScreen.this, GalleryActivity.class);
                    intent.putExtra("prev_save", false);
                    startActivity(intent);
                } else {
                    requestStoragePermission(0);
                }
            }
        });

        viewPager = (WrapContentViewPager) findViewById(R.id.native_pager);
        viewPagerAdapterMain = new ViewPagerAdapterMain();
        viewPager.setAdapter(viewPagerAdapterMain);
    }

    public void selectPhoto(String from) {
        //startActivity(new Intent(MainActivity.this,com.hangoverstudios.men.photo.suite.editor.app.bgremoverlite.MainActivity.class));
        String str = "firstpage_select";
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.gallery_dialog);
        LinearLayout galleryImage = dialog.findViewById(R.id.linear_gallery);
        LinearLayout preSavedImage = dialog.findViewById(R.id.linear_crop_saved);
        if(from.equals("toEdit")){
            preSavedImage.setVisibility(View.VISIBLE);
        }else{
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
                    Intent intent = new Intent(UpdatedScreen.this, GalleryActivity.class);
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

    private void takePhoto() {
        File photo = null;
        try {
            photo = createImageFiles();
            Uri photoURI = FileProvider.getUriForFile(UpdatedScreen.this, getApplicationContext().getPackageName() + ".provider", photo);
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