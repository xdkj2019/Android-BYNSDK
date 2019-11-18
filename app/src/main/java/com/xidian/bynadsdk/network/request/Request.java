package com.xidian.bynadsdk.network.request;


import com.xidian.bynadsdk.bean.ADSBean;
import com.xidian.bynadsdk.bean.GoodsSingleDetailBean;
import com.xidian.bynadsdk.bean.GoodsTopBean;
import com.xidian.bynadsdk.network.response.Response;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

/**
 * Created by Zaifeng on 2018/2/28.
 * 封装请求的接口
 */

public interface Request {

    public static String HOST = "http://sdk.test.daff9.cn";
   // public static String HOST = "http://sdk.biyingniao.com";
    @GET("/api/opensdk/ads")
    Observable<Response<ADSBean>> getads(@HeaderMap HashMap<String, Object> hashMap, @QueryMap HashMap<String, Object> map);

    @GET("/api/opensdk/goods/top")
    Observable<Response<GoodsTopBean>> goodstop(@HeaderMap HashMap<String, Object> hashMap, @QueryMap HashMap<String, Object> map);

    @GET("/api/opensdk/goods/search")
    Observable<Response<GoodsTopBean>> goodssearch(@HeaderMap HashMap<String, Object> hashMap, @QueryMap HashMap<String, Object> map);

    @GET("/api/opensdk/goods/detail")
    Observable<Response<GoodsSingleDetailBean>> goodsdetail(@HeaderMap HashMap<String, Object> hashMap, @QueryMap HashMap<String, Object> map);

    @GET("/api/opensdk/goods/similar")
    Observable<Response<GoodsTopBean>> goodssimilar(@HeaderMap HashMap<String, Object> hashMap, @QueryMap HashMap<String, Object> map);


}
