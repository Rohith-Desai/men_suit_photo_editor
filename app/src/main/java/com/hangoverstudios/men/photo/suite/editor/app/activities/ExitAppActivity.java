package com.hangoverstudios.men.photo.suite.editor.app.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.hangoverstudios.men.photo.suite.editor.app.ads.MyInterstitialAdView;
import com.hangoverstudios.men.photo.suite.editor.app.model.OurAdsData;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;

public class ExitAppActivity extends AppCompatActivity {
    private TextView yes, no;
    private FrameLayout adContainerView;
    private AdView adViewBanner1;
    private NativeAd nativeAdGlobal;
    private LinearLayout layout_promotion;
    ImageView oneImg, twoImg, threeImg, fourImg, fiveImg, sixImg, sevenImg, eightImg, nineImg;
    //private RelativeLayout alternativeToNativeAd;
    //ScrollView scrollViewNative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet_exit);
       // alternativeToNativeAd = findViewById(R.id.alternative_to_native_ad_exit);
        //scrollViewNative = findViewById(R.id.ads_scroll_exit);
        MyInterstitialAdView.getInstance().onExitAD(ExitAppActivity.this);

//        layout_promotion = findViewById(R.id.enable_our_apps_exit);

//        if (RemoteConfigValues.getOurRemote().getOurApps() != null) {
//            if (RemoteConfigValues.getOurRemote().getOurApps().equals("true")) {
//                loadOurApps();
//                layout_promotion.setVisibility(View.VISIBLE);
//            } else {
//                layout_promotion.setVisibility(View.GONE);
//            }
//        }

       /* if (CommonMethods.getInstance().getSku().equals("") && CommonMethods.getInstance().getPurchaseState() == -1 || CommonMethods.getInstance().getSku().equals("removeads") && CommonMethods.getInstance().getPurchaseState() != 1) {
            if (RemoteConfigValues.getOurRemote().getShowNativeAd() != null) {
                if (RemoteConfigValues.getOurRemote().getShowNativeAd().equals("true")) {
                    if (RemoteConfigValues.getOurRemote().getAdRotationPolicyNative().equals("true")) {
                        alternativeToNativeAd.setVisibility(View.GONE);
                        scrollViewNative.setVisibility(View.VISIBLE);
                        admobAd();
                    } else {
                        if (RemoteConfigValues.getOurRemote().getNativeOnlyGoogle().equals("true")) {
                            alternativeToNativeAd.setVisibility(View.GONE);
                            scrollViewNative.setVisibility(View.VISIBLE);
                            admobAd();
                        }
                    }
                }
            }
        }
        else
        {
            alternativeToNativeAd.setVisibility(View.VISIBLE);
            scrollViewNative.setVisibility(View.GONE);
        }*/
        /*adContainerView = findViewById(R.id.admob_banner_exit_ad);
        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adViewBanner1, adContainerView, ExitAppActivity.this);
            }
        });*/

        yes = findViewById(R.id.exit_yes);
        no = findViewById(R.id.exit_no);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExitAppActivity.this, MainActivity.class);
                intent.putExtra("showAd", false);
                startActivity(intent);
                onBackPressed();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

   /* private void admobAd() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.admob_native_id));
        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        boolean isDestroyed = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            isDestroyed = isDestroyed();
                        }
                        if (isDestroyed || isFinishing() || isChangingConfigurations()) {
                            nativeAd.destroy();
                            return;
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (nativeAdGlobal != null) {
                            nativeAdGlobal.destroy();
                        }
                        nativeAdGlobal = nativeAd;
                        FrameLayout frameLayout = findViewById(R.id.native_ad_exit);
                        NativeAdView adView =
                                (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                        populateNativeAdView(nativeAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                });

        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(true).build();

        com.google.android.gms.ads.nativead.NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                    }
                                })
                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
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
    }*/
   void loadOurApps() {
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(ExitAppActivity.this));

       if (OurAdsData.getData().getOurApp1Link() == null || OurAdsData.getData().getOurApp2Link() == null ||
               OurAdsData.getData().getOurApp3Link() == null || OurAdsData.getData().getOurApp4Link() == null ||
               OurAdsData.getData().getOurApp5Link() == null || OurAdsData.getData().getOurApp6Link() == null ||
               OurAdsData.getData().getOurApp7Link() == null || OurAdsData.getData().getOurApp8Link() == null ||
               OurAdsData.getData().getOurApp9Link() == null
       ) {
           return;
       }

       if (OurAdsData.getData().getOurApp1icon() == null || OurAdsData.getData().getOurApp2icon() == null ||
               OurAdsData.getData().getOurApp3icon() == null || OurAdsData.getData().getOurApp4icon() == null ||
               OurAdsData.getData().getOurApp5icon() == null || OurAdsData.getData().getOurApp6icon() == null ||
               OurAdsData.getData().getOurApp7icon() == null || OurAdsData.getData().getOurApp8icon() == null ||
               OurAdsData.getData().getOurApp9icon() == null
       ) {
           return;
       }

       if (!OurAdsData.getData().getOurApp1Link().equals("")) {
           oneImg = findViewById(R.id.one);

           Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp1icon()).centerCrop().into(new BitmapImageViewTarget(oneImg) {
               @Override
               protected void setResource(Bitmap resource) {
                   RoundedBitmapDrawable circularBitmapDrawable =
                           RoundedBitmapDrawableFactory.create(ExitAppActivity.this.getResources(), resource);
                   circularBitmapDrawable.setCircular(true);
                   oneImg.setImageDrawable(circularBitmapDrawable);
               }
           });
//            Glide.with(this).load(OurAdsData.getData().getOurApp1icon()).thumbnail(0.5f)
//                    .crossFade()
//
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(oneImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp1icon(), oneImg);
           if (oneImg != null) {
               oneImg.setVisibility(View.VISIBLE);
               oneImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp1Link()));
                       startActivity(myIntent);
                   }
               });
           }
       }

       if (!OurAdsData.getData().getOurApp2Link().equals("")) {
           twoImg = findViewById(R.id.two);
           Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp2icon()).centerCrop()
                   .into(new BitmapImageViewTarget(twoImg) {
                       @Override
                       protected void setResource(Bitmap resource) {
                           RoundedBitmapDrawable circularBitmapDrawable =
                                   RoundedBitmapDrawableFactory.create(ExitAppActivity.this.getResources(), resource);
                           circularBitmapDrawable.setCircular(true);
                           twoImg.setImageDrawable(circularBitmapDrawable);
                       }
                   });
//            Glide.with(this).load(OurAdsData.getData().getOurApp2icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(twoImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp2icon(), twoImg);
           if (twoImg != null) {
               twoImg.setVisibility(View.VISIBLE);
               twoImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp2Link()));
                       startActivity(myIntent);
                   }
               });
           }
       }


       if (!OurAdsData.getData().getOurApp3Link().equals("")) {
           threeImg = findViewById(R.id.three);
           Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp3icon()).centerCrop()
                   .into(new BitmapImageViewTarget(threeImg) {
                       @Override
                       protected void setResource(Bitmap resource) {
                           RoundedBitmapDrawable circularBitmapDrawable =
                                   RoundedBitmapDrawableFactory.create(ExitAppActivity.this.getResources(), resource);
                           circularBitmapDrawable.setCircular(true);
                           threeImg.setImageDrawable(circularBitmapDrawable);
                       }
                   });
//            Glide.with(this).load(OurAdsData.getData().getOurApp3icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(threeImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp3icon(), threeImg);
           if (threeImg != null) {
               threeImg.setVisibility(View.VISIBLE);
               threeImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp3Link()));
                       startActivity(myIntent);
                   }
               });
           }
       }

       if (!OurAdsData.getData().getOurApp4Link().equals("")) {
           fourImg = findViewById(R.id.four);
           Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp4icon()).centerCrop()
                   .into(new BitmapImageViewTarget(fourImg) {
                       @Override
                       protected void setResource(Bitmap resource) {
                           RoundedBitmapDrawable circularBitmapDrawable =
                                   RoundedBitmapDrawableFactory.create(ExitAppActivity.this.getResources(), resource);
                           circularBitmapDrawable.setCircular(true);
                           fourImg.setImageDrawable(circularBitmapDrawable);
                       }
                   });
//            Glide.with(this).load(OurAdsData.getData().getOurApp4icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(fourImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp4icon(), fourImg);
           if (fourImg != null) {
               fourImg.setVisibility(View.VISIBLE);
               fourImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp4Link()));
                       startActivity(myIntent);
                   }
               });
           }
       }

       if (!OurAdsData.getData().getOurApp5Link().equals("")) {
           fiveImg = findViewById(R.id.five);
           Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp5icon()).centerCrop()
                   .into(new BitmapImageViewTarget(fiveImg) {
                       @Override
                       protected void setResource(Bitmap resource) {
                           RoundedBitmapDrawable circularBitmapDrawable =
                                   RoundedBitmapDrawableFactory.create(ExitAppActivity.this.getResources(), resource);
                           circularBitmapDrawable.setCircular(true);
                           fiveImg.setImageDrawable(circularBitmapDrawable);
                       }
                   });
//            Glide.with(this).load(OurAdsData.getData().getOurApp5icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(fiveImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp5icon(), fiveImg);
           if (fiveImg != null) {
               fiveImg.setVisibility(View.VISIBLE);
               fiveImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp5Link()));
                       startActivity(myIntent);
                   }
               });
           }
       }

       if (!OurAdsData.getData().getOurApp6Link().equals("")) {
           sixImg = findViewById(R.id.six);
           Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp6icon()).centerCrop()
                   .into(new BitmapImageViewTarget(sixImg) {
                       @Override
                       protected void setResource(Bitmap resource) {
                           RoundedBitmapDrawable circularBitmapDrawable =
                                   RoundedBitmapDrawableFactory.create(ExitAppActivity.this.getResources(), resource);
                           circularBitmapDrawable.setCircular(true);
                           sixImg.setImageDrawable(circularBitmapDrawable);
                       }
                   });
//            Glide.with(this).load(OurAdsData.getData().getOurApp6icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(sixImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp6icon(), sixImg);
           if (sixImg != null) {
               sixImg.setVisibility(View.VISIBLE);
               sixImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp6Link()));
                       startActivity(myIntent);
                   }
               });
           }
       }

       if (!OurAdsData.getData().getOurApp7Link().equals("")) {
           sevenImg = findViewById(R.id.seven);
           Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp7icon()).centerCrop()
                   .into(new BitmapImageViewTarget(sevenImg) {
                       @Override
                       protected void setResource(Bitmap resource) {
                           RoundedBitmapDrawable circularBitmapDrawable =
                                   RoundedBitmapDrawableFactory.create(ExitAppActivity.this.getResources(), resource);
                           circularBitmapDrawable.setCircular(true);
                           sevenImg.setImageDrawable(circularBitmapDrawable);
                       }
                   });
//            Glide.with(this).load(OurAdsData.getData().getOurApp7icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(sevenImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp7icon(), sevenImg);
           if (sevenImg != null) {
               sevenImg.setVisibility(View.VISIBLE);
               sevenImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp7Link()));
                       startActivity(myIntent);
                   }
               });
           }
       }

       if (!OurAdsData.getData().getOurApp8Link().equals("")) {
           eightImg = findViewById(R.id.eight);
           Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp8icon()).centerCrop()
                   .into(new BitmapImageViewTarget(eightImg) {
                       @Override
                       protected void setResource(Bitmap resource) {
                           RoundedBitmapDrawable circularBitmapDrawable =
                                   RoundedBitmapDrawableFactory.create(ExitAppActivity.this.getResources(), resource);
                           circularBitmapDrawable.setCircular(true);
                           eightImg.setImageDrawable(circularBitmapDrawable);
                       }
                   });
//            Glide.with(this).load(OurAdsData.getData().getOurApp8icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(eightImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp8icon(), eightImg);
           if (eightImg != null) {
               eightImg.setVisibility(View.VISIBLE);
               eightImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp8Link()));
                       startActivity(myIntent);
                   }
               });
           }
       }

       if (!OurAdsData.getData().getOurApp9Link().equals("")) {
           nineImg = findViewById(R.id.nine);
           Glide.with(this).asBitmap().load(OurAdsData.getData().getOurApp9icon()).centerCrop()
                   .into(new BitmapImageViewTarget(nineImg) {
                       @Override
                       protected void setResource(Bitmap resource) {
                           RoundedBitmapDrawable circularBitmapDrawable =
                                   RoundedBitmapDrawableFactory.create(ExitAppActivity.this.getResources(), resource);
                           circularBitmapDrawable.setCircular(true);
                           nineImg.setImageDrawable(circularBitmapDrawable);
                       }
                   });
//            Glide.with(this).load(OurAdsData.getData().getOurApp9icon()).thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(nineImg);
//            imageLoader.displayImage(OurAdsData.getData().getOurApp9icon(), nineImg);
           if (nineImg != null) {
               nineImg.setVisibility(View.VISIBLE);
               nineImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(OurAdsData.getData().getOurApp9Link()));
                       startActivity(myIntent);
                   }
               });
           }
       }


   }
}
