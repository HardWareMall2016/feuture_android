package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

/**
 * Created by Administrator on 2016/5/20.
 */
public class StoreManageUpgradeResponseBean extends BaseResponseBean {


    private DataEntity data;


    public void setData(DataEntity data) {
        this.data = data;
    }


    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int nextPrice;

        public void setNextPrice(int nextPrice) {
            this.nextPrice = nextPrice;
        }

        public int getNextPrice() {
            return nextPrice;
        }
    }
}
