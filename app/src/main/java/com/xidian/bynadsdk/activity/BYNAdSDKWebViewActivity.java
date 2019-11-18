package com.xidian.bynadsdk.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xidian.bynadsdk.BYNBaseActivity;
import com.xidian.bynadsdk.R;
import com.xidian.bynadsdk.utils.FinishActivityManager;
import com.xidian.bynadsdk.utils.StatusBarUtil;

import java.util.List;

public class BYNAdSDKWebViewActivity extends BYNBaseActivity {
    private WebView webView;
    private String url ,title;
    private WebSettings settings;
    private TextView titletv;
    private LinearLayout goBack;
    private LinearLayout outFinish;
    private ProgressBar progressBar;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.byn_activity_web_view);
        //设置透明
        StatusBarUtil.setStatusBarFullTransparent(this);
        //  StatusBarUtil.setStatusBarColor(this,R.color.white);
        //设置字体黑色
        StatusBarUtil.statusBarLightMode(this);
        webView=(WebView)findViewById(R.id.byn_web_webview);
        titletv=(TextView)findViewById(R.id.byn_web_text_title);
        goBack=(LinearLayout)findViewById(R.id.byn_web_go_back);
        outFinish=(LinearLayout)findViewById(R.id.byn_web_finish);
        progressBar=(ProgressBar)findViewById(R.id.byn_web_webview_progressBar);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptObject(this), "Android");
        // 设置UserAgent
        String userAgent = settings.getUserAgentString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.clearCache(true);
        webView.clearHistory();
        settings.setUserAgentString(userAgent);// 设置userAgent
        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath =getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        //允许弹出框
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //允许web定位
        settings.setGeolocationEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setHorizontalScrollbarOverlay(true);
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100||newProgress==0){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, true);
                super.onGeolocationPermissionsShowPrompt(origin, callback);


            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String urlOpen = request.getUrl().toString();

                if (urlOpen.contains("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(urlOpen));
                    startActivity(intent);
                    return true;
                }
                if (!urlOpen.startsWith("http:") && !urlOpen.startsWith("https:")) {
                    Log.e("打开的网页2", urlOpen);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlOpen));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri parse = Uri.parse(urlOpen);
                    String host = parse.getHost();
                    String scheme = parse.getScheme();
                    //判断某个Scheme是否有效
                    List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, 0);
                    boolean isValid = !activities.isEmpty();
                    if (isValid) {
                        startActivity(intent);
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        if(!TextUtils.isEmpty(title)){
            titletv.setText(title);
        }
        if(!TextUtils.isEmpty(url))
            webView.loadUrl(url);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    FinishActivityManager.getsManager().finishActivity();
                }
            }
        });
        outFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishActivityManager.getsManager().finishAllActivity();
            }
        });


    }


    class JavaScriptObject{
        Context mContxt;
        public JavaScriptObject(Context mContxt) {
            this.mContxt = mContxt;
        }
    }




}
