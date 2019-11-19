package com.xidian.bynadsdk;

import android.app.Application;
import android.content.Context;
import android.os.Binder;
import android.text.TextUtils;

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
     *
     * @param appKey 开放平台申请的key
     * @return
     */
    public void init(Application application, String appKey, String appSecret, BYNInitCallBack bynInitCallBack) {
        if (TextUtils.isEmpty(appKey)) {
            bynInitCallBack.onFailure(10001, "appKey为空");
            return;
        } else {
            this.appKey = appKey;
        }
        if (TextUtils.isEmpty(appSecret)) {
            bynInitCallBack.onFailure(10001, "appSecret为空");
            return;
        } else {
            this.appSecret = appSecret;
        }
        this.context = application;
        pkg = Utils.getAppPkg(Binder.getCallingPid(), application);
        NetWorkManager.getInstance().init();
        AlibcTradeSDK.asyncInit(application, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                bynInitCallBack.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                bynInitCallBack.onFailure(i, s);
            }
        });
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
    }

    public interface BYNInitCallBack {
        void onSuccess();

        void onFailure(int code, String message);
    }

}
