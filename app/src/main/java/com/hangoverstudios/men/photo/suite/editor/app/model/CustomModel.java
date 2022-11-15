package com.hangoverstudios.men.photo.suite.editor.app.model;

import android.media.MediaRecorder;
import android.widget.ImageView;

public class CustomModel {

    public interface OnCustomStateListener {
        void stateChanged();
    }

    private static CustomModel mInstance;
    private OnCustomStateListener mListener;
    private boolean mState;


    public MediaRecorder getMediaRecorder() {
        return mediaRecorder;
    }

    public void setMediaRecorder(MediaRecorder mediaRecorder) {
        this.mediaRecorder = mediaRecorder;
    }

    private MediaRecorder mediaRecorder = null;

    private boolean canDisplayAppOpen = true;

    private boolean wasHDSelected = false;
    private boolean isAudioEnabled = false;

    public boolean isCustomChecked() {
        return customChecked;
    }

    public void setCustomChecked(boolean customChecked) {
        this.customChecked = customChecked;
    }

    private boolean customChecked = false;
    private  ImageView imageView = null;
    private  boolean recordScreen = false;
    public boolean isRecordScreen() {
        return recordScreen;
    }

    public void setRecordScreen(boolean recordScreen) {
        this.recordScreen = recordScreen;
    }


    private CustomModel() {}

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public static CustomModel getInstance() {
        if(mInstance == null) {
            mInstance = new CustomModel();
        }
        return mInstance;
    }

    public void setListener(OnCustomStateListener listener) {
        mListener = listener;
    }

    public void changeState(boolean state) {
        if(mListener != null) {
            mState = state;
            notifyStateChange();
        }
    }



    public boolean getState() {
        return mState;
    }


    private void notifyStateChange() {
        mListener.stateChanged();
    }

    public boolean canDisplayAppOpen() {
        return canDisplayAppOpen;
    }

    public void setCanDisplayAppOpen(boolean canDisplayAppOpen) {
        this.canDisplayAppOpen = canDisplayAppOpen;
    }

    public boolean isWasHDSelected() {
        return wasHDSelected;
    }

    public void setWasHDSelected(boolean wasHDSelected) {
        this.wasHDSelected = wasHDSelected;
    }

    public boolean isAudioEnabled() {
        return isAudioEnabled;
    }

    public void setAudioEnabled(boolean audioEnabled) {
        isAudioEnabled = audioEnabled;
    }
}
