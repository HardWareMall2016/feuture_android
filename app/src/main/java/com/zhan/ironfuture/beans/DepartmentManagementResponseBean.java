package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class DepartmentManagementResponseBean extends BaseResponseBean {

    /**
     * accountName : 13166201138
     * reulstList : [{"deptId":1,"deptName":"管理层部门","companyId":1,"deptType":"DEPT_BASE_MANAGE","branch":"DEPT_BASE_BRANCH","budget":448018,"remark":"公司核心人员","createTime":1459332615000,"updaeTime":1463637526000,"endLockTime":1464158406000,"isTurnOn":0,"deptBussinessID":"DEPT_BASE_MANAGE","turnOnSum":null,"memberList":[{"personId":1,"accountName":"18626348698","password":null,"peopleName":"曾理","peopleSex":null,"peopleAge":null,"idCardNo":null,"phone":"18626348698","jobId":13,"companyId":1,"departmentId":1,"schoolName":"上海大学","realSalary":null,"lastTimeJobTime":1463642179000,"token":"2eeaa6a458ae4af2984f39f7576c01e2","tokenValidTime":1464247108000,"updatetime":1463642179000,"createTime":1462344339000,"jobName":"物流部门经理"},{"personId":2,"accountName":"15921237545","password":null,"peopleName":"刘明魁","peopleSex":null,"peopleAge":null,"idCardNo":null,"phone":"15921237545","jobId":2,"companyId":1,"departmentId":1,"schoolName":null,"realSalary":null,"lastTimeJobTime":1463039128000,"token":"4339f9881841473bb071a9c3be150d18","tokenValidTime":1464156039000,"updatetime":1463039128000,"createTime":1463030092000,"jobName":"管理员"},{"personId":4,"accountName":"13166201138","password":null,"peopleName":"蒲柯柯","peopleSex":null,"peopleAge":null,"idCardNo":null,"phone":"13166201138","jobId":1,"companyId":1,"departmentId":1,"schoolName":null,"realSalary":null,"lastTimeJobTime":1463641222000,"token":"2ea0ae74f31c442b89768b8a0b171c34","tokenValidTime":1464246128000,"updatetime":1463641222000,"createTime":1463038678000,"jobName":"企业法人"},{"personId":14,"accountName":"18616556160","password":null,"peopleName":"18616556160","peopleSex":null,"peopleAge":null,"idCardNo":null,"phone":"18616556160","jobId":1,"companyId":1,"departmentId":1,"schoolName":"北京大学","realSalary":null,"lastTimeJobTime":1463642158000,"token":"eddfd299ce324d9693554524f9007c97","tokenValidTime":1464241423000,"updatetime":1463642158000,"createTime":1463628626000,"jobName":"企业法人"}]},{"deptId":2,"deptName":"仓储购销部门","companyId":1,"deptType":"DEPT_BASE_STAROGE","branch":"DEPT_BASE_BRANCH","budget":89758.8,"remark":"仓储购销人员","createTime":1459332620000,"updaeTime":1463033969000,"endLockTime":1462240553000,"isTurnOn":0,"deptBussinessID":"DEPT_BASE_STAROGE","turnOnSum":null,"memberList":[]},{"deptId":3,"deptName":"原料加工部门","companyId":1,"deptType":"DEPT_PRODUCT_MATERIAL","branch":"DEPT_PRODUCT_BRANCH","budget":74100,"remark":"原料加工人员","createTime":1459332924000,"updaeTime":1463104788000,"endLockTime":1462240556000,"isTurnOn":0,"deptBussinessID":"DEPT_PRODUCT_MATERIAL","turnOnSum":null,"memberList":[]},{"deptId":4,"deptName":"生产部门","companyId":1,"deptType":"DEPT_PRODUCT_PRODUCT","branch":"DEPT_PRODUCT_BRANCH","budget":80000,"remark":"产品生产人员","createTime":1459332947000,"updaeTime":1462240559000,"endLockTime":1462240559000,"isTurnOn":0,"deptBussinessID":"DEPT_PRODUCT_PRODUCT","turnOnSum":null,"memberList":[{"personId":6,"accountName":"18721338327","password":null,"peopleName":"刘军","peopleSex":null,"peopleAge":null,"idCardNo":null,"phone":"18721338327","jobId":8,"companyId":1,"departmentId":4,"schoolName":"东京大学","realSalary":null,"lastTimeJobTime":1463550763000,"token":"8e489bf69b7649fb9655fabbd37ad7be","tokenValidTime":1464139606000,"updatetime":1463550763000,"createTime":1463104310000,"jobName":"生产部门经理"}]},{"deptId":15,"deptName":"基建部门","companyId":1,"deptType":"DEPT_TRADE_CONSTRUCT","branch":"DEPT_TRADE_BRANCH","budget":10000,"remark":null,"createTime":1463029656000,"updaeTime":1463119565000,"endLockTime":1464158206000,"isTurnOn":0,"deptBussinessID":"DEPT_TRADE_CONSTRUCT","turnOnSum":null,"memberList":[]},{"deptId":16,"deptName":"物流部门","companyId":1,"deptType":"DEPT_TRADE_LOGISTICS","branch":"DEPT_TRADE_BRANCH","budget":20000,"remark":null,"createTime":1463029656000,"updaeTime":1463029656000,"endLockTime":1464158555000,"isTurnOn":0,"deptBussinessID":"DEPT_TRADE_LOGISTICS","turnOnSum":null,"memberList":[]}]
     * peopleName : 蒲柯柯
     * comName : 铁未来1
     * comLogo : www.baidu.com
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String accountName;
        private String peopleName;
        private String comName;
        private String comLogo;
        /**
         * deptId : 1
         * deptName : 管理层部门
         * companyId : 1
         * deptType : DEPT_BASE_MANAGE
         * branch : DEPT_BASE_BRANCH
         * budget : 448018
         * remark : 公司核心人员
         * createTime : 1459332615000
         * updaeTime : 1463637526000
         * endLockTime : 1464158406000
         * isTurnOn : 0
         * deptBussinessID : DEPT_BASE_MANAGE
         * turnOnSum : null
         * memberList : [{"personId":1,"accountName":"18626348698","password":null,"peopleName":"曾理","peopleSex":null,"peopleAge":null,"idCardNo":null,"phone":"18626348698","jobId":13,"companyId":1,"departmentId":1,"schoolName":"上海大学","realSalary":null,"lastTimeJobTime":1463642179000,"token":"2eeaa6a458ae4af2984f39f7576c01e2","tokenValidTime":1464247108000,"updatetime":1463642179000,"createTime":1462344339000,"jobName":"物流部门经理"},{"personId":2,"accountName":"15921237545","password":null,"peopleName":"刘明魁","peopleSex":null,"peopleAge":null,"idCardNo":null,"phone":"15921237545","jobId":2,"companyId":1,"departmentId":1,"schoolName":null,"realSalary":null,"lastTimeJobTime":1463039128000,"token":"4339f9881841473bb071a9c3be150d18","tokenValidTime":1464156039000,"updatetime":1463039128000,"createTime":1463030092000,"jobName":"管理员"},{"personId":4,"accountName":"13166201138","password":null,"peopleName":"蒲柯柯","peopleSex":null,"peopleAge":null,"idCardNo":null,"phone":"13166201138","jobId":1,"companyId":1,"departmentId":1,"schoolName":null,"realSalary":null,"lastTimeJobTime":1463641222000,"token":"2ea0ae74f31c442b89768b8a0b171c34","tokenValidTime":1464246128000,"updatetime":1463641222000,"createTime":1463038678000,"jobName":"企业法人"},{"personId":14,"accountName":"18616556160","password":null,"peopleName":"18616556160","peopleSex":null,"peopleAge":null,"idCardNo":null,"phone":"18616556160","jobId":1,"companyId":1,"departmentId":1,"schoolName":"北京大学","realSalary":null,"lastTimeJobTime":1463642158000,"token":"eddfd299ce324d9693554524f9007c97","tokenValidTime":1464241423000,"updatetime":1463642158000,"createTime":1463628626000,"jobName":"企业法人"}]
         */

        private List<ReulstListEntity> reulstList;

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getPeopleName() {
            return peopleName;
        }

        public void setPeopleName(String peopleName) {
            this.peopleName = peopleName;
        }

        public String getComName() {
            return comName;
        }

        public void setComName(String comName) {
            this.comName = comName;
        }

        public String getComLogo() {
            return comLogo;
        }

        public void setComLogo(String comLogo) {
            this.comLogo = comLogo;
        }

        public List<ReulstListEntity> getReulstList() {
            return reulstList;
        }

        public void setReulstList(List<ReulstListEntity> reulstList) {
            this.reulstList = reulstList;
        }

        public static class ReulstListEntity {
            private int deptId;
            private String deptName;
            private int companyId;
            private String deptType;
            private String branch;
            private long budget;
            private String remark;
            private long createTime;
            private long updaeTime;
            private long endLockTime;
            private int isTurnOn;
            private String deptBussinessID;
            private int turnOnSum;
            /**
             * personId : 1
             * accountName : 18626348698
             * password : null
             * peopleName : 曾理
             * peopleSex : null
             * peopleAge : null
             * idCardNo : null
             * phone : 18626348698
             * jobId : 13
             * companyId : 1
             * departmentId : 1
             * schoolName : 上海大学
             * realSalary : null
             * lastTimeJobTime : 1463642179000
             * token : 2eeaa6a458ae4af2984f39f7576c01e2
             * tokenValidTime : 1464247108000
             * updatetime : 1463642179000
             * createTime : 1462344339000
             * jobName : 物流部门经理
             */

            private List<MemberListEntity> memberList;

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

            public long getBudget() {
                return budget;
            }

            public void setBudget(long budget) {
                this.budget = budget;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
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

            public int getIsTurnOn() {
                return isTurnOn;
            }

            public void setIsTurnOn(int isTurnOn) {
                this.isTurnOn = isTurnOn;
            }

            public String getDeptBussinessID() {
                return deptBussinessID;
            }

            public void setDeptBussinessID(String deptBussinessID) {
                this.deptBussinessID = deptBussinessID;
            }

            public int getTurnOnSum() {
                return turnOnSum;
            }

            public void setTurnOnSum(int turnOnSum) {
                this.turnOnSum = turnOnSum;
            }

            public List<MemberListEntity> getMemberList() {
                return memberList;
            }

            public void setMemberList(List<MemberListEntity> memberList) {
                this.memberList = memberList;
            }

            public static class MemberListEntity {
                private int personId;
                private String accountName;
                private String password;
                private String peopleName;
                private Object peopleSex;
                private Object peopleAge;
                private Object idCardNo;
                private String phone;
                private int jobId;
                private int companyId;
                private int departmentId;
                private String schoolName;
                private Object realSalary;
                private long lastTimeJobTime;
                private String token;
                private long tokenValidTime;
                private long updatetime;
                private long createTime;
                private String jobName;
                private String imgUrl;

                public int getPersonId() {
                    return personId;
                }

                public void setPersonId(int personId) {
                    this.personId = personId;
                }

                public String getAccountName() {
                    return accountName;
                }

                public void setAccountName(String accountName) {
                    this.accountName = accountName;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getPeopleName() {
                    return peopleName;
                }

                public void setPeopleName(String peopleName) {
                    this.peopleName = peopleName;
                }

                public Object getPeopleSex() {
                    return peopleSex;
                }

                public void setPeopleSex(Object peopleSex) {
                    this.peopleSex = peopleSex;
                }

                public Object getPeopleAge() {
                    return peopleAge;
                }

                public void setPeopleAge(Object peopleAge) {
                    this.peopleAge = peopleAge;
                }

                public Object getIdCardNo() {
                    return idCardNo;
                }

                public void setIdCardNo(Object idCardNo) {
                    this.idCardNo = idCardNo;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public int getJobId() {
                    return jobId;
                }

                public void setJobId(int jobId) {
                    this.jobId = jobId;
                }

                public int getCompanyId() {
                    return companyId;
                }

                public void setCompanyId(int companyId) {
                    this.companyId = companyId;
                }

                public int getDepartmentId() {
                    return departmentId;
                }

                public void setDepartmentId(int departmentId) {
                    this.departmentId = departmentId;
                }

                public String getSchoolName() {
                    return schoolName;
                }

                public void setSchoolName(String schoolName) {
                    this.schoolName = schoolName;
                }

                public Object getRealSalary() {
                    return realSalary;
                }

                public void setRealSalary(Object realSalary) {
                    this.realSalary = realSalary;
                }

                public long getLastTimeJobTime() {
                    return lastTimeJobTime;
                }

                public void setLastTimeJobTime(long lastTimeJobTime) {
                    this.lastTimeJobTime = lastTimeJobTime;
                }

                public String getToken() {
                    return token;
                }

                public void setToken(String token) {
                    this.token = token;
                }

                public long getTokenValidTime() {
                    return tokenValidTime;
                }

                public void setTokenValidTime(long tokenValidTime) {
                    this.tokenValidTime = tokenValidTime;
                }

                public long getUpdatetime() {
                    return updatetime;
                }

                public void setUpdatetime(long updatetime) {
                    this.updatetime = updatetime;
                }

                public long getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(long createTime) {
                    this.createTime = createTime;
                }

                public String getJobName() {
                    return jobName;
                }

                public void setJobName(String jobName) {
                    this.jobName = jobName;
                }

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }
            }
        }
    }
}
