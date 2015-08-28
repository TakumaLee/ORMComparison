package com.github.takumalee.simplefacebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Collection;

/**
 * Created by TakumaLee on 15/5/2.
 */
public class SimpleFacebook {
    private static final String TAG = SimpleFacebook.class.getSimpleName();

    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    private SimpleFacebook() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback);
    }

    public void registerCallback(FacebookCallback<LoginResult> callback) {
        this.facebookCallback = callback;
        LoginManager.getInstance().registerCallback(callbackManager, this.facebookCallback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static class SingletonHolder {
        public static SimpleFacebook INSTANCE = new SimpleFacebook();
    }
    public static SimpleFacebook getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void initialize(Context context) {
        FacebookSdk.sdkInitialize(context);
    }

    public void activateApp(Context context) {
        AppEventsLogger.activateApp(context);
    }

    public void deactivateApp(Context context) {
        AppEventsLogger.deactivateApp(context);
    }

    public void logout() {
        LoginManager.getInstance().logOut();
    }

    public void logInWithReadPermissions(Activity activity, Collection<String> permissions) {
        LoginManager.getInstance().logInWithReadPermissions(activity, permissions);
    }

    public void logInWithReadPermissions(Fragment fragment, Collection<String> permissions) {
        LoginManager.getInstance().logInWithReadPermissions(fragment, permissions);
    }

    public void logInWithPublishPermissions(Activity activity, Collection<String> permissions) {
        LoginManager.getInstance().logInWithPublishPermissions(activity, permissions);
    }

    public void logInWithPublishPermissions(Fragment fragment, Collection<String> permissions) {
        LoginManager.getInstance().logInWithPublishPermissions(fragment, permissions);
    }
}
