package com.xidian.bynadsdk;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.xidian.bynadsdk.splash.SplashParameter;
import com.xidian.bynadsdk.splash.SplashView;

/**
 * Created by Administrator on 2019/10/28.
 */

public interface BYNAdNative {
    void loadSplashAd(Activity activity, @NonNull SplashParameter var1, SplashADListener onSplashADListener);
    interface SplashADListener{
        void onSucess(SplashView splashView);
        void onError(int code, String message);
    }
}
