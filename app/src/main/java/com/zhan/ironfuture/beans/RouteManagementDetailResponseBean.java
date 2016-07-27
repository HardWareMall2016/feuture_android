package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class RouteManagementDetailResponseBean extends BaseResponseBean{
    /**
     * carList : [{"carId":2,"carName":null,"comName":null,"peopleSum":0,"fuelSum":0,"addFuel":200},{"carId":4,"carName":null,"comName":null,"peopleSum":0,"fuelSum":1,"addFuel":200},{"carId":6,"carName":null,"comName":null,"peopleSum":0,"fuelSum":10,"addFuel":200},{"carId":7,"carName":null,"comName":null,"peopleSum":0,"fuelSum":10,"addFuel":200}]
     * buyNewCar : 1000
     * siteList : [{"id":1,"name":"铁未来1"},{"id":2,"name":"铁未来2"}]
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int buyNewCar;
        /**
         * carId : 2
         * carName : null
         * comName : null
         * peopleSum : 0
         * fuelSum : 0
         * addFuel : 200
         */

        private List<CarListEntity> carList;
        /**
         * id : 1
         * name : 铁未来1
         */

        private List<SiteListEntity> siteList;

        public void setBuyNewCar(int buyNewCar) {
            this.buyNewCar = buyNewCar;
        }

        public void setCarList(List<CarListEntity> carList) {
            this.carList = carList;
        }

        public void setSiteList(List<SiteListEntity> siteList) {
            this.siteList = siteList;
        }

        public int getBuyNewCar() {
            return buyNewCar;
        }

        public List<CarListEntity> getCarList() {
            return carList;
        }

        public List<SiteListEntity> getSiteList() {
            return siteList;
        }

        public static class CarListEntity {
            private int carId;
            private String carName;
            private String comName;
            private int peopleSum;
            private double fuelSum;
            private int addFuel;

            public void setCarId(int carId) {
                this.carId = carId;
            }

            public void setCarName(String carName) {
                this.carName = carName;
            }

            public void setComName(String comName) {
                this.comName = comName;
            }

            public void setPeopleSum(int peopleSum) {
                this.peopleSum = peopleSum;
            }

            public void setFuelSum(double fuelSum) {
                this.fuelSum = fuelSum;
            }

            public void setAddFuel(int addFuel) {
                this.addFuel = addFuel;
            }

            public int getCarId() {
                return carId;
            }

            public String getCarName() {
                return carName;
            }

            public String getComName() {
                return comName;
            }

            public int getPeopleSum() {
                return peopleSum;
            }

            public double getFuelSum() {
                return fuelSum;
            }

            public int getAddFuel() {
                return addFuel;
            }
        }

        public static class SiteListEntity {
            private int id;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
