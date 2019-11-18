package com.xidian.bynadsdk.adapterholder;

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
 * Created by Administrator on 2019/11/11.
 */

public class BYNAdSDKSimarGoodsAdapter extends BaseViewHolder<GoodsDetailBean> {
    private ImageView goodsImage;
    private TextView titleTv,vouchersTv,couponAterPriceTv,discountTv;
    private LinearLayout vouchersLL;
    public BYNAdSDKSimarGoodsAdapter(ViewGroup itemView) {
        super(itemView, R.layout.bynad_sdk_simargoods_layout);
        goodsImage = (ImageView)$(R.id.bynad_sdk_simargoods_item_image);
        discountTv = (TextView)$(R.id.bynad_sdk_simargoods_item_discount_tv);
        couponAterPriceTv = (TextView)$(R.id.bynad_sdk_simargoods_item_coupon_after_price_tv);
        titleTv = (TextView)$(R.id.bynad_sdk_simargoods_item_title);
        vouchersTv = (TextView)$(R.id.bynad_sdk_simargoods_item_vouchers_textview);
        vouchersLL=(LinearLayout)$(R.id.bynad_sdk_simargoods_item_vouchers_layout);
    }

    @Override
    public void setData(GoodsDetailBean data) {
        super.setData(data);
        // TODO: 2019/11/10 折扣字段

        if(!TextUtils.isEmpty(data.getDiscount_text())){
            discountTv.setVisibility(View.VISIBLE);
            discountTv.setText(data.getDiscount_text());
        }else{
            discountTv.setVisibility(View.GONE);
        }
        titleTv.getPaint().setFakeBoldText(true);
        couponAterPriceTv.getPaint().setFakeBoldText(true);
        discountTv.getPaint().setFakeBoldText(true);
        Utils.setRoundedAndCropImage(getContext(),data.getCover_image(),4,goodsImage);
        if(!TextUtils.isEmpty(data.getCoupon_money())&&Double.valueOf(data.getCoupon_money())>0){
            vouchersLL.setVisibility(View.VISIBLE);
            vouchersTv.setText(data.getCoupon_money()+"元");
        }else{
            vouchersLL.setVisibility(View.INVISIBLE);
        }
        titleTv.setText(data.getTitle());
        couponAterPriceTv.setText(data.getDiscount_price());


    }
}
