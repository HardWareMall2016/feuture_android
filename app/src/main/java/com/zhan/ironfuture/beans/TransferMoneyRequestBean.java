package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/19.
 */
public class TransferMoneyRequestBean extends BaseRequestBean{


    private DataEntity data;


    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }


    public static class DataEntity {
        private String outDeptId;
        private String inDeptId;
        private String amount;
        private String use;

        public void setOutDeptId(String outDeptId) {
            this.outDeptId = outDeptId;
        }

        public void setInDeptId(String inDeptId) {
            this.inDeptId = inDeptId;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public void setUse(String use) {
            this.use = use;
        }

        public String getOutDeptId() {
            return outDeptId;
        }

        public String getInDeptId() {
            return inDeptId;
        }

        public String getAmount() {
            return amount;
        }

        public String getUse() {
            return use;
        }
    }
}
