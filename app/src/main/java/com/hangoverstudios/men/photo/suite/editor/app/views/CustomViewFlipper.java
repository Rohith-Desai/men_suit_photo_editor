package com.hangoverstudios.men.photo.suite.editor.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class CustomViewFlipper extends ViewFlipper {

    public CustomViewFlipper(Context context) {
        super(context);
    }

    public CustomViewFlipper(Context context, AttributeSet attributeset) {
        super(context, attributeset);
    }

    protected void onDetachedFromWindow() {
        try {
            super.onDetachedFromWindow();
            return;
        } catch (IllegalArgumentException illegalargumentexception) {
            stopFlipping();
        }
    }
}
