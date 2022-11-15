package com.hangoverstudios.men.photo.suite.editor.app.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;


public class MyBannerView extends FrameLayout {
    private static final String TAG = "MyBannerView";
    public FrameLayout frameLayout;
    AdView mAdView;
    public ShimmerFrameLayout shimmerFrameLayout;
    Context context;

    public MyBannerView(Context context) {
        super(context);
        this.context = context;
        //init(null);
    }

    public MyBannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init(attributeSet);
    }

    public MyBannerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
        init(attributeSet);
    }

    private AdSize getAdSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, (int) (((float) displayMetrics.widthPixels) / displayMetrics.density));
    }

    private void init(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.MyBannerView, 0, 0);
            try {

            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        FrameLayout.inflate(context, R.layout.layout_my_banner, this);
        this.shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer);
        this.frameLayout = (FrameLayout) findViewById(R.id.adsContent);
        AdView adView = new AdView(context);
        this.mAdView = adView;
        adView.setAdSize(getAdSize());
        this.mAdView.setAdUnitId(getResources().getString(R.string.admob_banner_id));
        this.mAdView.setAdListener(new AdListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                shimmerFrameLayout.setVisibility(4);
                frameLayout.removeAllViews();
                frameLayout.addView(MyBannerView.this.mAdView);
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                MyBannerView.this.setVisibility(8);
            }

        });
        if (CommonMethods.getInstance().getSku().equals("") && CommonMethods.getInstance().getPurchaseState() == -1 || CommonMethods.getInstance().getSku().equals("removeads") && CommonMethods.getInstance().getPurchaseState() != 1) {
            if (RemoteConfigValues.getOurRemote().getRemoveAds() != null)
                if (!RemoteConfigValues.getOurRemote().getRemoveAds().equals("true")) {
                    load();
                }
        }
    }

    private void load() {
        AdRequest adRequest =
                new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //this.mAdView.loadAd(new AdRequest.Builder().addTestDevice("").build());
    }
}
