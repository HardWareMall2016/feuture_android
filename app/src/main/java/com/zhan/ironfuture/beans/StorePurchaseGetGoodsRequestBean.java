package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/24.
 */
public class StorePurchaseGetGoodsRequestBean extends BaseRequestBean{
    /**
     * productSum : 10
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String productSum;

        public void setProductSum(String productSum) {
            this.productSum = productSum;
        }

        public String getProductSum() {
            return productSum;
        }
    }
}
