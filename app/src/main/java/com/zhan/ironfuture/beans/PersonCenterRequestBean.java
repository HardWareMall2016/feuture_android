package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/23.
 */
public class PersonCenterRequestBean extends BaseRequestBean{
    private DataEntity data;
    /**
     * data : {}
     * token : e18af0ef43b848f69066d2e0024c0390
     * userID : 3
     */

    private String token;
    private int userID;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public DataEntity getData() {
        return data;
    }

    public String getToken() {
        return token;
    }

    public int getUserID() {
        return userID;
    }

    public static class DataEntity {
    }
}
