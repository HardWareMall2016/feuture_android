package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

import java.util.List;

public class CreateRouteLineRequestBean extends BaseRequestBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int comId;
        private List<Integer> companyList;

        public int getComId() {
            return comId;
        }

        public void setComId(int comId) {
            this.comId = comId;
        }

        public List<Integer> getCompanyList() {
            return companyList;
        }

        public void setCompanyList(List<Integer> companyList) {
            this.companyList = companyList;
        }
    }
}
