package com.example.apple.picturepickerdemo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by apple on 2017/8/18.
 */

public class ImageUtils {


    public static void loadImage(Context context, String path, ImageView imageView) {

        Glide.with(context).load(path).into(imageView);

    }


    public static void loadImage(Context context, Integer resourceID, ImageView imageView) {

        Glide.with(context).load(resourceID).into(imageView);

    }


    public static int getScreenWidth(Context context) {

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

    public static int getScreenHeight(Context context) {

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int height = dm.heightPixels;
        return height;
    }



}
