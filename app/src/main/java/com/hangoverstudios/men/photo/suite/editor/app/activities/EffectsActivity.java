package com.hangoverstudios.men.photo.suite.editor.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hangoverstudios.men.photo.suite.editor.app.ads.MyInterstitialAdView;
import com.hangoverstudios.men.photo.suite.editor.app.utils.CommonMethods;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hangoverstudios.men.photo.suite.editor.app.adapters.ThumbnailsAdapter;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.SplashScreen.mFirebaseAnalytics;
import static com.hangoverstudios.men.photo.suite.editor.app.adapters.ThumbnailsAdapter.selectedFilteredBitmapNew;

public class EffectsActivity extends AppCompatActivity {
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    static ArrayList<Object> filtersShortList = new ArrayList<>();
    RecyclerView recyclerView;
    public static EffectsActivity effectsActivity;
    public static Bitmap effectsActivityBitmap;
    public static ImageView filteredImageView;
    private LinearLayout backToCategories, saveEffectedBitmap;
    private AdView adViewBanner7;
    private FrameLayout adViewContainer;
    private CategoriesActivity categoriesActivity;
    String [] filternames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);
        filternames= new String[]{getResources().getString(R.string.none), getResources().getString(R.string.Struck), getResources().getString(R.string.Clarendon), getResources().getString(R.string.OldMan), getResources().getString(R.string.Mars), getResources().getString(R.string.Rise), getResources().getString(R.string.April), getResources().getString(R.string.Amazon), getResources().getString(R.string.Starlit), getResources().getString(R.string.Whisper), getResources().getString(R.string.Lime), getResources().getString(R.string.Haan), getResources().getString(R.string.BlueMess), getResources().getString(R.string.Adele), getResources().getString(R.string.Cruz), getResources().getString(R.string.Metropolis), getResources().getString(R.string.Audrey)};
        categoriesActivity = ChangeBackgroundData.getChangeBackgroundData().getCategoriesActivity();
        effectsActivity = this;
        if(categoriesActivity!=null){
            effectsActivityBitmap = categoriesActivity.getSavedBitmap();
            filteredImageView = findViewById(R.id.filteredImageView);
            filteredImageView.setImageBitmap(effectsActivityBitmap);
        }
        recyclerView = findViewById(R.id.filters_row);

        /*adViewContainer = findViewById(R.id.effects_banner_ad);
        adViewContainer.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adViewBanner7, adViewContainer, EffectsActivity.this);
            }
        });*/


        backToCategories = findViewById(R.id.linear_back_to_categories);
        saveEffectedBitmap = findViewById(R.id.linear_effect_save);

        //prepareData();
        //prepareList(filtersShortList);

        if (effectsActivityBitmap!=null){
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(EffectsActivity.this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            layoutManager.scrollToPosition(0);
            recyclerView.setLayoutManager(layoutManager);
//                    edit_recycle.setHasFixedSize(true);


            openFilters();

        }
        else {
            Toast.makeText(EffectsActivity.this,"Please select image", LENGTH_SHORT).show();
        }

        backToCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        saveEffectedBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEffectedBitmap.setClickable(false);
                if (selectedFilteredBitmapNew != null) {
                    MyInterstitialAdView.getInstance().activitiesAD(EffectsActivity.this);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(selectedFilteredBitmapNew, selectedFilteredBitmapNew.getWidth(), selectedFilteredBitmapNew.getHeight(), true);
                    categoriesActivity.updateSavedBitmap(resizedBitmap);
                } else {
                }
                onBackPressed();
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "effects");
        bundle.putString("Activity", "EffectsActivity");
        if (mFirebaseAnalytics != null)
            mFirebaseAnalytics.logEvent("effectsCategory", bundle);
    }

    private void prepareData() {
        filtersShortList.clear();
        filtersShortList.add("filter_rj_0");
        for (int j = 1; j <= 15; j++) {
            filtersShortList.add("filter_rj_" + j);
        }
    }

    private void prepareList(ArrayList list) {
      /*  recyclerView.setHasFixedSize(true);
        ItemAdapter framesAdapter = new ItemAdapter(EffectsActivity.this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(framesAdapter);
        recyclerView.setVisibility(View.VISIBLE);*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyInterstitialAdView.getInstance().activitiesAD(EffectsActivity.this);
    }
    private void openFilters(){

        final Context context = this.getApplication();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                Bitmap thumbImage =Bitmap.createScaledBitmap(effectsActivityBitmap, 640, 640, false) ;
                ThumbnailsManager.clearThumbs();
                List<Filter> filters = FilterPack.getFilterPack(getApplicationContext());
                Filter noFilter = new Filter(getString(R.string.none));
                ThumbnailItem thumbnailItems = new ThumbnailItem();
                thumbnailItems.image = thumbImage;
                thumbnailItems.filter = noFilter;
                ThumbnailsManager.addThumb(thumbnailItems);
                for (Filter filter : filters) {
                    ThumbnailItem thumbnailItem = new ThumbnailItem();
                    thumbnailItem.image = thumbImage;
                    thumbnailItem.filter = filter;
                    ThumbnailsManager.addThumb(thumbnailItem);
                }

                List<ThumbnailItem> thumbs = ThumbnailsManager.processThumbs(context);
                Log.e("THUMBSIZEEEE", String.valueOf(thumbs.size()));

                ThumbnailsAdapter adapter = new ThumbnailsAdapter(EffectsActivity.this,thumbs,filternames);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);

                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    @Override
    protected void onRestart() {
        saveEffectedBitmap.setClickable(true);
        super.onRestart();
    }
}