package com.hangoverstudios.men.photo.suite.editor.app.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class PhotoData {

    /*The reason of this, is pure paranoia, Im not kidding, the configuration changes in the manifest,
     * should be enough to the variables not to reset during the go to the camera, take the picture,
     * come back to the activity, but some time doesnt work!
     * So we at least save the last picture taken here*/

    private Context context;
    private static final String PHOTO_NAME_PREFIX = "men suit editor";
    private static final String PHOTO_PREFERENCE_NAME = "temp_data";
    private static final String PHOTO_KEY = "IMAGE_URI_PATH";

    public PhotoData(Context context) {
        this.context = context;
    }

    public File createImageFile() throws IOException {
        // Create an image file
        // To get the photo taken you have to pass a Uri from a file, this will create the file

        //A standarized name for the photo so every photo taken by your app have it in common, plus the current time in milli second to make unique
        String imageFileName = PHOTO_NAME_PREFIX + "_" + String.valueOf(System.currentTimeMillis());
        //Getting the device photo directory
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //This file is temporary, but the way Android works is very ambiguos with temporary files, so it work, is persistent. There is a method for not temporal files you can use it
        File image = File.createTempFile(
                imageFileName, /*prefix*/
                PHOTO_NAME_PREFIX, /*suffix*/
                storageDir      /*directory*/
        );

        return image;
    }

    public void saveUriPath(String selectedImageUriPath) {
        SharedPreferences savePhotoData = context.getSharedPreferences(PHOTO_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = savePhotoData.edit();
        prefEditor.putString(PHOTO_KEY, selectedImageUriPath);
        prefEditor.apply();
    }

    public String getUriPath() {
        SharedPreferences getSelectedImageUriPath = context.getSharedPreferences(PHOTO_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return getSelectedImageUriPath.getString(PHOTO_KEY, null);
    }

}
