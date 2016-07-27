package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
public class QueryOrderPurchaseResponseBean extends BaseResponseBean{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * goodsId : 1
         * goodsType : GOODS_MATERIAL_P1
         * goodsName : R-01
         * goodsCoefficient : 1
         * goodsPrice : 1
         * goodsState : 1
         * goodsLevel : 1
         * goodsSum : 100
         * sellSum : 1
         * descs : p1级原料
         */

        private List<P1p2ListEntity> p1p2List;

        public void setP1p2List(List<P1p2ListEntity> p1p2List) {
            this.p1p2List = p1p2List;
        }

        public List<P1p2ListEntity> getP1p2List() {
            return p1p2List;
        }

        public static class P1p2ListEntity {
            private int goodsId;
            private String goodsType;
            private String goodsName;
            private String goodsCoefficient;
            private int goodsPrice;
            private int goodsState;
            private int goodsLevel;
            private int goodsSum;
            private int sellSum;
            private String descs;

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public void setGoodsType(String goodsType) {
                this.goodsType = goodsType;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public void setGoodsCoefficient(String goodsCoefficient) {
                this.goodsCoefficient = goodsCoefficient;
            }

            public void setGoodsPrice(int goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public void setGoodsState(int goodsState) {
                this.goodsState = goodsState;
            }

            public void setGoodsLevel(int goodsLevel) {
                this.goodsLevel = goodsLevel;
            }

            public void setGoodsSum(int goodsSum) {
                this.goodsSum = goodsSum;
            }

            public void setSellSum(int sellSum) {
                this.sellSum = sellSum;
            }

            public void setDescs(String descs) {
                this.descs = descs;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public String getGoodsType() {
                return goodsType;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public String getGoodsCoefficient() {
                return goodsCoefficient;
            }

            public int getGoodsPrice() {
                return goodsPrice;
            }

            public int getGoodsState() {
                return goodsState;
            }

            public int getGoodsLevel() {
                return goodsLevel;
            }

            public int getGoodsSum() {
                return goodsSum;
            }

            public int getSellSum() {
                return sellSum;
            }

            public String getDescs() {
                return descs;
            }
        }
    }
}
