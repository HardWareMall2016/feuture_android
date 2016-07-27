package com.zhan.ironfuture.base;

/**
 * Created by WuYue on 2015/12/10.
 */
public class BaseRequestBean {

    public BaseRequestBean(){
        if(UserInfo.getCurrentUser()!=null){
            setUserID(UserInfo.getCurrentUser().getUserID());
            setToken(UserInfo.getCurrentUser().getToken());
        }
    }

    /**
     * token :
     * Data : {}
     * userID :
     */

    private String token;
    private int userID;

    public void setToken(String Token) {
        this.token = Token;
    }

    public void setUserID(int UserId) {
        this.userID = UserId;
    }

    public String getToken() {
        return token;
    }

    public int getUserID() {
        return userID;
    }
}
