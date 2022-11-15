package com.hangoverstudios.men.photo.suite.editor.app.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.hangoverstudios.men.photo.suite.editor.app.BuildConfig;
import com.hangoverstudios.men.photo.suite.editor.app.R;

import java.io.File;
import java.util.ArrayList;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.MainActivity.mainActivity;

public class SlideShowActivitymain extends Activity {
    private ViewFlipper myViewFlipper;
    private float initialXPoint;
    ArrayList<String> imgPaths = new ArrayList<>();
    String matchPath = null;
    int index;
    ImageView deleteImage, selectShare;
    LinearLayout selectBackToGallery;
    TextView imgName;
    String deletePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show_dummy);
        matchPath = getIntent().getStringExtra("matchPath");
        myViewFlipper = (ViewFlipper) findViewById(R.id.myflipper);
        if(mainActivity!=null){
            imgPaths = mainActivity.getImagesFromDevice();
            for (String key : imgPaths) {
                if (key.equals(matchPath)) {
                    index = imgPaths.indexOf(matchPath);
                    break;
                }
            }
            for (int i = 0; i < imgPaths.size(); i++) {
                updateImage(imgPaths.get(i), -1);
            }
            deletePath = imgPaths.get(myViewFlipper.getDisplayedChild());
            imgName = findViewById(R.id.img_name);
            imgName.setText(imgPaths.get(myViewFlipper.getDisplayedChild()).substring(imgPaths.get(myViewFlipper.getDisplayedChild()).lastIndexOf("/") + 1));
            deleteImage = findViewById(R.id.delete_image);
            selectShare = findViewById(R.id.select_share);
            selectBackToGallery = findViewById(R.id.select_back);
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deletePath != null) {
                        showDeleteDialog();
                    }
                }
            });
            selectBackToGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  mainActivity.updateList(false);
                    onBackPressed();
                }
            });
            selectShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareImage();
                }
            });
        }

    }

    private void updateImage(String path, int localIndex) {
        if (localIndex == -1) {
            ImageView image = new ImageView(getApplicationContext());
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(SlideShowActivitymain.this).load(path).into(image);
            myViewFlipper.addView(image);
            myViewFlipper.setDisplayedChild(index);
        } else {
            ImageView image = new ImageView(getApplicationContext());
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(SlideShowActivitymain.this).load(path).into(image);
            myViewFlipper.addView(image);
            myViewFlipper.setDisplayedChild(localIndex);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void shareImage() {
        ArrayList<Uri> files = new ArrayList<Uri>();
        ArrayList<String> deleteFile = new ArrayList<>();
        deleteFile.add(imgPaths.get(myViewFlipper.getDisplayedChild()));
        for (int i = 0; i < deleteFile.size(); i++) {

            Uri uri = FileProvider.getUriForFile(SlideShowActivitymain.this, BuildConfig.APPLICATION_ID + ".provider", new File(deleteFile.get(i)));
            files.add(uri);
        }
        Intent shareIntent;
        shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Man Suit Photo Editor : https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        startActivity(Intent.createChooser(shareIntent, "send"));
    }

    public void showDeleteDialog() {
        final Dialog dialog = new Dialog(SlideShowActivitymain.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_custom_dialog);
        TextView msgTxt = dialog.findViewById(R.id.dialog_msg);
        TextView msgSubTxt = dialog.findViewById(R.id.dialog_msg_sub);
        msgTxt.setText("Conform Delete.");
        msgSubTxt.setText("Are you sure want to delete selected items permanently.");
        TextView dialogOk = dialog.findViewById(R.id.dialog_ok);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeImage(imgPaths.get(myViewFlipper.getDisplayedChild()));
                if (imgPaths != null && imgPaths.size() > 0) {
                    myViewFlipper.removeViewAt(myViewFlipper.getDisplayedChild());
                } else {
                   // mainActivity.updateList(false);
                    onBackPressed();
                }
                dialog.dismiss();
            }
        });
        TextView dialogCancel = dialog.findViewById(R.id.dialog_cancel);
        dialogCancel.setVisibility(View.VISIBLE);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void removeImage(String path) {
        File fdelete = new File(path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                imgPaths = mainActivity.getImagesFromDevice();
                System.out.println("file Deleted :");
            } else {
                System.out.println("file not Deleted :");
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialXPoint = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalx = event.getX();
                if (initialXPoint > finalx) {
                    if (myViewFlipper.getDisplayedChild() == imgPaths.size() - 1)
                        break;
                    else {
                        myViewFlipper.showNext();
                        imgName.setText(imgPaths.get(myViewFlipper.getDisplayedChild()).substring(imgPaths.get(myViewFlipper.getDisplayedChild()).lastIndexOf("/") + 1));
                        deletePath = imgPaths.get(myViewFlipper.getDisplayedChild());
                    }
                } else {
                    if (myViewFlipper.getDisplayedChild() == 0)
                        break;
                    else {
                        myViewFlipper.showPrevious();
                        imgName.setText(imgPaths.get(myViewFlipper.getDisplayedChild()).substring(imgPaths.get(myViewFlipper.getDisplayedChild()).lastIndexOf("/") + 1));
                        deletePath = imgPaths.get(myViewFlipper.getDisplayedChild());
                    }
                }
                break;
        }
        return false;
    }
}
