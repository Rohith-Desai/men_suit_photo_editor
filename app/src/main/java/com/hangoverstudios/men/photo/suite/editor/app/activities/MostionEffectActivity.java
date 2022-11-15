package com.hangoverstudios.men.photo.suite.editor.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.motion.DensityThread;
import com.hangoverstudios.men.photo.suite.editor.app.motion.OpacityThread;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.views.BitmapView;
import com.hangoverstudios.men.photo.suite.editor.app.views.MultiTouchListenerview;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.SplashScreen.mFirebaseAnalytics;

public class MostionEffectActivity extends AppCompatActivity {


    RelativeLayout simpleRelativeLayout;
    Bitmap forebitmap;
    String background_image;
    ImageView backimage;
    Bitmap bitmap;
    FrameLayout rlMotion;
    FrameLayout.LayoutParams imageViewParam9;
    RelativeLayout centre_rel;
    BitmapView bitmapView;
    ImageView frontBimap;
    SeekBar densityBar;
    SeekBar opacityBar;


    ImageView backArrow;
    TextView applymotion;

    private ProgressDialog progressDialog;

    private String filename;
    String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostion_effect);
        bitmapView=(BitmapView)findViewById(R.id.motionBitmap);
       // progressDialog = new ProgressDialog(this);
        background_image = getIntent().getStringExtra("item_type");

        simpleRelativeLayout = (RelativeLayout) findViewById(R.id.rlMain);
        simpleRelativeLayout.setDrawingCacheEnabled(true);
       // rlMotion = (FrameLayout) findViewById(R.id.rlMotion);
        backimage = findViewById(R.id.img_bg);
        frontBimap =findViewById(R.id.full_foreground);

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(background_image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backimage.setImageBitmap(bitmap);

        if (CommonMethods.getInstance()!=null){
            forebitmap=CommonMethods.getInstance().getMotionbitmap();
        }




        frontBimap.setImageBitmap(forebitmap);
        densityBar=findViewById(R.id.densityseekBar);
        opacityBar=findViewById(R.id.opacityseekBar);
        opacityBar.setMax(9);
        opacityBar.setProgress(7);
        densityBar.setMax(9);
        densityBar.setProgress(4);

        ViewTreeObserver vto = frontBimap.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                bitmapView.setBitmap(saveDrawnBitmap(frontBimap));
                bitmapView.setDensity(densityBar.getProgress());
                bitmapView.setOpacity(opacityBar.getProgress());


                // remove the listener so it won't get called again if the view layout changes
                frontBimap.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // add your calculations here
            }});








        // rlMotion.addView(bitmapView);



        opacityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("opacitychecking", String.valueOf(i));
                bitmapView.setOpacity(i);





            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        densityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                bitmapView.setDensity(i);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        backArrow=findViewById(R.id.back_to_img);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        applymotion=findViewById(R.id.applyMotion);
        filename = "img_" + String.format("%d.jpg", System.currentTimeMillis());
        applymotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applymotion.setClickable(false);

              //  new SaveMotionImage().execute();

               // Bitmap bitmap = saveDrawnfinalBitmap(simpleRelativeLayout);
                bitmapView.savecanvas();
                Bitmap bitmap = simpleRelativeLayout.getDrawingCache();
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

                if (  filePath != null) {
                    Intent intent = new Intent(MostionEffectActivity.this, SaveFinishActivity.class);
                    intent.putExtra("PATH", filePath);
                    intent.putExtra("FROM", "MostionEffectActivity");
                    startActivity(intent);
//        MyInterstitialAdView.getInstance().activitiesAD(CategoriesActivity.this);
                    //finish();
                } else {
                    Toast.makeText(MostionEffectActivity.this, "saving failed try again..", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

   /* class SaveMotionImage extends AsyncTask<Void, Void, Void> {
        String imgPath = null;
        File saveFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            saveFile = save();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (saveFile != null && filePath != null) {
                Intent intent = new Intent(MostionEffectActivity.this, SaveFinishActivity.class);
                intent.putExtra("PATH", filePath);
                intent.putExtra("FROM", "MostionEffectActivity");
                startActivity(intent);

            } else {
                Toast.makeText(MostionEffectActivity.this, "saving failed try again..", Toast.LENGTH_SHORT).show();
            }


        }
    }*/
  /*  public File save() {

        Bitmap bitmap = saveDrawnfinalBitmap(simpleRelativeLayout);
        String path = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) ?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/Saved Images"
                : Environment.getExternalStorageDirectory().toString() + "/" + getResources().getString(R.string.app_name) + "/Saved Images";

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
    }*/
    public Bitmap saveDrawnBitmap(ImageView imageView) {
        Bitmap saveBitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(saveBitmap);
        imageView.draw(cv);
        return saveBitmap;
    }
   /* public Bitmap saveDrawnfinalBitmap(RelativeLayout relativeLayout) {


        Bitmap saveBitmaps = Bitmap.createBitmap(relativeLayout.getMeasuredWidth(), relativeLayout.getMeasuredHeight() ,Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(saveBitmaps);
        relativeLayout.layout(relativeLayout.getLeft(),relativeLayout.getTop(),relativeLayout.getRight(),relativeLayout.getBottom());
        bitmapView.savecanvas();
        relativeLayout.draw(cv);
        return saveBitmaps;
    }*/

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onRestart() {
        applymotion.setClickable(true);
        super.onRestart();
    }
}