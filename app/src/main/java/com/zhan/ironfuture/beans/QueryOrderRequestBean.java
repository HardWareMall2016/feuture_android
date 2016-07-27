package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * 作者：伍岳 on 2016/6/6 13:59
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
public class QueryOrderRequestBean extends BaseRequestBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private Integer pageId ;
        private Integer pageDirection ;
        private Integer pageCount ;
        private Integer orderId;
        private Integer companyId;
        private Integer orderType;
        private String destination;
        private Integer pick_company_id;
        private Integer orderCategory;
        private Integer state;
        private Long createTime;
        private int queryDeep;
        private String searchKey;
        private String searchType;


        public Integer getPageId() {
            return pageId;
        }

        public void setPageId(Integer pageId) {
            this.pageId = pageId;
        }

        public Integer getPageDirection() {
            return pageDirection;
        }

        public void setPageDirection(Integer pageDirection) {
            this.pageDirection = pageDirection;
        }

        public Integer getPageCount() {
            return pageCount;
        }

        public void setPageCount(Integer pageCount) {
            this.pageCount = pageCount;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public Integer getOrderType() {
            return orderType;
        }

        public void setOrderType(Integer orderType) {
            this.orderType = orderType;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public int getQueryDeep() {
            return queryDeep;
        }

        public void setQueryDeep(int queryDeep) {
            this.queryDeep = queryDeep;
        }

        public Integer getOrderCategory() {
            return orderCategory;
        }

        public void setOrderCategory(Integer orderCategory) {
            this.orderCategory = orderCategory;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public Integer getPick_company_id() {
            return pick_company_id;
        }

        public void setPick_company_id(Integer pick_company_id) {
            this.pick_company_id = pick_company_id;
        }

        public String getSearchKey() {
            return searchKey;
        }

        public void setSearchKey(String searchKey) {
            this.searchKey = searchKey;
        }

        public String getSearchType() {
            return searchType;
        }

        public void setSearchType(String searchType) {
            this.searchType = searchType;
        }
    }
}
