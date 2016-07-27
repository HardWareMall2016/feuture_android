package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by Administrator on 2016/5/31.
 */
public class ProductLineDetailBeginRequestBean extends BaseRequestBean{
    /**
     * proLineId : 4
     * time : 1
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int proLineId;
        private long time;
        private int recipeId;

        public int getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(int recipeId) {
            this.recipeId = recipeId;
        }


        public void setProLineId(int proLineId) {
            this.proLineId = proLineId;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getProLineId() {
            return proLineId;
        }

        public long getTime() {
            return time;
        }
    }
}
