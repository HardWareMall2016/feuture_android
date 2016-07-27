package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/26.
 */
public class ProductLineRequestBean extends BaseRequestBean{
    /**
     * prolineType : PROLINE_FINAL
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String prolineType;

        public void setProlineType(String prolineType) {
            this.prolineType = prolineType;
        }

        public String getProlineType() {
            return prolineType;
        }
    }
}
