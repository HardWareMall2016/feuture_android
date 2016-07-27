package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

/**
 * Created by Administrator on 2016/5/12.
 */
public class LoginsResponseBean extends BaseResponseBean {
    /**
     * personId : 1
     * accountName : 18626348698
     * peopleName : 曾理
     * peopleSex : null
     * peopleAge : null
     * idCardNo : null
     * phone : 18626348698
     * jobId : 13
     * jobName : null
     * companyId : 1
     * companyName : 物流部门经理
     * departmentId : 1
     * departmentName : null
     * schoolName : 上海大学
     * token : 2eeaa6a458ae4af2984f39f7576c01e2
     * realSalary : 0
     * lastTimeJobTime : 1463642179000
     * imgUrl : www.baidu.com
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
        private String companyName;
        private int departmentId;
        private Object departmentName;
        private String schoolName;
        private String token;
        private int realSalary;
        private long lastTimeJobTime;
        private String imgUrl;

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

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public void setDepartmentName(Object departmentName) {
            this.departmentName = departmentName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setRealSalary(int realSalary) {
            this.realSalary = realSalary;
        }

        public void setLastTimeJobTime(long lastTimeJobTime) {
            this.lastTimeJobTime = lastTimeJobTime;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
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

        public String getCompanyName() {
            return companyName;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public Object getDepartmentName() {
            return departmentName;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public String getToken() {
            return token;
        }

        public int getRealSalary() {
            return realSalary;
        }

        public long getLastTimeJobTime() {
            return lastTimeJobTime;
        }

        public String getImgUrl() {
            return imgUrl;
        }
    }
}
