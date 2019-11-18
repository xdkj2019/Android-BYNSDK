package com.xidian.bynadsdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xidian.bynadsdk.BYNBaseActivity;
import com.xidian.bynadsdk.R;
import com.xidian.bynadsdk.utils.FinishActivityManager;
import com.xidian.bynadsdk.utils.SearchPreference;
import com.xidian.bynadsdk.utils.StatusBarUtil;
import com.xidian.bynadsdk.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.next.tagview.TagCloudView;

public class BYNAdSDKSearchActivity extends BYNBaseActivity {

    private EditText searchKeyET;
    private ImageView searchButtonIV;
    private ImageView editCleanIV;
    private TextView finishTV;
    private TextView cleanCacheTV;
    private ImageView cleanCalcheIV;
    private TagCloudView favinfoTCV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bynad_sdksearch);
        StatusBarUtil.setStatusBarFullTransparent(this);
        initView();
        initClick();
    }


    private void initView() {
        searchKeyET=(EditText)findViewById(R.id.activity_bynad_sdksearch_search_key_et);
        searchButtonIV=(ImageView)findViewById(R.id.activity_bynad_sdksearch_search_button_iv) ;
        editCleanIV=(ImageView)findViewById(R.id.activity_bynad_sdksearch_edittext_clean_iv);
        finishTV=(TextView)findViewById(R.id.activity_bynad_sdksearch_finish_tv);
        cleanCacheTV=(TextView)findViewById(R.id.activity_bynad_sdksearch_clean_cache_tv);
        cleanCalcheIV=(ImageView)findViewById(R.id.activity_bynad_sdksearch_clean_cache_iv);
        favinfoTCV=(TagCloudView)findViewById(R.id.activity_bynad_sdksearch_favinfo);
        cleanCacheTV.getPaint().setFakeBoldText(true);
    }
    private void initClick() {
        finishTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishActivityManager.getsManager().finishActivity();
            }
        });

        searchKeyET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null&&s.length()>0){
                    editCleanIV.setVisibility(View.VISIBLE);
                }else{
                    editCleanIV.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchKeyET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (null != event && KeyEvent.KEYCODE_ENTER == event.getKeyCode()) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            if(TextUtils.isEmpty(searchKeyET.getText().toString())){
                                Utils.toast("请输入合法的字符");
                                return true;
                            }
                            SearchPreference.getInstance(BYNAdSDKSearchActivity.this).setKey(searchKeyET.getText().toString());
                            startActivity(new Intent(BYNAdSDKSearchActivity.this, BYNAdSDKSearchResultActivity.class).putExtra("key", searchKeyET.getText().toString()));

                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });



        editCleanIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKeyET.setText("");
            }
        });
        cleanCalcheIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPreference.getInstance(BYNAdSDKSearchActivity.this).clean();
                favinfoTCV.removeAllViews();
            }
        });
        searchButtonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPreference.getInstance(BYNAdSDKSearchActivity.this).setKey(searchKeyET.getText().toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Set<String> set = SearchPreference.getInstance(this).getKeys();
        if (set != null && set.size() > 0) {
            final List<String> list = new ArrayList<>(set);
            TagCloudView tagCloudView8 = favinfoTCV;
            tagCloudView8.setTags(list);
            tagCloudView8.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
                @Override
                public void onTagClick(int i) {
                    startActivity(new Intent(BYNAdSDKSearchActivity.this, BYNAdSDKSearchResultActivity.class).putExtra("key",  list.get(i)));
                }
            });
        }
    }
}
