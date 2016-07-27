package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/31.
 */
public class StaffListDetailResponseBean extends BaseResponseBean{
    /**
     * postId : 14
     * postName : 物流部门职工
     * deptType : DEPT_TRADE_LOGISTICS
     * remark : 0
     * state : 1
     * createTime : 1459396531000
     * updateTime : 1463644692000
     * proLineList : [{"id":2,"name":"路线5,车辆2"}]
     * carsList : [{"id":2,"name":"路线5,车辆2"},{"id":3,"name":"路线6,车辆3"},{"id":4,"name":"路线5,车辆4"},{"id":5,"name":"路线7,车辆5"},{"id":6,"name":"路线5,车辆6"},{"id":7,"name":"路线5,车辆7"},{"id":16,"name":"路线21,车辆16"},{"id":17,"name":"路线22,车辆17"},{"id":18,"name":"路线23,车辆18"},{"id":39,"name":"路线41,车辆39"},{"id":40,"name":"路线44,车辆40"},{"id":41,"name":"路线45,车辆41"},{"id":42,"name":"路线46,车辆42"},{"id":43,"name":"路线47,车辆43"},{"id":44,"name":"路线39,车辆44"},{"id":47,"name":"路线39,车辆47"},{"id":48,"name":"路线39,车辆48"},{"id":49,"name":"路线39,车辆49"},{"id":61,"name":"路线41,车辆61"},{"id":62,"name":"路线41,车辆62"},{"id":63,"name":"路线61,车辆63"},{"id":64,"name":"路线62,车辆64"},{"id":65,"name":"路线63,车辆65"},{"id":66,"name":"路线64,车辆66"},{"id":67,"name":"路线65,车辆67"},{"id":68,"name":"路线66,车辆68"},{"id":69,"name":"路线67,车辆69"},{"id":70,"name":"路线68,车辆70"},{"id":71,"name":"路线69,车辆71"},{"id":72,"name":"路线70,车辆72"},{"id":73,"name":"路线71,车辆73"},{"id":74,"name":"路线72,车辆74"},{"id":75,"name":"路线73,车辆75"},{"id":76,"name":"路线74,车辆76"},{"id":77,"name":"路线75,车辆77"},{"id":78,"name":"路线76,车辆78"},{"id":79,"name":"路线77,车辆79"},{"id":80,"name":"路线78,车辆80"},{"id":81,"name":"路线79,车辆81"}]
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int postId;
        private String postName;
        private String deptType;
        private String remark;
        private String state;
        private long createTime;
        private long updateTime;
        /**
         * id : 2
         * name : 路线5,车辆2
         */

        private List<ProLineListEntity> proLineList;
        /**
         * id : 2
         * name : 路线5,车辆2
         */

        private List<CarsListEntity> carsList;

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public void setDeptType(String deptType) {
            this.deptType = deptType;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public void setProLineList(List<ProLineListEntity> proLineList) {
            this.proLineList = proLineList;
        }

        public void setCarsList(List<CarsListEntity> carsList) {
            this.carsList = carsList;
        }

        public int getPostId() {
            return postId;
        }

        public String getPostName() {
            return postName;
        }

        public String getDeptType() {
            return deptType;
        }

        public String getRemark() {
            return remark;
        }

        public String getState() {
            return state;
        }

        public long getCreateTime() {
            return createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public List<ProLineListEntity> getProLineList() {
            return proLineList;
        }

        public List<CarsListEntity> getCarsList() {
            return carsList;
        }

        public static class ProLineListEntity {
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

        public static class CarsListEntity {
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
