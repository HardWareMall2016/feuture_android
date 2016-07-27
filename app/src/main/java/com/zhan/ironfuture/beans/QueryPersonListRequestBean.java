package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by WuYue on 2016/5/31.
 */
public class QueryPersonListRequestBean extends BaseRequestBean {
    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int deptid;
        private String serachKey;

        public int getDeptid() {
            return deptid;
        }

        public void setDeptid(int deptid) {
            this.deptid = deptid;
        }

        public String getSerachKey() {
            return serachKey;
        }

        public void setSerachKey(String serachKey) {
            this.serachKey = serachKey;
        }
    }
}
