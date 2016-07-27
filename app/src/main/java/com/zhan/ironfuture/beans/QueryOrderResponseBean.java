package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * 作者：伍岳 on 2016/6/6 15:18
 * 邮箱：wuyue8512@163.com
 //
 //         .............................................
 //                  美女坐镇                  BUG辟易
 //         .............................................
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 //
 */
public class QueryOrderResponseBean extends BaseResponseBean{

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private List<AmountListEntity> amountList;

        private List<CanTransportListEntity> canTransportList;

        private List<MarketOrderDtoListEntity> marketOrderDtoList;

        public List<AmountListEntity> getAmountList() {
            return amountList;
        }

        public void setAmountList(List<AmountListEntity> amountList) {
            this.amountList = amountList;
        }

        public List<CanTransportListEntity> getCanTransportList() {
            return canTransportList;
        }

        public void setCanTransportList(List<CanTransportListEntity> canTransportList) {
            this.canTransportList = canTransportList;
        }

        public List<MarketOrderDtoListEntity> getMarketOrderDtoList() {
            return marketOrderDtoList;
        }

        public void setMarketOrderDtoList(List<MarketOrderDtoListEntity> marketOrderDtoList) {
            this.marketOrderDtoList = marketOrderDtoList;
        }

        public static class AmountListEntity {
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CanTransportListEntity {
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class MarketOrderDtoListEntity {

            private int orderId;
            private String orderType;
            private Object orderCategory;
            private int bsOrderId;
            private int totalAmount;
            private int companyId;
            private String productAddress;
            private String destination;
            private Object flowId;
            private int penalty;
            private int pickCompanyId;
            private int kindCount;
            private String state;
            private long remainingTime;
            private long createTime;
            private long updateTime;
            private long logisStatTime;
            private String logisEndTime;
            private Object queryDeep;
            private String companyName;
            private String pickCompanyName;
            /**
             * orderDetailId : 17
             * productId : 6
             * productPrice : 1
             * productQuantity : 33
             * orderId : 3
             * alreadyLoadSum : 30
             * endSum : 0
             * goodsName : R-06
             */

            private List<MarketOrderDescDtoListEntity> marketOrderDescDtoList;
            /**
             * flowLogId : 6
             * flowActId : 1
             * flowId : 1
             * stepId : 11
             * actSeqNo : 3
             * logInfo : 物流订单3当前到铁未来11
             * createTime : 1465191373000
             */

            private List<CarsGoodsDetailDtoListEntity> carsGoodsDetailDtoList;

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public Object getOrderCategory() {
                return orderCategory;
            }

            public void setOrderCategory(Object orderCategory) {
                this.orderCategory = orderCategory;
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

            public Object getFlowId() {
                return flowId;
            }

            public void setFlowId(Object flowId) {
                this.flowId = flowId;
            }

            public int getPenalty() {
                return penalty;
            }

            public void setPenalty(int penalty) {
                this.penalty = penalty;
            }

            public int getPickCompanyId() {
                return pickCompanyId;
            }

            public void setPickCompanyId(int pickCompanyId) {
                this.pickCompanyId = pickCompanyId;
            }

            public int getKindCount() {
                return kindCount;
            }

            public void setKindCount(int kindCount) {
                this.kindCount = kindCount;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public long getRemainingTime() {
                return remainingTime;
            }

            public void setRemainingTime(long remainingTime) {
                this.remainingTime = remainingTime;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public long getLogisStatTime() {
                return logisStatTime;
            }

            public void setLogisStatTime(long logisStatTime) {
                this.logisStatTime = logisStatTime;
            }

            public String getLogisEndTime() {
                return logisEndTime;
            }

            public void setLogisEndTime(String logisEndTime) {
                this.logisEndTime = logisEndTime;
            }

            public Object getQueryDeep() {
                return queryDeep;
            }

            public void setQueryDeep(Object queryDeep) {
                this.queryDeep = queryDeep;
            }

            public List<MarketOrderDescDtoListEntity> getMarketOrderDescDtoList() {
                return marketOrderDescDtoList;
            }

            public void setMarketOrderDescDtoList(List<MarketOrderDescDtoListEntity> marketOrderDescDtoList) {
                this.marketOrderDescDtoList = marketOrderDescDtoList;
            }

            public List<CarsGoodsDetailDtoListEntity> getCarsGoodsDetailDtoList() {
                return carsGoodsDetailDtoList;
            }

            public void setCarsGoodsDetailDtoList(List<CarsGoodsDetailDtoListEntity> carsGoodsDetailDtoList) {
                this.carsGoodsDetailDtoList = carsGoodsDetailDtoList;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getPickCompanyName() {
                return pickCompanyName;
            }

            public void setPickCompanyName(String pickCompanyName) {
                this.pickCompanyName = pickCompanyName;
            }

            public static class MarketOrderDescDtoListEntity {
                private int orderDetailId;
                private int productId;
                private int productPrice;
                private int productQuantity;
                private int orderId;
                private int alreadyLoadSum;
                private int endSum;
                private String goodsName;

                public int getOrderDetailId() {
                    return orderDetailId;
                }

                public void setOrderDetailId(int orderDetailId) {
                    this.orderDetailId = orderDetailId;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public int getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(int productPrice) {
                    this.productPrice = productPrice;
                }

                public int getProductQuantity() {
                    return productQuantity;
                }

                public void setProductQuantity(int productQuantity) {
                    this.productQuantity = productQuantity;
                }

                public int getOrderId() {
                    return orderId;
                }

                public void setOrderId(int orderId) {
                    this.orderId = orderId;
                }

                public int getAlreadyLoadSum() {
                    return alreadyLoadSum;
                }

                public void setAlreadyLoadSum(int alreadyLoadSum) {
                    this.alreadyLoadSum = alreadyLoadSum;
                }

                public int getEndSum() {
                    return endSum;
                }

                public void setEndSum(int endSum) {
                    this.endSum = endSum;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }
            }

            public static class CarsGoodsDetailDtoListEntity {
                private int flowLogId;
                private int flowActId;
                private int flowId;
                private int stepId;
                private int actSeqNo;
                private String logInfo;
                private long createTime;

                public int getFlowLogId() {
                    return flowLogId;
                }

                public void setFlowLogId(int flowLogId) {
                    this.flowLogId = flowLogId;
                }

                public int getFlowActId() {
                    return flowActId;
                }

                public void setFlowActId(int flowActId) {
                    this.flowActId = flowActId;
                }

                public int getFlowId() {
                    return flowId;
                }

                public void setFlowId(int flowId) {
                    this.flowId = flowId;
                }

                public int getStepId() {
                    return stepId;
                }

                public void setStepId(int stepId) {
                    this.stepId = stepId;
                }

                public int getActSeqNo() {
                    return actSeqNo;
                }

                public void setActSeqNo(int actSeqNo) {
                    this.actSeqNo = actSeqNo;
                }

                public String getLogInfo() {
                    return logInfo;
                }

                public void setLogInfo(String logInfo) {
                    this.logInfo = logInfo;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }
            }
        }
    }
}
