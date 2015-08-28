package com.takumalee.ormcomparison.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.login.LoginResult;
import com.github.takumalee.simplefacebook.SimpleFacebook;
import com.github.takumalee.simplefacebook.utils.Utils;
import com.github.takumalee.view.CircularProgressImageView;
import com.google.ads.mediation.flurry.FlurryAdapterExtras;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.nineoldandroids.animation.Animator;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.takumalee.ormcomparison.BuildConfig;
import com.takumalee.ormcomparison.ORMApplication;
import com.takumalee.ormcomparison.R;
import com.takumalee.ormcomparison.config.ATLConfig;
import com.takumalee.ormcomparison.config.AppConfig;
import com.takumalee.ormcomparison.config.BaseConfig;
import com.takumalee.ormcomparison.fragment.HomeFragment;
import com.takumalee.ormcomparison.fragment.base.ActivityBaseFragment;
import com.takumalee.ormcomparison.views.CustomHeader;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.Fabric;

public class ORMHomeActivity extends FragmentControlActivity {

    private static final String TAG = ORMHomeActivity.class.getSimpleName();

    private MaterialMenuDrawable materialMenu;
    private MaterialMenuDrawable.IconState currentIconState = MaterialMenuDrawable.IconState.BURGER;
    private boolean isAnimating = false;

    private HomeFragment homeFragment;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CustomHeader drawerHeader;
    private FrameLayout adsContainer;
//    private FloatingActionButton actionButton;
    private AdView adView;

    private FrameLayout menuAds;
    private NativeAd nativeAd;

    private boolean isAddAdsView = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atlhome);
        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        registerFacebookCallback();

        homeFragment = HomeFragment.newInstance();

        if (null == savedInstanceState) {
            setupViewComponent(R.id.frameLayout_MainScrollContainer, homeFragment);
        }

        setOnImplementListener(new OnImplementListener() {
            @Override
            public void startNewFragmentListener() {
//                actionButton.setVisibility(View.GONE);
            }

            @Override
            public void endFragment() {
//                startAnimation();
            }
        });

//        homeAccount = new MaterialAccount(getResources(), "Guest", "no comment", R.drawable.anonymous, R.drawable.wallpaper);

        drawerHeader = (CustomHeader) findViewById(R.id.drawer_header);
        adsContainer = (FrameLayout) findViewById(R.id.frameLayout_MainAdsContainer);
        initAdView();

//        actionButton = (FloatingActionButton) findViewById(R.id.fab_action_button);
//        actionButton.setImageResource(R.drawable.ic_add_white_24dp);
//        actionButton.setBackgroundTintList(BaseConfig.getSimpleColorState(ge));
//        actionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startNewFragment(EditFragment.newInstance(AccountRecord.InputType.OUTLAY, null));
//                enableArrowAnimation();
//            }
//        });


        materialMenu = new MaterialMenuDrawable(this, Color.argb(255 * 54 / 100, 0, 0, 0), MaterialMenuDrawable.Stroke.THIN);
        materialMenu.setColor(Color.WHITE);
        materialMenu.setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                if (currentIconState != MaterialMenuDrawable.IconState.ARROW) {
//                    onBackPressed();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

//        RelativeLayout toolbarContainer = (RelativeLayout) toolbar.findViewById(R.id.relativeLayout_ToolbarContainer);
//
//        AppCompatSpinner spinner = new AppCompatSpinner(this);
//        spinner.setLayoutParams(new ViewGroup.LayoutParams(256, ViewGroup.LayoutParams.WRAP_CONTENT));
//        spinner.setDropDownWidth(MMMConfig.dpToPx(96));
//        SpinnerAdapter spinnerAdapter = new SpinnerAdapter();
//        spinnerAdapter.filterTimes = MMMConfig.findStringArrayById(R.array.filter_times);
//        spinner.setAdapter(spinnerAdapter);
//        spinnerAdapter.notifyDataSetChanged();
//
//        toolbarContainer.addView(spinner);

//        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
//        coordinatorLayout.

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(materialMenu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setElevation(0);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (null != navigationView) {
            setupDrawerContent(navigationView);
        }

//        showFullDialog();
        if (isFacebookLogin()) {
            login();
        }
//        getSupportActionBar().setHomeAsUpIndicator(materialMenu);

    }

    private void initAdView() {
        try {
            adView = new AdView(this);
            adView.setAdUnitId(BaseConfig.findStringById(R.string.admob_banner_ad_unit_id));
            adView.setAdSize(AdSize.BANNER);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    if (isAddAdsView) {
                        adsContainer.removeAllViews();
                        isAddAdsView = false;
                    }
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (!isAddAdsView) {
                        adsContainer.addView(adView);
                        isAddAdsView = true;
                    }
                }
            });
            FlurryAdapterExtras flurryAdapterExtras = new FlurryAdapterExtras();
            flurryAdapterExtras.setLogEnabled(true);
            adView.loadAd(new AdRequest.Builder()
                    .addNetworkExtras(flurryAdapterExtras)
                    .build());//addTestDevice("D9C9E2E8F0F728B02A3BA1D73BB24C6D").

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.getMenu().addSubMenu(getResources().getString(R.string.version) + ": " + AppConfig.getAppVersion());
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_home:
                                menuItem.setChecked(true);
                                break;

                            case R.id.navigation_rating:
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ATLConfig.GOOGLE_PLAY_URL)));
                                break;
                            case R.id.navigation_feedback:
                                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:unlimitales@gmail.com"));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "[MMM]");
                                startActivity(intent);
                                break;
                            case R.id.login_facebook:
                                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                                if (null != accessToken) {
                                    SimpleFacebook.getInstance().logout();
//                                    menuItem.setTitle(R.string.login_facebook);
                                    checkIsLogin();
                                } else {
//                                    menuItem.setTitle(R.string.logout_facebook);
                                    login();
                                }
                                break;
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void enableArrowAnimation() {
        currentIconState = MaterialMenuDrawable.IconState.ARROW;
        materialMenu.setTransformationDuration(1000);
        animateIcon();
    }

    public void enableMenuAnimation() {
        currentIconState = MaterialMenuDrawable.IconState.BURGER;
        materialMenu.setTransformationDuration(500);
        animateIcon();
    }

//    public void startAnimation() {
//        actionButton.setVisibility(View.GONE);
//        actionButton.setVisibility(View.VISIBLE);
//        MaterialAnimation.ScaleViewSizeAnimator(actionButton);
//    }



    private void animateIcon() {
        materialMenu.animateIconState(currentIconState);
    }

    private void registerFacebookCallback() {
        SimpleFacebook.getInstance().registerCallback(new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v(TAG, "onSuccess: " + loginResult.toString());
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse response) {
//                        fbSection.setTitle(getResources().getString(R.string.logout_facebook));
//                        fbSection.setIcon(getResources().getDrawable(R.drawable.logout));
                        Log.v(TAG, "Check Login");
                        final ORMApplication application = (ORMApplication) getApplicationContext();
                        try {
                            if (!jsonObject.getString("id").equals(application.myAccount.getFbId())) {
                                application.myAccount.setFbId(jsonObject.getString("id"));
                                application.myAccount.setUsername(jsonObject.getString("name"));
                                application.myAccount.setHeaderPic(jsonObject.getJSONObject("picture").getJSONObject("data").getString("url"));
                                application.myAccount.setCoverPic(jsonObject.getJSONObject("cover").getString("source"));
//                                application.myAccount.setEmail(currentProfile.getE());
                                application.currentUsername = jsonObject.getString("name");
                                application.currentPhoto = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                                application.isGuest = false;
//                                Glide.with(MHomeActivity.this).load(application.myAccount.getHeaderPic()).into(drawerHeader.getCpImageView());
//                                Bitmap photo = Glide.with(MHomeActivity.this).load(application.myAccount.getHeaderPic()).asBitmap().into(-1, -1).get();
//                                Bitmap cover = Glide.with(MHomeActivity.this).load(application.myAccount.getCoverPic()).asBitmap().into(-1, -1).get();
//                                drawerHeader.getCpImageView().setImageBitmap(photo);
//                                drawerHeader.setBackground(new BitmapDrawable(getResources(), cover));
//                                String title = application.myAccount.getUsername();
//                                String subTitle = "";
                                fetchBitmapFromServer();
                            }
                        } catch (Exception e) {

                        }
                    }
                });
                Bundle parameters = new Bundle();
                Iterator<String> iterator = ORMApplication.getProperties().iterator();
                String fields = Utils.join(iterator, ",");
                parameters.putString("fields", fields);
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.v(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException e) {
                Log.v(TAG, "onError");
            }
        });
    }

    private void fetchBitmapFromServer() {
        final ORMApplication application = (ORMApplication) getApplicationContext();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap photo = null;//ImageLoader.getInstance().loadImageSync(application.myAccount.getHeaderPic());
                Bitmap cover = null;
                try {
                    photo = Glide.with(ORMHomeActivity.this).load(application.myAccount.getHeaderPic()).asBitmap().into(-1, -1).get();
                    cover = Glide.with(ORMHomeActivity.this).load(application.myAccount.getCoverPic()).asBitmap().into(-1, -1).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                String title = application.myAccount.getUsername();
                String subTitle = "";//application.myAccount.getEmail();

                refreshDrawerHeader(photo, cover);
            }
        }).start();
    }

    private void refreshDrawerHeader(final Bitmap photo, final Bitmap cover) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawerHeader.getCpImageView().setImageBitmap(photo);
                drawerHeader.setBackground(new BitmapDrawable(getResources(), cover));
            }
        });
    }

    private void checkIsLogin () {
        final ORMApplication application = (ORMApplication) getApplicationContext();
        if (isFacebookLogin()) {
            /* check is login? false */
            resetAccount(application);
            drawerHeader.getCpImageView().setImageResource(R.mipmap.anonymous);
            drawerHeader.setBackgroundResource(R.mipmap.wallpaper);
        } else {
            login();

        }
    }

    private boolean isFacebookLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (null == accessToken) {
            return false;
        } else {
            return true;
        }
    }

    private void resetAccount(ORMApplication application) {
        application.myAccount.setFbId("");
        application.myAccount.setUsername("Guest");
        application.myAccount.setHeaderPic("");
        application.myAccount.setCoverPic("");
        application.currentUsername = "";
        application.currentPhoto = "";
        application.isGuest = true;
    }

//    private void loginoutWithFB(MenuItem menuItem) {
//
//    }

    private void login() {
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, ORMApplication.getPermissions(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                Log.v(TAG, "ParseFacebook Login");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adView.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        adView.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adView.destroy();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
            return;
        }
//        if (!homeFragment.onBackPressed()) {
//            return;
//        }
        if (isAnimating) {
//            return;
        }
        if (currentIconState == MaterialMenuDrawable.IconState.ARROW) {
            enableMenuAnimation();
//        } else {
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alt_home, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_ads);
        menuAds = (FrameLayout) MenuItemCompat.getActionView(menuItem);
        menuAds.setPadding(0, 0, 10, 0);
        initFacebookNativeAd();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            if (currentIconState == MaterialMenuDrawable.IconState.ARROW) {
                onBackPressed();
                enableMenuAnimation();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
//        } else if (id == R.id.menu_spinner_history) {
//            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.frameLayout_ScrollContainer), Gravity.BOTTOM);
        }

        return super.onOptionsItemSelected(item);
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
                CircularProgressImageView iconImageView = new CircularProgressImageView(ORMHomeActivity.this);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        SimpleFacebook.getInstance().onActivityResult(requestCode, resultCode, data);
        if (null != currentFragment) currentFragment.onActivityResult(requestCode, resultCode, data);
    }


//    @Override
//    public void onAccountOpening(MaterialAccount materialAccount) {
//
//    }
//
//    @Override
//    public void onChangeAccount(MaterialAccount materialAccount) {
//        final MMMApplication application = (MMMApplication) getApplicationContext();
//        application.currentUsername = materialAccount.getTitle();
//        application.currentPhoto = application.myAccount.getHeaderPic();
//        if (materialAccount.getAccountNumber() == ((MaterialAccount)getAccountList().get(getAccountList().size() - 1)).getAccountNumber()) {
//            application.isGuest = true;
//        } else {
//            application.isGuest = false;
//        }
//    }

//    private void showFullDialog() {
//        final DialogFragment dialogFragment = new SplashFragment();
//        dialogFragment.show(getSupportFragmentManager().beginTransaction(), "Dialog Full Screen");
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                if (null != dialogFragment) {
//                    dialogFragment.dismissAllowingStateLoss();
//                }
////                startAnimation();
//            }
//        }, 3000);
//    }

    @Override
    public void startNewFragment(ActivityBaseFragment fragment) {
        super.startNewFragment(fragment);
        ((FrameLayout) findViewById(R.id.frameLayout_ScrollContainer)).removeAllViews();
    }

    public void addCustomView(View customView) {
        ((FrameLayout) findViewById(R.id.frameLayout_ScrollContainer)).removeAllViews();
        ((FrameLayout) findViewById(R.id.frameLayout_ScrollContainer)).addView(customView);
    }

}
