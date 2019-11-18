package com.xidian.bynadsdk.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.xidian.bynadsdk.BYNAdSDK;
import com.xidian.bynadsdk.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by Administrator on 2019/10/21.
 */

public class Utils {

    public static int dip2px(Context context, float dipValue){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dipValue,resources.getDisplayMetrics());
    }
    //得到屏幕宽
    public static int getScreenWidth(Context teamItamActivity) {
        Resources resources = teamItamActivity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return width;
    }
    //得到屏幕高
    public static int getScreenHeight(Context teamItamActivity) {
        Resources resources = teamItamActivity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return height;
    }
    //获取调用者包名
    public static String getAppPkg(int pid, Context context) {
        String processName = "";
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo info : list) {
                if (info.pid == pid) {
                    processName = info.processName;
                    break;
                }
            }
        }
        return processName;
    }
    public static boolean isPackageExist(Context context,String pkgName){
        boolean isExist = false;
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        if (pName.contains(pkgName)) {
            isExist = true;
        } else {
            isExist = false;
        }
        return isExist;
    }
    //获取版本号
    public static String getVersion() {
        Context context = BYNAdSDK.getInstance().context;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            return "";
        }
    }
    public static String md5Decode32(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException",e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        //对生成的16字节数组进行补零操作
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
    public static int dp2px(Context context, int dp) {
        if (context == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }
    //初始化listView
    public static void initListView(Context ctx, EasyRecyclerView recyclerView, DividerDecoration decoration,
                                    final RecyclerArrayAdapter adapter,
                                    PullListener listener, RecyclerArrayAdapter.OnItemClickListener itemClickListener) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);

        //DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY,Util.dip2px(this,0.5f), Util.dip2px(this,72),0);

        if (decoration == null) {
            decoration = new DividerDecoration(Color.parseColor("#dddddd"), 0);
        }

        if (decoration != null) {
            decoration.setDrawLastItem(false);
            recyclerView.addItemDecoration(decoration);
        }

        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.bynad_sdk_view_more, listener);//default more
        if (itemClickListener != null) {
            adapter.setOnItemClickListener(itemClickListener);
        }
        adapter.setError(R.layout.bynad_sdk_view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });

        recyclerView.setRefreshListener(listener);
        listener.onRefresh();
    }

    public static void setRoundGlide(Context context, int dp, boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom, String imgUrl, ImageView imageView){
        CornerTransform transformation = new CornerTransform(context, dip2px(context, dp));
        transformation.setExceptCorner(leftTop, rightTop, rightBottom, false);

        Glide.with(context)
                .load(imgUrl)
                .asBitmap()
                .skipMemoryCache(true)
                .transform(transformation)
                .into(imageView);
    }

    public static void setRoundedAndCropImage(Context context,String url, int cornerRadius,  ImageView imageView) {
        GlideRoundTransform glideRoundTransform;
        if (cornerRadius == 0) {
            glideRoundTransform = new GlideRoundTransform(context);
        } else {
            glideRoundTransform = new GlideRoundTransform(context, cornerRadius);
        }

        Glide.with(context)
                .load(url)
                .transform(glideRoundTransform)
                .into(imageView);

    }
    public static void toast(String s) {
        Toast.makeText(BYNAdSDK.getInstance().context, s, Toast.LENGTH_SHORT).show();

    }
}
