package com.zhan.ironfuture.beans;


import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/3/7.
 */
public class LoginRequestBean extends BaseRequestBean {
    /**
     * LoginName : ashley@zhan.com
     * PassWord : 96E79218965EB72C92A5
     */

    private DataEntity Data;

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public DataEntity getData() {
        return Data;
    }

    public static class DataEntity {
        private String LoginName;
        private String PassWord;

        public void setLoginName(String LoginName) {
            this.LoginName = LoginName;
        }

        public void setPassWord(String PassWord) {
            this.PassWord = PassWord;
        }

        public String getLoginName() {
            return LoginName;
        }

        public String getPassWord() {
            return PassWord;
        }
    }
}
