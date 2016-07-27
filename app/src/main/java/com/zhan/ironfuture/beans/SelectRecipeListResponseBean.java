package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by WuYue on 2016/5/27.
 */
public class SelectRecipeListResponseBean extends BaseResponseBean {


    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private List<RecipeListEntity> recipeList;

        private List<SpaceGoodsListEntity> spaceGoodsList;

        public List<RecipeListEntity> getRecipeList() {
            return recipeList;
        }

        public void setRecipeList(List<RecipeListEntity> recipeList) {
            this.recipeList = recipeList;
        }

        public List<SpaceGoodsListEntity> getSpaceGoodsList() {
            return spaceGoodsList;
        }

        public void setSpaceGoodsList(List<SpaceGoodsListEntity> spaceGoodsList) {
            this.spaceGoodsList = spaceGoodsList;
        }

        public static class RecipeListEntity {
            private int recipeId;
            private String recipeName;
            private int comId;
            private String recipeType;
            private int proOutputId;
            private int statue;
            private long createTime;
            private long updateTime;
            private String proOutputName;
            private String goodsLogo;

            private List<DetailsEntity> details;

            public int getRecipeId() {
                return recipeId;
            }

            public void setRecipeId(int recipeId) {
                this.recipeId = recipeId;
            }

            public String getRecipeName() {
                return recipeName;
            }

            public void setRecipeName(String recipeName) {
                this.recipeName = recipeName;
            }

            public int getComId() {
                return comId;
            }

            public void setComId(int comId) {
                this.comId = comId;
            }

            public String getRecipeType() {
                return recipeType;
            }

            public void setRecipeType(String recipeType) {
                this.recipeType = recipeType;
            }

            public int getProOutputId() {
                return proOutputId;
            }

            public void setProOutputId(int proOutputId) {
                this.proOutputId = proOutputId;
            }

            public int getStatue() {
                return statue;
            }

            public void setStatue(int statue) {
                this.statue = statue;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public String getProOutputName() {
                return proOutputName;
            }

            public void setProOutputName(String proOutputName) {
                this.proOutputName = proOutputName;
            }

            public String getGoodsLogo() {
                return goodsLogo;
            }

            public void setGoodsLogo(String goodsLogo) {
                this.goodsLogo = goodsLogo;
            }

            public List<DetailsEntity> getDetails() {
                return details;
            }

            public void setDetails(List<DetailsEntity> details) {
                this.details = details;
            }

            public static class DetailsEntity {
                private int detailId;
                private int recipeId;
                private int goodsId;
                private String materialName;
                private int materialSum;
                private int seqno;
                private String updatetime;
                private String goodsLogo;

                public int getDetailId() {
                    return detailId;
                }

                public void setDetailId(int detailId) {
                    this.detailId = detailId;
                }

                public int getRecipeId() {
                    return recipeId;
                }

                public void setRecipeId(int recipeId) {
                    this.recipeId = recipeId;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public String getMaterialName() {
                    return materialName;
                }

                public void setMaterialName(String materialName) {
                    this.materialName = materialName;
                }

                public int getMaterialSum() {
                    return materialSum;
                }

                public void setMaterialSum(int materialSum) {
                    this.materialSum = materialSum;
                }

                public int getSeqno() {
                    return seqno;
                }

                public void setSeqno(int seqno) {
                    this.seqno = seqno;
                }

                public String getUpdatetime() {
                    return updatetime;
                }

                public void setUpdatetime(String updatetime) {
                    this.updatetime = updatetime;
                }

                public String getGoodsLogo() {
                    return goodsLogo;
                }

                public void setGoodsLogo(String goodsLogo) {
                    this.goodsLogo = goodsLogo;
                }
            }
        }

        public static class SpaceGoodsListEntity {
            private int spaceId;
            private String goodsLogo;
            private int goodsId;
            private int goodsCount;
            private String goodsName;

            public int getSpaceId() {
                return spaceId;
            }

            public void setSpaceId(int spaceId) {
                this.spaceId = spaceId;
            }

            public String getGoodsLogo() {
                return goodsLogo;
            }

            public void setGoodsLogo(String goodsLogo) {
                this.goodsLogo = goodsLogo;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
        }
    }
}
