package com.takumalee.ormcomparison.config;


import com.takumalee.ormcomparison.R;
import com.takumalee.ormcomparison.context.ApplicationContextSingleton;

/**
 * Created by TakumaLee on 15/1/21.
 */
public class ATLConfig extends BaseConfig {
    public static int FLAG_PAGE_CODE = 0;

    public static final String HOME = findStringById(R.string.home_page);

    public static final String FANPAGE = findStringById(R.string.fanpage);

    public static final String RATING = findStringById(R.string.rating);

    public static final String FEEDBACK = findStringById(R.string.feedback);

    public static final String ABOUTUS = findStringById(R.string.about_us);

    public static final String OPEN_NOTIFICATION = findStringById(R.string.open_notification);

    public static final String GA_PROPERTY_ID = findStringById(R.string.ga_trackingId);

    public static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=org.unlimitales.mmm";

    public static final String FACEBOOK_FANS_ID = "1018818351466709";

//    public static final String FACEBOOK_FANS_URL = "https://www.facebook.com/MoeMoneyMaster?ref=hl";

    public static final int MAIN_COLOR = ApplicationContextSingleton.getApplicationContext().getResources().getColor(R.color.main_color);
//    public static final int MAIN_DEEP_COLOR = ApplicationContextSingleton.getApplicationContext().getResources().getColor(R.color.yellow_pressed);

}
