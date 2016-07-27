package com.zhan.ironfuture.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/30.
 */
public class StevedoringDetailContent implements Serializable {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
    public int getOrderGoodsCount() {
        return orderGoodsCount;
    }

    public void setOrderGoodsCount(int orderGoodsCount) {
        this.orderGoodsCount = orderGoodsCount;
    }


    private String title ;
    private int orderId ;
    private int carId ;
    private int orderGoodsCount ;
}
