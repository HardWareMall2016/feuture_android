package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

/**
 * Created by Administrator on 2016/5/12.
 */
public class RegisterNextResponseBean extends BaseResponseBean {
    /**
     * personId : 116
     * accountName : twl_13166201140
     * peopleName : 13166201140
     * peopleSex : null
     * peopleAge : null
     * idCardNo : null
     * phone : 13166201140
     * jobId : 0
     * jobName : null
     * companyId : 0
     * companyName : null
     * departmentId : 0
     * departmentName : null
     * schoolName : null
     * token : 7c768bfa65da47b3a33c1847eabc8660
     * realSalary : 0.0
     * lastTimeJobTime : 1467083608000
     * imgUrl : null
     * id : 0
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int personId;
        private String accountName;
        private String peopleName;
        private Object peopleSex;
        private Object peopleAge;
        private Object idCardNo;
        private String phone;
        private int jobId;
        private Object jobName;
        private int companyId;
        private Object companyName;
        private int departmentId;
        private Object departmentName;
        private Object schoolName;
        private String token;
        private double realSalary;
        private long lastTimeJobTime;
        private String imgUrl;
        private int id;

        public void setPersonId(int personId) {
            this.personId = personId;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public void setPeopleName(String peopleName) {
            this.peopleName = peopleName;
        }

        public void setPeopleSex(Object peopleSex) {
            this.peopleSex = peopleSex;
        }

        public void setPeopleAge(Object peopleAge) {
            this.peopleAge = peopleAge;
        }

        public void setIdCardNo(Object idCardNo) {
            this.idCardNo = idCardNo;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setJobId(int jobId) {
            this.jobId = jobId;
        }

        public void setJobName(Object jobName) {
            this.jobName = jobName;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public void setCompanyName(Object companyName) {
            this.companyName = companyName;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public void setDepartmentName(Object departmentName) {
            this.departmentName = departmentName;
        }

        public void setSchoolName(Object schoolName) {
            this.schoolName = schoolName;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setRealSalary(double realSalary) {
            this.realSalary = realSalary;
        }

        public void setLastTimeJobTime(long lastTimeJobTime) {
            this.lastTimeJobTime = lastTimeJobTime;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPersonId() {
            return personId;
        }

        public String getAccountName() {
            return accountName;
        }

        public String getPeopleName() {
            return peopleName;
        }

        public Object getPeopleSex() {
            return peopleSex;
        }

        public Object getPeopleAge() {
            return peopleAge;
        }

        public Object getIdCardNo() {
            return idCardNo;
        }

        public String getPhone() {
            return phone;
        }

        public int getJobId() {
            return jobId;
        }

        public Object getJobName() {
            return jobName;
        }

        public int getCompanyId() {
            return companyId;
        }

        public Object getCompanyName() {
            return companyName;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public Object getDepartmentName() {
            return departmentName;
        }

        public Object getSchoolName() {
            return schoolName;
        }

        public String getToken() {
            return token;
        }

        public double getRealSalary() {
            return realSalary;
        }

        public long getLastTimeJobTime() {
            return lastTimeJobTime;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public int getId() {
            return id;
        }
    }
}
