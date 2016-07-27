package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/23.
 */
public class RouteManagementDetailAddFullRequestBean extends BaseRequestBean{
    /**
     * carsId : 5
     * fuelCount : 1
     */

    private DataEntity data;
    /**
     * data : {"carsId":5,"fuelCount":1}
     * token : 3f3017720f9d428492c14c8d88619f95
     * userID : 45
     */


    public void setData(DataEntity data) {
        this.data = data;
    }


    public DataEntity getData() {
        return data;
    }



    public static class DataEntity {
        private int carsId;
        private Integer fuelCount;

        public void setCarsId(int carsId) {
            this.carsId = carsId;
        }

        public void setFuelCount(Integer fuelCount) {
            this.fuelCount = fuelCount;
        }

        public int getCarsId() {
            return carsId;
        }

        public Integer getFuelCount() {
            return fuelCount;
        }
    }
}
