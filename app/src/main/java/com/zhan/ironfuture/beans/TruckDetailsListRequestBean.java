package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/26.
 */
public class TruckDetailsListRequestBean extends BaseRequestBean{
    /**
     * comId : 1
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String comId;

        public void setComId(String comId) {
            this.comId = comId;
        }

        public String getComId() {
            return comId;
        }
    }
}
