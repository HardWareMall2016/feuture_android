package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

/**
 * Created by WuYue on 2016/5/31.
 */
public class GetProLinePersonInfoResponseBean extends BaseResponseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int prolineId;
        private String prolineName;
        private int proOutputId;
        private String outputName;
        private int personSum;
        private int teamSum;
        private int yesterdayPersonSum;
        private int todayPersonSum;

        public int getProlineId() {
            return prolineId;
        }

        public void setProlineId(int prolineId) {
            this.prolineId = prolineId;
        }

        public String getProlineName() {
            return prolineName;
        }

        public void setProlineName(String prolineName) {
            this.prolineName = prolineName;
        }

        public int getProOutputId() {
            return proOutputId;
        }

        public void setProOutputId(int proOutputId) {
            this.proOutputId = proOutputId;
        }

        public String getOutputName() {
            return outputName;
        }

        public void setOutputName(String outputName) {
            this.outputName = outputName;
        }

        public int getPersonSum() {
            return personSum;
        }

        public void setPersonSum(int personSum) {
            this.personSum = personSum;
        }

        public int getTeamSum() {
            return teamSum;
        }

        public void setTeamSum(int teamSum) {
            this.teamSum = teamSum;
        }

        public int getYesterdayPersonSum() {
            return yesterdayPersonSum;
        }

        public void setYesterdayPersonSum(int yesterdayPersonSum) {
            this.yesterdayPersonSum = yesterdayPersonSum;
        }

        public int getTodayPersonSum() {
            return todayPersonSum;
        }

        public void setTodayPersonSum(int todayPersonSum) {
            this.todayPersonSum = todayPersonSum;
        }
    }
}
