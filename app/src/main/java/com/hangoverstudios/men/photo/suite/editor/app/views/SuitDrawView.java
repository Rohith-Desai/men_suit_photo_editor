package com.hangoverstudios.men.photo.suite.editor.app.views;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.hangoverstudios.men.photo.suite.editor.app.R;

public class SuitDrawView extends View {

    public Context mContext;
    public static int mode = 0;
    public static Bitmap savedBitmap;

    Bitmap bm;
    Bitmap clippedBitmap;
    Bitmap magicPointer;
    int[] saveBitmapData;
    int[] lastBitmapData;
    int viewWidth, viewHeight;
    int bmWidth, bmHeight;

    static Canvas newCanvas;
    static Paint eraser, uneraser;
    private Paint mBitmapPaint;
    private Paint mMaskPaint;
    static Canvas CANVAS;

    private static Path mPath, mPathErase;
    public static int ERASE_MODE = 0;
    public static int UNERASE_MODE = 1;
    public static int MAGIC_MODE = 2;
    public static int MAGIC_MODE_RESTORE = 3;
    public static int MOVING_MODE = 4;
    public static int MIRROR_MODE = 5;

    public PointF touchPoint;
    public PointF drawingPoint;
    public int magicTouchRange = 200;
    public int magicThreshold = 15;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private int strokeWidth = 10;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int touchMode = NONE;
    String TAG = "tri.dung";
    boolean TOUCH = false;
    ArrayList<int[]> stackChange;
    ArrayList<Boolean> checkMirrorStep;
    int currentIndex = -1;
    final int STACKSIZE = 10;
    private String filename;
    HashMap<Integer, Float> zoomIncrease = new HashMap<>();
    HashMap<Integer, Float> newDistance = new HashMap<>();
    public static int POINTER_DISTANCE;
    public static int POINTER_OFFSET = 20;
    int ZOOM_PROGRESS = 0;
    Bitmap bmp;
    public MotionEvent motionEvent;
    Point zoomPos = new Point();
    private float[] lastEvent = null;
    private float newRot = 0f;
    private float d = 0f;
    private DisplayMetrics dMetrics;
    private int mScreenwidth, mScreenHeight;

    public SuitDrawView(Context context, Bitmap bm, int w, int h, int viewwidth, int viewheight) {
        super(context);
        bmp = bm;
        mContext = context;
        viewWidth = viewwidth;
        viewHeight = viewheight;
        bmWidth = w;
        bmHeight = h;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init(bm, w, h);
        zoomIncrease.put(1, (float) 1.5);
        zoomIncrease.put(2, (float) 2.0);
        zoomIncrease.put(3, (float) 2.5);
        zoomIncrease.put(4, (float) 3.0);
        zoomIncrease.put(5, (float) 3.5);

        newDistance.put(1, (float) 500);
        newDistance.put(2, (float) 525);
        newDistance.put(3, (float) 550);
        newDistance.put(4, (float) 575);
        newDistance.put(5, (float) 600);
    }

    public void switchMode(int _mode) {
        mode = _mode;
        resetPath();
        saveLastMaskData();
        if (mode == MAGIC_MODE || mode == MAGIC_MODE_RESTORE) {
            magicPointer = BitmapFactory.decodeResource(getResources(), R.drawable.color_select);
        } else if (mode == ERASE_MODE || mode == UNERASE_MODE) {
            magicPointer = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.color_select), strokeWidth + 5, strokeWidth + 5, false);
        }
        invalidate();

    }

    public void init(Bitmap bitmap, int w, int h) {
        mPath = new Path();
        mPathErase = new Path();

        eraser = new Paint();
        eraser.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        eraser.setAntiAlias(true);
        eraser.setStyle(Paint.Style.STROKE);
        eraser.setStrokeJoin(Paint.Join.ROUND);
        eraser.setStrokeCap(Paint.Cap.ROUND);
        eraser.setStrokeWidth(strokeWidth);

        uneraser = new Paint();
        uneraser.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
        uneraser.setAntiAlias(true);
        uneraser.setStyle(Paint.Style.STROKE);
        uneraser.setStrokeJoin(Paint.Join.ROUND);
        uneraser.setStrokeCap(Paint.Cap.ROUND);
        uneraser.setStrokeWidth(strokeWidth);


        dMetrics = getResources().getDisplayMetrics();
        mScreenwidth = dMetrics.widthPixels;
        mScreenHeight = dMetrics.heightPixels;
//        matrix.postTranslate((viewWidth - w) / 2, (viewHeight - h) / 2);
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);

        Display mdisp = wm.getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxX = mdispSize.x;
        int maxY = mdispSize.y;
        
        matrix.postTranslate(maxX/4, maxY/10);
//        matrix.postTranslate(300, 200);

        mBitmapPaint = new Paint();
        mBitmapPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        mBitmapPaint.setAntiAlias(true);

        mMaskPaint = new Paint();
        mMaskPaint.setAntiAlias(true);

        bm = bitmap;
        if(bm!=null){
            bm = bm.copy(Bitmap.Config.ARGB_8888, true);
        }

        clippedBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        newCanvas = new Canvas(clippedBitmap);
        newCanvas.save();
        newCanvas.drawARGB(255, 255, 255, 255);
        magicTouchRange = w > h ? h / 2 : w / 2;

        saveBitmapData = new int[w * h];
        bm.getPixels(saveBitmapData, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
        lastBitmapData = new int[w * h];
        magicPointer = BitmapFactory.decodeResource(getResources(), R.drawable.color_select);
        touchPoint = new PointF(w / 2, h / 2);
        drawingPoint = new PointF(w / 4, h / 2);

        saveLastMaskData();
        stackChange = new ArrayList<>();
        checkMirrorStep = new ArrayList<>();
        addToStack(false);

        filename = "img_" + String.format("%d.jpg", System.currentTimeMillis());
        POINTER_DISTANCE = (int) (POINTER_OFFSET * mContext.getResources().getDisplayMetrics().density);
    }

    void addToStack(boolean isMirror) {
        if (stackChange.size() >= STACKSIZE) {
            stackChange.remove(0);
            if (currentIndex > 0) currentIndex--;
        }
        if (stackChange != null) {
            if (currentIndex == 0) {
                int size = stackChange.size();
                for (int i = size - 1; i > 0; i--) {
                    stackChange.remove(i);
                    checkMirrorStep.remove(i);
                }
            }
            int[] pix = new int[clippedBitmap.getWidth() * clippedBitmap.getHeight()];
            clippedBitmap.getPixels(pix, 0, clippedBitmap.getWidth(), 0, 0, clippedBitmap.getWidth(), clippedBitmap.getHeight());
            stackChange.add(pix);
            checkMirrorStep.add(isMirror);
            currentIndex = stackChange.size() - 1;
        }
    }

    public Bitmap drawBitmap(Bitmap bmpDraw) {

        if (mode == ERASE_MODE || mode == UNERASE_MODE) {
            if (mode == ERASE_MODE) {
                uneraser.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
            } else {
                uneraser.setXfermode(new PorterDuffXfermode(Mode.SRC));
            }

            float strokeRatio = 1;

            if (SCALE > 1) strokeRatio = SCALE;

            eraser.setStrokeWidth(strokeWidth / strokeRatio);
            uneraser.setStrokeWidth(strokeWidth / strokeRatio);

            newCanvas.drawPath(mPath, eraser);
            newCanvas.drawPath(mPathErase, uneraser);
        }

        return clippedBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CANVAS = canvas;
        if (ZOOM_PROGRESS != 0 && !TOUCH) {
            Matrix matrix2 = new Matrix();
            matrix2.reset();
            matrix2.postScale(ZOOM_PROGRESS, ZOOM_PROGRESS, zoomPos.x, zoomPos.y);
            canvas.drawBitmap(bm, matrix2, mMaskPaint);
            canvas.drawBitmap(drawBitmap(bm), matrix2, mBitmapPaint);
        } else {
            canvas.drawBitmap(bm, matrix, mMaskPaint);
            canvas.drawBitmap(drawBitmap(bm), matrix, mBitmapPaint);
        }
        if (mode == MAGIC_MODE || mode == MAGIC_MODE_RESTORE || mode == ERASE_MODE || mode == UNERASE_MODE) {
            canvas.drawBitmap(magicPointer, drawingPoint.x - magicPointer.getWidth() / 2, drawingPoint.y - magicPointer.getHeight() / 2, mMaskPaint);
        }
        super.onDraw(canvas);
    }

    public void saveLastMaskData() {
        clippedBitmap.getPixels(lastBitmapData, 0, clippedBitmap.getWidth(), 0, 0, clippedBitmap.getWidth(), clippedBitmap.getHeight());
    }

    public void resetPath() {
        mPath.reset();
        mPathErase.reset();
    }

    private void touch_start(float x, float y) {
        mPath.reset();
        mPathErase.reset();

        if (mode == ERASE_MODE) mPath.moveTo(x, y);
        else mPathErase.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            if (mode == ERASE_MODE) mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            else mPathErase.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        if (mode == ERASE_MODE) mPath.lineTo(mX, mY);
        else mPathErase.lineTo(mX, mY);
    }

    PointF DownPT = new PointF();
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    float SCALE = 1.0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        zoomPos.x = (int) event.getX();
        zoomPos.y = (int) event.getY();
        setMotionEvent(event);
        if (mode == ERASE_MODE || mode == UNERASE_MODE) {
            y = y - POINTER_DISTANCE;
        }
        if (mode == MAGIC_MODE || mode == MAGIC_MODE_RESTORE || mode == ERASE_MODE || mode == UNERASE_MODE) {
            drawingPoint.x = x;
            drawingPoint.y = y;
        }
        if (mode != MOVING_MODE) {
            float[] v = new float[9];
            matrix.getValues(v);
            float mScalingFactor = v[Matrix.MSCALE_X];
            RectF r = new RectF();
            matrix.mapRect(r);
            float scaledX = (x - r.left);
            float scaledY = (y - r.top);
            scaledX /= mScalingFactor;
            scaledY /= mScalingFactor;
            x = scaledX;
            y = scaledY;
        }

        int maskedAction = event.getActionMasked();

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                lastEvent = null;
                touchMode = DRAG;

                if (mode == ERASE_MODE || mode == UNERASE_MODE) {
                    touch_start(x, y);
                } else if (mode == MOVING_MODE) {
                    DownPT.x = event.getX();
                    DownPT.y = event.getY();
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (touchMode == DRAG) {
                    if (mode == ERASE_MODE || mode == UNERASE_MODE) touch_move(x, y);
                    else if (mode == MOVING_MODE) {
                        PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
                        matrix.postTranslate(mv.x, mv.y);
                        DownPT.x = event.getX();
                        DownPT.y = event.getY();
                    } else if (mode == MAGIC_MODE || mode == MAGIC_MODE_RESTORE) {
                        touchPoint.x = x;
                        touchPoint.y = y;
                    }
                    invalidate();
                } else if (touchMode == ZOOM && mode == MOVING_MODE) {
                    draw(CANVAS);
                    setZoom1(event);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (mode == ERASE_MODE || mode == UNERASE_MODE) {
                    touch_up();
                    Log.d(TAG, "add to stack");
                    addToStack(false);
                } else if (mode == MAGIC_MODE || mode == MAGIC_MODE_RESTORE) {
                    touchPoint.x = x;
                    touchPoint.y = y;
                    saveLastMaskData();
                }
                invalidate();
                resetPath();
                break;
            case MotionEvent.ACTION_POINTER_UP: // second finger lifted
                touchMode = NONE;
                lastEvent = null;
                Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
                draw(CANVAS);
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                setZoom2(event);
                break;
        }
        return true;
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private void setMotionEvent(MotionEvent event) {
        motionEvent = event;
    }

    private void setZoom1(MotionEvent event) {
        TOUCH = true;
        float newDist = spacing(event);
        Log.d(TAG, "newDist=" + newDist);
        if (newDist > 5f) {
            matrix.set(savedMatrix);
            SCALE = newDist / oldDist;
            matrix.postScale(SCALE, SCALE, mid.x, mid.y);
            Log.d(TAG, "scale =" + SCALE);
        }
        if (lastEvent != null && event.getPointerCount() == 2) {
            newRot = rotation(event);
            float r = newRot - d;
            float[] values = new float[9];
            matrix.getValues(values);
            float tx = values[0];
            float ty = values[4];
            float sx = values[8];
            float xc = (bmp.getWidth() / 2) * sx;
            float yc = (bmp.getHeight() / 2) * sx;
            matrix.postRotate(r, tx + xc, ty + yc);
        }
        invalidate();
    }

    private void setZoom2(MotionEvent event) {
        TOUCH = true;
        oldDist = spacing(event);
        if (oldDist > 5f) {
            savedMatrix.set(matrix);
            midPoint(mid, event);
            touchMode = ZOOM;
        }
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}

