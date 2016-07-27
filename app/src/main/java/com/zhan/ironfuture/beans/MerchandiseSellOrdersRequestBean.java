package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/6/13.
 */
public class MerchandiseSellOrdersRequestBean extends BaseRequestBean{
    /**
     * queryDeep : 1
     * destination : 1
     * pageId : 0
     * pageDirection : 1
     * pageCount : 5
     * state : 4
     * orderType : 4
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int queryDeep;
        private String destination;
        private int pageId;
        private int pageDirection;
        private int pageCount;
        private int state;
        private int orderType;
        private Integer companyId;

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }


        public void setQueryDeep(int queryDeep) {
            this.queryDeep = queryDeep;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public void setPageId(int pageId) {
            this.pageId = pageId;
        }

        public void setPageDirection(int pageDirection) {
            this.pageDirection = pageDirection;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public void setState(int state) {
            this.state = state;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getQueryDeep() {
            return queryDeep;
        }

        public String getDestination() {
            return destination;
        }

        public int getPageId() {
            return pageId;
        }

        public int getPageDirection() {
            return pageDirection;
        }

        public int getPageCount() {
            return pageCount;
        }

        public int getState() {
            return state;
        }

        public int getOrderType() {
            return orderType;
        }
    }
}
