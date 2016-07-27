package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by WuYue on 2016/5/26.
 */
public class GetGarbageListRequestBean extends BaseRequestBean{

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int comId;

        public int getComId() {
            return comId;
        }

        public void setComId(int comId) {
            this.comId = comId;
        }
    }
}
