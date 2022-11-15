package com.hangoverstudios.men.photo.suite.editor.app.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.activities.ExitAppActivity;
import com.hangoverstudios.men.photo.suite.editor.app.activities.MainActivity;
import com.hangoverstudios.men.photo.suite.editor.app.activities.SaveFinishActivity;
import com.hangoverstudios.men.photo.suite.editor.app.activities.SetSuitActivity;
import com.hangoverstudios.men.photo.suite.editor.app.activities.StickerEditActivity;
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;
import com.hangoverstudios.men.photo.suite.editor.app.mask.MaskEffect;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;

public class MyNativeView extends FrameLayout {
    private static final String TAG = "MyNativeView";
    public FrameLayout framAds;
    public FrameLayout frameLayout;
    public RelativeLayout lv_image;
    public NativeAd nativeAd;
    public ShimmerFrameLayout shimmerFrameLayout;
    private Context mContext;
    private Boolean dialogContext = false;

    public MyNativeView(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public MyNativeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;
        init(attributeSet);
    }

    public MyNativeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        mContext = context;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {

        FrameLayout.inflate(getContext(), R.layout.layout_my_native, this);
        this.framAds = (FrameLayout) findViewById(R.id.fm_ads);
        this.lv_image = (RelativeLayout) findViewById(R.id.lv_image);
        this.shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer);
        this.frameLayout = (FrameLayout) findViewById(R.id.native_adsContent);

        if(mContext != null) {
            if (mContext instanceof MainActivity)
                showNativeAdBasedOnKeys("nativeMain");
            else if (mContext instanceof ExitAppActivity || mContext instanceof SaveFinishActivity || mContext instanceof MaskEffect)
                showNativeAdBasedOnKeys("nativeExitSave");
            else
                showNativeAdBasedOnKeys("nativeDialog");
        }

    }

    private void showNativeAdBasedOnKeys(String key) {
        if (CommonMethods.getInstance().getSku().equals("") && CommonMethods.getInstance().getPurchaseState() == -1 || CommonMethods.getInstance().getSku().equals("removeads") && CommonMethods.getInstance().getPurchaseState() != 1) {
            if (key.equals("nativeExitSave")) {
                dialogContext = false;
                if (RemoteConfigValues.getOurRemote().getShowNativeAd() != null) {
                    if (RemoteConfigValues.getOurRemote().getShowNativeAd().equals("true")) {
                        nativeAdCommonPart();
                    } else {
                        MyNativeView.this.lv_image.setVisibility(VISIBLE);
                        MyNativeView.this.framAds.setVisibility(GONE);
                    }
                }
            }
            else if(key.equals("nativeDialog"))
            {
                dialogContext = true;
                if (RemoteConfigValues.getOurRemote().getShowNativeAdOnDialog() != null) {
                    if (RemoteConfigValues.getOurRemote().getShowNativeAdOnDialog().equals("true")) {
                        nativeAdCommonPart();
                    } else {
                        MyNativeView.this.lv_image.setVisibility(VISIBLE);
                        MyNativeView.this.framAds.setVisibility(GONE);
                    }
                }
            }
            else if(key.equals("nativeMain"))
            {
                dialogContext = false;
                if (RemoteConfigValues.getOurRemote().getShowNativeAdOnMainScreen() != null) {
                    if (RemoteConfigValues.getOurRemote().getShowNativeAdOnMainScreen().equals("true")) {
                        nativeAdCommonPart();
                    } else {
                        MyNativeView.this.lv_image.setVisibility(VISIBLE);
                        MyNativeView.this.framAds.setVisibility(GONE);
                    }
                }
            }
        } else {
            MyNativeView.this.lv_image.setVisibility(VISIBLE);
            MyNativeView.this.framAds.setVisibility(GONE);
        }
    }

    private void nativeAdCommonPart() {
        if (RemoteConfigValues.getOurRemote().getAdRotationPolicyNative().equals("true")) {
            MyNativeView.this.framAds.setVisibility(VISIBLE);
            MyNativeView.this.lv_image.setVisibility(GONE);

            // CommonMethods.getInstance().admobAd(MainActivity.this, nativeAdContainer);
            adMobNativeAd();
        } else {
            if (RemoteConfigValues.getOurRemote().getNativeOnlyGoogle().equals("true")) {
                MyNativeView.this.framAds.setVisibility(VISIBLE);
                MyNativeView.this.lv_image.setVisibility(GONE);
                // CommonMethods.getInstance().admobAd(MainActivity.this, nativeAdContainer);
                adMobNativeAd();
            } else {
                MyNativeView.this.lv_image.setVisibility(VISIBLE);
                MyNativeView.this.framAds.setVisibility(GONE);
            }
        }
    }

    public void adMobNativeAd() {
        AdLoader.Builder builder = new AdLoader.Builder(getContext(), getContext().getString(R.string.admob_native_id));
        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        boolean isDestroyed = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            if(dialogContext){
                                isDestroyed = ((Activity) ((ContextThemeWrapper) mContext).getBaseContext()).isDestroyed();
                                if (isDestroyed || ((Activity) ((ContextThemeWrapper) mContext).getBaseContext()).isFinishing() || ((Activity) ((ContextThemeWrapper) mContext).getBaseContext()).isChangingConfigurations()) {
                                    nativeAd.destroy();
                                    return;
                                }
                            }else{
                                isDestroyed = ((Activity) getContext()).isDestroyed();
                                if (isDestroyed || ((Activity) getContext()).isFinishing() || ((Activity) getContext()).isChangingConfigurations()) {
                                    nativeAd.destroy();
                                    return;
                                }
                            }
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (MyNativeView.this.nativeAd != null) {
                            MyNativeView.this.nativeAd.destroy();
                        }
                        MyNativeView.this.nativeAd = nativeAd;
                        if(dialogContext){
                            NativeAdView adView =
                                    (NativeAdView) ((Activity) ((ContextThemeWrapper) mContext).getBaseContext()).getLayoutInflater().inflate(R.layout.ad_unified, null);
                            populateNativeAdView(nativeAd, adView);
                            frameLayout.removeAllViews();
                            //shimmerFrameLayout.setVisibility(4);
                            frameLayout.addView(adView);
                        }else{
                            NativeAdView adView =
                                    (NativeAdView) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.ad_unified, null);
                            populateNativeAdView(nativeAd, adView);
                            frameLayout.removeAllViews();
                            //shimmerFrameLayout.setVisibility(4);
                            frameLayout.addView(adView);
                        }
                    }
                });

        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(true).build();

        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @SuppressLint("WrongConstant")
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                        MyNativeView.this.lv_image.setVisibility(VISIBLE);
                                        MyNativeView.this.framAds.setVisibility(GONE);
                                    }

                                    @Override
                                    public void onAdLoaded() {
                                        super.onAdLoaded();
                                        MyNativeView.this.framAds.setVisibility(VISIBLE);
                                        MyNativeView.this.lv_image.setVisibility(GONE);
                                        //shimmerFrameLayout.setVisibility(GONE);
                                        if ( CommonMethods.getInstance().getNativeaddMainListener()!=null){
                                            CommonMethods.getInstance().getNativeaddMainListener().MainnativeadListenert();

                                        }
                                    }
                                })
                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public static void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        } else {
        }
    }
}
