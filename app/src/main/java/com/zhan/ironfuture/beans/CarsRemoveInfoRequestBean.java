package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/6/15.
 */
public class CarsRemoveInfoRequestBean extends BaseRequestBean{
    /**
     * comId : 1
     * isHandling : 1
     * pageDirection : 1
     * carId : 6
     * pageId : 0
     * pageCount : 5
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
        private String isHandling;
        private String salary ;
        private String isUrgent ;
        private int pageDirection;
        private String carId;
        private int pageId;
        private int pageCount;

        public String getIsUrgent() {
            return isUrgent;
        }

        public void setIsUrgent(String isUrgent) {
            this.isUrgent = isUrgent;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public void setComId(String comId) {
            this.comId = comId;
        }

        public void setIsHandling(String isHandling) {
            this.isHandling = isHandling;
        }

        public void setPageDirection(int pageDirection) {
            this.pageDirection = pageDirection;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public void setPageId(int pageId) {
            this.pageId = pageId;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public String getComId() {
            return comId;
        }

        public String getIsHandling() {
            return isHandling;
        }

        public int getPageDirection() {
            return pageDirection;
        }

        public String getCarId() {
            return carId;
        }

        public int getPageId() {
            return pageId;
        }

        public int getPageCount() {
            return pageCount;
        }
    }
}
