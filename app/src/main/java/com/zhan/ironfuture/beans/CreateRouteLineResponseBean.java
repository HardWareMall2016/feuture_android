package com.zhan.ironfuture.beans;


import com.zhan.ironfuture.base.BaseResponseBean;


public class CreateRouteLineResponseBean extends BaseResponseBean {

    /**
     * lineStateSum : 6
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int lineStateSum;

        public int getLineStateSum() {
            return lineStateSum;
        }

        public void setLineStateSum(int lineStateSum) {
            this.lineStateSum = lineStateSum;
        }
    }
}
