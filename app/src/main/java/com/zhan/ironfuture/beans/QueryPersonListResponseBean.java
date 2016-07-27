package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by WuYue on 2016/5/31.
 */
public class QueryPersonListResponseBean extends BaseResponseBean{

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * bigClass : A
         * personList : [{"personId":43,"accountName":"account_name_13512336101","jobId":1,"jobName":"企业法人","personLogo":null,"salary":50001},{"personId":44,"accountName":"account_name_13512336102","jobId":2,"jobName":"管理员","personLogo":null,"salary":2000}]
         */

        private List<PersonEntity> person;

        public List<PersonEntity> getPerson() {
            return person;
        }

        public void setPerson(List<PersonEntity> person) {
            this.person = person;
        }

        public static class PersonEntity {
            private String bigClass;
            /**
             * personId : 43
             * accountName : account_name_13512336101
             * jobId : 1
             * jobName : 企业法人
             * personLogo : null
             * salary : 50001
             */

            private List<PersonListEntity> personList;

            public String getBigClass() {
                return bigClass;
            }

            public void setBigClass(String bigClass) {
                this.bigClass = bigClass;
            }

            public List<PersonListEntity> getPersonList() {
                return personList;
            }

            public void setPersonList(List<PersonListEntity> personList) {
                this.personList = personList;
            }

            public static class PersonListEntity {
                private int personId;
                private String accountName;
                private int jobId;
                private String jobName;
                private String personLogo;
                private int salary;

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

                public int getJobId() {
                    return jobId;
                }

                public void setJobId(int jobId) {
                    this.jobId = jobId;
                }

                public String getJobName() {
                    return jobName;
                }

                public void setJobName(String jobName) {
                    this.jobName = jobName;
                }

                public String getPersonLogo() {
                    return personLogo;
                }

                public void setPersonLogo(String personLogo) {
                    this.personLogo = personLogo;
                }

                public int getSalary() {
                    return salary;
                }

                public void setSalary(int salary) {
                    this.salary = salary;
                }
            }
        }
    }
}
