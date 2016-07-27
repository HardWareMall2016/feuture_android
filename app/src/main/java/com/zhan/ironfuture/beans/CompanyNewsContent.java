package com.zhan.ironfuture.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/17.
 */
public class CompanyNewsContent implements Serializable {
    public int getSender() {
        return sender;
    }

    public void setSender(int receiver) {
        this.sender = receiver;
    }

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

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    private int sender ;
    private String title ;
    private String msgContent ;
    private int msgId ;
}
