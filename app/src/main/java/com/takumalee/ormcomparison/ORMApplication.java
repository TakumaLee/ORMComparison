package com.takumalee.ormcomparison;

import android.app.Application;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.flurry.android.FlurryAgent;
import com.github.takumalee.simplefacebook.SimpleFacebook;
import com.github.takumalee.simplefacebook.utils.Attributes;
import com.github.takumalee.simplefacebook.utils.PictureAttributes;
import com.github.takumalee.simplefacebook.utils.Utils;
import com.google.android.gms.analytics.Tracker;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.squareup.okhttp.OkHttpClient;
import com.takumalee.ormcomparison.config.AppConfig;
import com.takumalee.ormcomparison.context.ApplicationContextSingleton;
import com.takumalee.ormcomparison.database.ormlite.dao.DAOFactory;
import com.takumalee.ormcomparison.entity.MyAccount;
import com.takumalee.ormcomparison.facebook.FacebookPermission;
import com.takumalee.ormcomparison.manager.ParseManager;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.github.takumalee.simplefacebook.Permission.Builder;
import static com.github.takumalee.simplefacebook.Permission.EMAIL;
import static com.github.takumalee.simplefacebook.Permission.PUBLIC_PROFILE;
import static com.github.takumalee.simplefacebook.Permission.USER_ABOUT_ME;
import static com.github.takumalee.simplefacebook.Permission.USER_BIRTHDAY;
import static com.github.takumalee.simplefacebook.Permission.USER_PHOTOS;
import static com.github.takumalee.simplefacebook.Permission.USER_STATUS;


/**
 * Created by TakumaLee on 15/1/25.
 */
public class ORMApplication extends Application {
    private static final String TAG = ORMApplication.class.getSimpleName();

    public MyAccount myAccount;
    public RealmConfiguration realmConfig;

    private Tracker tracker;

    public String currentUsername;
    public String currentPhoto;
    public boolean isGuest = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Service is Not Running.");
        ApplicationContextSingleton.initialize(getApplicationContext());
        AppConfig.initConfig(getApplicationContext(), getResources().getString(R.string.app_name));

        initFlurry();

        myAccount = new MyAccount();
        SimpleFacebook.initialize(getApplicationContext());
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new OkHttpClient()));
        ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "bzqM3e1zqy05nrWUBlxqiLGUmWVW6JdtSlbFCbwQ", "6qpKT8FeIOg9gLNtqbJWvv9f33qoRAQej8nYiz0x");
        ParseFacebookUtils.initialize(getApplicationContext());
        ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                myAccount.setDeviceToken((String) ParseInstallation.getCurrentInstallation().get("deviceToken"));
                parseUserLogin();
            }
        });
//        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        DAOFactory.initSingleton(getApplicationContext());

        realmConfig = new RealmConfiguration.Builder(getApplicationContext())
                .name("Realm.sql")
                .build();
        Realm.setDefaultConfiguration(realmConfig);

    }

    private void initFlurry() {
        // configure Flurry
        FlurryAgent.setLogEnabled(false);

        // init Flurry
        FlurryAgent.init(this, "ZDHYNSBBC64YNKWY92KD");
    }

    public static Collection<String> getPermissions() {
        return new Builder()
                .add(USER_PHOTOS)
                .add(EMAIL)
                .add(USER_ABOUT_ME)
                .add(USER_BIRTHDAY)
                .add(PUBLIC_PROFILE)
                .add(USER_STATUS)
                .create();
    }

    public static Set<String> getProperties() {
        Set<String> properties = new HashSet<String>();
        properties.add(FacebookPermission.ID);
        properties.add(FacebookPermission.NAME);
        properties.add(FacebookPermission.COVER);
        properties.add(FacebookPermission.EMAIL);

        PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
        pictureAttributes.setHeight(500);
        pictureAttributes.setWidth(500);
        pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);
        Map<String, String> map = pictureAttributes.getAttributes();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FacebookPermission.PICTURE);
        stringBuilder.append('.');
        stringBuilder.append(Utils.join(map, '.', '(', ')'));
        properties.add(stringBuilder.toString());
        return properties;
    }

    private void parseUserLogin() {
        if (ParseUser.getCurrentUser() != null) { // start with existing user
            ParseManager.startWithCurrentUser(myAccount);
        } else { // If not logged in, login as a new anonymous user
            ParseManager.login(myAccount);
        }
    }

}
