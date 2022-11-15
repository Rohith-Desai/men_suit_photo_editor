package com.hangoverstudios.men.photo.suite.editor.app.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicColorMatrix;
import android.renderscript.ScriptIntrinsicConvolve3x3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * Created by him on 9/10/2017.
 */

public class PhotoFilter {
    RenderScript renderScript;
    Allocation inputAllocation, outputAllocation;
    Bitmap outBitmap;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter1(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix1 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix1.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        -0.33f, -0.33f, -0.33f, 1.0f,
                        -0.59f, -0.59f, -0.59f, 1.0f,
                        -0.11f, -0.11f, -0.11f, 1.0f,
                        1.0f, 1.0f, 1.0f, 1.0f
                }));
        colorMatrix1.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter2(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix2 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix2.setGreyscale();
        colorMatrix2.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter3(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix3 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix3.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        0f, 0f, 0f, 0f,
                        0f, 0.78f, 0f, 0f,
                        0f, 0f, 1f, 0f,
                        0f, 0f, 0f, 1f,
                }));
        colorMatrix3.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter4(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix4 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix4.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        0.3f, 0f, 0f, 0f,
                        0f, 0.65f, 0f, 0f,
                        0f, 0f, 0.49f, 0f,
                        0f, 0f, 0f, 1f

                }));
        colorMatrix4.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter5(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix5 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix5.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        -0.359705309629158f, 0.377252728606377f, 0.663841667303255f, 0f,
                        1.56680818833214f, 0.456668209492391f, 1.12613917506705f, 0f,
                        -0.147102878702981f, 0.226079061901232f, -0.729980842370303f, 0f,
                        0f, 0f, 0f, 1f
                }));
        colorMatrix5.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter6(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix6 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix6.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        1.2f, 0.1f, 0.2f, 0.7f,
                        0.7f, 1f, 0f, -0.5f,
                        -0.7f, 0.2f, 0.5f, 1.3f,
                        0, -0.1f, 0f, 0.9f
                }));
        colorMatrix6.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter7(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix7 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix7.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        1.22994596833595f, 0.0209523774645382f, 0.383244054685119f, 0f,
                        0.450138899443543f, 1.18737418804171f, -0.106933249401007f, 0f
                        - 0.340084867779496f, 0.131673434493755f, 1.06368919471589f, 0f,
                        0f, 0f, 0f,
                        11.91f, 11.91f, 11.91f, 0f}));
        colorMatrix7.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter8(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix8 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix8.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        1.44f, 0f, 0f, 0f,
                        0f, 1.44f, 0f, 0f,
                        0f, 0f, 1.44f, 0f,
                        0f, 0f, 0f, 1f,
                }));
        colorMatrix8.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter9(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix9 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix9.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        -2f, -1f, 1f, -2f,
                        0f, -2f, 0f, 1f,
                        0f, 0f, -1f, 1f,
                        0f, 0f, 0f, 1f
                }));
        colorMatrix9.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter10(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix10 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix10.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        1f, 0f, 0.1f, -0.1f,
                        0f, 1f, 0.2f, 0f,
                        0f, 0f, 1.3f, 0f,
                        0f, 0f, 0f, 1

                }));
        colorMatrix10.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter11(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix11 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix11.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        1.72814708519562f, -0.412104992562475f, 0.541145007437525f, 0f,
                        0.289378264402959f, 1.18835534216106f, -1.17637173559704f, 0f,
                        -1.01752534959858f, 0.223749650401417f, 1.63522672815952f, 0f,
                        0f, 0f, 0f, 1f
                }));
        colorMatrix11.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter12(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix12 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix12.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {
                        .309f, .409f, .309f, 0f,
                        .609f, .309f, .409f, 0f,
                        0.42f, .42f, .2f, 0f,
                        0f, 0f, 0f, 1f


                }));
        colorMatrix12.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter13(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicConvolve3x3 convolve1 = ScriptIntrinsicConvolve3x3.create(renderScript, Element.U8_4(renderScript));
        convolve1.setInput(inputAllocation);
        convolve1.setCoefficients(new float[]
                {
                        -2, -1, 0,
                        -1, 1, 1,
                        0, 1, 2
                });
        convolve1.forEach(outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter14(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicConvolve3x3 convolve2 = ScriptIntrinsicConvolve3x3.create(renderScript, Element.U8_4(renderScript));
        convolve2.setInput(inputAllocation);
        convolve2.setCoefficients(new float[]
                {
                        .2f, .3f, .2f, .1f, .1f, .1f, .2f, .3f, .2f,

                });
        convolve2.forEach(outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter15(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix13 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix13.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {

                        2.10279132254252f, -0.298212630531356f, 0.42128146417712f, 0f,
                        0.222897572029231f, 1.68701190285368f, -0.883421304780577f, 0f,
                        -0.765688894571747f, 0.171200727677677f, 2.02213984060346f, 0f,
                        0, 0, 0, 1f


                }));
        colorMatrix13.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap filter16(@NonNull Context context, @NonNull Bitmap bitmap) {
        renderScript = RenderScript.create(context);
        outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        inputAllocation = Allocation.createFromBitmap(renderScript, bitmap);
        outputAllocation = Allocation.createTyped(renderScript, inputAllocation.getType());
        final ScriptIntrinsicColorMatrix colorMatrix14 = ScriptIntrinsicColorMatrix.create(renderScript, Element.U8_4(renderScript));
        colorMatrix14.setColorMatrix(new android.renderscript.Matrix4f(new float[]
                {

                        1.27488526960083f, -0.228511311848763f, 0.441088688151237f, 0,
                        0.323664244263542f, 0.955140825713134f, -0.705935755736458f, 0,
                        -0.698549513864371f, 0.173370486135629f, 1.16484706758522f, 0,
                        0, 0, 0, 1


                }));
        colorMatrix14.forEach(inputAllocation, outputAllocation);
        outputAllocation.copyTo(outBitmap);
        return outBitmap;
    }
}

