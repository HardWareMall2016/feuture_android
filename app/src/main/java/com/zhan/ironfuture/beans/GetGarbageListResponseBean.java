package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by WuYue on 2016/5/26.
 */
public class GetGarbageListResponseBean extends BaseResponseBean{

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int state;
        private long unlockTime;

        private List<EndGoodsInfoEntity> endGoodsInfo;
        private List<GoodsBackListEntity> goodsBackList;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getUnlockTime() {
            return unlockTime;
        }

        public void setUnlockTime(long unlockTime) {
            this.unlockTime = unlockTime;
        }

        public List<EndGoodsInfoEntity> getEndGoodsInfo() {
            return endGoodsInfo;
        }

        public void setEndGoodsInfo(List<EndGoodsInfoEntity> endGoodsInfo) {
            this.endGoodsInfo = endGoodsInfo;
        }

        public List<GoodsBackListEntity> getGoodsBackList() {
            return goodsBackList;
        }

        public void setGoodsBackList(List<GoodsBackListEntity> goodsBackList) {
            this.goodsBackList = goodsBackList;
        }

        public static class EndGoodsInfoEntity {
            private int goodsId;
            private String goodsName;
            private int goodsSum;

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getGoodsSum() {
                return goodsSum;
            }

            public void setGoodsSum(int goodsSum) {
                this.goodsSum = goodsSum;
            }
        }

        public static class GoodsBackListEntity {
            private int spaceId;
            private int goodsId;
            private String goodsName;
            private int goodsSum;

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getGoodsSum() {
                return goodsSum;
            }

            public void setGoodsSum(int goodsSum) {
                this.goodsSum = goodsSum;
            }

            public int getSpaceId() {
                return spaceId;
            }

            public void setSpaceId(int spaceId) {
                this.spaceId = spaceId;
            }
        }
    }
}
