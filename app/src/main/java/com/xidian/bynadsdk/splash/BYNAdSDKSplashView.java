package com.xidian.bynadsdk.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.xidian.bynadsdk.R;
import com.xidian.bynadsdk.activity.BYNAdSDKHomeActivity;
import com.xidian.bynadsdk.activity.BYNAdSDKProductActivity;
import com.xidian.bynadsdk.activity.BYNAdSDKWebViewActivity;
import com.xidian.bynadsdk.bean.BYNAdSDKADSBean;
import com.xidian.bynadsdk.utils.CountDownProgressView;
import com.xidian.bynadsdk.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType.AlibcNativeFailModeJumpH5;

/**
 * Created by Administrator on 2019/10/21.
 */

public class BYNAdSDKSplashView extends RelativeLayout {
    private ImageView imageView;
    private CountDownProgressView countDownProgressView;
    private BYNAdSDKSplashViewInterfaceListener splashViewInterface;
    private RelativeLayout relativeLayout;
    private boolean isShowSkip = false;
    private long timeMillis = 3000;
    private View inflate;
    private BYNAdSDKADSBean bynAdSDKADSBean;
    private Context context;


    public BYNAdSDKSplashViewInterfaceListener getSplashViewInterface() {
        return splashViewInterface;
    }

    public void setSplashViewInterface(BYNAdSDKSplashViewInterfaceListener splashViewInterface) {
        this.splashViewInterface = splashViewInterface;
    }

    private int width;
    private int height;


    public BYNAdSDKSplashView(Context context) {
        super(context);
        this.context = context;
        inflate = LayoutInflater.from(context).inflate(R.layout.byn_splash_layout, this, true);
        initView();
        initClick((Activity) context);
    }

    public BYNAdSDKSplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate = LayoutInflater.from(context).inflate(R.layout.byn_splash_layout, this, true);
        initView();
        initClick((Activity) context);
    }

    private void initView() {

        relativeLayout = (RelativeLayout) findViewById(R.id.splash_relativelayout);
        imageView = (ImageView) findViewById(R.id.splash_image);
        countDownProgressView = (CountDownProgressView) findViewById(R.id.splash_countdownprogressview);
    }

    private void initClick(Activity activity) {

        countDownProgressView.setProgressListener(new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                if (isShowSkip) {
                    if (progress == 0) {
                        if(splashViewInterface!=null){
                            splashViewInterface.skipTimeout();
                        }

                    }
                }
            }
        });
        countDownProgressView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowSkip) {
                    if(splashViewInterface!=null){
                        splashViewInterface.skipClick();
                        countDownProgressView.stop();
                    }

                }
            }
        });

    }

    public void setSize(int width, int height) {
        LayoutParams params = (LayoutParams) relativeLayout.getLayoutParams();
        //  int i = Utils.dip2px(getContext(), height);
        if (height <= Utils.getScreenHeight(getContext()) * 0.7) {
            params.height = (int) (Utils.getScreenHeight(getContext()) * 0.7);
        } else {
            params.height = height;
        }
        this.height=height;
        params.width = LayoutParams.MATCH_PARENT;
        setLayoutParams(params);
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        if (countDownProgressView != null) {
            countDownProgressView.setTimeMillis(timeMillis);
        }
    }

    public void setShowSkip(boolean showSkip) {
        isShowSkip = showSkip;
        if (countDownProgressView != null) {
            if (isShowSkip) {
                countDownProgressView.setVisibility(VISIBLE);
            } else {
                countDownProgressView.setVisibility(GONE);
            }
        }
    }

    public void setBYNAdSDKADSBean(BYNAdSDKADSBean bynAdSDKADSBean) {
        this.bynAdSDKADSBean = bynAdSDKADSBean;
        if (isShowSkip) {
            countDownProgressView.start();
        }
        int jump_type = bynAdSDKADSBean.getJump_type();
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(splashViewInterface!=null){
                    splashViewInterface.imageClick();
                }

                if(countDownProgressView!=null){
                   countDownProgressView.stop();
               }
                if (jump_type == 1) {
                    Intent intent = new Intent(context, BYNAdSDKWebViewActivity.class);
                    intent.putExtra("url", bynAdSDKADSBean.getUrl()+"");
                    intent.putExtra("title", bynAdSDKADSBean.getTitle()+"");
                    context.startActivity(intent);
                } else if (jump_type == 2) {
                    AlibcShowParams showParams = new AlibcShowParams();
                    showParams.setOpenType(OpenType.Native);
                    showParams.setBackUrl("alisdk://");
                    showParams.setNativeOpenFailedMode(AlibcNativeFailModeJumpH5);
                    //自定义参数
                    Map<String, String> trackParams = new HashMap<>();
                    trackParams.put("isv_code", "appisvcode");
                    trackParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
                    AlibcTrade.openByUrl((Activity) context, "", bynAdSDKADSBean.getUrl(),
                            null, new WebViewClient(), new WebChromeClient(), showParams, new AlibcTaokeParams("", "", "")
                            , trackParams, new AlibcTradeCallback() {
                                @Override
                                public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {

                                }

                                @Override
                                public void onFailure(int i, String s) {

                                }
                            });
                } else if(jump_type==3){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bynAdSDKADSBean.getDeeplink()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //判断某个Scheme是否有效
                    List<ResolveInfo> activities = context.getPackageManager().queryIntentActivities(intent, 0);
                    boolean isValid = !activities.isEmpty();
                    if (isValid) {
                        context.startActivity(intent);
                    }else{
                        Intent webIntent = new Intent(context, BYNAdSDKWebViewActivity.class);
                        webIntent.putExtra("url", bynAdSDKADSBean.getUrl()+"");
                        webIntent.putExtra("title", bynAdSDKADSBean.getTitle()+"");
                        context.startActivity(webIntent);
                    }
                }else if (jump_type==4){
                    context.startActivity(new Intent(context, BYNAdSDKHomeActivity.class));
                }else if(jump_type==5){
                    context.startActivity(new Intent(context,BYNAdSDKProductActivity.class)
                            .putExtra("item_id", bynAdSDKADSBean.getItem_info().getItem_id())
                            .putExtra("activity_id", bynAdSDKADSBean.getItem_info().getActivity_id()));

                }
            }
        });
    }

    public ImageView getImageView() {
        return imageView;
    }
}
