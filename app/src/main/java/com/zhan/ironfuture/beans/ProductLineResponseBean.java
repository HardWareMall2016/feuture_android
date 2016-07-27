package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
 */
public class ProductLineResponseBean extends BaseResponseBean{
    /**
     * buyProLinePrice : 18000
     * proLineBackList : [{"prolineId":1,"prolineName":"","proOutputId":28,"outputName":"","recipeId":0,"recipeName":null,"endLockTime":1464209718000},{"prolineId":2,"prolineName":"","proOutputId":0,"outputName":"","recipeId":0,"recipeName":null,"endLockTime":1461555066000},{"prolineId":3,"prolineName":"","proOutputId":0,"outputName":"","recipeId":0,"recipeName":null,"endLockTime":1461555594000},{"prolineId":5,"prolineName":null,"proOutputId":0,"outputName":null,"recipeId":0,"recipeName":null,"endLockTime":1464164468000},{"prolineId":6,"prolineName":"我的产线名字","proOutputId":0,"outputName":null,"recipeId":0,"recipeName":null,"endLockTime":1464164561000}]
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int recipeSum;

        public int getRecipeSum() {
            return recipeSum;
        }

        public void setRecipeSum(int recipeSum) {
            this.recipeSum = recipeSum;
        }

        private int buyProLinePrice;
        /**
         * prolineId : 1
         * prolineName :
         * proOutputId : 28
         * outputName :
         * recipeId : 0
         * recipeName : null
         * endLockTime : 1464209718000
         */

        private List<ProLineBackListEntity> proLineBackList;

        public void setBuyProLinePrice(int buyProLinePrice) {
            this.buyProLinePrice = buyProLinePrice;
        }

        public void setProLineBackList(List<ProLineBackListEntity> proLineBackList) {
            this.proLineBackList = proLineBackList;
        }

        public int getBuyProLinePrice() {
            return buyProLinePrice;
        }

        public List<ProLineBackListEntity> getProLineBackList() {
            return proLineBackList;
        }

        public static class ProLineBackListEntity {
            private int prolineId;
            private String prolineName;
            private int proOutputId;
            private String outputName;
            private int recipeId;
            private String recipeName;
            private long endLockTime;


            public void setProlineId(int prolineId) {
                this.prolineId = prolineId;
            }

            public void setProlineName(String prolineName) {
                this.prolineName = prolineName;
            }

            public void setProOutputId(int proOutputId) {
                this.proOutputId = proOutputId;
            }

            public void setOutputName(String outputName) {
                this.outputName = outputName;
            }

            public void setRecipeId(int recipeId) {
                this.recipeId = recipeId;
            }

            public void setRecipeName(String recipeName) {
                this.recipeName = recipeName;
            }

            public void setEndLockTime(long endLockTime) {
                this.endLockTime = endLockTime;
            }

            public int getProlineId() {
                return prolineId;
            }

            public String getProlineName() {
                return prolineName;
            }

            public int getProOutputId() {
                return proOutputId;
            }

            public String getOutputName() {
                return outputName;
            }

            public int getRecipeId() {
                return recipeId;
            }

            public String getRecipeName() {
                return recipeName;
            }

            public long getEndLockTime() {
                return endLockTime;
            }
        }
    }
}
