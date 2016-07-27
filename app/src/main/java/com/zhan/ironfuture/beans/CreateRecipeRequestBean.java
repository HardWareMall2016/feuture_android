package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseRequestBean;

import java.util.List;

/**
 * Created by WuYue on 2016/5/30.
 */
public class CreateRecipeRequestBean extends BaseRequestBean {

    /**
     * recipeType : RECIPE_MATERIAL_P2
     * comId : 1
     * goodsList : [{"goodsId":1,"goodsSum":5},{"goodsId":2,"goodsSum":5},{"goodsId":3,"goodsSum":5},{"goodsId":4,"goodsSum":5}]
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
        private int comId;
        /**
         * goodsId : 1
         * goodsSum : 5
         */

        private List<GoodsListEntity> goodsList;

        public String getRecipeType() {
            return recipeType;
        }

        public void setRecipeType(String recipeType) {
            this.recipeType = recipeType;
        }

        public int getComId() {
            return comId;
        }

        public void setComId(int comId) {
            this.comId = comId;
        }

        public List<GoodsListEntity> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListEntity> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListEntity {
            private int goodsId;
            private int goodsSum;
            private int spaceId;

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getGoodsSum() {
                return goodsSum;
            }

            public void setGoodsSum(int goodsSum) {
                this.goodsSum = goodsSum;
            }

            public int getSpaceId() {
                return spaceId;
            }

            public void setSpaceId(int spaceId) {
                this.spaceId = spaceId;
            }
        }
    }
}
