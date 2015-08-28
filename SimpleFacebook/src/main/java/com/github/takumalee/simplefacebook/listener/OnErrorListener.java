package com.github.takumalee.simplefacebook.listener;

public interface OnErrorListener {
    void onException(Throwable throwable);

    void onFail(String reason);
}