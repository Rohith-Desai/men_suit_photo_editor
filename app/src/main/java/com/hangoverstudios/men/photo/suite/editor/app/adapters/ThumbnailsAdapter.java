package com.hangoverstudios.men.photo.suite.editor.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.EffectsActivity.effectsActivityBitmap;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.EffectsActivity.filteredImageView;

public class ThumbnailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "THUMBNAILS_ADAPTER";
    private static int lastPosition = -1;
    //private ThumbnailCallback thumbnailCallback;
    private List<ThumbnailItem> dataSet;
    Context context;
    String[] filternames;
    boolean isPortrate;
    public static Bitmap selectedFilteredBitmapNew;

    public ThumbnailsAdapter(Context context, List<ThumbnailItem> dataSet, String[]filternames) {
        Log.v(TAG, "Thumbnails Adapter has " + dataSet.size() + " items");
        this.dataSet = dataSet;
        //this.thumbnailCallback = thumbnailCallback;
        this.context=context;
        this.filternames=filternames;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.v(TAG, "On Create View Holder Called");
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_thumbnail_item, viewGroup, false);
        return new ThumbnailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {
        final ThumbnailItem thumbnailItem = dataSet.get(i);
        Log.v(TAG, "On Bind View Called");
        ThumbnailsViewHolder thumbnailsViewHolder = (ThumbnailsViewHolder) holder;
        thumbnailsViewHolder.thumbnail.setImageBitmap(thumbnailItem.image);
        thumbnailsViewHolder.fnames.setText(filternames[i]);
        thumbnailsViewHolder.thumbnail.setScaleType(ImageView.ScaleType.FIT_START);
        //setAnimation(thumbnailsViewHolder.thumbnail, i);
        thumbnailsViewHolder.linearfiltes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastPosition != i) {
                    int height=effectsActivityBitmap.getHeight();
                    int weidth=effectsActivityBitmap.getWidth();
                    try{

                        filteredImageView.setImageBitmap(thumbnailItem.filter.processFilter( Bitmap.createScaledBitmap(effectsActivityBitmap, weidth, height, false)));
                        selectedFilteredBitmapNew=thumbnailItem.filter.processFilter( Bitmap.createScaledBitmap(effectsActivityBitmap, weidth, height, false));
                        //setBitmap(thumbnailItem.filter.processFilter( Bitmap.createScaledBitmap(mPortraitEditActivity.filterBitMap, weidth, height, false)));

                    }
                    catch(Exception e){

                    }
                    lastPosition = i;
                }
            }

        });
    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ThumbnailsViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView fnames;
        public LinearLayout linearfiltes;

        public ThumbnailsViewHolder(View v) {
            super(v);
            this.thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            this.fnames=(TextView)v.findViewById(R.id.fnames);
            this.linearfiltes=(LinearLayout)v.findViewById(R.id.linearfiltes);
        }
    }
}

