package com.hangoverstudios.men.photo.suite.editor.app.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import android.graphics.BitmapShader;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.util.Log;

import com.hangoverstudios.men.photo.suite.editor.app.R;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.MainActivity.selectedImageBitmap;

public class CropView extends View implements View.OnTouchListener {
    private Paint paint;
    public static Bitmap croppedBitmap;
    private Context mContext;
    public static List<Point> points;
    private boolean flgPathDraw = true;
    private static int POINTER_DISTANCE;
    public static int POINTER_OFFSET = 20;

    private Point mfirstpoint = null;

    private boolean bfirstpoint;
    private Point mlastpoint = null;
    Paint shaderPaint;
    Point zoomPos = new Point();
    int size_of_the_circle = 5;
    BitmapShader shader;
    Bitmap bitmap = selectedImageBitmap;
    Matrix matrix;

    public CropView(Context context) {
        super(context);
        mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        this.setOnTouchListener(this);
        points = new ArrayList<Point>();
        bfirstpoint = false;
    }

    public CropView(Context context, Bitmap b) {
        super(context);
        mContext = context;
        croppedBitmap = b;
        setFocusable(true);
        setFocusableInTouchMode(true);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        this.setOnTouchListener(this);
        points = new ArrayList<Point>();

        bfirstpoint = false;
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        matrix = new Matrix();
        shaderPaint = new Paint();
        shaderPaint.setColor(getResources().getColor(R.color.blue));
        shaderPaint.setShader(shader);

    }

    public CropView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLUE);
        this.setOnTouchListener(this);
        points = new ArrayList<Point>();
        bfirstpoint = false;

    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.white));
        if(croppedBitmap!=null){
            canvas.drawBitmap(croppedBitmap, 0, 0, null);
        }
        //DisplayMetrics displayMetrics = new DisplayMetrics();
//        Log.v("Bitmap", "Height" + croppedBitmap.getHeight());
//        Log.v("Bitmap", "Width" + croppedBitmap.getWidth());
        Path path = new Path();
        boolean first = true;
        for (int i = 0; i < points.size(); i += 2) {
            Point point = points.get(i);
            if (first) {
                first = false;
                path.moveTo(point.x, point.y);
            } else if (i < points.size() - 1) {
                Point next = points.get(i + 1);
                path.quadTo(point.x, point.y, next.x, next.y);
            } else {
                mlastpoint = points.get(i);
                path.lineTo(point.x, point.y);
            }
        }
        canvas.drawCircle(zoomPos.x, zoomPos.y, size_of_the_circle, shaderPaint);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        POINTER_DISTANCE = (int) (POINTER_OFFSET * mContext.getResources().getDisplayMetrics().density);
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY() - POINTER_DISTANCE;
        zoomPos.x = (int) event.getX();
        zoomPos.y = (int) event.getY() - POINTER_DISTANCE;
        matrix.reset();
        matrix.postScale(2f, 2f, zoomPos.x, zoomPos.y);
        shader.setLocalMatrix(matrix);
        if (flgPathDraw) {
            if (bfirstpoint) {
                if (comparepoint(mfirstpoint, point)) {
                    points.add(mfirstpoint);
                    flgPathDraw = false;
                } else {
                    points.add(point);
                }
            } else {
                points.add(point);
            }
            if (!(bfirstpoint)) {

                mfirstpoint = point;
                bfirstpoint = true;
            }
        }
        invalidate();
        if (event.getAction() == MotionEvent.ACTION_UP) {

            mlastpoint = point;
            if (flgPathDraw) {
                if (points.size() > 12) {
                    if (!comparepoint(mfirstpoint, mlastpoint)) {
                        flgPathDraw = false;
                        points.add(mfirstpoint);
                    }
                }
            }
        }
        return true;
    }

    public void setFreeCropPointerOffset(int pointerOffset) {
        POINTER_OFFSET = pointerOffset;
        POINTER_DISTANCE = (int) (POINTER_OFFSET * mContext.getResources().getDisplayMetrics().density);
    }

    private boolean comparepoint(Point first, Point current) {
        int left_range_x = (int) (current.x - 3);
        int left_range_y = (int) (current.y - 3);
        int right_range_x = (int) (current.x + 3);
        int right_range_y = (int) (current.y + 3);
        if ((left_range_x < first.x && first.x < right_range_x)
                && (left_range_y < first.y && first.y < right_range_y)) {
            if (points.size() < 10) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }
}