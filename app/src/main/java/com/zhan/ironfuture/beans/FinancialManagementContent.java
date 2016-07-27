package com.zhan.ironfuture.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/19.
 */
public class FinancialManagementContent implements Serializable {
    public long getAsset() {
        return asset;
    }

    public void setAsset(long asset) {
        this.asset = asset;
    }

    private long asset ;
}
