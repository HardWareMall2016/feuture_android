package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/6/15.
 */
public class GetProLinePersonInfoRequestBean extends BaseRequestBean{
    /**
     * state : 22
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int state;

        public void setState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
