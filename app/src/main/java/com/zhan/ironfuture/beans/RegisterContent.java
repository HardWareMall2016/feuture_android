package com.zhan.ironfuture.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/11.
 */
public class RegisterContent implements Serializable {

    private String phoneNum;
    private String codeNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }
}