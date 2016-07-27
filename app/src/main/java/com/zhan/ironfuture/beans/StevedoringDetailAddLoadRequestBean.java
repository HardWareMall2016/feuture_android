package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15.
 */
public class StevedoringDetailAddLoadRequestBean extends BaseRequestBean{
    /**
     * addSum : 1
     * orderId : 20
     * spaceId : 0
     * carId : 6
     * goodsId : 4
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int addSum;
        private String orderId;
        private String spaceId;
        private String carId;
        private String goodsId;

        public void setAddSum(int addSum) {
            this.addSum = addSum;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public void setSpaceId(String spaceId) {
            this.spaceId = spaceId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public int getAddSum() {
            return addSum;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getSpaceId() {
            return spaceId;
        }

        public String getCarId() {
            return carId;
        }

        public String getGoodsId() {
            return goodsId;
        }
    }
}
