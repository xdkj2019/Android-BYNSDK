package com.xidian.bynadsdk;

import com.xidian.bynadsdk.presenter.SplashPresenter;

/**
 * Created by Administrator on 2019/10/28.
 */

public class BYNAdManager {
    public static BYNAdNative getBYNAdNative(BYNAdTypeEnum bynAdTypeEnum){
        if(bynAdTypeEnum== BYNAdTypeEnum.SPLASH){
            SplashPresenter splashPresenter = new SplashPresenter();
            return splashPresenter;
        }
       return null;
    }
}
