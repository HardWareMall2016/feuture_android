package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class CarsRemoveInfoResponseBean extends BaseResponseBean{
    /**
     * queryCriteria : {"isUrgent":[{"id":0,"name":"正常"},{"id":1,"name":"紧急"}],"isHandling":[{"id":0,"name":"不可装卸"},{"id":1,"name":"可装卸"}],"salaryList":[{"id":221,"name":"0000-4000"},{"id":222,"name":"4000-8000"},{"id":223,"name":"8000-12000"},{"id":224,"name":"12000-16000"},{"id":225,"name":"16000-0000"}]}
     * executePower : 999998336
     * orderList : [{"orderId":2,"goodsCount":231}]
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private QueryCriteriaEntity queryCriteria;
        private int executePower;
        /**
         * orderId : 2
         * goodsCount : 231
         */

        private List<OrderListEntity> orderList;

        public void setQueryCriteria(QueryCriteriaEntity queryCriteria) {
            this.queryCriteria = queryCriteria;
        }

        public void setExecutePower(int executePower) {
            this.executePower = executePower;
        }

        public void setOrderList(List<OrderListEntity> orderList) {
            this.orderList = orderList;
        }

        public QueryCriteriaEntity getQueryCriteria() {
            return queryCriteria;
        }

        public int getExecutePower() {
            return executePower;
        }

        public List<OrderListEntity> getOrderList() {
            return orderList;
        }

        public static class QueryCriteriaEntity {
            /**
             * id : 0
             * name : 正常
             */

            private List<IsUrgentEntity> isUrgent;
            /**
             * id : 0
             * name : 不可装卸
             */

            private List<IsHandlingEntity> isHandling;
            /**
             * id : 221
             * name : 0000-4000
             */

            private List<SalaryListEntity> salaryList;

            public void setIsUrgent(List<IsUrgentEntity> isUrgent) {
                this.isUrgent = isUrgent;
            }

            public void setIsHandling(List<IsHandlingEntity> isHandling) {
                this.isHandling = isHandling;
            }

            public void setSalaryList(List<SalaryListEntity> salaryList) {
                this.salaryList = salaryList;
            }

            public List<IsUrgentEntity> getIsUrgent() {
                return isUrgent;
            }

            public List<IsHandlingEntity> getIsHandling() {
                return isHandling;
            }

            public List<SalaryListEntity> getSalaryList() {
                return salaryList;
            }

            public static class IsUrgentEntity {
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

            public static class IsHandlingEntity {
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

            public static class SalaryListEntity {
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
        }

        public static class OrderListEntity {
            private int orderId;
            private int goodsCount;
            private int salary ;

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
            }

            public int getOrderId() {
                return orderId;
            }

            public int getGoodsCount() {
                return goodsCount;
            }

            public int getSalary() {
                return salary;
            }

            public void setSalary(int salary) {
                this.salary = salary;
            }
        }
    }
}
