package com.hangoverstudios.men.photo.suite.editor.app.mask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hangoverstudios.men.photo.suite.editor.app.R;

import java.util.ArrayList;

public class masksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<Object> maskLIst;
    Maskselection maskselection;



public masksAdapter(Context mContext, ArrayList<Object> maskLIst, Maskselection maskselection){
    this.mContext=mContext;
    this.maskLIst=maskLIst;
    this.maskselection=maskselection;

}
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.mask_layout, parent, false);
        MyViewHolder viewHolder=new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int  position) {
    masksAdapter.MyViewHolder myViewHolder=( masksAdapter.MyViewHolder)holder;

    myViewHolder.img.setImageResource( getImage(maskLIst.get(position).toString()));
    myViewHolder.img.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            maskselection.maskimageSelect(getImage(maskLIst.get(position).toString()));

        }
    });

    }



    @Override
    public int getItemCount() {
        return maskLIst.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        MyViewHolder(View view) {
            super(view);
//            myString = view.findViewById(R.id.string_text);
            img = view.findViewById(R.id.recMask);

        }
    }
    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

        return drawableResourceId;
    }
}
