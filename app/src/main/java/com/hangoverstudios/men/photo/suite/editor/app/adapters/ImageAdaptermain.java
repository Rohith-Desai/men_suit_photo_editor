package com.hangoverstudios.men.photo.suite.editor.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.activities.SlideShowActivity;
import com.hangoverstudios.men.photo.suite.editor.app.activities.SlideShowActivitymain;

import java.util.ArrayList;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.GalleryActivity.galleryActivity;

public class ImageAdaptermain extends RecyclerView.Adapter<ImageAdaptermain.ViewHolder> {
    public ArrayList<String> mData;
    private LayoutInflater mInflater;
    public Context CONTEXT;
    public boolean isLongSelection = false, selectAll;
    public ArrayList<Integer> selectedItems = new ArrayList<>();

    public ImageAdaptermain(Context context, ArrayList<String> data, boolean selectAll) {
        CONTEXT = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.selectAll = selectAll;
    }

    @Override
    public ImageAdaptermain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.image_itemdummy, parent, false);

        return new com.hangoverstudios.men.photo.suite.editor.app.adapters.ImageAdaptermain.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final com.hangoverstudios.men.photo.suite.editor.app.adapters.ImageAdaptermain.ViewHolder holder, final int position) {
        Glide.with(CONTEXT).load(mData.get(position)).into(holder.image);
        Log.e("INDEX", "select all : " + selectAll);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CONTEXT, SlideShowActivitymain.class);
                intent.putExtra("paths", mData);
                intent.putExtra("matchPath", mData.get(position));
                CONTEXT.startActivity(intent);
            }
        });
        /*if (selectAll) {
            selectedItems.clear();
            for (int i = 0; i < mData.size(); i++) {
                selectedItems.add(mData.indexOf(mData.get(i)));
                Log.e("INDEX", "selected items all : " + selectedItems.toString());
            }
            galleryActivity.selectCount.setText("selected : " + getItemCount());
            setImageFilter(holder);
        } else {
            if (selectedItems.size() == 0) {
                removeImageFilter(holder);
                galleryActivity.selectCount.setText("selected : ");
                galleryActivity.toolOptions.setVisibility(View.VISIBLE);
                galleryActivity.selectionOptions.setVisibility(View.GONE);
            } else {
                galleryActivity.toolOptions.setVisibility(View.GONE);
                galleryActivity.selectionOptions.setVisibility(View.VISIBLE);
            }
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (isLongSelection) {
                    if (selectedItems.contains(position)) {
                        selectedItems.remove(selectedItems.indexOf(position));
                        galleryActivity.selectCount.setText("selected : " + selectedItems.size());
                        removeImageFilter(holder);
                    } else {
                        selectedItems.add(position);
                        galleryActivity.selectCount.setText("selected : " + selectedItems.size());
                        setImageFilter(holder);
                    }
                } else {
                    Intent intent = new Intent(CONTEXT, SlideShowActivity.class);
                    intent.putExtra("paths", mData);
                    intent.putExtra("matchPath", mData.get(position));
                    CONTEXT.startActivity(intent);
                }
            }
        });
        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onLongClick(View v) {
                isLongSelection = true;
                galleryActivity.selectCount.setText("selected : 1");
                galleryActivity.selectionOptions.setVisibility(View.VISIBLE);
                galleryActivity.toolOptions.setVisibility(View.GONE);
                selectedItems.add(position);
                setImageFilter(holder);
                return true;
            }
        });
    }

    private void setImageFilter(ImageAdaptermain.ViewHolder holder) {
        holder.image.setColorFilter(CONTEXT.getResources().getColor(R.color.transparent_blue));
    }

    private void removeImageFilter(ImageAdaptermain.ViewHolder holder) {
        holder.image.setColorFilter(CONTEXT.getResources().getColor(R.color.transparent));
    }*/
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgs);
        }
    }
}
