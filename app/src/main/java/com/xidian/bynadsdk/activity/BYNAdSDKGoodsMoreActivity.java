package com.xidian.bynadsdk.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.xidian.bynadsdk.BYNAdSDK;
import com.xidian.bynadsdk.R;
import com.xidian.bynadsdk.adapterholder.BYNAdSDKSearchResultGoodsAdapter;
import com.xidian.bynadsdk.bean.BYNAdSDKCustomerBean;
import com.xidian.bynadsdk.bean.BYNAdSDKGoodsDetailBean;
import com.xidian.bynadsdk.utils.FinishActivityManager;
import com.xidian.bynadsdk.utils.PullListener;
import com.xidian.bynadsdk.utils.StatusBarUtil;
import com.xidian.bynadsdk.utils.Utils;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;

public class BYNAdSDKGoodsMoreActivity extends AppCompatActivity {
    public RecyclerArrayAdapter goodsAdapter = new RecyclerArrayAdapter(this) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new BYNAdSDKSearchResultGoodsAdapter(parent);
        }
    };
    private EasyRecyclerView recyclerView;
    private LinearLayout backLayout;
    private TextView finishAllTV;
    private int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bynad_sdkgoods_more);
        StatusBarUtil.setStatusBarFullTransparent(this);
        initView();
        initClick();
        initProduct();
    }

    private void initView() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.activity_bynad_sdk_goods_more_recycler);
        backLayout = (LinearLayout) findViewById(R.id.activity_bynad_sdk_goods_more_back_layout);
        finishAllTV = (TextView) findViewById(R.id.activity_bynad_sdk_goods_more_finish_all_tv);
    }

    private void initClick() {
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishActivityManager.getsManager().finishActivity();
            }
        });
        finishAllTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishActivityManager.getsManager().finishAllActivity();
            }
        });
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
                startActivity(new Intent(BYNAdSDKGoodsMoreActivity.this, BYNAdSDKProductActivity.class)
                        .putExtra("item_id", bynAdSDKGoodsDetailBean.getItem_id())
                        .putExtra("activity_id", bynAdSDKGoodsDetailBean.getActivity_id()));
            }
        });

    }
    private void getData(){
        long l = System.currentTimeMillis() / 1000;
        CompositeDisposable mDisposable = new CompositeDisposable();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("t", l + "");
        hashMap.put("sign", Utils.md5Decode32( BYNAdSDK.getInstance().appSecret+"appkey="+BYNAdSDK.getInstance().appKey+"tm="+l+BYNAdSDK.getInstance().appSecret));
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", num);
        map.put("page_size", "20");
        if(!TextUtils.isEmpty(BYNAdSDKCustomerBean.DEVICEVALUE)){

        }
    }
}
