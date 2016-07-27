package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

public class SetBudgetRequestBean extends BaseRequestBean {
    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private int deptId;

        private int budget;

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }
    }
}
