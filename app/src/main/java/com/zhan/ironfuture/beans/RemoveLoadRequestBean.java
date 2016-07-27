package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class RemoveLoadRequestBean extends BaseRequestBean{
    /**
     * spaceId : 1
     * goodsId : 2
     * addSum : 1
     * orderId : 10
     * carId : 20
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int comId ;
        private int goodsId;
        private int addSum;
        private int orderId;
        private int carId;
        private int spaceId;

        public int getComId() {
            return comId;
        }

        public void setComId(int comId) {
            this.comId = comId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public void setAddSum(int addSum) {
            this.addSum = addSum;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public int getAddSum() {
            return addSum;
        }

        public int getOrderId() {
            return orderId;
        }

        public int getCarId() {
            return carId;
        }

        public int getSpaceId() {
            return spaceId;
        }

        public void setSpaceId(int spaceId) {
            this.spaceId = spaceId;
        }
    }
}
