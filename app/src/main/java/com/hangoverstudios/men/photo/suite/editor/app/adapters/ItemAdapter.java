package com.hangoverstudios.men.photo.suite.editor.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.bumptech.glide.Glide;
import com.hangoverstudios.men.photo.suite.editor.app.model.PhotoFilter;

import java.util.List;

import static com.hangoverstudios.men.photo.suite.editor.app.activities.EffectsActivity.effectsActivityBitmap;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.EffectsActivity.filteredImageView;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int STRING = 3;
    private final Context mContext;
    private final List<Object> mRecyclerViewItems;
    public static Bitmap selectedFilteredBitmap;

    public ItemAdapter(Context mContext, List<Object> mRecyclerViewItems) {
        this.mContext = mContext;
        this.mRecyclerViewItems = mRecyclerViewItems;
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = mRecyclerViewItems.get(position);
        if (recyclerViewItem instanceof String) {
            return STRING;
        }

        return STRING;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View menuItemLayoutView2 = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.single_filter_item, parent, false);
        return new StringViewHolder(menuItemLayoutView2);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final StringViewHolder stringViewHolder = (StringViewHolder) holder;
        final PhotoFilter photoFilter = new PhotoFilter();
        Animation a = AnimationUtils.loadAnimation(mContext, R.anim.scale_up);
        Glide.with(mContext)
                .load(getImage(mRecyclerViewItems.get(position).toString()))
                .into(stringViewHolder.img);
        stringViewHolder.img.startAnimation(a);
        stringViewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 1:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter1(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 2:
                        if(effectsActivityBitmap!=null){
                            selectedFilteredBitmap = photoFilter.filter2(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 3:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter3(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 4:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter4(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 5:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter5(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 6:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter6(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 7:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter7(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 8:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter8(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 9:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter9(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 10:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter10(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 11:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter11(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 12:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter12(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 13:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter13(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 14:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter14(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 15:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter15(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    case 16:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = photoFilter.filter16(mContext, effectsActivityBitmap);
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                    default:
                        if(effectsActivityBitmap!=null) {
                            selectedFilteredBitmap = effectsActivityBitmap;
                            filteredImageView.setImageBitmap(selectedFilteredBitmap);
                        }
                        break;

                }
            }
        });
    }

    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());
        return drawableResourceId;
    }

    public class StringViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        StringViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.frame_img);
        }
    }


}


