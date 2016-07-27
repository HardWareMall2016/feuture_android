package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by WuYue on 2016/5/23.
 */
public class GetDeptPostResponse extends BaseResponseBean {

    /**
     * deptId : 1
     * deptName : 铁未来1_管理部门
     * companyId : 1
     * deptType : DEPT_BASE_MANAGE
     * branch : DEPT_BASE_BRANCH
     * budget : 10000
     * remark : null
     * createTime : 1463643690000
     * updaeTime : 1463967815000
     * endLockTime : 1463989290000
     * postList : [{"postId":1,"postName":"企业法人","deptType":"DEPT_BASE_MANAGE","remark":"1","state":"1","createTime":1459329807000,"updateTime":1463644706000},{"postId":2,"postName":"管理员","deptType":"DEPT_BASE_MANAGE","remark":"0","state":"1","createTime":1459329882000,"updateTime":1463644704000}]
     */

    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        private int deptId;
        private String deptName;
        private int companyId;
        private String deptType;
        private String branch;
        private int budget;
        private Object remark;
        private long createTime;
        private long updaeTime;
        private long endLockTime;
        /**
         * postId : 1
         * postName : 企业法人
         * deptType : DEPT_BASE_MANAGE
         * remark : 1
         * state : 1
         * createTime : 1459329807000
         * updateTime : 1463644706000
         */

        private List<PostListEntity> postList;

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getDeptType() {
            return deptType;
        }

        public void setDeptType(String deptType) {
            this.deptType = deptType;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdaeTime() {
            return updaeTime;
        }

        public void setUpdaeTime(long updaeTime) {
            this.updaeTime = updaeTime;
        }

        public long getEndLockTime() {
            return endLockTime;
        }

        public void setEndLockTime(long endLockTime) {
            this.endLockTime = endLockTime;
        }

        public List<PostListEntity> getPostList() {
            return postList;
        }

        public void setPostList(List<PostListEntity> postList) {
            this.postList = postList;
        }

        public static class PostListEntity {
            private int postId;
            private String postName;
            private String deptType;
            private String remark;
            private String state;
            private long createTime;
            private long updateTime;

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public String getPostName() {
                return postName;
            }

            public void setPostName(String postName) {
                this.postName = postName;
            }

            public String getDeptType() {
                return deptType;
            }

            public void setDeptType(String deptType) {
                this.deptType = deptType;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
