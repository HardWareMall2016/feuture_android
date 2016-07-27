package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/17.
 */
public class CompanyMessageGetDetailRequestBean extends BaseRequestBean{
    /**
     * msgId : 9
     */

    private DataEntity data;


    public void setData(DataEntity data) {
        this.data = data;
    }


    public DataEntity getData() {
        return data;
    }


    public static class DataEntity {
        private int msgId;

        public int getMsgId() {
            return msgId;
        }

        public void setMsgId(int msgId) {
            this.msgId = msgId;
        }
    }
}
