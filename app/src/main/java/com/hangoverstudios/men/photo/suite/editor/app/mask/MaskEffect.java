package com.hangoverstudios.men.photo.suite.editor.app.mask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.activities.MostionEffectActivity;
import com.hangoverstudios.men.photo.suite.editor.app.activities.SaveFinishActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.MainActivity.selectedImageBitmap;

public class MaskEffect extends AppCompatActivity implements Maskselection {
    RecyclerView maskBimaps;
    ArrayList<Object> masksArray=new ArrayList<>();
    LinearLayout mImageView, cacheFrame;
    Bitmap bmp;
    ImageView baCK;
    SeekBar seekbar,bssekbar;
    Bitmap original2;
    Bitmap original;
    int brightness;
    int constrant;
    private  PictureThread thred;
    private  PictureThreadContrst threadContrst;
    Uri imagepath;
    TextView applyEff;
    String filePath;
    ImageView backbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask_effect);
        constrant= 1;
        brightness= 155;

        mImageView= (LinearLayout)findViewById(R.id.imageview_id);
        baCK=(ImageView)findViewById(R.id.baCK);
        maskBimaps=findViewById(R.id.maskImages);
        applyEff=findViewById(R.id.applyEff);
        cacheFrame=findViewById(R.id.cacheFrame);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        bssekbar=(SeekBar)findViewById(R.id.brightseekbar);
        cacheFrame.setDrawingCacheEnabled(true);
        getAllmasks();
        masksAdapter  masksadapter=new masksAdapter(MaskEffect.this,masksArray,(Maskselection) this);
        maskBimaps.setHasFixedSize(true);
        maskBimaps.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        maskBimaps.setItemAnimator(new DefaultItemAnimator());
        maskBimaps.setAdapter(masksadapter);
//        imagepath=Uri.parse(getIntent().getStringExtra("image"));
        //byte[] bytesArray=getIntent().getByteArrayExtra("image");
        //bmp=BitmapFactory.decodeByteArray(bytesArray,0,bytesArray.length);
        bmp= selectedImageBitmap;
        //bmp= MediaStore.Images.Media.getBitmap(this.getContentResolver(),imagepath);
        thred=new PictureThread(baCK,bmp);
        thred.start();
        threadContrst=new PictureThreadContrst(baCK,bmp);
        threadContrst.start();
        makeMaskImage(mImageView, bmp,baCK,R.drawable.mag_19);

        applyEff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = cacheFrame.getDrawingCache();

                String filename = "img_" + String.format("%d.jpg", System.currentTimeMillis());
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
                if ( filePath != null) {
                    Intent intent = new Intent(MaskEffect.this, SaveFinishActivity.class);
                    intent.putExtra("PATH", filePath);
                    intent.putExtra("FROM", "MostionEffectActivity");
                    startActivity(intent);
//        MyInterstitialAdView.getInstance().activitiesAD(CategoriesActivity.this);
                    //finish();
                } else {
                    Toast.makeText(MaskEffect.this, "saving failed try again..", Toast.LENGTH_SHORT).show();
                }

                /*File file,f;
                if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                {
                    file =new File(android.os.Environment.getExternalStorageDirectory(),"TTImages_cache");
                    if(!file.exists())
                    {
                        file.mkdirs();

                    }
                    f = new File(file.getAbsolutePath()+file.seperator+ "filename"+".png");
                }
                FileOutputStream ostream = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                ostream.close();*/

            }

        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                constrant=seekBar.getProgress();
                thred.adjustBrightness(constrant,brightness-155);
                //constrant=progress;
                // imageView.setImageBitmap(changeBitmapContrastBrightness(BitmapFactory.decodeResource(getResources(), R.drawable.lhota), (float) progress / 100f, 1));
                //textView.setText("Contrast: "+(float) progress / 100f);
                ;
                //  Bitmap constrast=doBrightness(original,brightness);
                // baCK.setImageBitmap(adjustedContrast(original,  (double) progress));
                // baCK.setImageBitmap(changeBitmapContrastBrightness(original,constrant,brightness));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });




        bssekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                brightness=seekBar.getProgress();
                thred.adjustBrightness(constrant,seekBar.getProgress()-155);
                //brightness=progress;
                //Bitmap brightnesrr=adjustedContrast(original,constrant);

                //baCK.setImageBitmap(doBrightness(brightnesrr,  progress));
                // baCK.setImageBitmap(changeBitmapContrastBrightness(original,constrant,brightness));
                // imageView.setImageBitmap(changeBitmapContrastBrightness(BitmapFactory.decodeResource(getResources(), R.drawable.lhota), (float) progress / 100f, 1));
                //textView.setText("Contrast: "+(float) progress / 100f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // bssekbar.setMax(200);
        // bssekbar.setProgress(50);

        backbutton=findViewById(R.id.back_to_img);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void getAllmasks(){
        masksArray.add("mag_19");
        masksArray.add("mag_22");
        masksArray.add("mag_23");
        masksArray.add("mag_24");
        masksArray.add("mag_25");
        masksArray.add("mag_26");
        masksArray.add("mag_27");
    }
    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }
    public void makeMaskImage(LinearLayout mImageView, Bitmap mContent,ImageView baCK,int masks)
    {
        /*Bitmap original = BitmapFactory.decodeResource(getResources(), mContent);
        Bitmap mask = BitmapFactory.decodeResource(getResources(),R.drawable.mag_19);
        Bitmap result2 = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        result2.eraseColor(Color.TRANSPARENT);

       // Canvas mCanvas2 = new Canvas(result2);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(result2, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        mImageView.setImageBitmap(result);
        mImageView.setBackground(getResources().getDrawable(R.drawable.nature));
       // baCK.setImageBitmap(original);
        mImageView.setScaleType(ImageView.ScaleType.CENTER);*/

        // Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.vijay);
        original = mContent;
        // original2=changeBitmapContrastBrightness(original,constrant,brightness);
        //original2=doBrightness(original,brightness);
        Bitmap mask = BitmapFactory.decodeResource(getResources(),masks);
        Bitmap result2 = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap result3 = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap result4 = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        result2.eraseColor(Color.TRANSPARENT);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Canvas mCanvas2 = new Canvas(result2);
        Canvas mCanvas3 = new Canvas(result3);
        Canvas mCanvas4 = new Canvas(result4);

        mCanvas2.drawColor(getResources().getColor(R.color.white));
        //Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        //paint2.setStyle(Paint.Style.FILL);
        //paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        // RectF outerrect=new RectF(0,0,mask.getWidth(), mask.getHeight());
        // mCanvas2.drawRect(outerrect,paint2);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawBitmap(result2, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        Paint paint2 = new Paint();
        paint2.setAlpha(100);
        //mCanvas4.drawBitmap(result4, 0, 0, null);
        mCanvas4.drawBitmap(mask, 0, 0, paint2);
        //Bitmap bitmapbrightness=doBrightness(result4,-40);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        mCanvas3.drawBitmap(result, 0, 0, null);
        mCanvas3.drawBitmap(result4, 0, 0, paint);
        //Bitmap constrant=adjustedContrast(result3, 20d);
        // Bitmap constrantoriginal=adjustedContrast(original, constrant);

        BitmapDrawable back=new BitmapDrawable(result3);
        paint.setXfermode(null);
        mImageView.setBackgroundDrawable(back);
        baCK.setImageBitmap(original);
        baCK.setOnTouchListener(new MultiTouchListener());
        baCK.invalidate();

    }
    private Bitmap adjustedContrast(Bitmap src, double value)
    {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap

        // create a mutable empty bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        // create a canvas so that we can draw the bmOut Bitmap from source bitmap
        Canvas c = new Canvas();
        c.setBitmap(bmOut);

        // draw bitmap to bmOut from src bitmap so we can modify it
        c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));


        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.green(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.blue(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }

    public static Bitmap doBrightness(Bitmap src, int value) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // increase/decrease each channel
                R += value;
                if(R > 255) { R = 255; }
                else if(R < 0) { R = 0; }

                G += value;
                if(G > 255) { G = 255; }
                else if(G < 0) { G = 0; }

                B += value;
                if(B > 255) { B = 255; }
                else if(B < 0) { B = 0; }

                // apply new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    @Override
    public void maskimageSelect(int mask) {
        makeMaskImage(mImageView, bmp,baCK,mask);

    }
}