package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/23.
 */
public class JobMarketRequestJobRequestBean extends BaseRequestBean{
    /**
     * inviteId : 2
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int inviteId;

        public void setInviteId(int inviteId) {
            this.inviteId = inviteId;
        }

        public int getInviteId() {
            return inviteId;
        }
    }
}
