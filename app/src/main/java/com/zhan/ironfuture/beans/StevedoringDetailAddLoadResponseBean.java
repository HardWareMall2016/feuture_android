package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

/**
 * Created by Administrator on 2016/6/15.
 */
public class StevedoringDetailAddLoadResponseBean extends BaseResponseBean{
    /**
     * sum : 0
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int sum;

        public void setSum(int sum) {
            this.sum = sum;
        }

        public int getSum() {
            return sum;
        }
    }
}
