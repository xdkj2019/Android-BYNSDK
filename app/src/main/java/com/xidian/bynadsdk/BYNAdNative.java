package com.xidian.bynadsdk;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.xidian.bynadsdk.splash.BYNAdSDKSplashParameter;
import com.xidian.bynadsdk.splash.BYNAdSDKSplashView;

/**
 * Created by Administrator on 2019/10/28.
 */

public interface BYNAdNative {
    void loadSplashAd(Activity activity, @NonNull BYNAdSDKSplashParameter var1, SplashADListener onSplashADListener);
    interface SplashADListener{
        void onSucess(BYNAdSDKSplashView splashView);
        void onError(int code, String message);
    }
}
