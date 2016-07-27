package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * 作者：伍岳 on 2016/6/16 16:04
 * 邮箱：wuyue8512@163.com
 * //
 * //         .............................................
 * //                  美女坐镇                  BUG辟易
 * //         .............................................
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 * //
 */
public class PubJobInfoRequestBean extends BaseRequestBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int comId;
        private int postId;
        private int salary;
        private int recruitmentSum;
        private String jobDescribe;

        public int getComId() {
            return comId;
        }

        public void setComId(int comId) {
            this.comId = comId;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }

        public int getRecruitmentSum() {
            return recruitmentSum;
        }

        public void setRecruitmentSum(int recruitmentSum) {
            this.recruitmentSum = recruitmentSum;
        }

        public String getJobDescribe() {
            return jobDescribe;
        }

        public void setJobDescribe(String jobDescribe) {
            this.jobDescribe = jobDescribe;
        }
    }
}
