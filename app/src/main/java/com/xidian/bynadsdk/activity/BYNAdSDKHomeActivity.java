package com.xidian.bynadsdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.xidian.bynadsdk.BYNAdSDK;
import com.xidian.bynadsdk.BYNBaseActivity;
import com.xidian.bynadsdk.R;
import com.xidian.bynadsdk.utils.FinishActivityManager;
import com.xidian.bynadsdk.utils.MyLinearLayoutManager;
import com.xidian.bynadsdk.utils.StatusBarUtil;
import com.xidian.bynadsdk.utils.Utils;
import com.xidian.bynadsdk.adapterholder.BYNAdSDKHomeGoodsAdapter;
import com.xidian.bynadsdk.bean.GoodsDetailBean;
import com.xidian.bynadsdk.bean.GoodsTopBean;
import com.xidian.bynadsdk.network.NetWorkManager;
import com.xidian.bynadsdk.network.response.ResponseTransformer;
import com.xidian.bynadsdk.network.schedulers.SchedulerProvider;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BYNAdSDKHomeActivity extends BYNBaseActivity {

    private TextView updateTimeTv;
    private RelativeLayout searchLayout;
    private TextView searchTv;
    private LinearLayout backLayout;
    private EasyRecyclerView goodsListRecycler;
    private RelativeLayout bottomLayout;
    private ImageView goneIv;
    private TextView tutoialTv;
    public RecyclerArrayAdapter goodsAdapter = new RecyclerArrayAdapter(this) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new BYNAdSDKHomeGoodsAdapter(parent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bynad_sdkhome);
        StatusBarUtil.setStatusBarFullTransparent(this);
        initView();
        initData();
        initClick();
    }


    private void initView() {
        updateTimeTv = (TextView) findViewById(R.id.activity_bynad_sdkhome_update_time_textview);
        searchLayout = (RelativeLayout) findViewById(R.id.activity_bynad_sdkhome_search_layout);
        backLayout = (LinearLayout) findViewById(R.id.activity_bynad_sdkhome_back_layout);
        goodsListRecycler = (EasyRecyclerView) findViewById(R.id.activity_bynad_sdkhome_recycler);
        bottomLayout = (RelativeLayout)findViewById(R.id.activity_bynad_sdkhome_bottom_layout);
        goneIv = (ImageView)findViewById(R.id.activity_bynad_sdkhome_bottom_gone_iv);
        tutoialTv = (TextView)findViewById(R.id.activity_bynad_sdkhome_bottom_tutorial_title_tv);
        searchTv = (TextView)findViewById(R.id.activity_bynad_sdkhome_search_button_tv);
        tutoialTv.getPaint().setFakeBoldText(true);
        searchTv.getPaint().setFakeBoldText(true);
    }
    private void initData(){
        goodsListRecycler.setAdapter(goodsAdapter);
        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
        goodsListRecycler.setLayoutManager(linearLayoutManager);
        goodsAdapter.setNotifyOnChange(false);



        long l = System.currentTimeMillis() / 1000;
        CompositeDisposable mDisposable = new CompositeDisposable();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("t", l+"");
        hashMap.put("sign", Utils.md5Decode32(Utils.getVersion()+ BYNAdSDK.getInstance().appSecret+l+BYNAdSDK.getInstance().appSecret));
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page","1");
        map.put("page_size","20");
        Disposable subscribe = NetWorkManager.getRequest().goodstop(hashMap, map)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<GoodsTopBean>() {
                    @Override
                    public void accept(GoodsTopBean goodsTopBean) throws Exception {
                        goodsAdapter.addAll(goodsTopBean.getItems());
                        goodsAdapter.notifyDataSetChanged();
                        updateTimeTv.setText("更新时间："+goodsTopBean.getTop_update_time());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Utils.toast(throwable.getMessage());
                    }
                });
        mDisposable.add(subscribe);



    }

    private void initClick() {
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BYNAdSDKHomeActivity.this,BYNAdSDKSearchActivity.class));
            }
        });
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishActivityManager.getsManager().finishAllActivity();
            }
        });
        goneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomLayout.setVisibility(View.GONE);
            }
        });
        goodsAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GoodsDetailBean goodsDetailBean= (GoodsDetailBean) goodsAdapter.getItem(position);
                startActivity(new Intent(BYNAdSDKHomeActivity.this,BYNAdSDKProductActivity.class)
                        .putExtra("item_id",goodsDetailBean.getItem_id())
                        .putExtra("activity_id",goodsDetailBean.getActivity_id()));            }
        });
    }
}
