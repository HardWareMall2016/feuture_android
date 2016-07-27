package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/31.
 */
public class StaffListDetailRequestBean extends BaseRequestBean{
    /**
     * deptId : 1
     * companyId : 1
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int deptId;
        private int companyId;

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getDeptId() {
            return deptId;
        }

        public int getCompanyId() {
            return companyId;
        }
    }
}
