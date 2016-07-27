package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/31.
 */
public class ProductLineDetailRequestBean extends BaseRequestBean{
    /**
     * prolineId : 1
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
        private String prolineType ;

        public String getProlineType() {
            return prolineType;
        }

        public void setProlineType(String prolineType) {
            this.prolineType = prolineType;
        }

        public void setProlineId(int prolineId) {
            this.prolineId = prolineId;
        }

        public int getProlineId() {
            return prolineId;
        }
    }
}
