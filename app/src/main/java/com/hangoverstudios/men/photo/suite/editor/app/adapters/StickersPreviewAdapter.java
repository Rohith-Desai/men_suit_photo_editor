package com.hangoverstudios.men.photo.suite.editor.app.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

//import static com.applovin.sdk.AppLovinSdkUtils.runOnUiThread;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.StickerEditActivity.stickerEditActivity;

public class StickersPreviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int STRING = 3;
    private final Context mContext;
    ArrayList<Integer> mRecyclerViewItems;
    private NativeAd nativeAdGlobal;
    String typeOfD = "";
    String hair[] = {"hair_eight.png", "hair_eighteen.png", "hair_eleven.png", "hair_fifteen.png", "hair_five.png", "hair_four.png",
            "hair_fourteen.png", "hair_nighnteen.png", "hair_nine.png", "hair_one.png", "hair_seven.png", "hair_six.png",
            "hair_sixteen.png", "hair_ten.png", "hair_thirteen.png", "hair_three.png", "hair_twelve.png", "hair_twenty.png",
            "hair_twentyeight.png", "hair_twentyfive.png", "hair_twentyfour.png", "hair_twentyone.png", "hair_twentyseven.png",
            "hair_twentysix.png", "hair_twentythree.png", "hair_twentytwo.png", "hair_two.png"};

    String mustache[] = {"mustache_eight.png", "mustache_five.png", "mustache_four.png", "mustache_one.png", "mustache_seven.png", "mustache_six.png", "mustache_three.png", "mustache_two.png"};

    String beard[] = {"beard_eight.png", "beard_eleven.png", "beard_four.png", "beard_nine.png", "beard_one.png", "beard_seven.png",
            "beard_six.png", "beard_three.png", "beard_twelve.png", "beard_two.png"};

    String eyebrows[] = {"ebrows_eight.png", "ebrows_eleven.png", "ebrows_five.png", "ebrows_four.png", "ebrows_nine.png", "ebrows_one.png", "ebrows_seven.png",
            "ebrows_six.png", "ebrows_ten.png", "ebrows_thirteen.png", "ebrows_three.png", "ebrows_twelve.png", "ebrows_two.png"};

    String eyeballs[] = {"eye_eight.png", "eye_eleven.png", "eye_five.png", "eye_four.png", "eye_nine.png", "eye_one.png", "eye_seven.png", "eye_six.png", "eye_ten.png", "eye_three.png", "eye_twelve.png", "eye_two.png"};

    String sunglasses[] = {"glass_eight.png", "glass_eleven.png", "glass_five.png", "glass_four.png", "glass_nine.png",
            "glass_one.png", "glass_seven.png", "glass_six.png", "glass_ten.png", "glass_three.png", "glass_twelve.png", "glass_two.png"};

    String caps[] = {"cap_eight.png", "cap_eightenn.png", "cap_eleven.png", "cap_fifteen.png", "cap_five.png", "cap_forty.png",
            "cap_fortyone.png", "cap_four.png", "cap_fourtenn.png", "cap_nighnteen.png", "cap_nine.png", "cap_one.png", "cap_seven.png",
            "cap_seventeen.png", "cap_six.png", "cap_sixteen.png", "cap_ten.png", "cap_thirteen.png", "cap_three.png", "cap_twelve.png",
            "cap_twenty.png", "cap_twentyfour.png", "cap_twentyone.png", "cap_twentysix.png", "cap_twentythree.png", "cap_twentytwo.png",
            "cap_twnetyfive.png", "cap_two.png"};

    String stoles[] = {"stole_eight.png", "stole_five.png", "stole_four.png", "stole_nine.png", "stole_one.png", "stole_seven.png",
            "stole_six.png", "stole_three.png", "stole_two.png"};

    String tie[] = {"bow_tie_eight.png", "bow_tie_eleven.png", "bow_tie_five.png", "bow_tie_four.png", "bow_tie_nine.png",
            "bow_tie_one.png", "bow_tie_seven.png", "bow_tie_six.png", "bow_tie_ten.png", "bow_tie_three.png", "bow_tie_two.png",
            "tie_eight.png", "tie_eleven.png", "tie_five.png", "tie_four.png", "tie_nine.png", "tie_one.png", "tie_seven.png",
            "tie_six.png", "tie_ten.png", "tie_three.png", "tie_two.png"
    };

    String chain[] = {"chain_eight.png", "chain_eleven.png", "chain_fifteen.png", "chain_five.png", "chain_four.png",
            "chain_fouteen.png", "chain_nine.png", "chain_one.png", "chain_seven.png", "chain_six.png", "chain_ten.png",
            "chain_thirteen.png", "chain_three.png", "chain_twelve.png", "chain_two.png"};

    String earrings[] = {"ear_five.png", "ear_four.png", "ear_one.png", "ear_seven.png", "ear_six.png", "ear_three.png", "ear_two.png"};

    String sixPacks[] = {"pack_eight.png", "pack_five.png", "pack_four.png", "pack_nine.png", "pack_one.png", "pack_seven.png", "pack_six.png",
            "pack_three.png", "pack_two.png"};

    String eightPacks[] = {"eight_eight.png", "eight_five.png", "eight_four.png", "eight_one.png", "eight_seven.png", "eight_six.png",
            "eight_three.png", "eight_two.png"};

    String chest[] = {"chest_eight.png", "chest_five.png", "chest_four.png", "chest_one.png", "chest_seven.png", "chest_six.png", "chest_three.png", "chest_two.png"
    };

    String tattoos[] = {"tatto_eight.png", "tatto_eleven.png", "tatto_five.png", "tatto_four.png", "tatto_fourteen.png",
            "tatto_nine.png", "tatto_one.png", "tatto_seven.png", "tatto_six.png", "tatto_ten.png", "tatto_thirteen.png",
            "tatto_three.png", "tatto_twelve.png", "tatto_two.png"};

    String lovestickers[] = {"love_stickers_1.png", "love_stickers_2.png", "love_stickers_3.png", "love_stickers_4.png",
            "love_stickers_5.png", "love_stickers_6.png", "love_stickers_7.png", "love_stickers_8.png", "love_stickers_9.png",
            "love_stickers_10.png"};

    String expstickers[] = {"expressions_stickers_1.png", "expressions_stickers_2.png", "expressions_stickers_3.png",
            "expressions_stickers_4.png", "expressions_stickers_5.png", "expressions_stickers_6.png",
            "expressions_stickers_7.png", "expressions_stickers_8.png", "expressions_stickers_9.png", "expressions_stickers_10.png"};

    String wishstickers[] = {"stickers_wishe_1.png", "stickers_wishe_2.png", "stickers_wishe_3.png", "stickers_wishe_4.png",
            "stickers_wishe_5.png", "stickers_wishe_6.png", "stickers_wishe_7.png", "stickers_wishe_8.png", "stickers_wishe_9.png", "stickers_wishe_10.png"};

    String greetstickers[] = {"stickers_greet_1.png", "stickers_greet_2.png", "stickers_greet_3.png", "stickers_greet_4.png",
            "stickers_greet_5.png", "stickers_greet_6.png", "stickers_greet_7.png", "stickers_greet_8.png", "stickers_greet_9.png", "stickers_greet_10.png"};

    String totalFiles;
    TextView cFiles;
    int cdFile = 0;
    private ArrayList<String> lResources;
    Dialog dialog;

    public StickersPreviewAdapter(Context mContext, int[] mResources, String type) {
        typeOfD = type;
        mRecyclerViewItems = new ArrayList<>();
        this.mContext = mContext;
        for (int i = 0; i < mResources.length; i++) {
            mRecyclerViewItems.add(mResources[i]);
        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return STRING;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView2 = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.stickers_prev_item, parent, false);
        return new StickersPreviewAdapter.ViewHolder(menuItemLayoutView2);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final StickersPreviewAdapter.ViewHolder viewHolder = (StickersPreviewAdapter.ViewHolder) holder;
        Animation a = AnimationUtils.loadAnimation(mContext, R.anim.scale_up);
//        viewHolder.stickersPreviewItem.setImageResource(mRecyclerViewItems.get(position));
        Glide.with(mContext)
                .load(mRecyclerViewItems.get(position))
                .into(viewHolder.stickersPreviewItem);
        viewHolder.stickersPreviewItem.startAnimation(a);
        viewHolder.stickersPreviewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == mRecyclerViewItems.size() - 1) {
                    showDownloadDialog();
                } else {
                    Bitmap bitmap = BitmapFactory.decodeFile(stickerEditActivity.getURLForResource(mRecyclerViewItems.get(position)));

                    if (bitmap != null) {
                        stickerEditActivity.addStickerView(stickerEditActivity.getURLForResource(mRecyclerViewItems.get(position)));
                        stickerEditActivity.RESOURCE_ID_TO_COPY = stickerEditActivity.getURLForResource(mRecyclerViewItems.get(position));
                    } else {
                        String strPos = "";
                        if (position == 0) {
                            strPos = "one";
                        } else if (position == 1) {
                            strPos = "two";
                        } else {
                            strPos = "three";
                        }
                        Log.v("bitmapEventList", "res : " + typeOfD + "_" + strPos + ".PNG");
                        CommonMethods.getInstance().addToBitmapEventQueue(typeOfD + "_" + strPos);
                        if(stickerEditActivity!=null){
                            stickerEditActivity.addStickerView(mRecyclerViewItems.get(position));
                            stickerEditActivity.INT_RESOURCE_ID_TO_COPY = mRecyclerViewItems.get(position);
                        }
                    }
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView stickersPreviewItem;

        ViewHolder(View view) {
            super(view);
            stickersPreviewItem = view.findViewById(R.id.stickers_preview_single_item);
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        String[] paths;
        String fpath;

        public DownloadFileAsync(String[] paths) {
            super();
            this.paths = paths;
            for (int i = 0; i < paths.length; i++)
                System.out.println((i + 1) + ":  " + paths[i]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDownloadProgressDialog();
        }

        @Override
        protected String doInBackground(String... aurl) {
            try {
                for (int i = 0; i < aurl.length; i++) {
                    cdFile = i;
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cFiles.setText(cdFile + "/" + totalFiles);
                        }
                    });

                    String imgPath = RemoteConfigValues.getOurRemote().getImageUrl() + typeOfD + "/" + aurl[i];
                    fpath = getFileName(imgPath);
                    URL url = new URL(imgPath);
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    String paths = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)?
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/" + mContext.getResources().getString(R.string.app_name) + "/" + typeOfD + "/"
                            :Environment.getExternalStorageDirectory().toString()+ "/" +mContext.getResources().getString(R.string.app_name) + "/" + typeOfD + "/";
                    //File path = new File(Environment.getExternalStorageDirectory() + "/" + mContext.getResources().getString(R.string.app_name) + "/" + typeOfD + "/");
                    File path = new File(paths);
                    File file = new File(path, fpath);
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    int contentLength = connection.getContentLength();

                    DataInputStream stream = new DataInputStream(url.openStream());
                    byte[] buffer = new byte[contentLength];
                    stream.readFully(buffer);
                    stream.close();
                    DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
                    fos.write(buffer);
                    fos.flush();
                    fos.close();
                }
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
        }

        @Override
        protected void onPostExecute(String unused) {
            lResources = new ArrayList<>();
            System.out.println("unused: " + unused);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            String directory = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)?
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/" + mContext.getResources().getString(R.string.app_name) + "/" + typeOfD
                    :Environment.getExternalStorageDirectory().toString()+ "/" +mContext.getResources().getString(R.string.app_name) + "/" + typeOfD;
           // String directory = Environment.getExternalStorageDirectory().toString() + "/" + mContext.getResources().getString(R.string.app_name) + "/" + typeOfD;
            File directory1 = new File(directory);
            if (directory1.exists()) {
                File[] files = directory1.listFiles();
                int i = 0;
                if (files != null) {
                    for (File file : files) {
                        lResources.add(file.getAbsolutePath());
                        i++;
                    }
                }
            } else {
            }
            stickerEditActivity.setStickerPrevAdapter(typeOfD);
        }
    }

    public void showDownloadDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sticker_download_pop);
        TextView dialogOk = dialog.findViewById(R.id.yes);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeOfD.equals("hair")) {
                    totalFiles = hair.length + "";
                    new DownloadFileAsync(hair).execute(hair);
                } else if (typeOfD.equals("mustache")) {
                    totalFiles = mustache.length + "";
                    new DownloadFileAsync(mustache).execute(mustache);
                } else if (typeOfD.equals("beard")) {
                    totalFiles = beard.length + "";
                    new DownloadFileAsync(beard).execute(beard);
                } else if (typeOfD.equals("eyebrows")) {
                    totalFiles = eyebrows.length + "";
                    new DownloadFileAsync(eyebrows).execute(eyebrows);
                } else if (typeOfD.equals("eye")) {
                    totalFiles = eyeballs.length + "";
                    new DownloadFileAsync(eyeballs).execute(eyeballs);
                } else if (typeOfD.equals("glasses")) {
                    totalFiles = sunglasses.length + "";
                    new DownloadFileAsync(sunglasses).execute(sunglasses);
                } else if (typeOfD.equals("caps")) {
                    totalFiles = caps.length + "";
                    new DownloadFileAsync(caps).execute(caps);
                } else if (typeOfD.equals("stole")) {
                    totalFiles = stoles.length + "";
                    new DownloadFileAsync(stoles).execute(stoles);
                } else if (typeOfD.equals("tie")) {
                    totalFiles = tie.length + "";
                    new DownloadFileAsync(tie).execute(tie);
                } else if (typeOfD.equals("chain")) {
                    totalFiles = chain.length + "";
                    new DownloadFileAsync(chain).execute(chain);
                } else if (typeOfD.equals("ear")) {
                    totalFiles = earrings.length + "";
                    new DownloadFileAsync(earrings).execute(earrings);
                } else if (typeOfD.equals("packs")) {
                    totalFiles = sixPacks.length + "";
                    new DownloadFileAsync(sixPacks).execute(sixPacks);
                } else if (typeOfD.equals("epack")) {
                    totalFiles = eightPacks.length + "";
                    new DownloadFileAsync(eightPacks).execute(eightPacks);
                } else if (typeOfD.equals("chest")) {
                    totalFiles = chest.length + "";
                    new DownloadFileAsync(chest).execute(chest);
                } else if (typeOfD.equals("tattoo")) {
                    totalFiles = tattoos.length + "";
                    new DownloadFileAsync(tattoos).execute(tattoos);
                }else if (typeOfD.equals("lovekers")) {
                    totalFiles = lovestickers.length + "";
                    new DownloadFileAsync(lovestickers).execute(lovestickers);
                }else if (typeOfD.equals("wishkers")) {
                    totalFiles = wishstickers.length + "";
                    new DownloadFileAsync(wishstickers).execute(wishstickers);
                }else if (typeOfD.equals("greetkers")) {
                    totalFiles = greetstickers.length + "";
                    new DownloadFileAsync(greetstickers).execute(greetstickers);
                }else if (typeOfD.equals("expkers")) {
                    totalFiles = expstickers.length + "";
                    new DownloadFileAsync(expstickers).execute(expstickers);
                }
                dialog.dismiss();
            }
        });
        TextView dialogCancel = dialog.findViewById(R.id.no);
        dialogCancel.setVisibility(View.VISIBLE);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showDownloadProgressDialog() {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sticker_download_progress);
        cFiles = dialog.findViewById(R.id.s_file_count);
        dialog.show();
        /*if (CommonMethods.getInstance().getSku().equals("") && CommonMethods.getInstance().getPurchaseState() == -1 || CommonMethods.getInstance().getSku().equals("removeads") && CommonMethods.getInstance().getPurchaseState() != 1) {
            if (RemoteConfigValues.getOurRemote().getShowNativeAd() != null) {
                if (RemoteConfigValues.getOurRemote().getShowNativeAd().equals("true")) {
                    if (RemoteConfigValues.getOurRemote().getShowNativeAdOnDialog() != null
                            && RemoteConfigValues.getOurRemote().getShowNativeAdOnDialog().equals("true")) {
                        admobAd();
                    }
                }
            }
        }*/
    }

    public String getFileName(String wholePath) {
        String name;
        int start, end;
        start = wholePath.lastIndexOf('/');
        end = wholePath.length();
        name = wholePath.substring((start + 1), end);
        System.out.println("Start:" + start + "\t\tEnd:" + end + "\t\tName:" + name);
        return name;
    }

    /*private void admobAd() {
        AdLoader.Builder builder = new AdLoader.Builder(mContext, mContext.getString(R.string.admob_native_id));
        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        boolean isDestroyed = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            isDestroyed = ((Activity)mContext).isDestroyed();
                        }
                        if (((Activity)mContext).isDestroyed() || ((Activity)mContext).isFinishing() || ((Activity)mContext).isChangingConfigurations()) {
                            nativeAd.destroy();
                            return;
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (nativeAdGlobal != null) {
                            nativeAdGlobal.destroy();
                        }
                        nativeAdGlobal = nativeAd;
                        FrameLayout frameLayout = dialog.findViewById(R.id.sticker_native_ad_download_dialog);
                        NativeAdView adView =
                                (NativeAdView) LayoutInflater.from(mContext).inflate(R.layout.ad_unified, null);
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


}
