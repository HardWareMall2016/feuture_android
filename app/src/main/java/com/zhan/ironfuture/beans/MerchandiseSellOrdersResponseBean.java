package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class MerchandiseSellOrdersResponseBean extends BaseResponseBean{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * id : 1
         * name : 0-1000
         */

        private List<AmountListEntity> amountList;
        /**
         * id : 0
         * name : 否
         */

        private List<CanTransportListEntity> canTransportList;
        /**
         * pageId : null
         * pageCount : 20
         * pageDirection : null
         * bigType : null
         * inviteId : null
         * deptid : null
         * orderId : 22
         * orderType : 4
         * orderCategory : null
         * bsOrderId : null
         * totalAmount : 1500
         * totalAmountStart : null
         * totalAmountEnd : null
         * amountRange : null
         * companyId : 2
         * companyName : 伍氏集团
         * productAddress : 2
         * productAddressName : 伍氏集团
         * destination : 2
         * destinationName : 伍氏集团
         * canTransport : null
         * flowId : null
         * penalty : null
         * pickCompanyId : null
         * pickCompanyName : null
         * kindCount : 1
         * state : 2
         * remainingTime : 1466413489000
         * createTime : 1466413489000
         * updateTime : 1466413489000
         * logisStatTime : 1466413489000
         * logisEndTime : 2016-06-20 17:04:49.0
         * searchType : null
         * queryDeep : null
         * marketOrderDescDtoList : null
         * carsGoodsDetailDtoList : null
         * serachKey : null
         */

        private List<MarketOrderDtoListEntity> marketOrderDtoList;

        public void setAmountList(List<AmountListEntity> amountList) {
            this.amountList = amountList;
        }

        public void setCanTransportList(List<CanTransportListEntity> canTransportList) {
            this.canTransportList = canTransportList;
        }

        public void setMarketOrderDtoList(List<MarketOrderDtoListEntity> marketOrderDtoList) {
            this.marketOrderDtoList = marketOrderDtoList;
        }

        public List<AmountListEntity> getAmountList() {
            return amountList;
        }

        public List<CanTransportListEntity> getCanTransportList() {
            return canTransportList;
        }

        public List<MarketOrderDtoListEntity> getMarketOrderDtoList() {
            return marketOrderDtoList;
        }

        public static class AmountListEntity {
            private int id;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        public static class CanTransportListEntity {
            private int id;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }

        public static class MarketOrderDtoListEntity {
            private Object pageId;
            private int pageCount;
            private Object pageDirection;
            private Object bigType;
            private Object inviteId;
            private Object deptid;
            private int orderId;
            private String orderType;
            private Object orderCategory;
            private Object bsOrderId;
            private int totalAmount;
            private Object totalAmountStart;
            private Object totalAmountEnd;
            private Object amountRange;
            private int companyId;
            private String companyName;
            private String productAddress;
            private String productAddressName;
            private String destination;
            private String destinationName;
            private Object canTransport;
            private Object flowId;
            private int penalty;
            private Object pickCompanyId;
            private Object pickCompanyName;
            private int kindCount;
            private String state;
            private long remainingTime;
            private long createTime;
            private long updateTime;
            private long logisStatTime;
            private String logisEndTime;
            private Object searchType;
            private Object queryDeep;
            private Object marketOrderDescDtoList;
            private Object carsGoodsDetailDtoList;
            private Object serachKey;

            public void setPageId(Object pageId) {
                this.pageId = pageId;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public void setPageDirection(Object pageDirection) {
                this.pageDirection = pageDirection;
            }

            public void setBigType(Object bigType) {
                this.bigType = bigType;
            }

            public void setInviteId(Object inviteId) {
                this.inviteId = inviteId;
            }

            public void setDeptid(Object deptid) {
                this.deptid = deptid;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public void setOrderCategory(Object orderCategory) {
                this.orderCategory = orderCategory;
            }

            public void setBsOrderId(Object bsOrderId) {
                this.bsOrderId = bsOrderId;
            }

            public void setTotalAmount(int totalAmount) {
                this.totalAmount = totalAmount;
            }

            public void setTotalAmountStart(Object totalAmountStart) {
                this.totalAmountStart = totalAmountStart;
            }

            public void setTotalAmountEnd(Object totalAmountEnd) {
                this.totalAmountEnd = totalAmountEnd;
            }

            public void setAmountRange(Object amountRange) {
                this.amountRange = amountRange;
            }

            public void setCompanyId(int companyId) {
                this.companyId = companyId;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public void setProductAddress(String productAddress) {
                this.productAddress = productAddress;
            }

            public void setProductAddressName(String productAddressName) {
                this.productAddressName = productAddressName;
            }

            public void setDestination(String destination) {
                this.destination = destination;
            }

            public void setDestinationName(String destinationName) {
                this.destinationName = destinationName;
            }

            public void setCanTransport(Object canTransport) {
                this.canTransport = canTransport;
            }

            public void setFlowId(Object flowId) {
                this.flowId = flowId;
            }

            public void setPenalty(int penalty) {
                this.penalty = penalty;
            }

            public void setPickCompanyId(Object pickCompanyId) {
                this.pickCompanyId = pickCompanyId;
            }

            public void setPickCompanyName(Object pickCompanyName) {
                this.pickCompanyName = pickCompanyName;
            }

            public void setKindCount(int kindCount) {
                this.kindCount = kindCount;
            }

            public void setState(String state) {
                this.state = state;
            }

            public void setRemainingTime(long remainingTime) {
                this.remainingTime = remainingTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public void setLogisStatTime(long logisStatTime) {
                this.logisStatTime = logisStatTime;
            }

            public void setLogisEndTime(String logisEndTime) {
                this.logisEndTime = logisEndTime;
            }

            public void setSearchType(Object searchType) {
                this.searchType = searchType;
            }

            public void setQueryDeep(Object queryDeep) {
                this.queryDeep = queryDeep;
            }

            public void setMarketOrderDescDtoList(Object marketOrderDescDtoList) {
                this.marketOrderDescDtoList = marketOrderDescDtoList;
            }

            public void setCarsGoodsDetailDtoList(Object carsGoodsDetailDtoList) {
                this.carsGoodsDetailDtoList = carsGoodsDetailDtoList;
            }

            public void setSerachKey(Object serachKey) {
                this.serachKey = serachKey;
            }

            public Object getPageId() {
                return pageId;
            }

            public int getPageCount() {
                return pageCount;
            }

            public Object getPageDirection() {
                return pageDirection;
            }

            public Object getBigType() {
                return bigType;
            }

            public Object getInviteId() {
                return inviteId;
            }

            public Object getDeptid() {
                return deptid;
            }

            public int getOrderId() {
                return orderId;
            }

            public String getOrderType() {
                return orderType;
            }

            public Object getOrderCategory() {
                return orderCategory;
            }

            public Object getBsOrderId() {
                return bsOrderId;
            }

            public int getTotalAmount() {
                return totalAmount;
            }

            public Object getTotalAmountStart() {
                return totalAmountStart;
            }

            public Object getTotalAmountEnd() {
                return totalAmountEnd;
            }

            public Object getAmountRange() {
                return amountRange;
            }

            public int getCompanyId() {
                return companyId;
            }

            public String getCompanyName() {
                return companyName;
            }

            public String getProductAddress() {
                return productAddress;
            }

            public String getProductAddressName() {
                return productAddressName;
            }

            public String getDestination() {
                return destination;
            }

            public String getDestinationName() {
                return destinationName;
            }

            public Object getCanTransport() {
                return canTransport;
            }

            public Object getFlowId() {
                return flowId;
            }

            public int getPenalty() {
                return penalty;
            }

            public Object getPickCompanyId() {
                return pickCompanyId;
            }

            public Object getPickCompanyName() {
                return pickCompanyName;
            }

            public int getKindCount() {
                return kindCount;
            }

            public String getState() {
                return state;
            }

            public long getRemainingTime() {
                return remainingTime;
            }

            public long getCreateTime() {
                return createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public long getLogisStatTime() {
                return logisStatTime;
            }

            public String getLogisEndTime() {
                return logisEndTime;
            }

            public Object getSearchType() {
                return searchType;
            }

            public Object getQueryDeep() {
                return queryDeep;
            }

            public Object getMarketOrderDescDtoList() {
                return marketOrderDescDtoList;
            }

            public Object getCarsGoodsDetailDtoList() {
                return carsGoodsDetailDtoList;
            }

            public Object getSerachKey() {
                return serachKey;
            }
        }
    }
   /* *//**
     * pageId : null
     * pageCount : 20
     * pageDirection : null
     * serachKey : null
     * bigType : null
     * inviteId : null
     * deptid : null
     * orderId : 9
     * orderType : 4
     * orderCategory : null
     * bsOrderId : null
     * totalAmount : 2000
     * companyId : 3
     * productAddress : null
     * destination : 1
     * flowId : null
     * penalty : 0
     * pickCompanyId : null
     * kindCount : 8
     * state : 4
     * remainingTime : 1465292917000
     * createTime : 1464176347000
     * updateTime : 1465292917000
     * logisStatTime : 1464176347000
     * logisEndTime : 1464176347000
     * queryDeep : null
     * marketOrderDescDtoList : null
     * carsGoodsDetailDtoList : null
     *//*

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private Object pageId;
        private int pageCount;
        private Object pageDirection;
        private Object serachKey;
        private Object bigType;
        private Object inviteId;
        private Object deptid;
        private int orderId;
        private String orderType;
        private Object orderCategory;
        private Object bsOrderId;
        private int totalAmount;
        private int companyId;
        private Object productAddress;
        private String destination;
        private Object flowId;
        private int penalty;
        private Object pickCompanyId;
        private int kindCount;
        private String state;
        private long remainingTime;
        private long createTime;
        private long updateTime;
        private long logisStatTime;
        private String logisEndTime;
        private Object queryDeep;
        private Object marketOrderDescDtoList;
        private Object carsGoodsDetailDtoList;

        public void setPageId(Object pageId) {
            this.pageId = pageId;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public void setPageDirection(Object pageDirection) {
            this.pageDirection = pageDirection;
        }

        public void setSerachKey(Object serachKey) {
            this.serachKey = serachKey;
        }

        public void setBigType(Object bigType) {
            this.bigType = bigType;
        }

        public void setInviteId(Object inviteId) {
            this.inviteId = inviteId;
        }

        public void setDeptid(Object deptid) {
            this.deptid = deptid;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public void setOrderCategory(Object orderCategory) {
            this.orderCategory = orderCategory;
        }

        public void setBsOrderId(Object bsOrderId) {
            this.bsOrderId = bsOrderId;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public void setProductAddress(Object productAddress) {
            this.productAddress = productAddress;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public void setFlowId(Object flowId) {
            this.flowId = flowId;
        }

        public void setPenalty(int penalty) {
            this.penalty = penalty;
        }

        public void setPickCompanyId(Object pickCompanyId) {
            this.pickCompanyId = pickCompanyId;
        }

        public void setKindCount(int kindCount) {
            this.kindCount = kindCount;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setRemainingTime(long remainingTime) {
            this.remainingTime = remainingTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public void setLogisStatTime(long logisStatTime) {
            this.logisStatTime = logisStatTime;
        }

        public void setLogisEndTime(String logisEndTime) {
            this.logisEndTime = logisEndTime;
        }

        public void setQueryDeep(Object queryDeep) {
            this.queryDeep = queryDeep;
        }

        public void setMarketOrderDescDtoList(Object marketOrderDescDtoList) {
            this.marketOrderDescDtoList = marketOrderDescDtoList;
        }

        public void setCarsGoodsDetailDtoList(Object carsGoodsDetailDtoList) {
            this.carsGoodsDetailDtoList = carsGoodsDetailDtoList;
        }

        public Object getPageId() {
            return pageId;
        }

        public int getPageCount() {
            return pageCount;
        }

        public Object getPageDirection() {
            return pageDirection;
        }

        public Object getSerachKey() {
            return serachKey;
        }

        public Object getBigType() {
            return bigType;
        }

        public Object getInviteId() {
            return inviteId;
        }

        public Object getDeptid() {
            return deptid;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getOrderType() {
            return orderType;
        }

        public Object getOrderCategory() {
            return orderCategory;
        }

        public Object getBsOrderId() {
            return bsOrderId;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public int getCompanyId() {
            return companyId;
        }

        public Object getProductAddress() {
            return productAddress;
        }

        public String getDestination() {
            return destination;
        }

        public Object getFlowId() {
            return flowId;
        }

        public int getPenalty() {
            return penalty;
        }

        public Object getPickCompanyId() {
            return pickCompanyId;
        }

        public int getKindCount() {
            return kindCount;
        }

        public String getState() {
            return state;
        }

        public long getRemainingTime() {
            return remainingTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public long getLogisStatTime() {
            return logisStatTime;
        }

        public String getLogisEndTime() {
            return logisEndTime;
        }

        public Object getQueryDeep() {
            return queryDeep;
        }

        public Object getMarketOrderDescDtoList() {
            return marketOrderDescDtoList;
        }

        public Object getCarsGoodsDetailDtoList() {
            return carsGoodsDetailDtoList;
        }
    }*/
}
