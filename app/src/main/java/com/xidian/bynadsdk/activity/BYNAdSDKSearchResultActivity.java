package com.xidian.bynadsdk.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.xidian.bynadsdk.BYNAdSDK;
import com.xidian.bynadsdk.BYNBaseActivity;
import com.xidian.bynadsdk.R;
import com.xidian.bynadsdk.utils.FinishActivityManager;
import com.xidian.bynadsdk.utils.PullListener;
import com.xidian.bynadsdk.utils.StatusBarUtil;
import com.xidian.bynadsdk.utils.Utils;
import com.xidian.bynadsdk.adapterholder.BYNAdSDKSearchResultGoodsAdapter;
import com.xidian.bynadsdk.bean.BYNAdSDKGoodsDetailBean;
import com.xidian.bynadsdk.bean.BYNAdSDKGoodsTopBean;
import com.xidian.bynadsdk.network.NetWorkManager;
import com.xidian.bynadsdk.network.response.ResponseTransformer;
import com.xidian.bynadsdk.network.schedulers.SchedulerProvider;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BYNAdSDKSearchResultActivity extends BYNBaseActivity implements View.OnClickListener {
    private String key;
    private TextView keyTV, finishTV, comprehensiveTV, salesTV, priceTV, screeningTV;
    private ImageView cleanIV, searchButtonIV, salesIV, priceIV, screeningIV;
    private LinearLayout comprehensiveLayout, salesLayout, priceLayout, screeningLayout;
    private CheckBox preferentialCB;
    private EasyRecyclerView recyclerView;
    private View mask;
    private int num = 1;
    private String sort = "";//排序
    private boolean has_coupon = false;//是都查询有权
    private String start_price = "";
    private String end_price = "";
    public RecyclerArrayAdapter goodsAdapter = new RecyclerArrayAdapter(this) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new BYNAdSDKSearchResultGoodsAdapter(parent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bynad_sdksearch_result);
        StatusBarUtil.setStatusBarFullTransparent(this);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        initView();
        initProduct();
        initClick();
    }


    private void initProduct() {
        Utils.initListView(this, recyclerView, new DividerDecoration(Color.parseColor("#ffffff"), 10), goodsAdapter, new PullListener() {
            @Override
            public void onRefresh() {
                num = 1;
                getData();
            }

            @Override
            public void onLoadMore() {
                recyclerView.setRefreshing(false);
                if (num == 1) {
                    return;
                }
                getData();
            }
        }, new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                BYNAdSDKGoodsDetailBean bynAdSDKGoodsDetailBean = (BYNAdSDKGoodsDetailBean) goodsAdapter.getItem(position);
                startActivity(new Intent(BYNAdSDKSearchResultActivity.this,BYNAdSDKProductActivity.class)
                        .putExtra("item_id", bynAdSDKGoodsDetailBean.getItem_id())
                        .putExtra("activity_id", bynAdSDKGoodsDetailBean.getActivity_id()));            }
        });




    }

    public void getData() {
        long l = System.currentTimeMillis() / 1000;
        CompositeDisposable mDisposable = new CompositeDisposable();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("t", l + "");
        hashMap.put("sign", Utils.md5Decode32( BYNAdSDK.getInstance().appSecret+"appkey="+BYNAdSDK.getInstance().appKey+"tm="+l+BYNAdSDK.getInstance().appSecret));
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", num);
        map.put("page_size", "20");
        if (!TextUtils.isEmpty(sort)) {
            map.put("sort", sort);
        }
        if (!TextUtils.isEmpty(start_price)) {
            map.put("start_price", start_price);
        }
        if (!TextUtils.isEmpty(end_price)) {
            map.put("end_price", end_price);
        }
        map.put("has_coupon", has_coupon);

        Disposable subscribe = NetWorkManager.getRequest().goodssearch(hashMap, map)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<BYNAdSDKGoodsTopBean>() {
                    @Override
                    public void accept(BYNAdSDKGoodsTopBean goodsTopBean) throws Exception {
                        if (num == 1) {
                            if (goodsAdapter != null) {
                                goodsAdapter.clear();
                            }
                        }
                        if(goodsTopBean.getItems()==null||goodsTopBean.getItems().size()==0){
                            goodsAdapter.stopMore();
                        }else{
                            num=num+1;
                            goodsAdapter.addAll(goodsTopBean.getItems());
                            goodsAdapter.notifyDataSetChanged();
                            if(!goodsTopBean.isHas_next()){
                                goodsAdapter.stopMore();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
        mDisposable.add(subscribe);
    }

    private void initClick() {
        preferentialCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                has_coupon=isChecked;
                num=1;
                getData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.activity_bynad_sdksearch_result_finish_tv) {
            FinishActivityManager.getsManager().finishActivity();
            FinishActivityManager.getsManager().finishActivity(BYNAdSDKSearchActivity.class);
        } else if (i == R.id.activity_bynad_sdksearch_result_edittext_clean_iv) {
            FinishActivityManager.getsManager().finishActivity();
        } else if (i == R.id.activity_bynad_sdksearch_result_comprehensive_layout) {
            num = 1;
            sort="";
            comprehensiveTV.setTextColor(0xffF53C25);
            salesTV.setTextColor(0xff666666);
            priceTV.setTextColor(0xff666666);
            salesIV.setImageResource(R.mipmap.bynad_sdk_check);
            priceIV.setImageResource(R.mipmap.bynad_sdk_check);
            getData();
        } else if (i == R.id.activity_bynad_sdksearch_result_sales_layout) {
            num=1;
            comprehensiveTV.setTextColor(0xff666666);
            salesTV.setTextColor(0xffF53C25);
            priceTV.setTextColor(0xff666666);

            priceIV.setImageResource(R.mipmap.bynad_sdk_check);
            if(sort.equals("month_sales_des")){
                sort="month_sales_asc";
                salesIV.setImageResource(R.mipmap.bynad_sdk_checked);
            }else{
                sort="month_sales_des";
                salesIV.setImageResource(R.mipmap.bynad_sdk_checked_des);
            }
            getData();
        } else if (i == R.id.activity_bynad_sdksearch_result_price_layout) {
            num=1;
            comprehensiveTV.setTextColor(0xff666666);
            salesTV.setTextColor(0xff666666);
            priceTV.setTextColor(0xffF53C25);
            salesIV.setImageResource(R.mipmap.bynad_sdk_check);

            if(sort.equals("month_sales_des")){
                sort="month_sales_asc";
                priceIV.setImageResource(R.mipmap.bynad_sdk_checked);
            }else{
                sort="month_sales_des";
                priceIV.setImageResource(R.mipmap.bynad_sdk_checked_des);
            }
            getData();
        } else if (i == R.id.activity_bynad_sdksearch_result_screening_layout) {
            openPopup();
            screeningTV.setTextColor(0xffF53C25);
            screeningIV.setImageResource(R.mipmap.bynad_sdk_check_shaixuan);
        }
    }


    private void initView() {
        keyTV = (TextView) findViewById(R.id.activity_bynad_sdksearch_result_search_key_et);
        finishTV = (TextView) findViewById(R.id.activity_bynad_sdksearch_result_finish_tv);
        comprehensiveTV = (TextView) findViewById(R.id.activity_bynad_sdksearch_result_comprehensive_tv);
        salesTV = (TextView) findViewById(R.id.activity_bynad_sdksearch_result_sales_tv);
        priceTV = (TextView) findViewById(R.id.activity_bynad_sdksearch_result_price_tv);
        screeningTV = (TextView) findViewById(R.id.activity_bynad_sdksearch_result_screening_tv);
        searchButtonIV = (ImageView) findViewById(R.id.activity_bynad_sdksearch_search_result_button_iv);
        cleanIV = (ImageView) findViewById(R.id.activity_bynad_sdksearch_result_edittext_clean_iv);
        salesIV = (ImageView) findViewById(R.id.activity_bynad_sdksearch_result_sales_iv);
        priceIV = (ImageView) findViewById(R.id.activity_bynad_sdksearch_result_price_iv);
        screeningIV = (ImageView) findViewById(R.id.activity_bynad_sdksearch_result_screening_iv);
        comprehensiveLayout = (LinearLayout) findViewById(R.id.activity_bynad_sdksearch_result_comprehensive_layout);
        salesLayout = (LinearLayout) findViewById(R.id.activity_bynad_sdksearch_result_sales_layout);
        priceLayout = (LinearLayout) findViewById(R.id.activity_bynad_sdksearch_result_price_layout);
        screeningLayout = (LinearLayout) findViewById(R.id.activity_bynad_sdksearch_result_screening_layout);
        preferentialCB = (CheckBox) findViewById(R.id.activity_bynad_sdksearch_result_preferential_cb);
        recyclerView = (EasyRecyclerView) findViewById(R.id.activity_bynad_sdksearch_result_recycler);
        mask = findViewById(R.id.activity_bynad_sdksearch_result_mask);
        mask.setVisibility(View.GONE);
        keyTV.setText(key);
        finishTV.setOnClickListener(this);
        cleanIV.setOnClickListener(this);
        comprehensiveLayout.setOnClickListener(this);
        salesLayout.setOnClickListener(this);
        priceLayout.setOnClickListener(this);
        screeningLayout.setOnClickListener(this);
    }

    public void openPopup() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.bynad_sdk_search_result_popup_layout, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        EditText lowse = (EditText) contentView.findViewById(R.id.bynad_sdk_search_result_popup_price_lowse_edittext);
        EditText highest = (EditText) contentView.findViewById(R.id.bynad_sdk_search_result_popup_price_highest_edittext);
        TextView reset = (TextView) contentView.findViewById(R.id.bynad_sdk_search_result_popup_price_reset_tv);
        TextView sure = (TextView) contentView.findViewById(R.id.bynad_sdk_search_result_popup_price_sure_tv);
        // View alpha = contentView.findViewById(R.id.bynad_sdk_search_result_popup_price_button_layout);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        if(!TextUtils.isEmpty(start_price)){
            lowse.setText(start_price);
        }
        if(!TextUtils.isEmpty(end_price)){
            highest.setText(end_price);
        }
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mask.setVisibility(View.GONE);
                screeningTV.setTextColor(0xff666666);
                screeningIV.setImageResource(R.mipmap.bynad_sdk_checked_shaixuan);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_price="";
                end_price="";
                lowse.setText("");
                highest.setText("");
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(lowse.getText().toString())){
                    start_price=lowse.getText().toString();
                }else{
                    start_price="";
                }
                if(!TextUtils.isEmpty(highest.getText().toString())){
                    end_price=highest.getText().toString();
                }else {
                    end_price="";
                }
                num=1;
                getData();
                popupWindow.dismiss();
            }
        });
        mask.setVisibility(View.VISIBLE);
        popupWindow.showAsDropDown(screeningLayout);
    }
}
