package com.xidian.bynadsdk.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.xidian.bynadsdk.BYNAdNative;
import com.xidian.bynadsdk.BYNAdSDK;
import com.xidian.bynadsdk.bean.BYNAdSDKADSBean;
import com.xidian.bynadsdk.bean.BYNAdSDKCustomerBean;
import com.xidian.bynadsdk.network.Exception.ApiException;
import com.xidian.bynadsdk.network.NetWorkManager;
import com.xidian.bynadsdk.network.response.ResponseTransformer;
import com.xidian.bynadsdk.network.schedulers.SchedulerProvider;
import com.xidian.bynadsdk.splash.BYNAdSDKSplashParameter;
import com.xidian.bynadsdk.splash.BYNAdSDKSplashView;
import com.xidian.bynadsdk.utils.TopTransform;
import com.xidian.bynadsdk.utils.Utils;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/10/28.
 */

public class BYNAdSDKSplashPresenter implements BYNAdNative {
    @Override
    public void loadSplashAd(Activity activity, @NonNull BYNAdSDKSplashParameter var1, SplashADListener onSplashADListener) {
        CompositeDisposable mDisposable = new CompositeDisposable();

        BYNAdSDKSplashView splashView = new BYNAdSDKSplashView(activity);

        if(var1.getAdzone_id()==0){
            ApiException apiException = new ApiException(10002,"广告位id不可为空" );
            onSplashADListener.onError(apiException.getCode(), apiException.getDisplayMessage());
            return;
        }
        int height = var1.getHeight();
        int width = var1.getWidth();
        long timeMillis = var1.getTimeMillis();
        boolean showSkip = var1.isShowSkip();
        splashView.setSize(width, height);
        splashView.setShowSkip(showSkip);
        splashView.setTimeMillis(timeMillis);
        long l = System.currentTimeMillis() / 1000;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("t", l+"");
        hashMap.put("sign", Utils.md5Decode32( BYNAdSDK.getInstance().appSecret+"appkey="+BYNAdSDK.getInstance().appKey+"tm="+l+BYNAdSDK.getInstance().appSecret));
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("adzone_id", var1.getAdzone_id());
        if (var1 != null && !TextUtils.isEmpty(var1.getDevice_type())) {
            map.put("device_type", var1.getDevice_type());
            BYNAdSDKCustomerBean.DEVICETYPE=var1.getDevice_type();
        }
        if (var1 != null && !TextUtils.isEmpty(var1.getDevice_value())) {
            map.put("device_value", var1.getDevice_value());
            BYNAdSDKCustomerBean.DEVICEVALUE=var1.getDevice_value();
        }

        if (var1 != null && var1.getHeight() > Utils.getScreenHeight(BYNAdSDK.getInstance().context) * 0.7 && var1.getWidth() > 0) {
            map.put("size", Utils.getScreenWidth(BYNAdSDK.getInstance().context) + "*" + var1.getHeight());
        } else {
            map.put("size", Utils.getScreenWidth(BYNAdSDK.getInstance().context) + "*" + Utils.getScreenHeight(BYNAdSDK.getInstance().context));
        }

        if(Utils.isPackageExist(BYNAdSDK.getInstance().context,"com.taobao.taobao")){
            map.put("tb",1);
        }else{
            map.put("tb",0);
        }
        if(Utils.isPackageExist(BYNAdSDK.getInstance().context,"com.xunmeng.pinduoduo")){
            map.put("pdd",1);
        }else{
            map.put("pdd",0);
        }
        if(Utils.isPackageExist(BYNAdSDK.getInstance().context,"com.jingdong.app.mall")){
            map.put("jd",1);
        }else{
            map.put("jd",0);
        }
        if(Utils.isPackageExist(BYNAdSDK.getInstance().context,"com.dianping.v1")){
            map.put("dp",1);
        }else{
            map.put("dp",0);
        }
        if(Utils.isPackageExist(BYNAdSDK.getInstance().context,"com.sankuai.meituan")){
            map.put("mt",1);
        }else{
            map.put("mt",0);
        }
        if(Utils.isPackageExist(BYNAdSDK.getInstance().context,"me.ele")){
            map.put("elm",1);
        }else{
            map.put("elm",0);
        }



        Disposable subscribe = NetWorkManager.getRequest().getads(hashMap, map)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<BYNAdSDKADSBean>() {
                    @Override
                    public void accept(BYNAdSDKADSBean adsBean) throws Exception {
                        splashView.setBYNAdSDKADSBean(adsBean);
                        TopTransform transformation = new TopTransform(activity,height*1000/Utils.getScreenHeight(activity));
                        if(height== Utils.getScreenHeight(activity)){
                            Glide.with(activity).load(adsBean.getImage().getUrl()).into(splashView.getImageView());
                        }else{
                            Glide.with(activity).load(adsBean.getImage().getUrl())
                                    .bitmapTransform(transformation)
                                    .into(splashView.getImageView());
                        }

                        onSplashADListener.onSucess(splashView);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        try {
                            ApiException apiException = (ApiException) throwable;
                            onSplashADListener.onError(apiException.getCode(), apiException.getDisplayMessage());
                        }catch (Exception e){
                            onSplashADListener.onError(10000, throwable.getMessage());
                        }

                    }
                });
        mDisposable.add(subscribe);
    }
}
