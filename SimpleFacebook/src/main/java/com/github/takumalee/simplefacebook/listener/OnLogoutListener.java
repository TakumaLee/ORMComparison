package com.github.takumalee.simplefacebook.listener;

public interface OnLogoutListener extends OnLoadingListetener {
    /**
     * If user performed {@link FacebookTools#logout()} action, this callback
     * method will be invoked
     */
    void onLogout();
}