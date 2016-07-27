package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class StoreManageInfoResponseBean extends BaseResponseBean{

    /**
     * storageList : [{"spaceId":1,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":1,"goodsCount":7,"spaceSeqNo":1,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":"R-01"},{"spaceId":2,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":2,"goodsCount":1,"spaceSeqNo":2,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":"R-02"},{"spaceId":3,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":3,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":4,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":4,"goodsCount":0,"spaceSeqNo":4,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":"R-04"},{"spaceId":5,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":4,"goodsCount":2,"spaceSeqNo":5,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":"R-04"},{"spaceId":6,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":1,"goodsCount":7,"spaceSeqNo":6,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":"R-01"},{"spaceId":7,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":7,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":8,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":8,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":9,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":9,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":10,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":10,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":11,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":11,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":12,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":12,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":13,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":13,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":14,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":14,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":15,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":15,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":16,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":16,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":17,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":17,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":18,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":18,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":19,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":19,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":20,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":20,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":21,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":21,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null},{"spaceId":22,"storageId":1,"spaceType":"SHIP_SPACE_DEPOT","goodsId":null,"goodsCount":null,"spaceSeqNo":22,"spaceState":"GOODS_STATE_NORMAL","personId":null,"unlockTime":null,"storageLevel":4,"compId":1,"storageState":1,"createTime":1460598166000,"modifyTime":1460598166000,"comName":"铁未来1","goodsName":null}]
     * storagePrice : 27300
     */

    private DataEntity data;


    public void setData(DataEntity data) {
        this.data = data;
    }


    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int storagePrice;
        /**
         * spaceId : 1
         * storageId : 1
         * spaceType : SHIP_SPACE_DEPOT
         * goodsId : 1
         * goodsCount : 7
         * spaceSeqNo : 1
         * spaceState : GOODS_STATE_NORMAL
         * personId : null
         * unlockTime : null
         * storageLevel : 4
         * compId : 1
         * storageState : 1
         * createTime : 1460598166000
         * modifyTime : 1460598166000
         * comName : 铁未来1
         * goodsName : R-01
         */

        private List<StorageListEntity> storageList;

        public void setStoragePrice(int storagePrice) {
            this.storagePrice = storagePrice;
        }

        public void setStorageList(List<StorageListEntity> storageList) {
            this.storageList = storageList;
        }

        public int getStoragePrice() {
            return storagePrice;
        }

        public List<StorageListEntity> getStorageList() {
            return storageList;
        }

        public static class StorageListEntity {
            private int spaceId;
            private int storageId;
            private String spaceType;
            private int goodsId;
            private int goodsCount;
            private int spaceSeqNo;
            private String spaceState;
            private int personId;
            private long unlockTime;
            private int storageLevel;
            private int compId;
            private int storageState;
            private long createTime;
            private long modifyTime;
            private String comName;
            private String goodsName;
            private int spaceStateInt;//1正常状态2订单状态3不可发起物流4可装卸
            private String goodsType;

            public void setSpaceId(int spaceId) {
                this.spaceId = spaceId;
            }

            public void setStorageId(int storageId) {
                this.storageId = storageId;
            }

            public void setSpaceType(String spaceType) {
                this.spaceType = spaceType;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
            }

            public void setSpaceSeqNo(int spaceSeqNo) {
                this.spaceSeqNo = spaceSeqNo;
            }

            public void setSpaceState(String spaceState) {
                this.spaceState = spaceState;
            }

            public void setPersonId(int personId) {
                this.personId = personId;
            }

            public void setUnlockTime(long unlockTime) {
                this.unlockTime = unlockTime;
            }

            public void setStorageLevel(int storageLevel) {
                this.storageLevel = storageLevel;
            }

            public void setCompId(int compId) {
                this.compId = compId;
            }

            public void setStorageState(int storageState) {
                this.storageState = storageState;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public void setComName(String comName) {
                this.comName = comName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getSpaceId() {
                return spaceId;
            }

            public int getStorageId() {
                return storageId;
            }

            public String getSpaceType() {
                return spaceType;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public int getGoodsCount() {
                return goodsCount;
            }

            public int getSpaceSeqNo() {
                return spaceSeqNo;
            }

            public String getSpaceState() {
                return spaceState;
            }

            public int getPersonId() {
                return personId;
            }

            public long getUnlockTime() {
                return unlockTime;
            }

            public int getStorageLevel() {
                return storageLevel;
            }

            public int getCompId() {
                return compId;
            }

            public int getStorageState() {
                return storageState;
            }

            public long getCreateTime() {
                return createTime;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public String getComName() {
                return comName;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public int getSpaceStateInt() {
                return spaceStateInt;
            }

            public void setSpaceStateInt(int spaceStateInt) {
                this.spaceStateInt = spaceStateInt;
            }

            public String getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(String goodsType) {
                this.goodsType = goodsType;
            }
        }
    }
}
