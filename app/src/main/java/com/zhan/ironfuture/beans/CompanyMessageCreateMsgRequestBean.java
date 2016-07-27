package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/18.
 */
public class CompanyMessageCreateMsgRequestBean extends BaseRequestBean{
    /**
     * userID : 1
     * token : 13frafga
     * data : {"title":"tese","msgContent":"test 内容","receiver":"1","msgId":"1"}
     */

    /**
     * title : tese
     * msgContent : test 内容
     * receiver : 1
     * msgId : 1
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String title;
        private String msgContent;
        private int receiver;
        private Integer msgId;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public int getReceiver() {
            return receiver;
        }

        public void setReceiver(int receiver) {
            this.receiver = receiver;
        }

        public Integer getMsgId() {
            return msgId;
        }

        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
        }
    }
}
