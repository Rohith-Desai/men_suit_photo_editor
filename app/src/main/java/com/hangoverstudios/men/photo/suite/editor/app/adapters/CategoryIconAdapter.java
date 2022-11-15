package com.hangoverstudios.men.photo.suite.editor.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.activities.CategoriesstatusActivity;

import java.util.ArrayList;

public class CategoryIconAdapter  extends  RecyclerView.Adapter<CategoryIconAdapter.ViewHolder> {
    public ArrayList<String> categoryIcons;
    public ArrayList<String> categoryNames;
    public Context CONTEXT;

    public CategoryIconAdapter(Context context, ArrayList<String> categoryIcons,ArrayList<String> categoryNames){
        this.categoryIcons=categoryIcons;
        this.categoryNames=categoryNames;
        this.CONTEXT=context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.category_icons_adapter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryImage.setImageResource(getImage(categoryIcons.get(position)));
        holder.categoryText.setText(categoryNames.get(position));
        holder.categoryLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CONTEXT, CategoriesstatusActivity.class);
                intent.putExtra("position",position);
                CONTEXT.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout categoryLinear;
        ImageView categoryImage;
        TextView categoryText;


        ViewHolder(View itemView) {
            super(itemView);
            categoryLinear=itemView.findViewById(R.id.category_linear);
            categoryImage=itemView.findViewById(R.id.category_icon);
            categoryText=itemView.findViewById(R.id.category_name);

        }
    }
    public int getImage(String imageName) {

        int drawableResourceId = CONTEXT.getResources().getIdentifier(imageName, "drawable", CONTEXT.getPackageName());

        return drawableResourceId;
    }
}
