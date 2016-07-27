package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/12.
 */
public class LoginsRequestBean extends BaseRequestBean {
    private DataEntity Data;

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public DataEntity getData() {
        return Data;
    }

    public static class DataEntity {
        private String phone;
        private String phoneChkNo;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhoneChkNo() {
            return phoneChkNo;
        }

        public void setPhoneChkNo(String phoneChkNo) {
            this.phoneChkNo = phoneChkNo;
        }

    }
}
