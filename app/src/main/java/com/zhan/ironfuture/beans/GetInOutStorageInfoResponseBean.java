package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * 作者：伍岳 on 2016/6/20 15:05
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
public class GetInOutStorageInfoResponseBean extends BaseResponseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * actionId : 324
         * actionType : OPERATION_DESERT_GOODS
         * userId : 30
         * descs : 物品BS，增加30
         * counts : 10
         * eventTime : 1466150334000
         * accountName : twl_18721338327
         * peopleName : 18721338327
         * codeNameDesc : null
         */

        private List<LogListEntity> logList;

        public List<LogListEntity> getLogList() {
            return logList;
        }

        public void setLogList(List<LogListEntity> logList) {
            this.logList = logList;
        }

        public static class LogListEntity {
            private int actionId;
            private String actionType;
            private int userId;
            private String descs;
            private int counts;
            private long eventTime;
            private String accountName;
            private String peopleName;
            private String codeNameDesc;

            public int getActionId() {
                return actionId;
            }

            public void setActionId(int actionId) {
                this.actionId = actionId;
            }

            public String getActionType() {
                return actionType;
            }

            public void setActionType(String actionType) {
                this.actionType = actionType;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getDescs() {
                return descs;
            }

            public void setDescs(String descs) {
                this.descs = descs;
            }

            public int getCounts() {
                return counts;
            }

            public void setCounts(int counts) {
                this.counts = counts;
            }

            public long getEventTime() {
                return eventTime;
            }

            public void setEventTime(long eventTime) {
                this.eventTime = eventTime;
            }

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

            public String getCodeNameDesc() {
                return codeNameDesc;
            }

            public void setCodeNameDesc(String codeNameDesc) {
                this.codeNameDesc = codeNameDesc;
            }
        }
    }
}
