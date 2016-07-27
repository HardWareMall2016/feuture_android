package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

/**
 * Created by WuYue on 2016/5/27.
 */
public class SelectRecipeListRequestBean extends BaseRequestBean {


    /**
     * recipeType : RECIPE_PROLINE
     * gooodsType : GOODS_MATERIAL_P2
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String recipeType;
        private String gooodsType;

        public String getRecipeType() {
            return recipeType;
        }

        public void setRecipeType(String recipeType) {
            this.recipeType = recipeType;
        }

        public String getGooodsType() {
            return gooodsType;
        }

        public void setGooodsType(String gooodsType) {
            this.gooodsType = gooodsType;
        }
    }
}
