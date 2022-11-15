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

import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
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
import com.hangoverstudios.men.photo.suite.editor.app.firebase.RemoteConfigValues;
import com.hangoverstudios.men.photo.suite.editor.app.activities.SetSuitActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

//import static com.applovin.sdk.AppLovinSdkUtils.runOnUiThread;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.SetSuitActivity.setSuitActivity;

public class SuitsPreviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int STRING = 3;
    private final Context mContext;
    private ArrayList lResources;
    private NativeAd nativeAdGlobal;
    ArrayList<Integer> mRecyclerViewItems;
    String mainDirectory = "";
    String typeOfD = "";
    String blazer[] = {"blazer_eight.png", "blazer_five.png", "blazer_four.png", "blazer_nine.png", "blazer_one.png", "blazer_seven.png",
            "blazer_six.png", "blazer_ten.png", "blazer_three.png", "blazer_two.png"};

    String suit[] = {"suit_eigheen.png", "suit_eight.png", "suit_fifteen.png", "suit_four.png", "suit_fourteen.png",
            "suit_nighnteen.png", "suit_one.png", "suit_seven.png", "suit_seventeen.png", "suit_six.png", "suit_sixteen.png",
            "suit_thirteen.png", "suit_three.png", "suit_twenty.png", "suit_twentyfour.png", "suit_twentyone.png",
            "suit_twentythree.png", "suit_twentytwo.png", "suit_two.png"
    };

    String tradition[] = {"trad_eleven.png", "trad_five.png", "trad_four.png", "trad_fourteen.png", "trad_nine.png",
            "trad_one.png", "trad_seven.png", "trad_six.png", "trad_ten.png", "trad_thirteen.png", "trad_three.png",
            "trad_twelve.png", "trad_two.png"};

    String formal[] = {"formal_eight.png", "formal_five.png", "formal_four.png", "formal_nine.png", "formal_one.png",
            "formal_seven.png", "formal_six.png", "formal_three.png", "formal_two.png"};

    String jacket[] = {"jacket_five.png", "jacket_four.png", "jacket_one.png", "jacket_seven.png", "jacket_six.png",
            "jacket_three.png", "jacket_two.png"
    };
    String[] attitude = {"attitude_eight.png", "attitude_five.png", "attitude_four.png", "attitude_nine.png", "attitude_one.png",
            "attitude_seven.png", "attitude_six.png", "attitude_ten.png", "attitude_three.png", "attitude_two.png"
    };

    String[] border = {"border_eight.png", "border_eighteen.png", "border_eleven.png", "border_fifteen.png", "border_five.png",
            "border_four.png", "border_fourteen.png", "border_nine.png", "border_nineteen.png", "border_one.png", "border_seven.png",
            "border_seventeen.png", "border_six.png", "border_sixteen.png", "border_ten.png", "border_thirteen.png",
            "border_three.png", "border_twelve.png", "border_twety.png", "border_two.png"
    };

    String[] fashion = {"fashion_eight.png", "fashion_five.png", "fashion_four.png", "fashion_nine.png", "fashion_one.png",
            "fashion_seven.png", "fashion_six.png", "fashion_ten.png", "fashion_three.png", "fashion_two.png"
    };

    String[] love = {"love_eight.png", "love_five.png", "love_four.png", "love_nine.png", "love_one.png", "love_seven.png",
            "love_six.png", "love_ten.png", "love_three.png", "love_two.png"
    };

    String[] macho = {"macho_eight.png", "macho_five.png", "macho_four.png", "macho_nine.png", "macho_one.png", "macho_seven.png",
            "macho_six.png", "macho_ten.png", "macho_three.png", "macho_two.png"
    };

    String[] police = {"police_eight.png", "police_eleven.png", "police_five.png", "police_four.png",
            "police_nine.png", "police_seven.png", "police_six.png", "police_ten.png", "police_thirteen.png",
            "police_twelve.png"};

    String[] military = {"military_eight.png", "military_eleven.png", "military_five.png", "military_four.png",
            "military_nine.png", "military_seven.png", "military_six.png", "military_ten.png", "military_thirteen.png",
            "military_twelve.png"
    };

    String[] doctor = {"doctor_eight.png", "doctor_eleven.png", "doctor_five.png", "doctor_four.png", "doctor_nine.png",
            "doctor_seven.png", "doctor_six.png", "doctor_ten.png"};

    String[] mixed = {"mixed_eight.png", "mixed_eleven.png", "mixed_five.png", "mixed_four.png", "mixed_nine.png",
            "mixed_seven.png", "mixed_six.png", "mixed_ten.png"};

    String[] body = {"body_eight.png", "body_five.png", "body_four.png", "body_seven.png", "body_six.png"
    };
    String totalFiles;
    int cdFile = 0;
    TextView cFiles;
    Dialog dialog;

    public SuitsPreviewAdapter(Context mContext, int[] mResources, String type, String mainDirectory) {
        typeOfD = type;
        mRecyclerViewItems = new ArrayList<>();
        this.mContext = mContext;
        this.mainDirectory = mainDirectory;
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
                R.layout.suits_preview_item, parent, false);
        return new ViewHolder(menuItemLayoutView2);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        Animation a = AnimationUtils.loadAnimation(mContext, R.anim.scale_up);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mRecyclerViewItems.get(position), options);
        viewHolder.suitsPreviewItem.setImageBitmap(bitmap);
        viewHolder.suitsPreviewItem.startAnimation(a);
        viewHolder.suitsPreviewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == mRecyclerViewItems.size() - 1) {
                    showDownloadDialog();
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
                    setSuitActivity.startPosition = position;
                    setSuitActivity.updatePrevNext(position);
                }
            }
        });
    }

    public void showDownloadDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.download_pop);
        TextView dialogOk = dialog.findViewById(R.id.yes);
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeOfD.equals("blazer")) {
                    totalFiles = blazer.length + "";
                    new DownloadFileAsync(blazer).execute(blazer);
                } else if (typeOfD.equals("suit")) {
                    totalFiles = suit.length + "";
                    new DownloadFileAsync(suit).execute(suit);
                } else if (typeOfD.equals("tradition")) {
                    totalFiles = tradition.length + "";
                    new DownloadFileAsync(tradition).execute(tradition);
                } else if (typeOfD.equals("formal")) {
                    totalFiles = formal.length + "";
                    new DownloadFileAsync(formal).execute(formal);
                } else if (typeOfD.equals("jacket")) {
                    totalFiles = jacket.length + "";
                    new DownloadFileAsync(jacket).execute(jacket);
                } else if (typeOfD.equals("attitude")) {
                    totalFiles = attitude.length + "";
                    new DownloadFileAsync(attitude).execute(attitude);
                } else if (typeOfD.equals("border")) {
                    totalFiles = border.length + "";
                    new DownloadFileAsync(border).execute(border);
                } else if (typeOfD.equals("fashion")) {
                    totalFiles = fashion.length + "";
                    new DownloadFileAsync(fashion).execute(fashion);
                } else if (typeOfD.equals("love")) {
                    totalFiles = love.length + "";
                    new DownloadFileAsync(love).execute(love);
                } else if (typeOfD.equals("macho")) {
                    totalFiles = macho.length + "";
                    new DownloadFileAsync(macho).execute(macho);
                } else if (typeOfD.equals("police")) {
                    totalFiles = police.length + "";
                    new DownloadFileAsync(police).execute(police);
                } else if (typeOfD.equals("military")) {
                    totalFiles = military.length + "";
                    new DownloadFileAsync(military).execute(military);
                } else if (typeOfD.equals("doctor")) {
                    totalFiles = doctor.length + "";
                    new DownloadFileAsync(doctor).execute(doctor);
                } else if (typeOfD.equals("mixed")) {
                    totalFiles = mixed.length + "";
                    new DownloadFileAsync(mixed).execute(mixed);
                } else if (typeOfD.equals("body")) {
                    totalFiles = body.length + "";
                    new DownloadFileAsync(body).execute(body);
                }

                dialog.dismiss();
                //finish();
            }
        });
        TextView dialogCancel = dialog.findViewById(R.id.no);
        dialogCancel.setVisibility(View.VISIBLE);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //finish();
            }
        });

        dialog.show();

   /*     Intent intent = new Intent(GalleryActivity.this, CustomDialog.class);
        intent.putExtra("msg","Conform Delete");
        intent.putExtra("msg_sub","Are you sure want to delete selected items permanently.");
        startActivity(intent);*/
    }

    public void showDownloadProgressDialog() {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.download_progress);
        cFiles = dialog.findViewById(R.id.sd_file_count);
        /*if (RemoteConfigValues.getOurRemote().getShowNativeAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowNativeAd().equals("true")) {
                if (RemoteConfigValues.getOurRemote().getShowNativeAdOnDialog() != null
                        && RemoteConfigValues.getOurRemote().getShowNativeAdOnDialog().equals("true")) {
                    admobAd();
                }
            }
        }*/
        dialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView suitsPreviewItem;

        ViewHolder(View view) {
            super(view);
            suitsPreviewItem = view.findViewById(R.id.suits_preview_single_item);
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
            lResources = new ArrayList();
            System.out.println("unused: " + unused);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            String directory = (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q)?
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+ "/" + mContext.getResources().getString(R.string.app_name) + "/" + typeOfD + "/"
                    :Environment.getExternalStorageDirectory().toString()+ "/" +mContext.getResources().getString(R.string.app_name) + "/" + typeOfD + "/";
          //  String directory = Environment.getExternalStorageDirectory() + "/" + mContext.getResources().getString(R.string.app_name) + "/" + typeOfD + "/";
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
            if (mainDirectory.equals("dresses")) {
                ((SetSuitActivity) mContext).updateSuitAdapter("blazer", lResources);
            } else if (mainDirectory.equals("frames")) {
                ((SetSuitActivity) mContext).updateSuitAdapter("attitude", lResources);
            } else {
                ((SetSuitActivity) mContext).updateSuitAdapter("police", lResources);
            }

        }
    }

    public String getFileName(String wholePath) {
        String name = null;
        int start, end;
        start = wholePath.lastIndexOf('/');
        end = wholePath.length();
        name = wholePath.substring((start + 1), end);
        name = name;
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
                        FrameLayout frameLayout = dialog.findViewById(R.id.native_ad_download_dialog);
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



