package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/17.
 */
public class TalentMarketSelectSeekerRequestBean extends BaseRequestBean{
    /**
     * token : 0b42fe7890fb47679fb2f89607f458a0
     * userID : 4
     * data : {"pageId":"0","pageCount":"20","pageDirection":"1","bigType":"","serachKey":""}
     */

    /**
     * pageId : 0
     * pageCount : 20
     * pageDirection : 1
     * bigType :
     * serachKey :
     */

    private DataEntity data;


    public void setData(DataEntity data) {
        this.data = data;
    }


    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int pageId;
        private int pageCount;
        private int pageDirection;
        private String bigType;
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

        public String getBigType() {
            return bigType;
        }

        public void setBigType(String bigType) {
            this.bigType = bigType;
        }

        public String getSearchKey() {
            return searchKey;
        }

        public void setSearchKey(String searchKey) {
            this.searchKey = searchKey;
        }
    }
}
