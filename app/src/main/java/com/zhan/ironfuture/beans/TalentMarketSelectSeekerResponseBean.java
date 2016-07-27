package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class TalentMarketSelectSeekerResponseBean extends BaseResponseBean{

    private List<DataEntity> data;


    public void setData(List<DataEntity> data) {
        this.data = data;
    }


    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int talentsId;
        private int anticipateSalary;
        private int personId;
        private int postId;
        private int comId;
        private int deptId;
        private int realSalaryLast;
        private int peopleJobState;
        private long departureTime;
        private long createTime;
        private String departName;
        private String comName;
        private String postName;
        private String accountName;
        private String peopleName;
        private int schoolId;
        private String imgUrl;

        public void setTalentsId(int talentsId) {
            this.talentsId = talentsId;
        }

        public void setAnticipateSalary(int anticipateSalary) {
            this.anticipateSalary = anticipateSalary;
        }

        public void setPersonId(int personId) {
            this.personId = personId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public void setComId(int comId) {
            this.comId = comId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public void setRealSalaryLast(int realSalaryLast) {
            this.realSalaryLast = realSalaryLast;
        }

        public void setPeopleJobState(int peopleJobState) {
            this.peopleJobState = peopleJobState;
        }

        public void setDepartureTime(long departureTime) {
            this.departureTime = departureTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public void setComName(String comName) {
            this.comName = comName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public void setPeopleName(String peopleName) {
            this.peopleName = peopleName;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public int getTalentsId() {
            return talentsId;
        }

        public int getAnticipateSalary() {
            return anticipateSalary;
        }

        public int getPersonId() {
            return personId;
        }

        public int getPostId() {
            return postId;
        }

        public int getComId() {
            return comId;
        }

        public int getDeptId() {
            return deptId;
        }

        public int getRealSalaryLast() {
            return realSalaryLast;
        }

        public int getPeopleJobState() {
            return peopleJobState;
        }

        public long getDepartureTime() {
            return departureTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public String getDepartName() {
            return departName;
        }

        public String getComName() {
            return comName;
        }

        public String getPostName() {
            return postName;
        }

        public String getAccountName() {
            return accountName;
        }

        public String getPeopleName() {
            return peopleName;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
