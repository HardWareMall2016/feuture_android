package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/23.
 */
public class RouteManagementDetailRequestBean extends BaseRequestBean{
    /**
     * rodeLineId : 5
     */

    private DataEntity data;


    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }


    public static class DataEntity {
        private int rodeLineId;

        public void setRodeLineId(int rodeLineId) {
            this.rodeLineId = rodeLineId;
        }

        public int getRodeLineId() {
            return rodeLineId;
        }
    }
}
