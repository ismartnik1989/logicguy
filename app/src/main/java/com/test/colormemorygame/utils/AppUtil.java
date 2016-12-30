package com.test.colormemorygame.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.util.Random;

/**
 * @author : mukeshk
 * @created : 02/Dec/2016
 * @description : class handle all utility for all class and method .
 */
public class AppUtil {


    /**
     * @return : int
     * @author : mukesh
     * @created : 4/26/2016
     * @method name : replaceFragment
     * @description : this method generate random number with two input number
     */
    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


    /**
     * @return : void
     * @author : mukesh
     * @created : 4/26/2016
     * @method name : getDrawable
     * @description : this method help to hide fragment on activity
     */
    public static Drawable getDrawable(Context context, int id) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, context.getTheme());
        } else {
            return context.getResources().getDrawable(id);
        }

    }

    /**
     * @return : int
     * @author : mukesh
     * @created : 6/27/2016
     * @method name : getColors
     * @description : this method help to set color with corresponding id
     */
    public static int getColors(Context context, int id) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(id, context.getTheme());
        } else {
            return context.getResources().getColor(id);
        }
    }



}
