package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 */
public class StorePurchaseOrderRequestBean extends BaseRequestBean{
    /**
     * companyId : 1
     * orderType : 4
     * totalAmount : 1
     * remainingTime : 1464192000000
     * details : ["1_1"]
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int pickCompanyId;
        private int orderType;
        private int totalAmount;
        private long remainingTime;
        private List<String> details;


        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }

        public void setRemainingTime(long remainingTime) {
            this.remainingTime = remainingTime;
        }

        public void setDetails(List<String> details) {
            this.details = details;
        }

        public int getOrderType() {
            return orderType;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public long getRemainingTime() {
            return remainingTime;
        }

        public List<String> getDetails() {
            return details;
        }

        public int getPickCompanyId() {
            return pickCompanyId;
        }

        public void setPickCompanyId(int pickCompanyId) {
            this.pickCompanyId = pickCompanyId;
        }
    }
}
