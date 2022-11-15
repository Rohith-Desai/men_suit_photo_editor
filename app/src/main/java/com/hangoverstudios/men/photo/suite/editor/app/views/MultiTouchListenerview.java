package com.hangoverstudios.men.photo.suite.editor.app.views;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MultiTouchListenerview implements View.OnTouchListener {

    ImageView imageBack;
    ImageView imageBack2;
    ImageView imageBack3;
    ImageView imageBack4;
    ImageView imageBack5;
    ImageView imageBack6;
    ImageView imageBack7;
    ImageView imageBack8;
    ImageView imageBack9;
    ImageView invisibleImage;

    public boolean isTranslateEnabled;
    private int mActivePointerId;
    private float mPrevX;
    private float mPrevY;

    public MultiTouchListenerview(ImageView imageView1,ImageView imageView2,ImageView imageView3,ImageView imageView4,ImageView imageView5,
                                   ImageView imageView6,ImageView imageView7,ImageView imageView8,ImageView imageView9,ImageView invisibleImage) {
        this.mActivePointerId = -1;
        this.isTranslateEnabled = true;
        this.imageBack = imageView1;
        this.imageBack2 = imageView2;
        this.imageBack3 = imageView3;
        this.imageBack4 = imageView4;
        this.imageBack5 = imageView5;
        this.imageBack6 = imageView6;
        this.imageBack7 = imageView7;
        this.imageBack8 = imageView8;
        this.imageBack9 = imageView9;
        this.invisibleImage=invisibleImage;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        //   this.mScaleGestureDetector.onTouchEvent(view, motionEvent);


        if (!this.isTranslateEnabled) {
            return true;
        }
        int action = motionEvent.getAction();
        int actionMasked = motionEvent.getActionMasked() & action;
        Log.d("actionmasked", String.valueOf(actionMasked));
        int i = 0;
        if (actionMasked == 0) {
            this.mPrevX = motionEvent.getX();
            this.mPrevY = motionEvent.getY();
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else if (actionMasked == 1) {
            this.mActivePointerId = -1;
        } else if (actionMasked == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            if (findPointerIndex != -1) {
                float x = motionEvent.getX(findPointerIndex);
                float y = motionEvent.getY(findPointerIndex);

                Log.d("scrolllllx", String.valueOf(x));
                Log.d("scrollllly", String.valueOf(y));
                Log.d("currentxxxvll", String.valueOf(mPrevX));
                Log.d("currentyyyll", String.valueOf(mPrevY));



                float valuex=invisibleImage.getTranslationX();
                float valuey=invisibleImage.getTranslationY();

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

                    adjustTranslation(invisibleImage, (fingerx), (fingery));
                    adjustTranslation(this.imageBack, (this.imageBack.getTranslationX())-A, (this.imageBack.getTranslationY())-A);
                    adjustTranslation(this.imageBack2, (this.imageBack2.getTranslationX())-B, (this.imageBack2.getTranslationY())-B);
                    adjustTranslation(this.imageBack3, (this.imageBack3.getTranslationX())-C, (this.imageBack3.getTranslationY())-C);

                    adjustTranslation(this.imageBack4, (this.imageBack4.getTranslationX())-D, (this.imageBack4.getTranslationY())-D);
                    adjustTranslation(this.imageBack5, (this.imageBack5.getTranslationX())-E, (this.imageBack5.getTranslationY())-E);
                    adjustTranslation(this.imageBack6, (this.imageBack6.getTranslationX())-F, (this.imageBack6.getTranslationY())-F);
                    adjustTranslation(this.imageBack7, (this.imageBack7.getTranslationX())-G, (this.imageBack7.getTranslationY())-G);
                    adjustTranslation(this.imageBack8, (this.imageBack8.getTranslationX())-H, (this.imageBack8.getTranslationY())-H);
                    adjustTranslation(this.imageBack9, (this.imageBack9.getTranslationX())-I, (this.imageBack9.getTranslationY())-I);

                }
                else if (valuex<fingerx && valuey<fingery){

                    adjustTranslation(invisibleImage, (fingerx), (fingery));
                    adjustTranslation(this.imageBack, (this.imageBack.getTranslationX())+A, (this.imageBack.getTranslationY())+A);
                    adjustTranslation(this.imageBack2, (this.imageBack2.getTranslationX())+B, (this.imageBack2.getTranslationY())+B);
                    adjustTranslation(this.imageBack3, (this.imageBack3.getTranslationX())+C, (this.imageBack3.getTranslationY())+C);

                    adjustTranslation(this.imageBack4, (this.imageBack4.getTranslationX())+D, (this.imageBack4.getTranslationY())+D);
                    adjustTranslation(this.imageBack5, (this.imageBack5.getTranslationX())+E, (this.imageBack5.getTranslationY())+E);
                    adjustTranslation(this.imageBack6, (this.imageBack6.getTranslationX())+F, (this.imageBack6.getTranslationY())+F);
                    adjustTranslation(this.imageBack7, (this.imageBack7.getTranslationX())+G, (this.imageBack7.getTranslationY())+G);
                    adjustTranslation(this.imageBack8, (this.imageBack8.getTranslationX())+H, (this.imageBack8.getTranslationY())+H);
                    adjustTranslation(this.imageBack9, (this.imageBack9.getTranslationX())+I, (this.imageBack9.getTranslationY())+I);



                }
                else if (valuex>fingerx && valuey<fingery){
                    adjustTranslation(invisibleImage, (fingerx), (fingery));
                    adjustTranslation(this.imageBack, (this.imageBack.getTranslationX())-A, (this.imageBack.getTranslationY())+A);
                    adjustTranslation(this.imageBack2, (this.imageBack2.getTranslationX())-B, (this.imageBack2.getTranslationY())+B);
                    adjustTranslation(this.imageBack3, (this.imageBack3.getTranslationX())-C, (this.imageBack3.getTranslationY())+C);

                    adjustTranslation(this.imageBack4, (this.imageBack4.getTranslationX())-D, (this.imageBack4.getTranslationY())+D);
                    adjustTranslation(this.imageBack5, (this.imageBack5.getTranslationX())-E, (this.imageBack5.getTranslationY())+E);
                    adjustTranslation(this.imageBack6, (this.imageBack6.getTranslationX())-F, (this.imageBack6.getTranslationY())+F);
                    adjustTranslation(this.imageBack7, (this.imageBack7.getTranslationX())-G, (this.imageBack7.getTranslationY())+G);
                    adjustTranslation(this.imageBack8, (this.imageBack8.getTranslationX())-H, (this.imageBack8.getTranslationY())+H);
                    adjustTranslation(this.imageBack9, (this.imageBack9.getTranslationX())-I, (this.imageBack9.getTranslationY())+I);

                }
                else if (valuex<fingerx && valuey>fingery){

                    adjustTranslation(invisibleImage, (fingerx), (fingery));
                    adjustTranslation(this.imageBack, (this.imageBack.getTranslationX())+A, (this.imageBack.getTranslationY())-A);
                    adjustTranslation(this.imageBack2, (this.imageBack2.getTranslationX())+B, (this.imageBack2.getTranslationY())-B);
                    adjustTranslation(this.imageBack3, (this.imageBack3.getTranslationX())+C, (this.imageBack3.getTranslationY())-C);

                    adjustTranslation(this.imageBack4, (this.imageBack4.getTranslationX())+D, (this.imageBack4.getTranslationY())-D);
                    adjustTranslation(this.imageBack5, (this.imageBack5.getTranslationX())+E, (this.imageBack5.getTranslationY())-E);
                    adjustTranslation(this.imageBack6, (this.imageBack6.getTranslationX())+F, (this.imageBack6.getTranslationY())-F);
                    adjustTranslation(this.imageBack7, (this.imageBack7.getTranslationX())+G, (this.imageBack7.getTranslationY())-G);
                    adjustTranslation(this.imageBack8, (this.imageBack8.getTranslationX())+H, (this.imageBack8.getTranslationY())-H);
                    adjustTranslation(this.imageBack9, (this.imageBack9.getTranslationX())+I, (this.imageBack9.getTranslationY())-I);


                }
                else if (valuex==fingerx && valuey<fingery){

                    adjustTranslation(invisibleImage, (fingerx), (fingery));
                    adjustTranslation(this.imageBack, (this.imageBack.getTranslationX())+0f, (this.imageBack.getTranslationY())+A);
                    adjustTranslation(this.imageBack2, (this.imageBack2.getTranslationX())+0f, (this.imageBack2.getTranslationY())+B);
                    adjustTranslation(this.imageBack3, (this.imageBack3.getTranslationX())+0f, (this.imageBack3.getTranslationY())+C);

                    adjustTranslation(this.imageBack4, (this.imageBack4.getTranslationX())+0f, (this.imageBack4.getTranslationY())+D);
                    adjustTranslation(this.imageBack5, (this.imageBack5.getTranslationX())+0f, (this.imageBack5.getTranslationY())+E);
                    adjustTranslation(this.imageBack6, (this.imageBack6.getTranslationX())+0f, (this.imageBack6.getTranslationY())+F);
                    adjustTranslation(this.imageBack7, (this.imageBack7.getTranslationX())+0f, (this.imageBack7.getTranslationY())+G);
                    adjustTranslation(this.imageBack8, (this.imageBack8.getTranslationX())+0f, (this.imageBack8.getTranslationY())+H);
                    adjustTranslation(this.imageBack9, (this.imageBack9.getTranslationX())+0f, (this.imageBack9.getTranslationY())+I);


                }
                else if (valuex==fingerx && valuey>fingery){

                    adjustTranslation(invisibleImage, (fingerx), (fingery));
                    adjustTranslation(this.imageBack, (this.imageBack.getTranslationX())+0f, (this.imageBack.getTranslationY())-A);
                    adjustTranslation(this.imageBack2, (this.imageBack2.getTranslationX())+0f, (this.imageBack2.getTranslationY())-B);
                    adjustTranslation(this.imageBack3, (this.imageBack3.getTranslationX())+0f, (this.imageBack3.getTranslationY())-C);

                    adjustTranslation(this.imageBack4, (this.imageBack4.getTranslationX())+0f, (this.imageBack4.getTranslationY())-D);
                    adjustTranslation(this.imageBack5, (this.imageBack5.getTranslationX())+0f, (this.imageBack5.getTranslationY())-E);
                    adjustTranslation(this.imageBack6, (this.imageBack6.getTranslationX())+0f, (this.imageBack6.getTranslationY())-F);
                    adjustTranslation(this.imageBack7, (this.imageBack7.getTranslationX())+0f, (this.imageBack7.getTranslationY())-G);
                    adjustTranslation(this.imageBack8, (this.imageBack8.getTranslationX())+0f, (this.imageBack8.getTranslationY())-H);
                    adjustTranslation(this.imageBack9, (this.imageBack9.getTranslationX())+0f, (this.imageBack9.getTranslationY())-I);

                }
                else if (valuex<fingerx && valuey==fingery){

                    adjustTranslation(invisibleImage, (fingerx), (fingery));
                    adjustTranslation(this.imageBack, (this.imageBack.getTranslationX())+A, (this.imageBack.getTranslationY())+0f);
                    adjustTranslation(this.imageBack2, (this.imageBack2.getTranslationX())+B, (this.imageBack2.getTranslationY())+0f);
                    adjustTranslation(this.imageBack3, (this.imageBack3.getTranslationX())+C, (this.imageBack3.getTranslationY())+0f);

                    adjustTranslation(this.imageBack4, (this.imageBack4.getTranslationX())+D, (this.imageBack4.getTranslationY())+0f);
                    adjustTranslation(this.imageBack5, (this.imageBack5.getTranslationX())+E, (this.imageBack5.getTranslationY())+0f);
                    adjustTranslation(this.imageBack6, (this.imageBack6.getTranslationX())+F, (this.imageBack6.getTranslationY())+0f);
                    adjustTranslation(this.imageBack7, (this.imageBack7.getTranslationX())+G, (this.imageBack7.getTranslationY())+0f);
                    adjustTranslation(this.imageBack8, (this.imageBack8.getTranslationX())+H, (this.imageBack8.getTranslationY())+0f);
                    adjustTranslation(this.imageBack9, (this.imageBack9.getTranslationX())+I, (this.imageBack9.getTranslationY())+0f);

                }
                else if (valuex>fingerx && valuey==fingery){

                    adjustTranslation(invisibleImage, (fingerx), (fingery));
                    adjustTranslation(this.imageBack, (this.imageBack.getTranslationX())-A, (this.imageBack.getTranslationY())+0f);
                    adjustTranslation(this.imageBack2, (this.imageBack2.getTranslationX())-B, (this.imageBack2.getTranslationY())+0f);
                    adjustTranslation(this.imageBack3, (this.imageBack3.getTranslationX())-C, (this.imageBack3.getTranslationY())+0f);

                    adjustTranslation(this.imageBack4, (this.imageBack4.getTranslationX())-D, (this.imageBack4.getTranslationY())+0f);
                    adjustTranslation(this.imageBack5, (this.imageBack5.getTranslationX())-E, (this.imageBack5.getTranslationY())+0f);
                    adjustTranslation(this.imageBack6, (this.imageBack6.getTranslationX())-F, (this.imageBack6.getTranslationY())+0f);
                    adjustTranslation(this.imageBack7, (this.imageBack7.getTranslationX())-G, (this.imageBack7.getTranslationY())+0f);
                    adjustTranslation(this.imageBack8, (this.imageBack8.getTranslationX())-H, (this.imageBack8.getTranslationY())+0f);
                    adjustTranslation(this.imageBack9, (this.imageBack9.getTranslationX())-I, (this.imageBack9.getTranslationY())+0f);


                }



            }
        }
        return true;
    }
    private void adjustTranslation(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.setTranslationX(f);
        view.setTranslationY(f2);


    }



}
