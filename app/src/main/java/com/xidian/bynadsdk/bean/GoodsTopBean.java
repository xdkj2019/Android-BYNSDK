package com.xidian.bynadsdk.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/11/10.
 */

public class GoodsTopBean {
    private List<GoodsDetailBean> items;
    private boolean has_next;
    private String top_update_time;

    public List<GoodsDetailBean> getItems() {
        return items;
    }

    public void setItems(List<GoodsDetailBean> items) {
        this.items = items;
    }

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }

    public String getTop_update_time() {
        return top_update_time;
    }

    public void setTop_update_time(String top_update_time) {
        this.top_update_time = top_update_time;
    }
}
