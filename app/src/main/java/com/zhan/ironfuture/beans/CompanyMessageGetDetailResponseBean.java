package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class CompanyMessageGetDetailResponseBean extends BaseResponseBean{

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
     * accountName : 苹果ccc
     * replyMsgList : [{"msgId":10,"pMsgId":9,"msgType":"MSG_USER","msgContent":"test 内容 回复","msgState":0,"sendUserId":1,"receiver":1,"createTime":1463033710000,"readTime":1463120421000,"receiveFlag":null,"title":null,"businessID":null,"accountName":"18626348698"},{"msgId":23,"pMsgId":9,"msgType":"MSG_USER","msgContent":"Try","msgState":0,"sendUserId":1,"receiver":1,"createTime":1463375668000,"readTime":1463375668000,"receiveFlag":1,"title":null,"businessID":null,"accountName":"18626348698"},{"msgId":24,"pMsgId":9,"msgType":"MSG_USER","msgContent":"Tryrrrr","msgState":0,"sendUserId":1,"receiver":1,"createTime":1463375740000,"readTime":1463375740000,"receiveFlag":1,"title":null,"businessID":null,"accountName":"18626348698"},{"msgId":25,"pMsgId":9,"msgType":"MSG_USER","msgContent":"Wee","msgState":0,"sendUserId":1,"receiver":1,"createTime":1463376269000,"readTime":1463376269000,"receiveFlag":1,"title":null,"businessID":null,"accountName":"18626348698"},{"msgId":26,"pMsgId":9,"msgType":"MSG_USER","msgContent":"Look","msgState":0,"sendUserId":1,"receiver":1,"createTime":1463376353000,"readTime":1463376353000,"receiveFlag":1,"title":null,"businessID":null,"accountName":"18626348698"},{"msgId":27,"pMsgId":9,"msgType":"MSG_USER","msgContent":"Uuuuu","msgState":0,"sendUserId":1,"receiver":1,"createTime":1463377775000,"readTime":1463377775000,"receiveFlag":1,"title":null,"businessID":null,"accountName":"18626348698"},{"msgId":31,"pMsgId":9,"msgType":"MSG_USER","msgContent":"Uu","msgState":0,"sendUserId":1,"receiver":1,"createTime":1463477713000,"readTime":1463477732000,"receiveFlag":1,"title":null,"businessID":null,"accountName":"18626348698"},{"msgId":32,"pMsgId":9,"msgType":"MSG_USER","msgContent":"Uupp","msgState":0,"sendUserId":1,"receiver":1,"createTime":1463477719000,"readTime":1463477737000,"receiveFlag":1,"title":null,"businessID":null,"accountName":"18626348698"},{"msgId":37,"pMsgId":9,"msgType":"MSG_USER","msgContent":"test 内容","msgState":0,"sendUserId":4,"receiver":1,"createTime":1463484119000,"readTime":1463484138000,"receiveFlag":1,"title":"tese","businessID":null,"accountName":"13166201138"},{"msgId":38,"pMsgId":9,"msgType":"MSG_USER","msgContent":"test 内容","msgState":0,"sendUserId":4,"receiver":1,"createTime":1463485308000,"readTime":1463485326000,"receiveFlag":1,"title":"tese","businessID":null,"accountName":"13166201138"},{"msgId":39,"pMsgId":9,"msgType":"MSG_USER","msgContent":"test 内容","msgState":0,"sendUserId":4,"receiver":1,"createTime":1463485322000,"readTime":1463485341000,"receiveFlag":1,"title":"tese","businessID":null,"accountName":"13166201138"},{"msgId":40,"pMsgId":9,"msgType":"MSG_USER","msgContent":"test 内容","msgState":0,"sendUserId":4,"receiver":1,"createTime":1463485354000,"readTime":1463485373000,"receiveFlag":1,"title":"tese","businessID":null,"accountName":"13166201138"}]
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
        private Object pMsgId;
        private String msgType;
        private String msgContent;
        private int msgState;
        private int sendUserId;
        private int receiver;
        private long createTime;
        private long readTime;
        private int receiveFlag;
        private String title;
        private Object businessID;
        private String accountName;
        /**
         * msgId : 10
         * pMsgId : 9
         * msgType : MSG_USER
         * msgContent : test 内容 回复
         * msgState : 0
         * sendUserId : 1
         * receiver : 1
         * createTime : 1463033710000
         * readTime : 1463120421000
         * receiveFlag : null
         * title : null
         * businessID : null
         * accountName : 18626348698
         */

        private List<ReplyMsgListEntity> replyMsgList;

        public void setMsgId(int msgId) {
            this.msgId = msgId;
        }

        public void setPMsgId(Object pMsgId) {
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

        public void setBusinessID(Object businessID) {
            this.businessID = businessID;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public void setReplyMsgList(List<ReplyMsgListEntity> replyMsgList) {
            this.replyMsgList = replyMsgList;
        }

        public int getMsgId() {
            return msgId;
        }

        public Object getPMsgId() {
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

        public Object getBusinessID() {
            return businessID;
        }

        public String getAccountName() {
            return accountName;
        }

        public List<ReplyMsgListEntity> getReplyMsgList() {
            return replyMsgList;
        }

        public static class ReplyMsgListEntity {
            private int msgId;
            private int pMsgId;
            private String msgType;
            private String msgContent;
            private int msgState;
            private int sendUserId;
            private int receiver;
            private long createTime;
            private long readTime;
            private Object receiveFlag;
            private Object title;
            private Object businessID;
            private String accountName;
            private String msgLogo;
            private String personLogo ;

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

            public void setReceiveFlag(Object receiveFlag) {
                this.receiveFlag = receiveFlag;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public void setBusinessID(Object businessID) {
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

            public Object getReceiveFlag() {
                return receiveFlag;
            }

            public Object getTitle() {
                return title;
            }

            public Object getBusinessID() {
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

            public String getPersonLogo() {
                return personLogo;
            }

            public void setPersonLogo(String personLogo) {
                this.personLogo = personLogo;
            }
        }
    }
}
