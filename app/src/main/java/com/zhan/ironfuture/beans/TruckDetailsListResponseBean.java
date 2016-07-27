package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
 */
public class TruckDetailsListResponseBean extends BaseResponseBean{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * rodeLineId : 39
         * cbList : [{"carId":34,"carName":null,"comName":null,"peopleSum":0,"fuelSum":20,"addFuel":0},{"carId":44,"carName":null,"comName":null,"peopleSum":0,"fuelSum":10,"addFuel":0}]
         */

        private List<CarsInfoListEntity> carsInfoList;

        public void setCarsInfoList(List<CarsInfoListEntity> carsInfoList) {
            this.carsInfoList = carsInfoList;
        }

        public List<CarsInfoListEntity> getCarsInfoList() {
            return carsInfoList;
        }

        public static class CarsInfoListEntity {
            private int rodeLineId;
            /**
             * carId : 34
             * carName : null
             * comName : null
             * peopleSum : 0
             * fuelSum : 20
             * addFuel : 0
             */

            private List<CbListEntity> cbList;

            public void setRodeLineId(int rodeLineId) {
                this.rodeLineId = rodeLineId;
            }

            public void setCbList(List<CbListEntity> cbList) {
                this.cbList = cbList;
            }

            public int getRodeLineId() {
                return rodeLineId;
            }

            public List<CbListEntity> getCbList() {
                return cbList;
            }

            public static class CbListEntity {
                private int carId;
                private String carName;
                private String comName;
                private int peopleSum;
                private int fuelSum;
                private int addFuel;
                private int comId ;
                private String alreadyMax;

                public int getComId() {
                    return comId;
                }

                public void setComId(int comId) {
                    this.comId = comId;
                }


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

                public void setFuelSum(int fuelSum) {
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

                public int getFuelSum() {
                    return fuelSum;
                }

                public int getAddFuel() {
                    return addFuel;
                }

                public String getAlreadyMax() {
                    return alreadyMax;
                }

                public void setAlreadyMax(String alreadyMax) {
                    this.alreadyMax = alreadyMax;
                }
            }
        }
    }
}
