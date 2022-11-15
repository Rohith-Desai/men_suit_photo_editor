package com.hangoverstudios.men.photo.suite.editor.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.activities.CategoriesActivity;
import com.hangoverstudios.men.photo.suite.editor.app.interfaces.ChangeBackgroundSaveImage;
import com.hangoverstudios.men.photo.suite.editor.app.model.ChangeBackgroundData;

import java.util.ArrayList;
import java.util.List;
//import static com.hangoverstudios.men.photo.suite.editor.app.activities.CategoriesActivity.categoriesActivity;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.ChangeBackground.COLOR_BACKGROUND;
import static com.hangoverstudios.men.photo.suite.editor.app.activities.ChangeBackground.IMAGE_BACKGROUND;
//import static com.hangoverstudios.men.photo.suite.editor.app.activities.ChangeBackground.backgroundImageView;

public class ChangeBackgroundAdapter extends RecyclerView.Adapter<ChangeBackgroundAdapter.ViewHolder> {

    public ArrayList<Object> mData;
    private LayoutInflater mInflater;
    private ChangeBackgroundSaveImage listener;
    public Context CONTEXT;
    private int TYPE;
    private CategoriesActivity categoriesActivity;
    private View backgroundView;
    private View sfBackgroundView;
    private View sfDisplayingBackground;
    private List<String> colorList;
    private int selectedItem;
    String colors[] = new String[]{"#FFFFFF", "#EFDECD", "#CD4A4A", "#CC6666", "#BC5D58", "#FF5349", "#FD5E53", "#FD7C6E", "#FDBCB4", "#FF6E4A", "#FFA089", "#EA7E5D", "#B4674D", "#A5694F", "#FF7538", "#FF7F49", "#DD9475", "#FF8243", "#FFA474", "#9F8170", "#CD9575", "#EFCDB8", "#D68A59", "#DEAA88", "#FAA76C", "#FFCFAB", "#FFBD88", "#FDD9B5", "#FFA343", "#EFDBC5", "#FFB653", "#E7C697", "#8A795D", "#FAE7B5", "#FFCF48", "#FCD975", "#FDDB6D", "#FCE883", "#F0E891", "#ECEABE", "#BAB86C", "#FDFC74", "#FDFC74", "#FFFF99", "#C5E384", "#B2EC5D", "#87A96B", "#A8E4A0", "#1DF914", "#76FF7A", "#71BC78", "#6DAE81", "#9FE2BF", "#1CAC78", "#30BA8F", "#45CEA2", "#3BB08F", "#1CD3A2", "#17806D", "#158078", "#1FCECB", "#78DBE2", "#77DDE7", "#80DAEB", "#414A4C", "#199EBD", "#1CA9C9", "#1DACD6", "#9ACEEB", "#1A4876", "#1974D2", "#2B6CC4", "#1F75FE", "#C5D0E6", "#B0B7C6", "#5D76CB", "#A2ADD0", "#979AAA", "#ADADD6", "#7366BD", "#7442C8", "#7851A9", "#9D81BA", "#926EAE", "#CDA4DE", "#8F509D", "#C364C5", "#FB7EFD", "#FC74FD", "#8E4585", "#FF1DCE", "#FF1DCE", "#FF48D0", "#E6A8D7", "#C0448F", "#6E5160", "#DD4492", "#FF43A4", "#F664AF", "#FCB4D5", "#FFBCD9", "#F75394", "#FFAACC", "#E3256B", "#FDD7E4", "#CA3767", "#DE5D83", "#FC89AC", "#F780A1", "#C8385A", "#EE204D", "#FF496C", "#EF98AA", "#FC6C85", "#FC2847", "#FF9BAA", "#CB4154", "#EDEDED", "#DBD7D2", "#CDC5C2", "#95918C", "#232323"};

    public ChangeBackgroundAdapter(Context context, ArrayList<Object> data, int type) {

        CONTEXT = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.TYPE = type;
        this.listener = (ChangeBackgroundSaveImage) context;
        this.categoriesActivity = ChangeBackgroundData.getChangeBackgroundData().getCategoriesActivity();
        this.backgroundView = ChangeBackgroundData.getChangeBackgroundData().getBackgroundImageView();
        this.sfBackgroundView = ChangeBackgroundData.getChangeBackgroundData().getSfBackgroundImageView();
        this.sfDisplayingBackground = ChangeBackgroundData.getChangeBackgroundData().getDisplayingBackground();
        this.selectedItem = 0;
        /*colorList = new ArrayList<>(mData.size());
        for (Object object : mData) {
            colorList.add(Objects.toString(object, null));
        }*/

    }

    @Override
    public ChangeBackgroundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.change_background_item, parent, false);
        return new ChangeBackgroundAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ChangeBackgroundAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Glide.with(CONTEXT).load(mData.get(position)).into(holder.image);

        if (TYPE == IMAGE_BACKGROUND) {
            Drawable drawable = ContextCompat.getDrawable(CONTEXT, (Integer) mData.get(position));
            holder.image.getLayoutParams().height = dpToPx(60, CONTEXT);
            holder.image.getLayoutParams().width = dpToPx(40, CONTEXT);
            holder.image.setBackground(drawable);
        } else if (TYPE == COLOR_BACKGROUND) {
            holder.foreImage.setVisibility(View.GONE);
            Log.v("tag", "color : " + colors[position]);
            holder.image.getLayoutParams().height = dpToPx(60, CONTEXT);
            holder.image.getLayoutParams().width = dpToPx(40, CONTEXT);
            holder.image.setBackgroundColor(Color.parseColor(colors[position]));
        } else if (TYPE == 3) {
//            holder.foreImage.setVisibility(View.VISIBLE);
//            if (categoriesActivity != null)
//                holder.foreImage.setImageBitmap(categoriesActivity.getSavedBitmap());
            Drawable drawable = ContextCompat.getDrawable(CONTEXT, (Integer) mData.get(position));
//            holder.image.getLayoutParams().height = dpToPx(350, CONTEXT);
            holder.image.setBackground(drawable);

        } else if (TYPE == 4) {
            holder.foreImage.setVisibility(View.VISIBLE);
            if (categoriesActivity != null) {
                // holder.foreImage.setImageBitmap(categoriesActivity.getSavedBitmap());
                holder.foreImage.setImageBitmap(ChangeBackgroundData.getChangeBackgroundData().getSaveFinishedBitmap());
            }
            Drawable drawable = ContextCompat.getDrawable(CONTEXT, (Integer) mData.get(position));
            holder.image.getLayoutParams().height = dpToPx(100, CONTEXT);
            holder.image.getLayoutParams().width = dpToPx(75, CONTEXT);
            holder.image.setBackground(drawable);

        }
        holder.cardView.setCardBackgroundColor(CONTEXT.getResources().getColor(R.color.transparent));

        if (selectedItem == position) {
            holder.cardView.setCardBackgroundColor(CONTEXT.getResources().getColor(R.color.colorPrimaryDark));
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int previousItem = selectedItem;
                selectedItem = position;

                notifyItemChanged(previousItem);
                notifyItemChanged(position);
                if (TYPE == IMAGE_BACKGROUND) {
                    Drawable drawable = ContextCompat.getDrawable(CONTEXT, (Integer) mData.get(position));
                    backgroundView.setBackground(drawable);
                    sfBackgroundView.setBackground(drawable);
                } else if (TYPE == COLOR_BACKGROUND) {
                    backgroundView.setBackgroundColor(Color.parseColor(colors[position]));
                    ChangeBackgroundData.getChangeBackgroundData().getImageRecyclerView().setBackgroundColor(Color.parseColor(colors[position]));
                } else if (TYPE == 3) {
                    Drawable drawable = ContextCompat.getDrawable(CONTEXT, (Integer) mData.get(position));
                    backgroundView.setBackground(drawable);
//                    sfBackgroundView.setBackground(drawable);
//                    listener.saveImage();
                } else if (TYPE == 4) {
                    Drawable drawable = ContextCompat.getDrawable(CONTEXT, (Integer) mData.get(position));
                    sfBackgroundView.setBackground(drawable);
                   // sfDisplayingBackground.setBackground(drawable);
                    listener.saveImage();
                }
            }
        });
        holder.foreImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int previousItem = selectedItem;
                selectedItem = position;

                notifyItemChanged(previousItem);
                notifyItemChanged(position);
                if (TYPE == IMAGE_BACKGROUND) {
                    Drawable drawable = ContextCompat.getDrawable(CONTEXT, (Integer) mData.get(position));
                    backgroundView.setBackground(drawable);
                } else if (TYPE == COLOR_BACKGROUND) {
                    backgroundView.setBackgroundColor(Color.parseColor(colors[position]));
                } else if (TYPE == 3) {
                    Drawable drawable = ContextCompat.getDrawable(CONTEXT, (Integer) mData.get(position));
                    backgroundView.setBackground(drawable);
                    listener.saveImage();
                }else if (TYPE == 4) {
                    Drawable drawable = ContextCompat.getDrawable(CONTEXT, (Integer) mData.get(position));
                    sfBackgroundView.setBackground(drawable);
                    //sfDisplayingBackground.setBackground(drawable);
                    listener.saveImage();
                }
            }
        });
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    /*private void setImageFilter(ViewHolder holder) {
        holder.image.setColorFilter(CONTEXT.getResources().getColor(R.color.transparent_blue));
    }

    private void removeImageFilter(ViewHolder holder) {
        holder.image.setColorFilter(CONTEXT.getResources().getColor(R.color.transparent));
    }*/

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

        LinearLayout image;
        ImageView foreImage;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.change_back_img);
            foreImage = itemView.findViewById(R.id.fore_img);
            cardView = itemView.findViewById(R.id.card_bg);
        }
    }
}