package com.hangoverstudios.men.photo.suite.editor.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;
import com.hangoverstudios.men.photo.suite.editor.app.views.SuitDrawView;

public class DripEffect extends AppCompatActivity {

    LinearLayout layoutCanvas, dummy_layer;
    SuitDrawView mSuitDrawView;
    int viewWidth;
    int viewHeight;
    int bmWidth;
    int bmHeight;
    double bmRatio;
    double viewRatio;
    private CategoriesActivity categoriesActivity;
    ImageView suits_image, drip_ef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drip_effect);

        dummy_layer = (LinearLayout) findViewById(R.id.dummy_layer);
        suits_image = findViewById(R.id.suits_image);
        drip_ef = findViewById(R.id.drip_ef);

        Bitmap catSavedBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.drip_lay);

        categoriesActivity = ChangeBackgroundData.getChangeBackgroundData().getCategoriesActivity();

        if (categoriesActivity != null) {
            Bitmap catSavedBitmapNew = categoriesActivity.getSavedBitmap();
            suits_image.setImageBitmap(catSavedBitmapNew);
        }

        /*if (catSavedBitmap != null) {
            bmRatio = (double) catSavedBitmap.getHeight() / (double) catSavedBitmap.getWidth();
            if (bmRatio < viewRatio) {
                bmWidth = viewWidth;
                bmHeight = (int) (((double) viewWidth) * ((double) (catSavedBitmap.getHeight()) / (double) (catSavedBitmap.getWidth())));
            } else {
                bmHeight = viewHeight;
                bmWidth = (int) (((double) viewHeight) * ((double) (catSavedBitmap.getWidth()) / (double) (catSavedBitmap.getHeight())));
            }
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            mSuitDrawView = new SuitDrawView(this, catSavedBitmap, displayMetrics.widthPixels, displayMetrics.heightPixels, viewWidth, viewHeight);
            mSuitDrawView.setLayoutParams(new ViewGroup.LayoutParams(viewWidth, viewHeight));
            layoutCanvas.addView(mSuitDrawView);
            mSuitDrawView.switchMode(SuitDrawView.MOVING_MODE);
        }*/

        drip_ef.setImageBitmap(catSavedBitmap);

        PointF DownPT = new PointF(); // Record Mouse Position When Pressed Down
        PointF StartPT = new PointF(); // Record Start Position of 'img'
        drip_ef.setOnTouchListener(new View.OnTouchListener() {
            float lastY = Float.MIN_VALUE;
            float lastD = Float.MIN_VALUE;

            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_MOVE :
                        Log.d("TAGDRIP", "ACTION_MOVE: ");
                        drip_ef.setX((int)(StartPT.x + event.getX() - DownPT.x));
                        drip_ef.setY((int)(StartPT.y + event.getY() - DownPT.y));
                        StartPT.set( drip_ef.getX(), drip_ef.getY() );

                        lastD +=0.1;
                        /*if (lastY != Float.MIN_VALUE) {
                            final float dist = event.getRawY() - lastY;

                            dummy_layer.post(new Runnable() {
                                @Override
                                public void run() {
                                    ViewGroup.LayoutParams lp = dummy_layer.getLayoutParams();
                                    lp.height += dist;
                                    dummy_layer.setLayoutParams(lp);
                                }
                            });

                            v.requestLayout();
                        }*/

                        ViewGroup.LayoutParams lp = dummy_layer.getLayoutParams();
                        lp.height += lastD;
                        dummy_layer.setLayoutParams(lp);

                        break;
                    case MotionEvent.ACTION_DOWN :
                        Log.d("TAGDRIP", "ACTION_DOWN: ");
                        DownPT.set( event.getX(), event.getY() );
                        StartPT.set( drip_ef.getX(), drip_ef.getY() );
                        break;
                    case MotionEvent.ACTION_UP :
                        Log.d("TAGDRIP", "ACTION_UP: ");
                        // Nothing have to do

                        lastY = event.getRawY();
                        break;
                    default :
                        break;
                }
                return true;
            }
        });

    }
}