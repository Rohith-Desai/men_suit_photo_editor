package com.hangoverstudios.men.photo.suite.editor.app.motion;

import android.os.Handler;
import android.widget.ImageView;

public class OpacityThread extends Thread {

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

    private Handler handler;
    private boolean running = false;
    public OpacityThread(ImageView img1,ImageView img2,ImageView img3,ImageView img4,ImageView img5,ImageView img6,ImageView img7,ImageView img8,ImageView img9){
        this.img1=img1;
        this.img2=img2;
        this.img3=img3;
        this.img4=img4;
        this.img5=img5;
        this.img6=img6;
        this.img7=img7;
        this.img8=img8;
        this.img9=img9;

        handler = new Handler();



    }

    public  void  adjustOpacity(int opacityrange){

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
                            img1.setAlpha(0.9f);
                            img2.setAlpha(0.8f);
                            img3.setAlpha(0.7f);
                            img4.setAlpha(0.6f);

                            img5.setAlpha(0.5f);
                            img6.setAlpha(0.4f);
                            img7.setAlpha(0.3f);
                            img8.setAlpha(0.2f);
                            img9.setAlpha(0.1f);
                            running=false;
                            progress=-1;

                        }
                        else if (70 <=progress && progress<80){
                            img1.setAlpha(0.8f);
                            img2.setAlpha(0.7f);
                            img3.setAlpha(0.6f);
                            img4.setAlpha(0.5f);

                            img5.setAlpha(0.4f);
                            img6.setAlpha(0.3f);
                            img7.setAlpha(0.2f);
                            img8.setAlpha(0.1f);
                            img9.setAlpha(0.0f);
                            running=false;
                            progress=-1;

                        }
                        else if (60 <=progress && progress<70){
                            img1.setAlpha(0.7f);
                            img2.setAlpha(0.6f);
                            img3.setAlpha(0.5f);
                            img4.setAlpha(0.4f);

                            img5.setAlpha(0.3f);
                            img6.setAlpha(0.2f);
                            img7.setAlpha(0.1f);
                            img8.setAlpha(0.0f);
                            img9.setAlpha(0.0f);
                            running=false;
                            progress=-1;

                        }
                        else if (50 <=progress && progress<60){
                            img1.setAlpha(0.6f);
                            img2.setAlpha(0.5f);
                            img3.setAlpha(0.4f);
                            img4.setAlpha(0.3f);

                            img5.setAlpha(0.2f);
                            img6.setAlpha(0.1f);
                            img7.setAlpha(0.0f);
                            img8.setAlpha(0.0f);
                            img9.setAlpha(0.0f);
                            running=false;
                            progress=-1;

                        }
                        else if (40 <=progress && progress<50){
                            img1.setAlpha(0.5f);
                            img2.setAlpha(0.4f);
                            img3.setAlpha(0.3f);
                            img4.setAlpha(0.2f);

                            img5.setAlpha(0.1f);
                            img6.setAlpha(0.0f);
                            img7.setAlpha(0.0f);
                            img8.setAlpha(0.0f);
                            img9.setAlpha(0.0f);
                            running=false;
                            progress=-1;

                        }
                        else if (30<=progress && progress<40){
                            img1.setAlpha(0.4f);
                            img2.setAlpha(0.3f);
                            img3.setAlpha(0.2f);
                            img4.setAlpha(0.1f);
                            img5.setAlpha(0.0f);
                            img6.setAlpha(0.0f);
                            img7.setAlpha(0.0f);
                            img8.setAlpha(0.0f);
                            img9.setAlpha(0.0f);
                            running=false;
                            progress=-1;

                        }
                        else if (20<=progress && progress<30){
                            img1.setAlpha(0.3f);
                            img2.setAlpha(0.2f);
                            img3.setAlpha(0.1f);
                            img4.setAlpha(0.0f);
                            img5.setAlpha(0.0f);
                            img6.setAlpha(0.0f);
                            img7.setAlpha(0.0f);
                            img8.setAlpha(0.0f);
                            img9.setAlpha(0.0f);
                            running=false;
                            progress=-1;

                        }
                        else if (10<=progress&& progress<20){
                            img1.setAlpha(0.2f);
                            img2.setAlpha(0.1f);
                            img3.setAlpha(0.0f);
                            img4.setAlpha(0.0f);
                            img5.setAlpha(0.0f);
                            img6.setAlpha(0.0f);
                            img7.setAlpha(0.0f);
                            img8.setAlpha(0.0f);
                            img9.setAlpha(0.0f);
                            running=false;
                            progress=-1;

                        }
                        else if (1 <=progress && progress<10){
                            img1.setAlpha(0.1f);
                            img2.setAlpha(0.0f);
                            img3.setAlpha(0.0f);
                            img4.setAlpha(0.0f);
                            img5.setAlpha(0.0f);
                            img6.setAlpha(0.0f);
                            img7.setAlpha(0.0f);
                            img8.setAlpha(0.0f);
                            img9.setAlpha(0.0f);
                            running=false;
                            progress=-1;

                        }
                        else if (progress==0){
                            img1.setAlpha(0.0f);
                            img2.setAlpha(0.0f);
                            img3.setAlpha(0.0f);
                            img4.setAlpha(0.0f);
                            img5.setAlpha(0.0f);
                            img6.setAlpha(0.0f);
                            img7.setAlpha(0.0f);
                            img8.setAlpha(0.0f);
                            img9.setAlpha(0.0f);
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
