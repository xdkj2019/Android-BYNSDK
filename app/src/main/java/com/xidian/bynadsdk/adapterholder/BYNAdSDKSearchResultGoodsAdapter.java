package com.xidian.bynadsdk.adapterholder;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.xidian.bynadsdk.R;
import com.xidian.bynadsdk.utils.Utils;
import com.xidian.bynadsdk.bean.GoodsDetailBean;


/**
 * Created by Administrator on 2019/11/7.
 */

public class BYNAdSDKSearchResultGoodsAdapter extends BaseViewHolder<GoodsDetailBean> {
    private ImageView image;
    private ImageView iconIv;
    private TextView titleTv;
    private TextView shopNameTv;
    private TextView describeTv;
    private TextView vouchersTv;
    private TextView couponAterPriceTv;
    private TextView originalPriceTv;
    private TextView discountTv;
    private TextView salesTv;
    private LinearLayout vouchersLL;

    public BYNAdSDKSearchResultGoodsAdapter(ViewGroup itemView) {
        super(itemView, R.layout.bynad_sdksearch_result_goods_adapter_layout);
        image = (ImageView)$(R.id.bynad_sdksearch_result_goods_item_image);
        iconIv = (ImageView)$(R.id.bynad_sdksearch_result_goods_item_source_icon_image);
        titleTv = (TextView)$(R.id.bynad_sdksearch_result_goods_item_title_textview);
        shopNameTv = (TextView)$(R.id.bynad_sdksearch_result_goods_item_shop_name_textview);
        describeTv = (TextView)$(R.id.bynad_sdksearch_result_goods_item_describe_textview);
        vouchersTv = (TextView)$(R.id.bynad_sdksearch_result_goods_item_vouchers_textview);
        couponAterPriceTv = (TextView)$(R.id.bynad_sdksearch_result_goods_item_coupon_after_price_tv);
        originalPriceTv = (TextView)$(R.id.bynad_sdksearch_result_goods_item_original_price_tv);
        discountTv = (TextView)$(R.id.bynad_sdksearch_result_goods_item_discount_tv);
        salesTv = (TextView)$(R.id.bynad_sdksearch_result_goods_item_sales_tv);
        vouchersLL=(LinearLayout)$(R.id.bynad_sdksearch_result_goods_item_vouchers_layout);
    }
    @Override
    public void setData(GoodsDetailBean data) {
        super.setData(data);
        originalPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        titleTv.getPaint().setFakeBoldText(true);
        couponAterPriceTv.getPaint().setFakeBoldText(true);
        discountTv.getPaint().setFakeBoldText(true);
        //  Utils.setRoundGlide(getContext(),4,false,false,false,false,"http://img.haodanku.com/0_602041258520_1567594515.jpg",image);
        Utils.setRoundedAndCropImage(getContext(),data.getCover_image(),4,image);
        Utils.setRoundedAndCropImage(getContext(),data.getMall_icon(),0,iconIv);

        titleTv.setText(data.getTitle());
        shopNameTv.setText(data.getShop_name());
        describeTv.setText(data.getTag());
        if(!TextUtils.isEmpty(data.getDiscount_text())){
            discountTv.setVisibility(View.VISIBLE);
            discountTv.setText(data.getDiscount_text());
        }else{
            discountTv.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(data.getCoupon_money())&&Double.valueOf(data.getCoupon_money())>0){
            vouchersLL.setVisibility(View.VISIBLE);
            vouchersTv.setText(data.getCoupon_money()+"元");
        }else{
            vouchersLL.setVisibility(View.INVISIBLE);
        }
        couponAterPriceTv.setText(data.getDiscount_price());
        originalPriceTv.setText("¥"+data.getPrice());

        if(!TextUtils.isEmpty(data.getMonth_sales())&&Double.valueOf(data.getMonth_sales())>0){
            salesTv.setVisibility(View.VISIBLE);
            salesTv.setText("已售"+data.getMonth_sales()+"件");
        }else{
            salesTv.setVisibility(View.GONE);
        }



    }
}
