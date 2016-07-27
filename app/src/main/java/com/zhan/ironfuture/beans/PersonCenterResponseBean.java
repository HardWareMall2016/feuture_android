package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

/**
 * Created by Administrator on 2016/5/23.
 */
public class PersonCenterResponseBean extends BaseResponseBean{
    /**
     * personId : 1
     * accountName : 18626348698
     * peopleName : 曾理
     * peopleSex : 男
     * peopleAge : null
     * idCardNo : null
     * phone : 18626348698
     * jobId : 2
     * jobName : 管理员
     * companyId : 1
     * companyName : 铁未来1
     * departmentId : 1
     * departmentName : 铁未来1_管理部门
     * schoolName : 上海大学
     * token : bafa4a7031cd4c27a8b4e5897fe4edab
     * realSalary : 0
     * lastTimeJobTime : 1463986442000
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
        private String peopleSex;
        private String peopleAge;
        private String idCardNo;
        private String phone;
        private int jobId;
        private String jobName;
        private int companyId;
        private String companyName;
        private int departmentId;
        private String departmentName;
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

        public void setPeopleSex(String peopleSex) {
            this.peopleSex = peopleSex;
        }

        public void setPeopleAge(String peopleAge) {
            this.peopleAge = peopleAge;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setJobId(int jobId) {
            this.jobId = jobId;
        }

        public void setJobName(String jobName) {
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

        public void setDepartmentName(String departmentName) {
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

        public String getPeopleSex() {
            return peopleSex;
        }

        public String getPeopleAge() {
            return peopleAge;
        }

        public String getIdCardNo() {
            return idCardNo;
        }

        public String getPhone() {
            return phone;
        }

        public int getJobId() {
            return jobId;
        }

        public String getJobName() {
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

        public String getDepartmentName() {
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
