package com.xidian.bynadsdk;

import com.xidian.bynadsdk.presenter.BYNAdSDKSplashPresenter;

/**
 * Created by Administrator on 2019/10/28.
 */

public class BYNAdManager {
    public static BYNAdNative getBYNAdNative(BYNAdTypeEnum bynAdTypeEnum){
        if(bynAdTypeEnum== BYNAdTypeEnum.SPLASH){
            BYNAdSDKSplashPresenter splashPresenter = new BYNAdSDKSplashPresenter();
            return splashPresenter;
        }
       return null;
    }
}
