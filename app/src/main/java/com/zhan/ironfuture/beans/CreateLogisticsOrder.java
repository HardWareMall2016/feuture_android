package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

import java.util.List;

/**
 * 作者：伍岳 on 2016/6/22 14:17
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
public class CreateLogisticsOrder extends BaseRequestBean {
    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int orderType;
        private int bsOrderId;
        private int totalAmount;
        private int companyId;
        private String productAddress;
        private String destination;
        private int penalty;
        private Integer pickCompanyId;
        private long remainingTime;
        private List<String> details;

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getBsOrderId() {
            return bsOrderId;
        }

        public void setBsOrderId(int bsOrderId) {
            this.bsOrderId = bsOrderId;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getProductAddress() {
            return productAddress;
        }

        public void setProductAddress(String productAddress) {
            this.productAddress = productAddress;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getPenalty() {
            return penalty;
        }

        public void setPenalty(int penalty) {
            this.penalty = penalty;
        }

        public Integer getPickCompanyId() {
            return pickCompanyId;
        }

        public void setPickCompanyId(Integer pickCompanyId) {
            this.pickCompanyId = pickCompanyId;
        }

        public long getRemainingTime() {
            return remainingTime;
        }

        public void setRemainingTime(long remainingTime) {
            this.remainingTime = remainingTime;
        }

        public List<String> getDetails() {
            return details;
        }

        public void setDetails(List<String> details) {
            this.details = details;
        }
    }
}
