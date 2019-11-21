package com.xidian.bynadsdk.network.Exception;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


/**
 * Created by Zaifeng on 2018/2/28.
 * 本地异常处理。包括解析异常等其他异常
 */

public class CustomException {

    /**
     * 未知错误
     */
    public static final int UNKNOWN = 10000;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 10003;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 10004;


    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                || e instanceof NullPointerException
                || e instanceof NumberFormatException) {
            //解析错误
            ex = new ApiException(PARSE_ERROR, e.getMessage());
            Log.e("BYNSDKNetError","错误码:"+PARSE_ERROR+"；错误信息："+e.getMessage());
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage());
            Log.e("BYNSDKNetError","错误码:"+NETWORK_ERROR+"；错误信息："+e.getMessage());
            return ex;
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {

            //连接错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage());
            Log.e("BYNSDKNetError","错误码:"+NETWORK_ERROR+"；错误信息："+e.getMessage());
            return ex;
        } else {
            //未知错误
            ex = new ApiException(UNKNOWN, e.getMessage());
            Log.e("BYNSDKNetError","错误码:"+UNKNOWN+"；错误信息："+e.getMessage());
            return ex;
        }
    }
}
