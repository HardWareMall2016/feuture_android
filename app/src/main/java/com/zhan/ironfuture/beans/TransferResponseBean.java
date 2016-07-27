package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class TransferResponseBean extends BaseResponseBean{

    /**
     * comId : 1
     * comName : 苹果ccc
     * comType : 民营
     * comNation : 中国
     * comDescribe : null
     * comCreatTime : 1462357128000
     * comState : 1
     * comSumMoney : 447998
     * surplusMoney : 174000
     * comLogo : www.baidu.com
     */

    private List<DataEntity> data;


    public void setData(List<DataEntity> data) {
        this.data = data;
    }


    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int comId;
        private String comName;
        private String comType;
        private String comNation;
        private Object comDescribe;
        private long comCreatTime;
        private int comState;
        private int comSumMoney;
        private int surplusMoney;
        private String comLogo;

        public void setComId(int comId) {
            this.comId = comId;
        }

        public void setComName(String comName) {
            this.comName = comName;
        }

        public void setComType(String comType) {
            this.comType = comType;
        }

        public void setComNation(String comNation) {
            this.comNation = comNation;
        }

        public void setComDescribe(Object comDescribe) {
            this.comDescribe = comDescribe;
        }

        public void setComCreatTime(long comCreatTime) {
            this.comCreatTime = comCreatTime;
        }

        public void setComState(int comState) {
            this.comState = comState;
        }

        public void setComSumMoney(int comSumMoney) {
            this.comSumMoney = comSumMoney;
        }

        public void setSurplusMoney(int surplusMoney) {
            this.surplusMoney = surplusMoney;
        }

        public void setComLogo(String comLogo) {
            this.comLogo = comLogo;
        }

        public int getComId() {
            return comId;
        }

        public String getComName() {
            return comName;
        }

        public String getComType() {
            return comType;
        }

        public String getComNation() {
            return comNation;
        }

        public Object getComDescribe() {
            return comDescribe;
        }

        public long getComCreatTime() {
            return comCreatTime;
        }

        public int getComState() {
            return comState;
        }

        public int getComSumMoney() {
            return comSumMoney;
        }

        public int getSurplusMoney() {
            return surplusMoney;
        }

        public String getComLogo() {
            return comLogo;
        }
    }
}
