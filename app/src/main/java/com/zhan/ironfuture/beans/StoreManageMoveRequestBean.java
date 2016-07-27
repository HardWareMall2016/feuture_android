package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/20.
 */
public class StoreManageMoveRequestBean extends BaseRequestBean{
    /**
     * nowSpaceId : 1
     * afterSpaceId : 1
     * goodsCount : 3
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }


    public DataEntity getData() {
        return data;
    }


    public static class DataEntity {
        private int nowSpaceId;
        private int afterSpaceId;
        private String goodsCount;

        public void setNowSpaceId(int nowSpaceId) {
            this.nowSpaceId = nowSpaceId;
        }

        public void setAfterSpaceId(int afterSpaceId) {
            this.afterSpaceId = afterSpaceId;
        }

        public void setGoodsCount(String goodsCount) {
            this.goodsCount = goodsCount;
        }

        public int getNowSpaceId() {
            return nowSpaceId;
        }

        public int getAfterSpaceId() {
            return afterSpaceId;
        }

        public String getGoodsCount() {
            return goodsCount;
        }
    }
}
