package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class BbsListResponseBean extends BaseResponseBean{
    /**
     * msgId : 9
     * pMsgId : null
     * msgType : MSG_USER
     * msgContent : test 内容
     * msgState : 0
     * sendUserId : 1
     * receiver : 1
     * createTime : 1463033406000
     * readTime : 1463120418000
     * receiveFlag : 3
     * title : tese
     * businessID : null
     * accountName : 铁未来1
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int msgId;
        private int pMsgId;
        private String msgType;
        private String msgContent;
        private int msgState;
        private int sendUserId;
        private int receiver;
        private long createTime;
        private long readTime;
        private int receiveFlag;
        private String title;
        private int businessID;
        private String accountName;
        private String msgLogo;

        public void setMsgId(int msgId) {
            this.msgId = msgId;
        }

        public void setPMsgId(int pMsgId) {
            this.pMsgId = pMsgId;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public void setMsgState(int msgState) {
            this.msgState = msgState;
        }

        public void setSendUserId(int sendUserId) {
            this.sendUserId = sendUserId;
        }

        public void setReceiver(int receiver) {
            this.receiver = receiver;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setReadTime(long readTime) {
            this.readTime = readTime;
        }

        public void setReceiveFlag(int receiveFlag) {
            this.receiveFlag = receiveFlag;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setBusinessID(int businessID) {
            this.businessID = businessID;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public int getMsgId() {
            return msgId;
        }

        public int getPMsgId() {
            return pMsgId;
        }

        public String getMsgType() {
            return msgType;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public int getMsgState() {
            return msgState;
        }

        public int getSendUserId() {
            return sendUserId;
        }

        public int getReceiver() {
            return receiver;
        }

        public long getCreateTime() {
            return createTime;
        }

        public long getReadTime() {
            return readTime;
        }

        public int getReceiveFlag() {
            return receiveFlag;
        }

        public String getTitle() {
            return title;
        }

        public int getBusinessID() {
            return businessID;
        }

        public String getAccountName() {
            return accountName;
        }

        public String getMsgLogo() {
            return msgLogo;
        }

        public void setMsgLogo(String msgLogo) {
            this.msgLogo = msgLogo;
        }
    }
}
