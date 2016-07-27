package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
public class MerchandiseSalesTradingResponseBean extends BaseResponseBean{
    /**
     * marketSysId : 2
     * goodsId : 56
     * price : 249113
     * buySum : null
     * creatBuytime : 1465354442000
     * endBuytime : 1465959242000
     * sum : 100
     * alreadySum : 100
     * goodsNmae : ID-III
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int marketSysId;
        private String goodsId;
        private int price;
        private Object buySum;
        private long creatBuytime;
        private long endBuytime;
        private int sum;
        private int alreadySum;
        private String goodsNmae;

        public void setMarketSysId(int marketSysId) {
            this.marketSysId = marketSysId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void setBuySum(Object buySum) {
            this.buySum = buySum;
        }

        public void setCreatBuytime(long creatBuytime) {
            this.creatBuytime = creatBuytime;
        }

        public void setEndBuytime(long endBuytime) {
            this.endBuytime = endBuytime;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public void setAlreadySum(int alreadySum) {
            this.alreadySum = alreadySum;
        }

        public void setGoodsNmae(String goodsNmae) {
            this.goodsNmae = goodsNmae;
        }

        public int getMarketSysId() {
            return marketSysId;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public int getPrice() {
            return price;
        }

        public Object getBuySum() {
            return buySum;
        }

        public long getCreatBuytime() {
            return creatBuytime;
        }

        public long getEndBuytime() {
            return endBuytime;
        }

        public int getSum() {
            return sum;
        }

        public int getAlreadySum() {
            return alreadySum;
        }

        public String getGoodsNmae() {
            return goodsNmae;
        }
    }
}
