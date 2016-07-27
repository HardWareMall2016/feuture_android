package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/30.
 */
public class StevedoringLoadRequestBean extends BaseRequestBean{
    /**
     * comId : 1
     * carId : 1
     * rodeLineId : 39
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String comId;
        private int carId;
        private int rodeLineId;

        public void setComId(String comId) {
            this.comId = comId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public void setRodeLineId(int rodeLineId) {
            this.rodeLineId = rodeLineId;
        }

        public String getComId() {
            return comId;
        }

        public int getCarId() {
            return carId;
        }

        public int getRodeLineId() {
            return rodeLineId;
        }
    }
}
