package com.takumalee.ormcomparison.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class WindowSizeUtils {
	
	public static DisplayMetrics getWindowMetrics(Context context) {
		return context.getResources().getDisplayMetrics();		
	}
	
	public static int getWindowWidthSize(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;		
	}
	
	public static int getWindowHeightSize(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	
	public static int getWindowDensityDpi(Context context) {
		return context.getResources().getDisplayMetrics().densityDpi;
	}
	
	public static float getWindowDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

}
