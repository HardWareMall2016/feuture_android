package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

/**
 * Created by Administrator on 2016/5/23.
 */
public class RouteManagementDetailAddFullResponseBean extends BaseResponseBean{
    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private double nowfullSum;

        public double getNowfullSum() {
            return nowfullSum;
        }

        public void setNowfullSum(double nowfullSum) {
            this.nowfullSum = nowfullSum;
        }
    }
}
