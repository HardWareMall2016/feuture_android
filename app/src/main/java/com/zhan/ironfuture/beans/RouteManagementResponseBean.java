package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
public class RouteManagementResponseBean extends BaseResponseBean{

    private DataEntity data;


    public void setData(DataEntity data) {
        this.data = data;
    }


    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * rodeLineId : 5
         * rodeLineName : 运输路线
         * rodeLineSum : 2
         * rodeLineBeginId : 2
         * rodeLineBeginName : 铁未来2
         * rodeLineEndId : 0
         * rodeLineEndName : null
         */

        private List<RlbbListEntity> rlbbList;

        public void setRlbbList(List<RlbbListEntity> rlbbList) {
            this.rlbbList = rlbbList;
        }

        public List<RlbbListEntity> getRlbbList() {
            return rlbbList;
        }

        public static class RlbbListEntity {
            private int rodeLineId;
            private String rodeLineName;
            private int rodeLineSum;
            private int rodeLineBeginId;
            private String rodeLineBeginName;
            private int rodeLineEndId;
            private String rodeLineEndName;

            public void setRodeLineId(int rodeLineId) {
                this.rodeLineId = rodeLineId;
            }

            public void setRodeLineName(String rodeLineName) {
                this.rodeLineName = rodeLineName;
            }

            public void setRodeLineSum(int rodeLineSum) {
                this.rodeLineSum = rodeLineSum;
            }

            public void setRodeLineBeginId(int rodeLineBeginId) {
                this.rodeLineBeginId = rodeLineBeginId;
            }

            public void setRodeLineBeginName(String rodeLineBeginName) {
                this.rodeLineBeginName = rodeLineBeginName;
            }

            public void setRodeLineEndId(int rodeLineEndId) {
                this.rodeLineEndId = rodeLineEndId;
            }

            public void setRodeLineEndName(String rodeLineEndName) {
                this.rodeLineEndName = rodeLineEndName;
            }

            public int getRodeLineId() {
                return rodeLineId;
            }

            public String getRodeLineName() {
                return rodeLineName;
            }

            public int getRodeLineSum() {
                return rodeLineSum;
            }

            public int getRodeLineBeginId() {
                return rodeLineBeginId;
            }

            public String getRodeLineBeginName() {
                return rodeLineBeginName;
            }

            public int getRodeLineEndId() {
                return rodeLineEndId;
            }

            public String getRodeLineEndName() {
                return rodeLineEndName;
            }
        }
    }
}
