package com.hangoverstudios.men.photo.suite.editor.app.model;

public class InterstitialAdModel {

    public interface OnInterstitialAdStateListener {
        void stateInterstitialAdChanged();
    }

    private static com.hangoverstudios.men.photo.suite.editor.app.model.InterstitialAdModel mInstance;
    private com.hangoverstudios.men.photo.suite.editor.app.model.InterstitialAdModel.OnInterstitialAdStateListener mInterstitialAdListener;
    private boolean mInterstitialAdState;

    private InterstitialAdModel() {}

    public static com.hangoverstudios.men.photo.suite.editor.app.model.InterstitialAdModel getInstance() {
        if(mInstance == null) {
            mInstance = new com.hangoverstudios.men.photo.suite.editor.app.model.InterstitialAdModel();
        }
        return mInstance;
    }

    public void setInterstitialAdListener(com.hangoverstudios.men.photo.suite.editor.app.model.InterstitialAdModel.OnInterstitialAdStateListener listener) {
        mInterstitialAdListener = listener;
    }

    public void changeInterstitialAdState(boolean state) {
        if(mInterstitialAdListener != null) {
            mInterstitialAdState = state;
            notifyStateChange();
        }
    }



    public boolean getInterstitialAdState() {
        return mInterstitialAdState;
    }


    private void notifyStateChange() {
        mInterstitialAdListener.stateInterstitialAdChanged();
    }

}
