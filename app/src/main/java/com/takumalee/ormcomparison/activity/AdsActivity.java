package com.takumalee.ormcomparison.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.github.takumalee.view.CircularProgressImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.takumalee.ormcomparison.R;
import com.takumalee.ormcomparison.config.ATLConfig;


//import com.facebook.AppEventsLogger;
//import com.flurry.android.FlurryAgent;

//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.ExceptionReporter;

public class AdsActivity extends FragmentControlActivity {
    private static String TAG = AdsActivity.class.getSimpleName();

    private AdView adView;
//    private AdLoader adLoader;
    private Fragment fragment;
	private FrameLayout bannerAdsContainer;
	private FrameLayout	frameLayout;

    private FrameLayout menuAds;
    private NativeAd nativeAd;
    private boolean isAddAdsView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_ads);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout_BannerAds_Container);
        bannerAdsContainer = (FrameLayout) findViewById(R.id.frameLayout_BannerAds);
        View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        frameLayout.addView(contentView);
        initAdView();
    }

    @Override
    public void setContentView(View view) {
        View containerView = LayoutInflater.from(this).inflate(R.layout.activity_ads, null);
        super.setContentView(containerView);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout_BannerAds_Container);
        bannerAdsContainer = (FrameLayout) findViewById(R.id.frameLayout_BannerAds);
        frameLayout.addView(view);
        initAdView();
    }

    private void initAdView() {

        try {
            adView = new AdView(this);
            adView.setAdUnitId(ATLConfig.findStringById(R.string.admob_banner_ad_unit_id));
            adView.setAdSize(AdSize.BANNER);
            adView.setAdListener(new AdListener() {

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    if (isAddAdsView) {
                        bannerAdsContainer.removeAllViews();
                        isAddAdsView = false;
                    }
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (!isAddAdsView) {
                        bannerAdsContainer.addView(adView);
                        isAddAdsView = true;
                    }
                }
            });
            adView.loadAd(new AdRequest.Builder().build());//.addTestDevice("D9C9E2E8F0F728B02A3BA1D73BB24C6D")
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null)
            adView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adView != null)
            adView.pause();
    }

//    public final <E extends View> E getView(int id) {
//        return (E) findViewById(id);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null)
            adView.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ads, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_ads);
        menuAds = (FrameLayout) MenuItemCompat.getActionView(menuItem);
        menuAds.setPadding(0, 0, 10, 0);
        initFacebookNativeAd();
        return super.onCreateOptionsMenu(menu);
    }

    private void initFacebookNativeAd() {
        nativeAd = new NativeAd(this, ATLConfig.findStringById(R.string.facebook_audience_network_placement_id));
        nativeAd.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (ad != nativeAd) {
                    return;
                }
                CircularProgressImageView iconImageView = new CircularProgressImageView(AdsActivity.this);

                String titleForAd = nativeAd.getAdTitle();
                NativeAd.Image iconForAd = nativeAd.getAdIcon();

                NativeAd.downloadAndDisplayImage(iconForAd, iconImageView);

                menuAds.addView(iconImageView);

                // Register the native ad view with the native ad instance
                nativeAd.registerViewForInteraction(iconImageView);

            }

            @Override
            public void onAdClicked(Ad ad) {

            }
        });
        nativeAd.loadAd();
    }
}
