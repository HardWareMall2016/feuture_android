package com.zhan.ironfuture.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/16.
 */
public class RemoveStevedoringDetailContent implements Serializable {
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
    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }
    public String getmIsHandling() {
        return mIsHandling;
    }

    public void setmIsHandling(String mIsHandling) {
        this.mIsHandling = mIsHandling;
    }

    private String title ;
    private int orderId ;
    private int carId ;
    private int orderGoodsCount ;
    private int comId ;
    private String mIsHandling ;


}
