package com.hangoverstudios.men.photo.suite.editor.app.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BitmapView extends androidx.appcompat.widget.AppCompatImageView {


    private static final int BITMAP_SIZE = 100;
    private static final int POINT = 250;
    private static final int ALPHA = 25;
    private  int DENSITY = 10;
    //private static final int IMAGE_GAP = 50;
    Bitmap mBitmap;
    Paint paint1;
    Paint paint2;
    Paint paint3;
    int colour;
    float translationX, translationY;
    int i;
    int densityRange;

    ArrayList<Float> pointsX=new ArrayList<>();
    ArrayList<Float> pointsY=new ArrayList<>();
    ArrayList<Integer> opacity=new ArrayList<>();
    //float imageTranslation[] = {100,80,60,40,20,10};
    //float pointsY[] = {10,20,30,40,50};
    Context context;
    int width;
    int height;
    Rect frameToDraw;
    RectF whereToDraw;
    Rect sourceRect;
    Rect destinationRect;
    private int mActivePointerId;
    private float mPrevX;
    private float mPrevY;
    PointF[] points=new PointF[10];

    public BitmapView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public BitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public BitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    protected void init() {
        colour = (ALPHA & 0xFF) << 24;
        for (i=0;i<points.length;i++){
            points[i]=new PointF();

        }

        // mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cap_new);
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);

        points[0].x=45f;
        points[1].x=40f;
        points[2].x=35f;
        points[3].x=30f;
        points[4].x=25f;
        points[5].x=20f;
        points[6].x=15f;
        points[7].x=10f;
        points[8].x=5f;
        points[9].x=0f;


        points[0].y=0f;
        points[1].y=0f;
        points[2].y=0f;
        points[3].y=0f;
        points[4].y=0f;
        points[5].y=0f;
        points[6].y=0f;
        points[7].y=0f;
        points[8].y=0f;
        points[9].y=0f;
       /* pointsX.add(45f);
        pointsX.add(40f);
        pointsX.add(35f);
        pointsX.add(30f);
        pointsX.add(25f);
        pointsX.add(20f);
        pointsX.add(15f);
        pointsX.add(10f);
        pointsX.add(5f);
        pointsX.add(0f);
        pointsY.add(0f);
        pointsY.add(0f);
        pointsY.add(0f);
        pointsY.add(0f);
        pointsY.add(0f);
        pointsY.add(0f);
        pointsY.add(0f);
        pointsY.add(0f);
        pointsY.add(0f);
        pointsY.add(0f);*/
        opacity.add(25);
        opacity.add(50);
        opacity.add(75);
        opacity.add(100);
        opacity.add(125);
        opacity.add(150);
        opacity.add(175);
        opacity.add(200);
        opacity.add(225);
        opacity.add(255);


        // paint1.setAlpha(5);
   /*     paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setAlpha(ALPHA-25);
        paint2.setAlpha(ALPHA-10);*/
        // paint1.setAlpha(ALPHA);
        //mBitmap = Bitmap.createScaledBitmap(mBitmap, dpToPx(BITMAP_SIZE), dpToPx(BITMAP_SIZE), false);
        i=0;




    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

     /*   canvas.save();
        //canvas.rotate(0, POINT, POINT);
        canvas.drawBitmap(mBitmap, POINT, POINT, paint1);
        canvas.restore();*/

        if (mBitmap!=null){
            while(i < DENSITY) {
                paint1.setAlpha(opacity.get(i));
                drawMotion(canvas,  points[i].x, points[i].y);
                i++;
            /*if(i == DENSITY-1)
            {
                drawMotion(canvas, pointsX[i], pointsX[i]);
            }else if(i == DENSITY-2)
            {
                drawMotion(canvas, pointsX[i], pointsX[i]);
            }else if(i == DENSITY-3)
            {
                drawMotion(canvas, pointsX[i], pointsX[i]);
            }*/
            }


        }


    }

    private void drawMotion(Canvas canvas, float x, float y) {

        if (mBitmap!=null){
            canvas.save();
            //canvas.rotate(-30, POINT, POINT);
            canvas.drawBitmap(mBitmap,x, y, paint1);
           // canvas.translate(x, y);
            canvas.restore();

        }



       /* canvas.save();
        // canvas.rotate(-60, POINT, POINT);
        canvas.drawBitmap(mBitmap, POINT, POINT, paint3);
        canvas.translate(200, 200);
        canvas.restore();*/

    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        //event.getX();
        // assign global x, y
        //invalidate();
        int action = motionEvent.getAction();
        int actionMasked = motionEvent.getActionMasked() & action;
        Log.d("actionmasked", String.valueOf(actionMasked));
        i = densityRange;
        if (actionMasked == 0) {
            this.mPrevX = motionEvent.getX();
            this.mPrevY = motionEvent.getY();

            this.mActivePointerId = motionEvent.getPointerId(0);

            Log.d("touch","down here");
        }
        else if (actionMasked == 1) {
            Log.d("touch","down here");
            this.mActivePointerId = -1;
        } else if (actionMasked == 2) {
            Log.d("touch","move here");
            int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            if (findPointerIndex != -1) {
                float x = motionEvent.getX(findPointerIndex);
                float y = motionEvent.getY(findPointerIndex);

                Log.d("scrolllllx", String.valueOf(x));
                Log.d("scrollllly", String.valueOf(y));
                Log.d("currentxxxvll", String.valueOf(mPrevX));
                Log.d("currentyyyll", String.valueOf(mPrevY));


                //  float valuex=invisibleImage.getTranslationX();
                //  float valuey=invisibleImage.getTranslationY();

               // float valuex=pointsX.get(8);
               // float valuey=pointsY.get(8);
                float valuex=points[8].x;
                float valuey=points[8].y;


                float fingerx=valuex+(x - this.mPrevX);
                float fingery=valuey+(y - this.mPrevY);

                float A,B,C,D,E,F,G,H,I;
                A=1F;
                B=2F;
                C=3F;
                D=4F;
                E=5F;
                F=6F;
                G=7F;
                H=8F;
                I=9F;





                if (valuex>fingerx && valuey>fingery){

                   /* float bitmap1x=pointsX.get(0)-I;
                    float bitmap2x=pointsX.get(1)-H;
                    float bitmap3x=pointsX.get(2)-G;
                    float bitmap4x=pointsX.get(3)-F;
                    float bitmap5x=pointsX.get(4)-E;
                    float bitmap6x=pointsX.get(5)-D;
                    float bitmap7x=pointsX.get(6)-C;
                    float bitmap8x=pointsX.get(7)-B;
                    float bitmap9x=pointsX.get(8)-A;
                    float bitmap10x=pointsX.get(9);*/
                    float bitmap1x=points[0].x-I;
                    float bitmap2x=points[1].x-H;
                    float bitmap3x=points[2].x-G;
                    float bitmap4x=points[3].x-F;
                    float bitmap5x=points[4].x-E;
                    float bitmap6x=points[5].x-D;
                    float bitmap7x=points[6].x-C;
                    float bitmap8x=points[7].x-B;
                    float bitmap9x=points[8].x-A;
                    float bitmap10x=points[9].x;

                    //   pointsX.clear();
                    float bitmap1y=points[0].y-I;
                    float bitmap2y=points[1].y-H;
                    float bitmap3y=points[2].y-G;
                    float bitmap4y=points[3].y-F;
                    float bitmap5y=points[4].y-E;
                    float bitmap6y=points[5].y-D;
                    float bitmap7y=points[6].y-C;
                    float bitmap8y=points[7].y-B;
                    float bitmap9y=points[8].y-A;
                    float bitmap10y=points[9].y;
                    //   pointsY.clear();
                    points[0].x=bitmap1x;
                    points[1].x=bitmap2x;
                    points[2].x=bitmap3x;
                    points[3].x=bitmap4x;
                    points[4].x=bitmap5x;
                    points[5].x=bitmap6x;
                    points[6].x=bitmap7x;
                    points[7].x=bitmap8x;
                    points[8].x=bitmap9x;
                    points[9].x=bitmap10x;

                    points[0].y=bitmap1y;
                    points[1].y=bitmap2y;
                    points[2].y=bitmap3y;
                    points[3].y=bitmap4y;
                    points[4].y=bitmap5y;
                    points[5].y=bitmap6y;
                    points[6].y=bitmap7y;
                    points[7].y=bitmap8y;
                    points[8].y=bitmap9y;
                    points[9].y=bitmap10y;


                    invalidate();




                }
                else if (valuex<fingerx && valuey<fingery){






                    float bitmap1x=points[0].x+I;
                    float bitmap2x=points[1].x+H;
                    float bitmap3x=points[2].x+G;
                    float bitmap4x=points[3].x+F;
                    float bitmap5x=points[4].x+E;
                    float bitmap6x=points[5].x+D;
                    float bitmap7x=points[6].x+C;
                    float bitmap8x=points[7].x+B;
                    float bitmap9x=points[8].x+A;
                    float bitmap10x=points[9].x;

                    //   pointsX.clear();
                    float bitmap1y=points[0].y+I;
                    float bitmap2y=points[1].y+H;
                    float bitmap3y=points[2].y+G;
                    float bitmap4y=points[3].y+F;
                    float bitmap5y=points[4].y+E;
                    float bitmap6y=points[5].y+D;
                    float bitmap7y=points[6].y+C;
                    float bitmap8y=points[7].y+B;
                    float bitmap9y=points[8].y+A;
                    float bitmap10y=points[9].y;
                    //   pointsY.clear();
                    points[0].x=bitmap1x;
                    points[1].x=bitmap2x;
                    points[2].x=bitmap3x;
                    points[3].x=bitmap4x;
                    points[4].x=bitmap5x;
                    points[5].x=bitmap6x;
                    points[6].x=bitmap7x;
                    points[7].x=bitmap8x;
                    points[8].x=bitmap9x;
                    points[9].x=bitmap10x;

                    points[0].y=bitmap1y;
                    points[1].y=bitmap2y;
                    points[2].y=bitmap3y;
                    points[3].y=bitmap4y;
                    points[4].y=bitmap5y;
                    points[5].y=bitmap6y;
                    points[6].y=bitmap7y;
                    points[7].y=bitmap8y;
                    points[8].y=bitmap9y;
                    points[9].y=bitmap10y;

                    invalidate();






                }
                else if (valuex>fingerx && valuey<fingery){
                    float bitmap1x=points[0].x-I;
                    float bitmap2x=points[1].x-H;
                    float bitmap3x=points[2].x-G;
                    float bitmap4x=points[3].x-F;
                    float bitmap5x=points[4].x-E;
                    float bitmap6x=points[5].x-D;
                    float bitmap7x=points[6].x-C;
                    float bitmap8x=points[7].x-B;
                    float bitmap9x=points[8].x-A;
                    float bitmap10x=points[9].x;

                    //   pointsX.clear();
                    float bitmap1y=points[0].y+I;
                    float bitmap2y=points[1].y+H;
                    float bitmap3y=points[2].y+G;
                    float bitmap4y=points[3].y+F;
                    float bitmap5y=points[4].y+E;
                    float bitmap6y=points[5].y+D;
                    float bitmap7y=points[6].y+C;
                    float bitmap8y=points[7].y+B;
                    float bitmap9y=points[8].y+A;
                    float bitmap10y=points[9].y;
                    //   pointsY.clear();
                    points[0].x=bitmap1x;
                    points[1].x=bitmap2x;
                    points[2].x=bitmap3x;
                    points[3].x=bitmap4x;
                    points[4].x=bitmap5x;
                    points[5].x=bitmap6x;
                    points[6].x=bitmap7x;
                    points[7].x=bitmap8x;
                    points[8].x=bitmap9x;
                    points[9].x=bitmap10x;

                    points[0].y=bitmap1y;
                    points[1].y=bitmap2y;
                    points[2].y=bitmap3y;
                    points[3].y=bitmap4y;
                    points[4].y=bitmap5y;
                    points[5].y=bitmap6y;
                    points[6].y=bitmap7y;
                    points[7].y=bitmap8y;
                    points[8].y=bitmap9y;
                    points[9].y=bitmap10y;

                    invalidate();


                }
                else if (valuex<fingerx && valuey>fingery){
                    float bitmap1x=points[0].x+I;
                    float bitmap2x=points[1].x+H;
                    float bitmap3x=points[2].x+G;
                    float bitmap4x=points[3].x+F;
                    float bitmap5x=points[4].x+E;
                    float bitmap6x=points[5].x+D;
                    float bitmap7x=points[6].x+C;
                    float bitmap8x=points[7].x+B;
                    float bitmap9x=points[8].x+A;
                    float bitmap10x=points[9].x;

                    //   pointsX.clear();
                    float bitmap1y=points[0].y-I;
                    float bitmap2y=points[1].y-H;
                    float bitmap3y=points[2].y-G;
                    float bitmap4y=points[3].y-F;
                    float bitmap5y=points[4].y-E;
                    float bitmap6y=points[5].y-D;
                    float bitmap7y=points[6].y-C;
                    float bitmap8y=points[7].y-B;
                    float bitmap9y=points[8].y-A;
                    float bitmap10y=points[9].y;
                    //   pointsY.clear();
                    points[0].x=bitmap1x;
                    points[1].x=bitmap2x;
                    points[2].x=bitmap3x;
                    points[3].x=bitmap4x;
                    points[4].x=bitmap5x;
                    points[5].x=bitmap6x;
                    points[6].x=bitmap7x;
                    points[7].x=bitmap8x;
                    points[8].x=bitmap9x;
                    points[9].x=bitmap10x;

                    points[0].y=bitmap1y;
                    points[1].y=bitmap2y;
                    points[2].y=bitmap3y;
                    points[3].y=bitmap4y;
                    points[4].y=bitmap5y;
                    points[5].y=bitmap6y;
                    points[6].y=bitmap7y;
                    points[7].y=bitmap8y;
                    points[8].y=bitmap9y;
                    points[9].y=bitmap10y;


                    invalidate();


                }
                else if (valuex==fingerx && valuey<fingery){

                    float bitmap1x=points[0].x;
                    float bitmap2x=points[1].x;
                    float bitmap3x=points[2].x;
                    float bitmap4x=points[3].x;
                    float bitmap5x=points[4].x;
                    float bitmap6x=points[5].x;
                    float bitmap7x=points[6].x;
                    float bitmap8x=points[7].x;
                    float bitmap9x=points[8].x;
                    float bitmap10x=points[9].x;

                    //   pointsX.clear();
                    float bitmap1y=points[0].y+I;
                    float bitmap2y=points[1].y+H;
                    float bitmap3y=points[2].y+G;
                    float bitmap4y=points[3].y+F;
                    float bitmap5y=points[4].y+E;
                    float bitmap6y=points[5].y+D;
                    float bitmap7y=points[6].y+C;
                    float bitmap8y=points[7].y+B;
                    float bitmap9y=points[8].y+A;
                    float bitmap10y=points[9].y;
                    //   pointsY.clear();
                    points[0].x=bitmap1x;
                    points[1].x=bitmap2x;
                    points[2].x=bitmap3x;
                    points[3].x=bitmap4x;
                    points[4].x=bitmap5x;
                    points[5].x=bitmap6x;
                    points[6].x=bitmap7x;
                    points[7].x=bitmap8x;
                    points[8].x=bitmap9x;
                    points[9].x=bitmap10x;

                    points[0].y=bitmap1y;
                    points[1].y=bitmap2y;
                    points[2].y=bitmap3y;
                    points[3].y=bitmap4y;
                    points[4].y=bitmap5y;
                    points[5].y=bitmap6y;
                    points[6].y=bitmap7y;
                    points[7].y=bitmap8y;
                    points[8].y=bitmap9y;
                    points[9].y=bitmap10y;

                    invalidate();


                }
                else if (valuex==fingerx && valuey>fingery){

                    float bitmap1x=points[0].x;
                    float bitmap2x=points[1].x;
                    float bitmap3x=points[2].x;
                    float bitmap4x=points[3].x;
                    float bitmap5x=points[4].x;
                    float bitmap6x=points[5].x;
                    float bitmap7x=points[6].x;
                    float bitmap8x=points[7].x;
                    float bitmap9x=points[8].x;
                    float bitmap10x=points[9].x;

                    //   pointsX.clear();
                    float bitmap1y=points[0].y-I;
                    float bitmap2y=points[1].y-H;
                    float bitmap3y=points[2].y-G;
                    float bitmap4y=points[3].y-F;
                    float bitmap5y=points[4].y-E;
                    float bitmap6y=points[5].y-D;
                    float bitmap7y=points[6].y-C;
                    float bitmap8y=points[7].y-B;
                    float bitmap9y=points[8].y-A;
                    float bitmap10y=points[9].y;
                    //   pointsY.clear();
                    points[0].x=bitmap1x;
                    points[1].x=bitmap2x;
                    points[2].x=bitmap3x;
                    points[3].x=bitmap4x;
                    points[4].x=bitmap5x;
                    points[5].x=bitmap6x;
                    points[6].x=bitmap7x;
                    points[7].x=bitmap8x;
                    points[8].x=bitmap9x;
                    points[9].x=bitmap10x;

                    points[0].y=bitmap1y;
                    points[1].y=bitmap2y;
                    points[2].y=bitmap3y;
                    points[3].y=bitmap4y;
                    points[4].y=bitmap5y;
                    points[5].y=bitmap6y;
                    points[6].y=bitmap7y;
                    points[7].y=bitmap8y;
                    points[8].y=bitmap9y;
                    points[9].y=bitmap10y;
                    invalidate();

                }
                else if (valuex<fingerx && valuey==fingery){

                    float bitmap1x=points[0].x+I;
                    float bitmap2x=points[1].x+H;
                    float bitmap3x=points[2].x+G;
                    float bitmap4x=points[3].x+F;
                    float bitmap5x=points[4].x+E;
                    float bitmap6x=points[5].x+D;
                    float bitmap7x=points[6].x+C;
                    float bitmap8x=points[7].x+B;
                    float bitmap9x=points[8].x+A;
                    float bitmap10x=points[9].x;

                    //   pointsX.clear();
                    float bitmap1y=points[0].y;
                    float bitmap2y=points[1].y;
                    float bitmap3y=points[2].y;
                    float bitmap4y=points[3].y;
                    float bitmap5y=points[4].y;
                    float bitmap6y=points[5].y;
                    float bitmap7y=points[6].y;
                    float bitmap8y=points[7].y;
                    float bitmap9y=points[8].y;
                    float bitmap10y=points[9].y;
                    //   pointsY.clear();
                    points[0].x=bitmap1x;
                    points[1].x=bitmap2x;
                    points[2].x=bitmap3x;
                    points[3].x=bitmap4x;
                    points[4].x=bitmap5x;
                    points[5].x=bitmap6x;
                    points[6].x=bitmap7x;
                    points[7].x=bitmap8x;
                    points[8].x=bitmap9x;
                    points[9].x=bitmap10x;

                    points[0].y=bitmap1y;
                    points[1].y=bitmap2y;
                    points[2].y=bitmap3y;
                    points[3].y=bitmap4y;
                    points[4].y=bitmap5y;
                    points[5].y=bitmap6y;
                    points[6].y=bitmap7y;
                    points[7].y=bitmap8y;
                    points[8].y=bitmap9y;
                    points[9].y=bitmap10y;


                    invalidate();

                }
                else if (valuex>fingerx && valuey==fingery){

                    float bitmap1x=points[0].x-I;
                    float bitmap2x=points[1].x-H;
                    float bitmap3x=points[2].x-G;
                    float bitmap4x=points[3].x-F;
                    float bitmap5x=points[4].x-E;
                    float bitmap6x=points[5].x-D;
                    float bitmap7x=points[6].x-C;
                    float bitmap8x=points[7].x-B;
                    float bitmap9x=points[8].x-A;
                    float bitmap10x=points[9].x;

                    //   pointsX.clear();
                    float bitmap1y=points[0].y;
                    float bitmap2y=points[1].y;
                    float bitmap3y=points[2].y;
                    float bitmap4y=points[3].y;
                    float bitmap5y=points[4].y;
                    float bitmap6y=points[5].y;
                    float bitmap7y=points[6].y;
                    float bitmap8y=points[7].y;
                    float bitmap9y=points[8].y;
                    float bitmap10y=points[9].y;
                    //   pointsY.clear();
                    points[0].x=bitmap1x;
                    points[1].x=bitmap2x;
                    points[2].x=bitmap3x;
                    points[3].x=bitmap4x;
                    points[4].x=bitmap5x;
                    points[5].x=bitmap6x;
                    points[6].x=bitmap7x;
                    points[7].x=bitmap8x;
                    points[8].x=bitmap9x;
                    points[9].x=bitmap10x;

                    points[0].y=bitmap1y;
                    points[1].y=bitmap2y;
                    points[2].y=bitmap3y;
                    points[3].y=bitmap4y;
                    points[4].y=bitmap5y;
                    points[5].y=bitmap6y;
                    points[6].y=bitmap7y;
                    points[7].y=bitmap8y;
                    points[8].y=bitmap9y;
                    points[9].y=bitmap10y;

                    invalidate();


                }









            }


        }
        return true;

    }
    public void savecanvas(){
        i=densityRange;
        invalidate();
    }
    public void setDensity(int density){
        if (density==0){
            densityRange=9;
            i=densityRange;
            invalidate();

        }
        else if (density==1){
            densityRange=8;
            i=densityRange;
            invalidate();

        }
        else if (density==2){
            densityRange=7;
            i=densityRange;
            invalidate();

        }
        else if (density==3){
            densityRange=6;
            i=densityRange;
            invalidate();

        }
        else if (density==4){
            densityRange=5;
            i=densityRange;
            invalidate();

        }
        else if (density==5){
            densityRange=4;
            i=densityRange;
            invalidate();

        }
        else if (density==6){
            densityRange=3;
            i=densityRange;
            invalidate();

        }
        else if (density==7){
            densityRange=2;
            i=densityRange;
            invalidate();

        }
        else if (density==8){
            densityRange=1;
            i=densityRange;
            invalidate();

        }
        else if (density==9){
            densityRange=0;
            i=densityRange;
            invalidate();
        }

    }
    public void setOpacity(int opacitys){
        i=densityRange;

        if (opacitys==0){
            opacity.clear();
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(255);
            invalidate();



        }
        else if (opacitys==1){
            opacity.clear();

            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(25);
            opacity.add(255);
            invalidate();

        }
        else if (opacitys==2){
            opacity.clear();

            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(25);
            opacity.add(50);
            opacity.add(255);
            invalidate();

        }
        else if (opacitys==3){

            opacity.clear();
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(25);
            opacity.add(50);
            opacity.add(75);
            opacity.add(255);
            invalidate();

        }
        else if (opacitys==4){
            opacity.clear();
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(25);
            opacity.add(50);
            opacity.add(75);
            opacity.add(100);
            opacity.add(255);
            invalidate();

        }
        else if (opacitys==5){
            opacity.clear();
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(25);
            opacity.add(50);
            opacity.add(75);
            opacity.add(100);
            opacity.add(125);
            opacity.add(255);
            invalidate();

        }
        else if (opacitys==6){

            opacity.clear();
            opacity.add(0);
            opacity.add(0);
            opacity.add(0);
            opacity.add(25);
            opacity.add(50);
            opacity.add(75);
            opacity.add(100);
            opacity.add(125);
            opacity.add(150);
            opacity.add(255);
            invalidate();

        }
        else if (opacitys==7){
            opacity.clear();
            opacity.add(0);
            opacity.add(0);
            opacity.add(25);
            opacity.add(50);
            opacity.add(75);
            opacity.add(100);
            opacity.add(125);
            opacity.add(150);
            opacity.add(175);
            opacity.add(255);
            invalidate();

        }
        else if (opacitys==8){
            opacity.clear();
            opacity.add(0);
            opacity.add(25);
            opacity.add(50);
            opacity.add(75);
            opacity.add(100);
            opacity.add(125);
            opacity.add(150);
            opacity.add(175);
            opacity.add(200);
            opacity.add(255);
            invalidate();

        }
        else if (opacitys==9){
            opacity.clear();
            opacity.add(25);
            opacity.add(50);
            opacity.add(75);
            opacity.add(100);
            opacity.add(125);
            opacity.add(150);
            opacity.add(175);
            opacity.add(200);
            opacity.add(225);
            opacity.add(255);
            invalidate();

        }



    }

    public void setBitmap(Bitmap bitmap) {


        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;




        mBitmap = bitmap;

        // frameToDraw = new Rect(0, 0,  mBitmap.getWidth(),mBitmap.getHeight());

        // whereToDraw = new RectF(0, 0, width, height);
        //   Bitmap resultBitmap= Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //    sourceRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        //     destinationRect = new Rect((resultBitmap.getWidth() - mBitmap.getWidth())/2, 0, (resultBitmap.getWidth() + mBitmap.getWidth())/2, mBitmap.getHeight());
        //  mBitmap = Bitmap.createScaledBitmap(mBitmap, dpToPx(w), dpToPx(h), false);
        // mBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        Bitmap result = Bitmap.createBitmap(width, mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);




        invalidate();
    }
}

