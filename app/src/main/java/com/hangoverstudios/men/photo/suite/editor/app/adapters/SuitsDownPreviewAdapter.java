package com.hangoverstudios.men.photo.suite.editor.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import java.util.ArrayList;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.SetSuitActivity.localEventQueue;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.SetSuitActivity.setSuitActivity;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.StickerEditActivity.stickerEditActivity;

public class SuitsDownPreviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int STRING = 3;
    private final Context mContext;
    ArrayList<String> mRecyclerViewItems;
    String typeOfD = "";
    String mainDirectory = "";

    public SuitsDownPreviewAdapter(Context mContext, ArrayList<String> mResources, String subCatType, String mainDirectory) {
        typeOfD = subCatType;
        this.mainDirectory = mainDirectory;
        mRecyclerViewItems = new ArrayList<>();
        this.mContext = mContext;
        mRecyclerViewItems = mResources;
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
        Bitmap myBitmap = BitmapFactory.decodeFile(mRecyclerViewItems.get(position));
        viewHolder.suitsPreviewItem.setImageBitmap(myBitmap);
        viewHolder.suitsPreviewItem.startAnimation(a);
        viewHolder.suitsPreviewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("bitmapEventList", "res : " + mRecyclerViewItems.get(position));
                localEventQueue.add(mRecyclerViewItems.get(position));
                if (mainDirectory.equals("dresses") || mainDirectory.equals("frames") || mainDirectory.equals("uniforms")) {
                    setSuitActivity.startPosition = position;
                    setSuitActivity.updatePrevNext(position);
                } else {
                    stickerEditActivity.addStickerView(mRecyclerViewItems.get(position));
                    stickerEditActivity.RESOURCE_ID_TO_COPY = mRecyclerViewItems.get(position);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView suitsPreviewItem;

        ViewHolder(View view) {
            super(view);
            suitsPreviewItem = view.findViewById(R.id.suits_preview_single_item);
        }
    }
}



