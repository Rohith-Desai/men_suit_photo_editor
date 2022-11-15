package com.hangoverstudios.men.photo.suite.editor.app.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.activities.CategoriesActivity;
import com.hangoverstudios.men.photo.suite.editor.app.activities.ChangeBackground;
import com.hangoverstudios.men.photo.suite.editor.app.activities.EraserActivity;
import com.hangoverstudios.men.photo.suite.editor.app.activities.bg_eraser_save;

import java.time.chrono.Era;

public class ChangeBackgroundData {

    public static final ChangeBackgroundData  changeBackgroundData = new ChangeBackgroundData();
    private CategoriesActivity categoriesActivity;
    private EraserActivity eraserActivity;
    private ChangeBackground changeBackground;
    private bg_eraser_save bgEraserSave;
    private View backgroundImageView;
    private View sfBackgroundImageView;
    private View displayingBackground;
    private TextView doneTxt;
    private ImageView doneImg;
    private TextView freeTxt;
    private ImageView freeImg;
    private RecyclerView imageRecyclerView;
    private Bitmap saveFinishedBitmap;
    private boolean isBackgroundApplied = false;

    public boolean isBackgroundApplied() {
        return isBackgroundApplied;
    }

    public void setBackgroundApplied(boolean backgroundApplied) {
        isBackgroundApplied = backgroundApplied;
    }

    public Bitmap getSaveFinishedBitmap() {
        return saveFinishedBitmap;
    }

    public void setSaveFinishedBitmap(Bitmap saveFinishedBitmap) {
        this.saveFinishedBitmap = saveFinishedBitmap;
    }

    public static ChangeBackgroundData getChangeBackgroundData() {
        return changeBackgroundData;
    }

    public CategoriesActivity getCategoriesActivity() {
        return categoriesActivity;
    }

//    public void setEraserActivity(EraserActivity eraserActivity){
//        this.eraserActivity = eraserActivity;
//    }
//
    public EraserActivity getEraserActivity(){
        return eraserActivity;
    }

    public void setCategoriesActivity(CategoriesActivity categoriesActivity) {
        this.categoriesActivity = categoriesActivity;
    }

    public void setbg_eraser_save(bg_eraser_save bgEraserSave) {
        this.bgEraserSave = bgEraserSave;
    }

    public void setChangeBackground(ChangeBackground changeBackground){
        this.changeBackground = changeBackground;
    }

    public View getBackgroundImageView() {
        return backgroundImageView;
    }

    public void setBackgroundImageView(View backgroundImageView) {
        this.backgroundImageView = backgroundImageView;
    }

    public void setColorFilter(Context context,ImageView imageView, TextView textView) {
        textView.setTextColor(context.getResources().getColor(R.color.blue_2));
    }

    public void removeColorFilter(Context context, ImageView imageView, TextView textView) {
        imageView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        imageView.setColorFilter(context.getResources().getColor(R.color.transparent));
        textView.setTextColor(context.getResources().getColor(R.color.black));
        textView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
    }

    public TextView getDoneTxt() {
        return doneTxt;
    }

    public void setDoneTxt(TextView doneTxt) {
        this.doneTxt = doneTxt;
    }

    public ImageView getDoneImg() {
        return doneImg;
    }

    public void setDoneImg(ImageView doneImg) {
        this.doneImg = doneImg;
    }

    public TextView getFreeTxt() {
        return freeTxt;
    }

    public void setFreeTxt(TextView freeTxt) {
        this.freeTxt = freeTxt;
    }

    public ImageView getFreeImg() {
        return freeImg;
    }

    public void setFreeImg(ImageView freeImg) {
        this.freeImg = freeImg;
    }

    public RecyclerView getImageRecyclerView() {
        return imageRecyclerView;
    }

    public void setImageRecyclerView(RecyclerView imageRecyclerView) {
        this.imageRecyclerView = imageRecyclerView;
    }
    public View getSfBackgroundImageView() {
        return sfBackgroundImageView;
    }

    public void setSfBackgroundImageView(View sfBackgroundImageView) {
        this.sfBackgroundImageView = sfBackgroundImageView;
    }
    public View getDisplayingBackground() {
        return displayingBackground;
    }

    public void setDisplayingBackground(View displayingBackground) {
        this.displayingBackground = displayingBackground;
    }

}
