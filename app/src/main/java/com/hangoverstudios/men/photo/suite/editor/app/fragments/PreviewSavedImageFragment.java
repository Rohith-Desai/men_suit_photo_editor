package com.hangoverstudios.men.photo.suite.editor.app.fragments;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.activities.SaveFinishActivity;


public class PreviewSavedImageFragment extends Fragment {

    View view;
    ShapeableImageView previewImage;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.preview_image_layout, container, false);
        previewImage =  view.findViewById(R.id.preview_image);
        previewImage.setImageBitmap(((SaveFinishActivity)getActivity()).getSavedBitmap());
        previewImage.setShapeAppearanceModel(new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,50)
                .build());
        //previewImage.setStrokeWidth(5);
       // previewImage.setStrokeColor(ColorStateList.valueOf(getActivity().getColor(R.color.white)));
        return view;
    }
}
