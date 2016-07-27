package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class JobMarketResponseBean extends BaseResponseBean{
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * inviteId : 20
         * comId : 3
         * postId : 14
         * recruitmentSum : 1
         * jobDescribe : 天涯海角个
         * salary : 10.0
         * recState : 1
         * pubTime : 1467610598000
         * comName : 金未来
         * comLogo : http://172.18.0.51:8080/tiewl///IMAGE/REALnull/2016/1467603121004.gif
         * postName : 物流部门职工
         */

        private List<JobComListEntity> jobComList;
        /**
         * id : 31
         * name : 管理层部门
         * comLogo : null
         */

        private List<DeptListEntity> deptList;

        public void setJobComList(List<JobComListEntity> jobComList) {
            this.jobComList = jobComList;
        }

        public void setDeptList(List<DeptListEntity> deptList) {
            this.deptList = deptList;
        }

        public List<JobComListEntity> getJobComList() {
            return jobComList;
        }

        public List<DeptListEntity> getDeptList() {
            return deptList;
        }

        public static class JobComListEntity {
            private int inviteId;
            private int comId;
            private int postId;
            private int recruitmentSum;
            private String jobDescribe;
            private double salary;
            private int recState;
            private long pubTime;
            private String comName;
            private String comLogo;
            private String postName;

            public void setInviteId(int inviteId) {
                this.inviteId = inviteId;
            }

            public void setComId(int comId) {
                this.comId = comId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public void setRecruitmentSum(int recruitmentSum) {
                this.recruitmentSum = recruitmentSum;
            }

            public void setJobDescribe(String jobDescribe) {
                this.jobDescribe = jobDescribe;
            }

            public void setSalary(double salary) {
                this.salary = salary;
            }

            public void setRecState(int recState) {
                this.recState = recState;
            }

            public void setPubTime(long pubTime) {
                this.pubTime = pubTime;
            }

            public void setComName(String comName) {
                this.comName = comName;
            }

            public void setComLogo(String comLogo) {
                this.comLogo = comLogo;
            }

            public void setPostName(String postName) {
                this.postName = postName;
            }

            public int getInviteId() {
                return inviteId;
            }

            public int getComId() {
                return comId;
            }

            public int getPostId() {
                return postId;
            }

            public int getRecruitmentSum() {
                return recruitmentSum;
            }

            public String getJobDescribe() {
                return jobDescribe;
            }

            public double getSalary() {
                return salary;
            }

            public int getRecState() {
                return recState;
            }

            public long getPubTime() {
                return pubTime;
            }

            public String getComName() {
                return comName;
            }

            public String getComLogo() {
                return comLogo;
            }

            public String getPostName() {
                return postName;
            }
        }

        public static class DeptListEntity {
            private int id;
            private String name;
            private Object comLogo;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setComLogo(Object comLogo) {
                this.comLogo = comLogo;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public Object getComLogo() {
                return comLogo;
            }
        }
    }
}
