package com.xidian.bynadsdk.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/11/10.
 */

public class GoodsSingleDetailBean {
    /**
     * item_id : 3641879797
     * title : 简诺正宗土鸡蛋农家散养新鲜30枚天然纯农村柴鸡蛋草鸡蛋
     * recommend : 简诺正宗土鸡蛋农家散养新鲜30枚天然纯农村柴鸡蛋草鸡蛋
     * price : 12.90
     * month_sales : 3.6万
     * cover_image : https://t00img.yangkeduo.com/goods/images/2018-10-24/c9ac4c28fefe7abefd292f9c0e4a0381.jpeg
     * mall_id : 3
     * activity_type : 1
     * tk_rate : 20
     * shop_name : 斯维因水果生鲜
     * images : ["https://t00img.yangkeduo.com/goods/images/2018-10-24/15028bfd568beffb59971c787399fbdc.jpeg","https://t00img.yangkeduo.com/goods/images/2018-10-24/7ad880db545a3a6b324850c93f950ccd.jpeg","https://t00img.yangkeduo.com/goods/images/2018-10-24/4d6c644aea9559d02948a5f4e790238a.jpeg","https://t00img.yangkeduo.com/goods/images/2018-10-24/f9250c4acff329a103c8b0402a9072bb.jpeg"]
     * detail_images : []
     * dsr_info : {"descriptionMatch":4.7,"serviceAttitude":4.7,"deliverySpeed":4.7}
     * tkl :
     * mall_icon : https://img.daff9.cn/xidian/static/pin.png
     * coupon_money : 6.00
     * discount_price : 6.90
     * fl_commission : 赚0.43
     * activity_id :
     * coupon_explain_money :
     * coupon_starttime : 2019-05-30 00:00:00
     * coupon_endtime : 2019-06-30 23:59:59
     * coupon_click_url : https://mobile.yangkeduo.com/app.html?launch_url=duo_coupon_landing.html%3Fgoods_id%3D3641879797%26pid%3D1001496_52090526%26authDuoId%3D1031777%26cpsSign%3DCC_190530_1001496_52090526_3d7dac6010732ba6ecf960a36caf491c%26duoduo_type%3D2
     * need_oauth : true
     * oauth_url : http://www.biyingniao.com/oauth/taobao xxxx
     * detail_url : http://www.biyingniao.com/oauth/taobao xxxx
     * presale_deposit : 0
     * presale_discount_fee_text :
     * presale_end_time :
     * presale_start_time :
     * presale_tail_end_time :
     * presale_tail_start_time :
     * tag : ads
     */

    private String item_id;
    private String title;
    private String recommend;
    private String price;
    private String month_sales;
    private String cover_image;
    private int mall_id;
    private int activity_type;
    private int tk_rate;
    private String shop_name;
    private DsrInfoBean dsr_info;
    private String tkl;
    private String mall_icon;
    private String coupon_money;
    private String discount_price;
    private String fl_commission;
    private String activity_id;
    private String coupon_explain_money;
    private String coupon_starttime;
    private String coupon_endtime;
    private String coupon_click_url;
    private boolean need_oauth;
    private String oauth_url;
    private String detail_url;
    private int presale_deposit;
    private String presale_discount_fee_text;
    private String presale_end_time;
    private String presale_start_time;
    private String presale_tail_end_time;
    private String presale_tail_start_time;
    private String tag;
    private List<String> images;
    private List<?> detail_images;
    private String shop_image;

    public String getShop_image() {
        return shop_image;
    }

    public void setShop_image(String shop_image) {
        this.shop_image = shop_image;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMonth_sales() {
        return month_sales;
    }

    public void setMonth_sales(String month_sales) {
        this.month_sales = month_sales;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public int getMall_id() {
        return mall_id;
    }

    public void setMall_id(int mall_id) {
        this.mall_id = mall_id;
    }

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }

    public int getTk_rate() {
        return tk_rate;
    }

    public void setTk_rate(int tk_rate) {
        this.tk_rate = tk_rate;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public DsrInfoBean getDsr_info() {
        return dsr_info;
    }

    public void setDsr_info(DsrInfoBean dsr_info) {
        this.dsr_info = dsr_info;
    }

    public String getTkl() {
        return tkl;
    }

    public void setTkl(String tkl) {
        this.tkl = tkl;
    }

    public String getMall_icon() {
        return mall_icon;
    }

    public void setMall_icon(String mall_icon) {
        this.mall_icon = mall_icon;
    }

    public String getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(String coupon_money) {
        this.coupon_money = coupon_money;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getFl_commission() {
        return fl_commission;
    }

    public void setFl_commission(String fl_commission) {
        this.fl_commission = fl_commission;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getCoupon_explain_money() {
        return coupon_explain_money;
    }

    public void setCoupon_explain_money(String coupon_explain_money) {
        this.coupon_explain_money = coupon_explain_money;
    }

    public String getCoupon_starttime() {
        return coupon_starttime;
    }

    public void setCoupon_starttime(String coupon_starttime) {
        this.coupon_starttime = coupon_starttime;
    }

    public String getCoupon_endtime() {
        return coupon_endtime;
    }

    public void setCoupon_endtime(String coupon_endtime) {
        this.coupon_endtime = coupon_endtime;
    }

    public String getCoupon_click_url() {
        return coupon_click_url;
    }

    public void setCoupon_click_url(String coupon_click_url) {
        this.coupon_click_url = coupon_click_url;
    }

    public boolean isNeed_oauth() {
        return need_oauth;
    }

    public void setNeed_oauth(boolean need_oauth) {
        this.need_oauth = need_oauth;
    }

    public String getOauth_url() {
        return oauth_url;
    }

    public void setOauth_url(String oauth_url) {
        this.oauth_url = oauth_url;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public int getPresale_deposit() {
        return presale_deposit;
    }

    public void setPresale_deposit(int presale_deposit) {
        this.presale_deposit = presale_deposit;
    }

    public String getPresale_discount_fee_text() {
        return presale_discount_fee_text;
    }

    public void setPresale_discount_fee_text(String presale_discount_fee_text) {
        this.presale_discount_fee_text = presale_discount_fee_text;
    }

    public String getPresale_end_time() {
        return presale_end_time;
    }

    public void setPresale_end_time(String presale_end_time) {
        this.presale_end_time = presale_end_time;
    }

    public String getPresale_start_time() {
        return presale_start_time;
    }

    public void setPresale_start_time(String presale_start_time) {
        this.presale_start_time = presale_start_time;
    }

    public String getPresale_tail_end_time() {
        return presale_tail_end_time;
    }

    public void setPresale_tail_end_time(String presale_tail_end_time) {
        this.presale_tail_end_time = presale_tail_end_time;
    }

    public String getPresale_tail_start_time() {
        return presale_tail_start_time;
    }

    public void setPresale_tail_start_time(String presale_tail_start_time) {
        this.presale_tail_start_time = presale_tail_start_time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<?> getDetail_images() {
        return detail_images;
    }

    public void setDetail_images(List<?> detail_images) {
        this.detail_images = detail_images;
    }

    public static class DsrInfoBean {
        /**
         * descriptionMatch : 4.7
         * serviceAttitude : 4.7
         * deliverySpeed : 4.7
         */

        private String descriptionMatch;
        private String serviceAttitude;
        private String deliverySpeed;

        public String getDescriptionMatch() {
            return descriptionMatch;
        }

        public void setDescriptionMatch(String descriptionMatch) {
            this.descriptionMatch = descriptionMatch;
        }

        public String getServiceAttitude() {
            return serviceAttitude;
        }

        public void setServiceAttitude(String serviceAttitude) {
            this.serviceAttitude = serviceAttitude;
        }

        public String getDeliverySpeed() {
            return deliverySpeed;
        }

        public void setDeliverySpeed(String deliverySpeed) {
            this.deliverySpeed = deliverySpeed;
        }
    }

}
