package com.xidian.bynadsdk;

import android.app.Application;
import android.content.Context;
import android.os.Binder;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.xidian.bynadsdk.network.NetWorkManager;
import com.xidian.bynadsdk.utils.Utils;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

/**
 * Created by Administrator on 2019/10/28.
 */

public class BYNAdSDK {
    private static BYNAdSDK instance = new BYNAdSDK();
    public String appKey;
    public String appSecret;
    public Context context;
    public String pkg;
    private BYNAdSDK(){}
    public static BYNAdSDK getInstance(){
        return instance;
    }
    /**
     * 初始化项目
     * @param appKey    开放平台申请的key
     * @return
     */
    public void init(Application context,String appKey,String appSecret, AlibcTradeInitCallback alibcTradeInitCallback){
        this.appKey=appKey;
        this.appSecret = appSecret;
        this.context=context;
        pkg = Utils.getAppPkg(Binder.getCallingPid(), context);
        NetWorkManager.getInstance().init();
        AlibcTradeSDK.asyncInit(context, alibcTradeInitCallback);
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
    }

}
