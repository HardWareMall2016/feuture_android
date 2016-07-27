package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

public class FireRequestBean extends BaseRequestBean {
    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private int staffId;

        public int getStaffId() {
            return staffId;
        }

        public void setStaffId(int staffId) {
            this.staffId = staffId;
        }
    }
}
