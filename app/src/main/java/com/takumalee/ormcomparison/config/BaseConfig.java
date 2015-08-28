package com.takumalee.ormcomparison.config;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Window;


import com.takumalee.ormcomparison.context.ApplicationContextSingleton;

import java.util.Random;

/**
 * Created by TakumaLee on 15/6/1.
 */
public class BaseConfig {
    public static int getRandomBackgroundColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        return color;
    }

    public static final String findStringById(int id) {
        return ApplicationContextSingleton.getApplicationContext().getResources().getString(id);
    }

    public static final String[] findStringArrayById(int id) {
        return ApplicationContextSingleton.getApplicationContext().getResources().getStringArray(id);
    }

    public static int getStatusBarHeight(Context context) {
        Rect rectangle = new Rect();
        Window window = ((Activity) context).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static ColorStateList getSimpleColorState(int color) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{color});
    }

    public static Drawable getLoadingRandomColors() {
        Drawable drawable = new ColorDrawable(getRandomBackgroundColor());
        return drawable;
    }
}
