package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class FinancialManagementResponseBean extends BaseResponseBean{
    /**
     * moneyLogList : [{"id":1,"comId":1,"handleType":null,"amount":100,"descriptor":"bb","inOut":1,"createTime":1463467909000}]
     * surplusMoney : 174000
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private long surplusMoney;
        /**
         * id : 1
         * comId : 1
         * handleType : null
         * amount : 100
         * descriptor : bb
         * inOut : 1
         * createTime : 1463467909000
         */

        private List<MoneyLogListEntity> moneyLogList;

        public void setSurplusMoney(long surplusMoney) {
            this.surplusMoney = surplusMoney;
        }

        public void setMoneyLogList(List<MoneyLogListEntity> moneyLogList) {
            this.moneyLogList = moneyLogList;
        }

        public long getSurplusMoney() {
            return surplusMoney;
        }

        public List<MoneyLogListEntity> getMoneyLogList() {
            return moneyLogList;
        }

        public static class MoneyLogListEntity {
            private int id;
            private int comId;
            private Object handleType;
            private long amount;
            private String descriptor;
            private int inOut;
            private long createTime;

            public void setId(int id) {
                this.id = id;
            }

            public void setComId(int comId) {
                this.comId = comId;
            }

            public void setHandleType(Object handleType) {
                this.handleType = handleType;
            }

            public void setAmount(long amount) {
                this.amount = amount;
            }

            public void setDescriptor(String descriptor) {
                this.descriptor = descriptor;
            }

            public void setInOut(int inOut) {
                this.inOut = inOut;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public int getComId() {
                return comId;
            }

            public Object getHandleType() {
                return handleType;
            }

            public long getAmount() {
                return amount;
            }

            public String getDescriptor() {
                return descriptor;
            }

            public int getInOut() {
                return inOut;
            }

            public long getCreateTime() {
                return createTime;
            }
        }
    }
}
