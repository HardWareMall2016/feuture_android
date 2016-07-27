package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by WuYue on 2016/5/25.
 */
public class DiscardGoodsRequestBean extends BaseRequestBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int afterSpaceId;
        private int goodsCount;

        public int getAfterSpaceId() {
            return afterSpaceId;
        }

        public void setAfterSpaceId(int afterSpaceId) {
            this.afterSpaceId = afterSpaceId;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }
    }
}
