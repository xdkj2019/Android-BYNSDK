package com.xidian.bynadsdk.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.xidian.bynadsdk.BYNAdSDK;
import com.xidian.bynadsdk.BYNBaseActivity;
import com.xidian.bynadsdk.R;
import com.xidian.bynadsdk.utils.ADInfo;
import com.xidian.bynadsdk.utils.FinishActivityManager;
import com.xidian.bynadsdk.utils.ImageCycleView;
import com.xidian.bynadsdk.utils.StatusBarUtil;
import com.xidian.bynadsdk.utils.Utils;
import com.xidian.bynadsdk.adapterholder.BYNAdSDKSimarGoodsAdapter;
import com.xidian.bynadsdk.bean.GoodsDetailBean;
import com.xidian.bynadsdk.bean.GoodsSingleDetailBean;
import com.xidian.bynadsdk.bean.GoodsTopBean;
import com.xidian.bynadsdk.network.NetWorkManager;
import com.xidian.bynadsdk.network.response.ResponseTransformer;
import com.xidian.bynadsdk.network.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.alibaba.baichuan.trade.biz.applink.adapter.AlibcFailModeType.AlibcNativeFailModeJumpH5;


public class BYNAdSDKProductActivity extends BYNBaseActivity {
    private LinearLayout backActivityLL;
    private TextView finishAllActivityTV,goodsTitleTV,couponAfterPriceTV,originalPriceTV,salesTV,couponValueTV;
    private TextView period0fValidity_TV,couponReceiveButtonTV,recommendedReasonTV,shopNameTV,shopMassgeFirstTV,shopMassgeSecondTV,shopMassgeThirdTV;
    private TextView goodsBuyButtonTV;
    private ImageCycleView goodsImages;
    private ImageView mallIconIV,shopIconIV;
    private LinearLayout goodsCouponLayout,goHomeLayout,goodsSimilarLayout;
    private RelativeLayout recommendedReasonLayout;
    private EasyRecyclerView goodsSimailarRecycler;
    private View firstSegmentation,secondSegmentation,thirdSegmentation,fourthSegmentation;
    private String item_id;
    private String activity_id;
    private WebView webView;
    private GoodsSingleDetailBean detailBean;
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            // 使用Glide对图片进行加装！
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setTag(null);
            Utils.setRoundedAndCropImage(BYNAdSDKProductActivity.this,imageURL,0,imageView);

        }
    };
    public RecyclerArrayAdapter simarGoodsAdapter = new RecyclerArrayAdapter(this) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new BYNAdSDKSimarGoodsAdapter(parent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bynad_sdkproduct);
        StatusBarUtil.setStatusBarFullTransparent(this);
        Intent intent = getIntent();
        item_id=intent.getStringExtra("item_id");
        activity_id=intent.getStringExtra("activity_id");
        initView();
        initClick();
        initData();
    }
    private void initData(){
        long l = System.currentTimeMillis() / 1000;
        CompositeDisposable mDisposable = new CompositeDisposable();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("t", l+"");
        hashMap.put("sign", Utils.md5Decode32(Utils.getVersion()+ BYNAdSDK.getInstance().appSecret+l+BYNAdSDK.getInstance().appSecret));
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("item_id",item_id);
        if(!TextUtils.isEmpty(activity_id)){
            map.put("activity_id",activity_id);
        }
        Disposable subscribe = NetWorkManager.getRequest().goodsdetail(hashMap, map)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<GoodsSingleDetailBean>() {
                    @Override
                    public void accept(GoodsSingleDetailBean goodsSingleDetailBean) throws Exception {
                        detailBean=goodsSingleDetailBean;
                        if (goodsSingleDetailBean.getImages()!= null) {
                            ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
                            if(goodsSingleDetailBean.getImages().size()>0){
                                List<String> images = goodsSingleDetailBean.getImages();
                                for (int i = 0; i < images.size(); i++) {
                                    ADInfo info = new ADInfo();
                                    info.setUrl(images.get(i));
                                    infos.add(info);
                                    goodsImages.pushImageCycle();
                                }
                            }else{
                                ADInfo info = new ADInfo();
                                info.setUrl(goodsSingleDetailBean.getCover_image());
                                infos.add(info);
                                goodsImages.pushImageCycle();
                            }
                            goodsImages.setImageResources(infos, mAdCycleViewListener, Gravity.CENTER);
                        }
                        Utils.setRoundedAndCropImage(BYNAdSDKProductActivity.this,goodsSingleDetailBean.getMall_icon(),0,mallIconIV);
                        Utils.setRoundedAndCropImage(BYNAdSDKProductActivity.this,goodsSingleDetailBean.getShop_image(),4,shopIconIV);

                        goodsTitleTV.setText("\u3000\u0020"+goodsSingleDetailBean.getTitle());
                        couponAfterPriceTV.setText(goodsSingleDetailBean.getDiscount_price()+"");
                        originalPriceTV.setText("¥"+goodsSingleDetailBean.getPrice());
                        if(!TextUtils.isEmpty(goodsSingleDetailBean.getMonth_sales())){
                            salesTV.setVisibility(View.VISIBLE);
                            salesTV.setText("已售"+goodsSingleDetailBean.getMonth_sales());
                        }else{
                            salesTV.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(goodsSingleDetailBean.getCoupon_money())&&Double.valueOf(goodsSingleDetailBean.getCoupon_money())>0){
                            goodsCouponLayout.setVisibility(View.VISIBLE);
                            couponValueTV.setText(goodsSingleDetailBean.getCoupon_money());
                            period0fValidity_TV.setText(goodsSingleDetailBean.getCoupon_explain_money());
                        }else{
                            goodsCouponLayout.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(goodsSingleDetailBean.getRecommend())){
                            recommendedReasonLayout.setVisibility(View.VISIBLE);
                            secondSegmentation.setVisibility(View.VISIBLE);
                            recommendedReasonTV.setText("\u3000\u3000\u3000\u3000\u3000\u3000"+goodsSingleDetailBean.getRecommend());
                        }else{
                            recommendedReasonLayout.setVisibility(View.GONE);
                            secondSegmentation.setVisibility(View.GONE);
                        }
                        // TODO: 2019/11/10 缺少店铺图片
                        Utils.setRoundedAndCropImage(BYNAdSDKProductActivity.this,goodsSingleDetailBean.getShop_image(),4,shopIconIV);
                        Utils.setRoundedAndCropImage(BYNAdSDKProductActivity.this,goodsSingleDetailBean.getMall_icon(),4,mallIconIV);
                        shopNameTV.setText(goodsSingleDetailBean.getShop_name());
                        if(!TextUtils.isEmpty(goodsSingleDetailBean.getDsr_info().getDescriptionMatch())){
                            shopMassgeFirstTV.setVisibility(View.VISIBLE);
                            shopMassgeFirstTV.setText("宝贝描述 " + goodsSingleDetailBean.getDsr_info().getDescriptionMatch());
                        }else{
                            shopMassgeFirstTV.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(goodsSingleDetailBean.getDsr_info().getServiceAttitude())){
                            shopMassgeSecondTV.setVisibility(View.VISIBLE);
                            shopMassgeSecondTV.setText("服务描述 " +goodsSingleDetailBean.getDsr_info().getServiceAttitude());
                        }else{
                            shopMassgeSecondTV.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(goodsSingleDetailBean.getDsr_info().getDeliverySpeed())){
                            shopMassgeThirdTV.setVisibility(View.VISIBLE);
                            shopMassgeThirdTV.setText("处理速度 " +goodsSingleDetailBean.getDsr_info().getDeliverySpeed());
                        }else{
                            shopMassgeThirdTV.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(goodsSingleDetailBean.getDetail_url())){
                            webView.loadUrl(goodsSingleDetailBean.getDetail_url());
                        }







                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Utils.toast(throwable.getMessage());
                    }
                });

        mDisposable.add(subscribe);

        CompositeDisposable mDisposablesimilar = new CompositeDisposable();
        Disposable subscribesimilar = NetWorkManager.getRequest().goodssimilar(hashMap, map)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<GoodsTopBean>() {
                    @Override
                    public void accept(GoodsTopBean goodsTopBean) throws Exception {
                        if(goodsTopBean.getItems()!=null&&goodsTopBean.getItems().size()>0){
                            goodsSimilarLayout.setVisibility(View.VISIBLE);
                            fourthSegmentation.setVisibility(View.VISIBLE);
                            simarGoodsAdapter.addAll(goodsTopBean.getItems());
                            simarGoodsAdapter.notifyDataSetChanged();
                        }else{
                            goodsSimilarLayout.setVisibility(View.GONE);
                            fourthSegmentation.setVisibility(View.GONE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Utils.toast(throwable.getMessage());
                    }
                });

        mDisposablesimilar.add(subscribesimilar);

    }
    private void initClick() {
        backActivityLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishActivityManager.getsManager().finishActivity();
            }
        });
        finishAllActivityTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishActivityManager.getsManager().finishAllActivity();
            }
        });
        goHomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishActivityManager.getsManager().finishAllActivity();
                startActivity(new Intent(BYNAdSDKProductActivity.this, BYNAdSDKHomeActivity.class));
            }
        });
        goodsBuyButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailBean==null){
                    Utils.toast("商品信息有误，请联系管理员");
                    return;
                }
                if(TextUtils.isEmpty(detailBean.getCoupon_click_url())){
                    Utils.toast("优惠券链接不存在");
                    return;
                }
                //一期不做授权
             //   if(detailBean.isNeed_oauth()){
             //       auth(detailBean.getOauth_url());
              //  }else{
                    buyOrReceive(detailBean.getCoupon_click_url());
              //  }
            }
        });
        couponReceiveButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailBean==null){
                    Utils.toast("商品信息有误，请联系管理员");
                    return;
                }
                if(TextUtils.isEmpty(detailBean.getCoupon_click_url())){
                    Utils.toast("优惠券链接不存在");
                    return;
                }

               // if(detailBean.isNeed_oauth()){
                 //   auth(detailBean.getOauth_url());
                //}else{
                    buyOrReceive(detailBean.getCoupon_click_url());
             //   }
            }
        });

        simarGoodsAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GoodsDetailBean goodsDetailBean= (GoodsDetailBean) simarGoodsAdapter.getItem(position);
                // FinishActivityManager.getsManager().finishActivity();
                startActivity(new Intent(BYNAdSDKProductActivity.this,BYNAdSDKProductActivity.class)
                        .putExtra("item_id",goodsDetailBean.getItem_id())
                        .putExtra("activity_id",goodsDetailBean.getActivity_id()));


            }
        });
    }
    public void auth(String url){
        // TODO: 2019/11/11 缺少授权转链,是否需要判断商品类型（拼多多或者淘宝）
    }

    public void buyOrReceive(String url){
        AlibcShowParams showParams = new AlibcShowParams();
        showParams.setOpenType(OpenType.Native);
        showParams.setBackUrl("alisdk://");
        showParams.setNativeOpenFailedMode(AlibcNativeFailModeJumpH5);
        //自定义参数
        Map<String, String> trackParams = new HashMap<>();
        trackParams.put("isv_code", "appisvcode");
        trackParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
        AlibcTrade.openByUrl(BYNAdSDKProductActivity.this, "", url,
                null, new WebViewClient(), new WebChromeClient(), showParams, new AlibcTaokeParams("", "", "")
                , trackParams, new AlibcTradeCallback() {
                    @Override
                    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                    }

                    @Override
                    public void onFailure(int i, String s) {
                    }
                });
    }





    private void initView() {
        backActivityLL=(LinearLayout)findViewById(R.id.activity_bynad_sdkproduct_back_layout);
        goodsCouponLayout=(LinearLayout)findViewById(R.id.activity_bynad_sdkproduct_goods_coupon_layout);
        goHomeLayout=(LinearLayout)findViewById(R.id.activity_bynad_sdkproduct_goods_go_home_layout);
        goodsSimilarLayout=(LinearLayout)findViewById(R.id.activity_bynad_sdkproduct_goods_similar_layout);

        finishAllActivityTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_finish_all_tv);
        goodsTitleTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_goods_title);
        couponAfterPriceTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_coupon_after_price_tv);
        originalPriceTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_original_price_tv);
        salesTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_sales_tv);
        couponValueTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_goods_coupon_value_tv);
        period0fValidity_TV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_goods_coupon_period_of_validity_tv);
        couponReceiveButtonTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_goods_coupon_receive_button_tv);
        recommendedReasonTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_goods_recommended_reason_tv);
        shopNameTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_shop_name_tv);
        shopMassgeFirstTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_shop_massge_first_tv);
        shopMassgeSecondTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_shop_massge_second_tv);
        shopMassgeThirdTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_shop_massge_third_tv);
        goodsBuyButtonTV=(TextView)findViewById(R.id.activity_bynad_sdkproduct_goods_buy_button_tv);

        goodsImages=(ImageCycleView)findViewById(R.id.activity_bynad_sdkproduct_cycle_icv);
        mallIconIV=(ImageView)findViewById(R.id.activity_bynad_sdkproduct_mall_icon_iv);
        shopIconIV=(ImageView)findViewById(R.id.activity_bynad_sdkproduct_shop_icon_iv);

        recommendedReasonLayout=(RelativeLayout)findViewById(R.id.activity_bynad_sdkproduct_goods_recommended_reason_layout);
        goodsSimailarRecycler=(EasyRecyclerView)findViewById(R.id.activity_bynad_sdkproduct_goods_similar_recycler);

        firstSegmentation = findViewById(R.id.activity_bynad_sdkproduct_goods_segmentation_first);
        secondSegmentation = findViewById(R.id.activity_bynad_sdkproduct_goods_segmentation_second);
        thirdSegmentation = findViewById(R.id.activity_bynad_sdkproduct_goods_segmentation_third);
        fourthSegmentation = findViewById(R.id.activity_bynad_sdkproduct_goods_segmentation_fourth);

        webView=(WebView)findViewById(R.id.activity_bynad_sdkproduct_goods_details_webview);
        webView.getSettings().setDomStorageEnabled(true);
        // 设置支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 启动缓存
        webView.getSettings().setAppCacheEnabled(true);
        // 设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        goodsTitleTV.getPaint().setFakeBoldText(true);
        couponAfterPriceTV.getPaint().setFakeBoldText(true);
        originalPriceTV.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        couponValueTV.getPaint().setFakeBoldText(true);
        couponReceiveButtonTV.getPaint().setFakeBoldText(true);
        goodsBuyButtonTV.getPaint().setFakeBoldText(true);

        goodsSimailarRecycler.setAdapter(simarGoodsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        goodsSimailarRecycler.setLayoutManager(linearLayoutManager);
        simarGoodsAdapter.setNotifyOnChange(false);



    }
}

