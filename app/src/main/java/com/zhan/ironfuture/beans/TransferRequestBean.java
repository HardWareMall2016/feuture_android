package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/19.
 */
public class TransferRequestBean extends BaseRequestBean{
    /**
     * pageId : 1
     * pageCount : 2
     * pageDirection : 1
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
