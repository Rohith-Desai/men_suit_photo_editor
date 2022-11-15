package com.hangoverstudios.men.photo.suite.editor.app.motion;

import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DensityThread  extends Thread{

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    ImageView img5;
    ImageView img6;
    ImageView img7;
    ImageView img8;
    ImageView img9;
    int progress;

    FrameLayout.LayoutParams imageViewParam1;
    FrameLayout.LayoutParams imageViewParam2;
    FrameLayout.LayoutParams imageViewParam3;
    FrameLayout.LayoutParams imageViewParam4;
    FrameLayout.LayoutParams imageViewParam5;
    FrameLayout.LayoutParams imageViewParam6;
    FrameLayout.LayoutParams imageViewParam7;
    FrameLayout.LayoutParams imageViewParam8;
    FrameLayout.LayoutParams imageViewParam9;



    private Handler handler;
    private boolean running = false;
    public DensityThread(ImageView img1,ImageView img2,ImageView img3,ImageView img4,ImageView img5,ImageView img6,ImageView img7,ImageView img8,ImageView img9,
                         FrameLayout.LayoutParams imageViewParam1,FrameLayout.LayoutParams imageViewParam2,FrameLayout.LayoutParams imageViewParam3,FrameLayout.LayoutParams imageViewParam4,FrameLayout.LayoutParams imageViewParam5,
                         FrameLayout.LayoutParams imageViewParam6,FrameLayout.LayoutParams imageViewParam7,FrameLayout.LayoutParams imageViewParam8,FrameLayout.LayoutParams imageViewParam9){
        this.img1=img1;
        this.img2=img2;
        this.img3=img3;
        this.img4=img4;
        this.img5=img5;
        this.img6=img6;
        this.img7=img7;
        this.img8=img8;
        this.img9=img9;

        this.imageViewParam1=imageViewParam1;
        this.imageViewParam2=imageViewParam2;
        this.imageViewParam3=imageViewParam3;
        this.imageViewParam4=imageViewParam4;
        this.imageViewParam5=imageViewParam5;
        this.imageViewParam6=imageViewParam6;
        this.imageViewParam7=imageViewParam7;
        this.imageViewParam8=imageViewParam8;
        this.imageViewParam9=imageViewParam9;


        handler = new Handler();



    }
    public  void  adjustDensity(int opacityrange){

        progress = (int)opacityrange;

        running=true;
    }

    @Override
    public void run(){

        while (true){
            if (running && progress!=-1){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (progress>=80){
                            /*imageViewParam1.setMargins(10,0,0,0);
                            img1.setLayoutParams(imageViewParam1);

                            imageViewParam2.setMargins(20,0,0,0);
                            img2.setLayoutParams(imageViewParam2);
                            imageViewParam3.setMargins(30,0,0,0);
                            img3.setLayoutParams(imageViewParam3);
                            imageViewParam4.setMargins(40,0,0,0);
                            img4.setLayoutParams(imageViewParam4);
                            imageViewParam5.setMargins(50,0,0,0);
                            img5.setLayoutParams(imageViewParam5);
                            imageViewParam6.setMargins(60,0,0,0);
                            img6.setLayoutParams(imageViewParam6);
                            imageViewParam7.setMargins(70,0,0,0);
                            img7.setLayoutParams(imageViewParam7);
                            imageViewParam8.setMargins(80,0,0,0);
                            img8.setLayoutParams(imageViewParam8);
                            imageViewParam9.setMargins(90,0,0,0);
                            img9.setLayoutParams(imageViewParam9);*/


                            img1.setVisibility(View.VISIBLE);
                            img2.setVisibility(View.VISIBLE);
                            img3.setVisibility(View.VISIBLE);
                            img4.setVisibility(View.VISIBLE);
                            img5.setVisibility(View.VISIBLE);
                            img6.setVisibility(View.VISIBLE);
                            img7.setVisibility(View.VISIBLE);
                            img8.setVisibility(View.VISIBLE);
                            img9.setVisibility(View.
                                    VISIBLE);
                            running=false;
                            progress=-1;

                        }
                        else if (70<=progress && progress<80){

                           /* imageViewParam1.setMargins(20,0,0,0);
                            img1.setLayoutParams(imageViewParam1);

                            imageViewParam2.setMargins(40,0,0,0);
                            img2.setLayoutParams(imageViewParam2);
                            imageViewParam3.setMargins(60,0,0,0);
                            img3.setLayoutParams(imageViewParam3);
                            imageViewParam4.setMargins(80,0,0,0);
                            img4.setLayoutParams(imageViewParam4);
                            imageViewParam5.setMargins(100,0,0,0);
                            img5.setLayoutParams(imageViewParam5);
                            imageViewParam6.setMargins(120,0,0,0);
                            img6.setLayoutParams(imageViewParam6);
                            imageViewParam7.setMargins(140,0,0,0);
                            img7.setLayoutParams(imageViewParam7);
                            imageViewParam8.setMargins(160,0,0,0);
                            img8.setLayoutParams(imageViewParam8);
                            imageViewParam9.setMargins(180,0,0,0);
                            img9.setLayoutParams(imageViewParam9);*/



                            img1.setVisibility(View.VISIBLE);
                            img2.setVisibility(View.VISIBLE);
                            img3.setVisibility(View.VISIBLE);
                            img4.setVisibility(View.VISIBLE);
                            img5.setVisibility(View.VISIBLE);
                            img6.setVisibility(View.VISIBLE);
                            img7.setVisibility(View.VISIBLE);
                            img8.setVisibility(View.VISIBLE);
                            img9.setVisibility(View.
                                    GONE);
                            running=false;
                            progress=-1;

                        }
                        else if (60<=progress && progress<70){
                           /* imageViewParam1.setMargins(30,0,0,0);
                            img1.setLayoutParams(imageViewParam1);

                            imageViewParam2.setMargins(60,0,0,0);
                            img2.setLayoutParams(imageViewParam2);
                            imageViewParam3.setMargins(90,0,0,0);
                            img3.setLayoutParams(imageViewParam3);
                            imageViewParam4.setMargins(120,0,0,0);
                            img4.setLayoutParams(imageViewParam4);
                            imageViewParam5.setMargins(150,0,0,0);
                            img5.setLayoutParams(imageViewParam5);
                            imageViewParam6.setMargins(180,0,0,0);
                            img6.setLayoutParams(imageViewParam6);
                            imageViewParam7.setMargins(210,0,0,0);
                            img7.setLayoutParams(imageViewParam7);
                            imageViewParam8.setMargins(240,0,0,0);
                            img8.setLayoutParams(imageViewParam8);
                            imageViewParam9.setMargins(270,0,0,0);
                            img9.setLayoutParams(imageViewParam9);*/

                            img1.setVisibility(View.VISIBLE);
                            img2.setVisibility(View.VISIBLE);
                            img3.setVisibility(View.VISIBLE);
                            img4.setVisibility(View.VISIBLE);
                            img5.setVisibility(View.VISIBLE);
                            img6.setVisibility(View.VISIBLE);
                            img7.setVisibility(View.VISIBLE);
                            img8.setVisibility(View.GONE);
                            img9.setVisibility(View.
                                    GONE);
                            running=false;
                            progress=-1;

                        }
                        else if (50 <=progress && progress<60){

                          /*  imageViewParam1.setMargins(40,0,0,0);
                            img1.setLayoutParams(imageViewParam1);

                            imageViewParam2.setMargins(80,0,0,0);
                            img2.setLayoutParams(imageViewParam2);
                            imageViewParam3.setMargins(120,0,0,0);
                            img3.setLayoutParams(imageViewParam3);
                            imageViewParam4.setMargins(160,0,0,0);
                            img4.setLayoutParams(imageViewParam4);
                            imageViewParam5.setMargins(200,0,0,0);
                            img5.setLayoutParams(imageViewParam5);
                            imageViewParam6.setMargins(240,0,0,0);
                            img6.setLayoutParams(imageViewParam6);
                            imageViewParam7.setMargins(280,0,0,0);
                            img7.setLayoutParams(imageViewParam7);
                            imageViewParam8.setMargins(320,0,0,0);
                            img8.setLayoutParams(imageViewParam8);
                            imageViewParam9.setMargins(360,0,0,0);
                            img9.setLayoutParams(imageViewParam9);*/


                            img1.setVisibility(View.VISIBLE);
                            img2.setVisibility(View.VISIBLE);
                            img3.setVisibility(View.VISIBLE);
                            img4.setVisibility(View.VISIBLE);
                            img5.setVisibility(View.VISIBLE);
                            img6.setVisibility(View.VISIBLE);
                            img7.setVisibility(View.GONE);
                            img8.setVisibility(View.GONE);
                            img9.setVisibility(View.
                                    GONE);
                            running=false;
                            progress=-1;

                        }
                        else if (40 <=progress && progress<50){

                           /* imageViewParam1.setMargins(50,0,0,0);
                            img1.setLayoutParams(imageViewParam1);

                            imageViewParam2.setMargins(100,0,0,0);
                            img2.setLayoutParams(imageViewParam2);
                            imageViewParam3.setMargins(150,0,0,0);
                            img3.setLayoutParams(imageViewParam3);
                            imageViewParam4.setMargins(200,0,0,0);
                            img4.setLayoutParams(imageViewParam4);
                            imageViewParam5.setMargins(250,0,0,0);
                            img5.setLayoutParams(imageViewParam5);
                            imageViewParam6.setMargins(300,0,0,0);
                            img6.setLayoutParams(imageViewParam6);
                            imageViewParam7.setMargins(350,0,0,0);
                            img7.setLayoutParams(imageViewParam7);
                            imageViewParam8.setMargins(400,0,0,0);
                            img8.setLayoutParams(imageViewParam8);
                            imageViewParam9.setMargins(450,0,0,0);
                            img9.setLayoutParams(imageViewParam9);*/
                            img1.setVisibility(View.VISIBLE);
                            img2.setVisibility(View.VISIBLE);
                            img3.setVisibility(View.VISIBLE);
                            img4.setVisibility(View.VISIBLE);
                            img5.setVisibility(View.VISIBLE);
                            img6.setVisibility(View.GONE);
                            img7.setVisibility(View.GONE);
                            img8.setVisibility(View.GONE);
                            img9.setVisibility(View.
                                    GONE);
                            running=false;
                            progress=-1;

                        }
                        else if (30 <=progress && progress<40){
                           /* imageViewParam1.setMargins(60,0,0,0);
                            img1.setLayoutParams(imageViewParam1);

                            imageViewParam2.setMargins(120,0,0,0);
                            img2.setLayoutParams(imageViewParam2);
                            imageViewParam3.setMargins(180,0,0,0);
                            img3.setLayoutParams(imageViewParam3);
                            imageViewParam4.setMargins(240,0,0,0);
                            img4.setLayoutParams(imageViewParam4);
                            imageViewParam5.setMargins(300,0,0,0);
                            img5.setLayoutParams(imageViewParam5);
                            imageViewParam6.setMargins(360,0,0,0);
                            img6.setLayoutParams(imageViewParam6);
                            imageViewParam7.setMargins(420,0,0,0);
                            img7.setLayoutParams(imageViewParam7);
                            imageViewParam8.setMargins(480,0,0,0);
                            img8.setLayoutParams(imageViewParam8);
                            imageViewParam9.setMargins(540,0,0,0);
                            img9.setLayoutParams(imageViewParam9);*/
                            img1.setVisibility(View.VISIBLE);
                            img2.setVisibility(View.VISIBLE);
                            img3.setVisibility(View.VISIBLE);
                            img4.setVisibility(View.VISIBLE);
                            img5.setVisibility(View.GONE);
                            img6.setVisibility(View.GONE);
                            img7.setVisibility(View.GONE);
                            img8.setVisibility(View.GONE);
                            img9.setVisibility(View.GONE);
                            running=false;
                            progress=-1;

                        }
                        else if (20 <=progress && progress<30){
                          /*  imageViewParam1.setMargins(70,0,0,0);
                            img1.setLayoutParams(imageViewParam1);

                            imageViewParam2.setMargins(140,0,0,0);
                            img2.setLayoutParams(imageViewParam2);
                            imageViewParam3.setMargins(210,0,0,0);
                            img3.setLayoutParams(imageViewParam3);
                            imageViewParam4.setMargins(280,0,0,0);
                            img4.setLayoutParams(imageViewParam4);
                            imageViewParam5.setMargins(350,0,0,0);
                            img5.setLayoutParams(imageViewParam5);
                            imageViewParam6.setMargins(420,0,0,0);
                            img6.setLayoutParams(imageViewParam6);
                            imageViewParam7.setMargins(490,0,0,0);
                            img7.setLayoutParams(imageViewParam7);
                            imageViewParam8.setMargins(560,0,0,0);
                            img8.setLayoutParams(imageViewParam8);
                            imageViewParam9.setMargins(630,0,0,0);
                            img9.setLayoutParams(imageViewParam9);*/


                            img1.setVisibility(View.VISIBLE);
                            img2.setVisibility(View.VISIBLE);
                            img3.setVisibility(View.VISIBLE);
                            img4.setVisibility(View.GONE);
                            img5.setVisibility(View.GONE);
                            img6.setVisibility(View.GONE);
                            img7.setVisibility(View.GONE);
                            img8.setVisibility(View.GONE);
                            img9.setVisibility(View.GONE);
                            running=false;
                            progress=-1;

                        }
                        else if (10 <=progress && progress<20){
                           /* imageViewParam1.setMargins(80,0,0,0);
                            img1.setLayoutParams(imageViewParam1);

                            imageViewParam2.setMargins(160,0,0,0);
                            img2.setLayoutParams(imageViewParam2);
                            imageViewParam3.setMargins(240,0,0,0);
                            img3.setLayoutParams(imageViewParam3);
                            imageViewParam4.setMargins(320,0,0,0);
                            img4.setLayoutParams(imageViewParam4);
                            imageViewParam5.setMargins(400,0,0,0);
                            img5.setLayoutParams(imageViewParam5);
                            imageViewParam6.setMargins(480,0,0,0);
                            img6.setLayoutParams(imageViewParam6);
                            imageViewParam7.setMargins(560,0,0,0);
                            img7.setLayoutParams(imageViewParam7);
                            imageViewParam8.setMargins(640,0,0,0);
                            img8.setLayoutParams(imageViewParam8);
                            imageViewParam9.setMargins(720,0,0,0);
                            img9.setLayoutParams(imageViewParam9);*/


                            img1.setVisibility(View.VISIBLE);
                            img2.setVisibility(View.VISIBLE);
                            img3.setVisibility(View.GONE);
                            img4.setVisibility(View.GONE);
                            img5.setVisibility(View.GONE);
                            img6.setVisibility(View.GONE);
                            img7.setVisibility(View.GONE);
                            img8.setVisibility(View.GONE);
                            img9.setVisibility(View.GONE);
                            running=false;
                            progress=-1;

                        }
                        else if (1<=progress && progress<10){

                           /* imageViewParam1.setMargins(90,0,0,0);
                            img1.setLayoutParams(imageViewParam1);*/



                            img1.setVisibility(View.VISIBLE);
                            img2.setVisibility(View.GONE);
                            img3.setVisibility(View.GONE);
                            img4.setVisibility(View.GONE);
                            img5.setVisibility(View.GONE);
                            img6.setVisibility(View.GONE);
                            img7.setVisibility(View.GONE);
                            img8.setVisibility(View.GONE);
                            img9.setVisibility(View.GONE);
                            running=false;
                            progress=-1;

                        }
                        else if (progress==0){
                            img1.setVisibility(View.GONE);
                            img2.setVisibility(View.GONE);
                            img3.setVisibility(View.GONE);
                            img4.setVisibility(View.GONE);
                            img5.setVisibility(View.GONE);
                            img6.setVisibility(View.GONE);
                            img7.setVisibility(View.GONE);
                            img8.setVisibility(View.GONE);
                            img9.setVisibility(View.GONE);

                            running=false;
                            progress=-1;

                        }
                        else {
                            running=false;
                            progress=-1;

                        }






                    }
                });
            }
        }
    }
}
