package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class StorePurchaseGetGoodsResponseBean extends BaseResponseBean{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * goodsId : 54
         * goodsType : GOODS_FINAL_PRODUCT
         * goodsName : C2
         * goodsCoefficient : null
         * goodsPrice : 400
         * goodsState : 1
         * goodsLevel : 3
         * goodsSum : null
         * sellSum : 0
         * descs : 产品
         */

        private List<ProductListEntity> productList;
        /**
         * goodsId : 1
         * goodsType : GOODS_MATERIAL_P1
         * goodsName : R-01
         * goodsCoefficient : 1
         * goodsPrice : 1
         * goodsState : 1
         * goodsLevel : 1
         * goodsSum : 22
         * sellSum : 0
         * descs : p1级原料
         */

        private List<P1ListEntity> p1List;
        /**
         * goodsId : 27
         * goodsType : GOODS_MATERIAL_P2
         * goodsName : A
         * goodsCoefficient : 1
         * goodsPrice : 648
         * goodsState : 1
         * goodsLevel : 2
         * goodsSum : 40076
         * sellSum : 0
         * descs : p2级原料
         */

        private List<P2ListEntity> p2List;

        public void setProductList(List<ProductListEntity> productList) {
            this.productList = productList;
        }

        public void setP1List(List<P1ListEntity> p1List) {
            this.p1List = p1List;
        }

        public void setP2List(List<P2ListEntity> p2List) {
            this.p2List = p2List;
        }

        public List<ProductListEntity> getProductList() {
            return productList;
        }

        public List<P1ListEntity> getP1List() {
            return p1List;
        }

        public List<P2ListEntity> getP2List() {
            return p2List;
        }

        public static class ProductListEntity {
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

        public static class P1ListEntity {
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

        public static class P2ListEntity {
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
