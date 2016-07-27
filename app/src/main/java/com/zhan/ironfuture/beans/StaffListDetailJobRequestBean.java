package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/31.
 */
public class StaffListDetailJobRequestBean extends BaseRequestBean{
    /**
     * jobId : 1
     * personId : 1
     * realSalary : 2
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int jobId;
        private int id;
        private int personId;
        private int realSalary;

        public void setJobId(int jobId) {
            this.jobId = jobId;
        }

        public void setPersonId(int personId) {
            this.personId = personId;
        }

        public void setRealSalary(int realSalary) {
            this.realSalary = realSalary;
        }

        public int getJobId() {
            return jobId;
        }

        public int getPersonId() {
            return personId;
        }

        public int getRealSalary() {
            return realSalary;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
