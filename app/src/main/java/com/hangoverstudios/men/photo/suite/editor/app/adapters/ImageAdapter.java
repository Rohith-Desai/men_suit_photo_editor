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

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.activities.CategoriesActivity;
import com.hangoverstudios.men.photo.suite.editor.app.activities.SlideShowActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.GalleryActivity.galleryActivity;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    public ArrayList<String> mData;
    private LayoutInflater mInflater;
    public Context CONTEXT;
    public boolean isLongSelection = false, selectAll, isPrevSavedImage;
    public ArrayList<Integer> selectedItems = new ArrayList<>();

    public ImageAdapter(Context context, ArrayList<String> data, boolean selectAll, boolean isPrevSavedImage) {
        CONTEXT = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.selectAll = selectAll;
        this.isPrevSavedImage = isPrevSavedImage;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.image_item, parent, false);
        return new ImageAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ImageAdapter.ViewHolder holder, final int position) {
        Glide.with(CONTEXT).load(mData.get(position)).into(holder.image);
        Log.e("INDEX", "select all : " + selectAll);
        if (selectAll) {
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
                if(!isPrevSavedImage) {
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
                else {
                    Intent intent = new Intent(CONTEXT, CategoriesActivity.class);
                    intent.putExtra("prevImagePath", mData.get(position));
                    CONTEXT.startActivity(intent);
                }
            }
        });
        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onLongClick(View v) {
                if(!isPrevSavedImage)
                {
                    isLongSelection = true;
                    galleryActivity.selectCount.setText("selected : 1");
                    galleryActivity.selectionOptions.setVisibility(View.VISIBLE);
                    galleryActivity.toolOptions.setVisibility(View.GONE);
                    selectedItems.add(position);
                    setImageFilter(holder);
                }
                return true;
            }
        });
    }

    private void setImageFilter(ViewHolder holder) {
        holder.image.setColorFilter(CONTEXT.getResources().getColor(R.color.transparent_blue));
    }

    private void removeImageFilter(ViewHolder holder) {
        holder.image.setColorFilter(CONTEXT.getResources().getColor(R.color.transparent));
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
            image = itemView.findViewById(R.id.img);
        }
    }
}



