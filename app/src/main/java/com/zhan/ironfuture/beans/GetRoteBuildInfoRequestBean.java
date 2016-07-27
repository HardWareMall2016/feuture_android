package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by WuYue on 2016/5/23.
 */
public class GetRoteBuildInfoRequestBean extends BaseRequestBean {

    /**
     * pageId : 0
     * pageCount : 20
     * pageDirection : 2
     * serachKey : 理
     */

    private DataEntity data;
    /**
     * data : {"pageId":"0","pageCount":"20","pageDirection":"2","serachKey":"理"}
     * token : aa903a0bf7004365882a00957fc55a95
     * userID : 14
     */

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private int pageId;
        private int pageCount;
        private int pageDirection;
        private String searchKey;

        public int getPageId() {
            return pageId;
        }

        public void setPageId(int pageId) {
            this.pageId = pageId;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getPageDirection() {
            return pageDirection;
        }

        public void setPageDirection(int pageDirection) {
            this.pageDirection = pageDirection;
        }
        public String getSearchKey() {
            return searchKey;
        }

        public void setSearchKey(String searchKey) {
            this.searchKey = searchKey;
        }


    }
}
