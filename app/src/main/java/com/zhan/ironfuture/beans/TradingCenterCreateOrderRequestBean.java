package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

import java.util.List;

/**
 * 作者：伍岳 on 2016/6/29 14:28
 * 邮箱：wuyue8512@163.com
 * //
 * //         .............................................
 * //                  美女坐镇                  BUG辟易
 * //         .............................................
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 * //
 */
public class TradingCenterCreateOrderRequestBean extends BaseRequestBean {
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int companyId;
        private int penalty;
        private String type;
        private int priceOrder;
        private int pickCompanyId;
        private int orderType;
        private int totalAmount;
        private long remainingTime;
        private List<String> details;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getPenalty() {
            return penalty;
        }

        public void setPenalty(int penalty) {
            this.penalty = penalty;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPriceOrder() {
            return priceOrder;
        }

        public void setPriceOrder(int priceOrder) {
            this.priceOrder = priceOrder;
        }

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
