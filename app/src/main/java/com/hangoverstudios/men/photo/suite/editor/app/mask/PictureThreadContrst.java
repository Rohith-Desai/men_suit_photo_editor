package com.hangoverstudios.men.photo.suite.editor.app.mask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Handler;
import android.widget.ImageView;

public class PictureThreadContrst extends Thread {

    private ImageView imageView;
    private Bitmap bitmap;
    private Bitmap  temp_bitmap;
    private Canvas canvas;
    private Paint paint;
    private ColorMatrix colorMatrix;
    private ColorMatrixColorFilter colorMatrixColorFilter;
    private Handler handler;
    private boolean running = false;
    public PictureThreadContrst(ImageView imageView, Bitmap bitmap){
        this.imageView=imageView;
        this.bitmap=bitmap;
        if(bitmap!=null){
            temp_bitmap=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
            canvas=new Canvas(temp_bitmap);
            paint=new Paint();
            handler = new Handler();
        }
    }
    public  void  adjustBrightness(int amount){
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        amount, 0, 0, 0, 1,
                        0, amount, 0, 0, 1,
                        0, 0, amount, 0, 1,
                        0, 0, 0, 1, 0
                });
        colorMatrixColorFilter=new ColorMatrixColorFilter(cm);
        paint.setColorFilter(colorMatrixColorFilter);
        running=true;
    }
    @Override
    public void run(){

        while (true){
            if (running){
                canvas.drawBitmap(bitmap,0,0,paint);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(temp_bitmap);
                        running=false;

                    }
                });
            }
        }
    }
}
