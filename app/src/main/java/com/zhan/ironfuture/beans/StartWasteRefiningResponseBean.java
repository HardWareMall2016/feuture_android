package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

/**
 * Created by WuYue on 2016/5/27.
 */
public class StartWasteRefiningResponseBean extends BaseResponseBean{

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private long unlockTime;

        public long getUnlockTime() {
            return unlockTime;
        }

        public void setUnlockTime(long unlockTime) {
            this.unlockTime = unlockTime;
        }
    }
}
