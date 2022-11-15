package com.hangoverstudios.men.photo.suite.editor.app.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hangoverstudios.men.photo.suite.editor.app.BuildConfig;
import com.hangoverstudios.men.photo.suite.editor.app.ads.MyInterstitialAdView;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.ImageAdapter;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.nativead.NativeAd;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class GalleryActivity extends AppCompatActivity {
    ImageAdapter imageAdapter;
    public RecyclerView imageRecycler;
    ArrayList<String> imagesList;
    ImageView selectDelete, selectSelectAll, selectShare;
    LinearLayout selectBack;
    public TextView selectCount, noImagesToShow, txtTitle;
    public static GalleryActivity galleryActivity;
    public LinearLayout selectionOptions, toolOptions, backToHome;
    private NativeAd nativeAdGlobal;
    private AdView adViewBanner10;
    private FrameLayout adViewContainer;
    private RelativeLayout alternativeToNativeAd;
    ScrollView scrollViewNative;
    boolean isPrevSavedImage = false;
    public static final String subFolderName1 = "/Saved Images/";
    private static final String subFolderName2 = "/PrevSavedImages/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_dummy);
        noImagesToShow = findViewById(R.id.no_images_txt);
        txtTitle = findViewById(R.id.txt_title);

//        scrollViewNative = findViewById(R.id.native_ads_scroll);
//        alternativeToNativeAd = findViewById(R.id.alternative_to_native_ad_gall);
        galleryActivity = this;
        if (getIntent().hasExtra("prev_save"))
            isPrevSavedImage = getIntent().getBooleanExtra("prev_save", false);
        /*if (RemoteConfigValues.getOurRemote().getShowNativeAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowNativeAd().equals("true")) {
                if (RemoteConfigValues.getOurRemote().getAdRotationPolicyNative().equals("true")) {
                    alternativeToNativeAd.setVisibility(View.GONE);
                    scrollViewNative.setVisibility(View.VISIBLE);
                    admobAd();
                } else {
                    if (RemoteConfigValues.getOurRemote().getNativeOnlyGoogle().equals("true")) {
                        alternativeToNativeAd.setVisibility(View.GONE);
                        scrollViewNative.setVisibility(View.VISIBLE);
                        admobAd();
                    }
                }
            }
        }
        else
        {
            alternativeToNativeAd.setVisibility(View.VISIBLE);
            scrollViewNative.setVisibility(View.GONE);
        }*/
        /*adViewContainer = findViewById(R.id.gallery_banner_ad);
        adViewContainer.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adViewBanner10, adViewContainer, GalleryActivity.this);
            }
        });*/

        imageRecycler = findViewById(R.id.image_recycler);
        if (isPrevSavedImage) {
            txtTitle.setText("Edit Later");
            String path = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)?
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/" + getResources().getString(R.string.app_name) + "/PrevSavedImages"
                    :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/PrevSavedImages";
           // isDirectoryNotEmpty(Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/PrevSavedImages")
            if (isDirectoryNotEmpty(path)) {
                imagesList = getImagesFromDevice(subFolderName2);
                if (imagesList != null && imagesList.size() > 0) {
                    noImagesToShow.setVisibility(View.GONE);
                } else {
                    noImagesToShow.setVisibility(View.VISIBLE);
                }
                setImageAdapter(imagesList, false, isPrevSavedImage);
            } else {
                noImagesToShow.setVisibility(View.VISIBLE);
            }
        } else {
            String path = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)?
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/" + getResources().getString(R.string.app_name) + "/Saved Images"
                    :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + "/Saved Images";
           // isDirectoryNotEmpty(Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images")

            if (isDirectoryNotEmpty(path)) {
                imagesList = getImagesFromDevice(subFolderName1);
                if (imagesList != null && imagesList.size() > 0) {
                    noImagesToShow.setVisibility(View.GONE);
                } else {
                    noImagesToShow.setVisibility(View.VISIBLE);
                }
                setImageAdapter(imagesList, false, isPrevSavedImage);
            } else {
                noImagesToShow.setVisibility(View.VISIBLE);
            }
        }

        selectionOptions = findViewById(R.id.select_options);
        selectCount = findViewById(R.id.select_count);
        selectDelete = findViewById(R.id.select_delete);

        selectShare = findViewById(R.id.select_share);
        selectBack = findViewById(R.id.select_back_linear);
        selectSelectAll = findViewById(R.id.select_selectall);

        toolOptions = findViewById(R.id.tool_options);
        selectDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
        selectBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateList(false);
                selectCount.setText("selected : 0");
                selectionOptions.setVisibility(View.GONE);
                toolOptions.setVisibility(View.VISIBLE);
            }
        });
        selectShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < imageAdapter.selectedItems.size(); i++) {
                    shareImages();
                }
            }
        });
        selectSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageAdapter.selectAll) {
                    updateList(false);
                } else {
                    updateList(true);
                }
            }
        });

        backToHome = findViewById(R.id.back_to_home_linear);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setImageAdapter(final ArrayList<String> imageFilesList, boolean selectAll, boolean isPrevSavedImage) {
        imageAdapter = new ImageAdapter(GalleryActivity.this, imageFilesList, selectAll, isPrevSavedImage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        imageRecycler.setLayoutManager(gridLayoutManager);
        imageRecycler.setHasFixedSize(true);
        imageRecycler.setItemViewCacheSize(20);
        imageRecycler.setDrawingCacheEnabled(true);
        imageRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        imageRecycler.setAdapter(imageAdapter);
    }

    public ArrayList<String> getImagesFromDevice(String str) {
        final ArrayList<String> tempAudioList = new ArrayList<>();
        String directoryPath = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/" + getResources().getString(R.string.app_name) + str
                :Environment.getExternalStorageDirectory().toString()+ "/" + getResources().getString(R.string.app_name) + str;
       // String directoryPath = Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + str;
        File directory1 = new File(directoryPath);
        File[] files = directory1.listFiles();
        Arrays.sort(files, new Comparator() {
            public int compare(Object o1, Object o2) {

                if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                    return -1;
                } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            }
        });
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

    public void shareImages() {
        ArrayList<Uri> files = new ArrayList<Uri>();
        for (int i = 0; i < imageAdapter.selectedItems.size(); i++) {

            Uri uri = FileProvider.getUriForFile(GalleryActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(imagesList.get(imageAdapter.selectedItems.get(i))));
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

    public void showDeleteDialog() {
        final Dialog dialog = new Dialog(GalleryActivity.this);
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
                deleteAllSelectedItems();
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

    public void deleteAllSelectedItems() {
        if (imageAdapter.selectedItems.size() != 0) {
            for (int i = 0; i < imageAdapter.selectedItems.size(); i++) {
                Log.e("DELETE", "delete images paths : " + imagesList.get(imageAdapter.selectedItems.get(i)));
                removeImage(imagesList.get(imageAdapter.selectedItems.get(i)));
            }
            updateList(false);
            selectionOptions.setVisibility(View.GONE);
            toolOptions.setVisibility(View.VISIBLE);
        } else {
            updateList(false);
            selectionOptions.setVisibility(View.GONE);
            toolOptions.setVisibility(View.VISIBLE);
        }
    }

    public void updateList(boolean selectAll) {
        imagesList = getImagesFromDevice(subFolderName1);
        if (getImagesFromDevice(subFolderName1) != null) {
            imageRecycler.setVisibility(View.VISIBLE);
            noImagesToShow.setVisibility(View.GONE);
            setImageAdapter(imagesList, selectAll, isPrevSavedImage);
        } else {
            imageRecycler.setVisibility(View.GONE);
            selectionOptions.setVisibility(View.GONE);
            toolOptions.setVisibility(View.VISIBLE);
            noImagesToShow.setVisibility(View.VISIBLE);
        }
    }

    public void removeImage(String path) {
        File fdelete = new File(path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :");
            } else {
                System.out.println("file not Deleted :");
            }
        }
    }

    @Override
    public void onBackPressed() {
        MyInterstitialAdView.getInstance().activitiesAD(GalleryActivity.this);
        super.onBackPressed();
    }

}
