package com.xidian.bynadsdk.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xidian.bynadsdk.R;
import com.xidian.bynadsdk.activity.BYNAdSDKHomeActivity;
import com.xidian.bynadsdk.bean.ADSBean;
import com.xidian.bynadsdk.utils.CountDownProgressView;
import com.xidian.bynadsdk.utils.Utils;

/**
 * Created by Administrator on 2019/10/21.
 */

public class SplashView extends RelativeLayout {
    private ImageView imageView;
    private CountDownProgressView countDownProgressView;
    private SplashViewInterfaceListener splashViewInterface;
    private RelativeLayout relativeLayout;
    private boolean isShowSkip = false;
    private long timeMillis = 3000;
    private View inflate;
    private ADSBean adsBean;
    private Context context;


    public SplashViewInterfaceListener getSplashViewInterface() {
        return splashViewInterface;
    }

    public void setSplashViewInterface(SplashViewInterfaceListener splashViewInterface) {
        this.splashViewInterface = splashViewInterface;
    }

    private int width;
    private int height;


    public SplashView(Context context) {
        super(context);
        this.context = context;
        inflate = LayoutInflater.from(context).inflate(R.layout.byn_splash_layout, this, true);
        initView();
        initClick((Activity) context);
    }

    public SplashView(Context context, AttributeSet attrs) {
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
        if (height < Utils.getScreenHeight(getContext()) * 0.7) {
            params.height = LayoutParams.MATCH_PARENT;
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

    public void setAdsBean(ADSBean adsBean) {
        this.adsBean = adsBean;
        if (isShowSkip) {
            countDownProgressView.start();
        }
        int jump_type = adsBean.getJump_type();
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(splashViewInterface!=null){
                    splashViewInterface.imageClick();
                }

                if(countDownProgressView!=null){
                   countDownProgressView.stop();
               }
//                if (jump_type == 1) {
//                  Intent intent = new Intent(context, BYNAdSDKWebViewActivity.class);
//                  intent.putExtra("url",adsBean.getUrl()+"");
//                  intent.putExtra("title",adsBean.getTitle()+"");
//                  context.startActivity(intent);
//                } else if (jump_type == 2) {
//                    AlibcShowParams showParams = new AlibcShowParams();
//                    showParams.setOpenType(OpenType.Native);
//                    showParams.setBackUrl("alisdk://");
//                    showParams.setNativeOpenFailedMode(AlibcNativeFailModeJumpH5);
//                    //自定义参数
//                    Map<String, String> trackParams = new HashMap<>();
//                    trackParams.put("isv_code", "appisvcode");
//                    trackParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
//                    AlibcTrade.openByUrl((Activity) context, "", adsBean.getUrl(),
//                            null, new WebViewClient(), new WebChromeClient(), showParams, new AlibcTaokeParams("", "", "")
//                            , trackParams, new AlibcTradeCallback() {
//                                @Override
//                                public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
//
//                                }
//
//                                @Override
//                                public void onFailure(int i, String s) {
//
//                                }
//                            });
//                } else if(jump_type==3){
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adsBean.getDeep_link()));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    //判断某个Scheme是否有效
//                    List<ResolveInfo> activities = context.getPackageManager().queryIntentActivities(intent, 0);
//                    boolean isValid = !activities.isEmpty();
//                    if (isValid) {
//                        context.startActivity(intent);
//                    }else{
//                        Intent webIntent = new Intent(context, BYNAdSDKWebViewActivity.class);
//                        webIntent.putExtra("url",adsBean.getUrl()+"");
//                        webIntent.putExtra("title",adsBean.getTitle()+"");
//                        context.startActivity(webIntent);
//                    }
//                }else{
//                    context.startActivity(new Intent(context, BYNAdSDKHomeActivity.class));
//                }
                context.startActivity(new Intent(context, BYNAdSDKHomeActivity.class));
            }
        });
    }

    public ImageView getImageView() {
        return imageView;
    }
}
