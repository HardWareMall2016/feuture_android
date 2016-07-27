package com.zhan.ironfuture.base;

public class BaseResponseBean {
    /**
     * code : 0
     * message :
     */
    private int code;
    private String message;
    private long serverTime;

    public void setCode(int Code) {
        this.code = Code;
    }

    public void setMessage(String Message) {
        this.message = Message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }
}
