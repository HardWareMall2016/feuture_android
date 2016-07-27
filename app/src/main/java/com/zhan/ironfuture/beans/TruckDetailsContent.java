package com.zhan.ironfuture.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/27.
 */
public class TruckDetailsContent implements Serializable {
    private int carId ;
    private int rodeLineId ;
    private int comId ;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }


    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getRodeLineId() {
        return rodeLineId;
    }

    public void setRodeLineId(int rodeLineId) {
        this.rodeLineId = rodeLineId;
    }

}
