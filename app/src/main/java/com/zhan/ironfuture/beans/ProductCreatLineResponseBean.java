package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

/**
 * Created by Administrator on 2016/5/26.
 */
public class ProductCreatLineResponseBean extends BaseResponseBean{
    /**
     * prolineId : 7
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int prolineId;

        public void setProlineId(int prolineId) {
            this.prolineId = prolineId;
        }

        public int getProlineId() {
            return prolineId;
        }
    }
}
