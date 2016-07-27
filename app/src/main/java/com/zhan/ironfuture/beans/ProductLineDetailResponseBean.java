package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/31.
 */
public class ProductLineDetailResponseBean extends BaseResponseBean{
    /**
     * recipeList : [{"recipeId":61,"recipeName":"p2级原料配方","comId":3,"recipeType":"RECIPE_MATERIAL_P2","proOutputId":51,"statue":1,"createTime":1466565120000,"updateTime":1466565120000,"proOutputName":"F1","goodsLogo":"testGoodsLogo","details":[{"detailId":206,"recipeId":61,"goodsId":19,"materialName":"R-19","materialSum":1,"seqno":1,"updatetime":"2016-06-22 11:11:32","goodsLogo":"testGoodsLogo"},{"detailId":207,"recipeId":61,"goodsId":2,"materialName":"R-02","materialSum":1,"seqno":2,"updatetime":"2016-06-22 11:11:33","goodsLogo":"testGoodsLogo"},{"detailId":208,"recipeId":61,"goodsId":5,"materialName":"R-05","materialSum":1,"seqno":3,"updatetime":"2016-06-22 11:11:33","goodsLogo":"testGoodsLogo"},{"detailId":209,"recipeId":61,"goodsId":7,"materialName":"R-07","materialSum":1,"seqno":4,"updatetime":"2016-06-22 11:11:33","goodsLogo":"testGoodsLogo"}]},{"recipeId":60,"recipeName":"p2级原料配方","comId":3,"recipeType":"RECIPE_MATERIAL_P2","proOutputId":51,"statue":1,"createTime":1466562167000,"updateTime":1466562167000,"proOutputName":"F1","goodsLogo":"testGoodsLogo","details":[{"detailId":202,"recipeId":60,"goodsId":1,"materialName":"R-01","materialSum":1,"seqno":1,"updatetime":"2016-06-22 10:22:19","goodsLogo":"testGoodsLogo"},{"detailId":203,"recipeId":60,"goodsId":13,"materialName":"R-13","materialSum":1,"seqno":2,"updatetime":"2016-06-22 10:22:19","goodsLogo":"testGoodsLogo"},{"detailId":204,"recipeId":60,"goodsId":2,"materialName":"R-02","materialSum":1,"seqno":3,"updatetime":"2016-06-22 10:22:19","goodsLogo":"testGoodsLogo"},{"detailId":205,"recipeId":60,"goodsId":5,"materialName":"R-05","materialSum":1,"seqno":4,"updatetime":"2016-06-22 10:22:19","goodsLogo":"testGoodsLogo"}]}]
     * yesterdaySum : 0
     * proGoodsName : null
     * proSum : 0
     * proGoodsId : null
     * todaySum : 0
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int yesterdaySum;
        private String proGoodsName;
        private int proSum;
        private int proGoodsId;
        private int todaySum;
        /**
         * recipeId : 61
         * recipeName : p2级原料配方
         * comId : 3
         * recipeType : RECIPE_MATERIAL_P2
         * proOutputId : 51
         * statue : 1
         * createTime : 1466565120000
         * updateTime : 1466565120000
         * proOutputName : F1
         * goodsLogo : testGoodsLogo
         * details : [{"detailId":206,"recipeId":61,"goodsId":19,"materialName":"R-19","materialSum":1,"seqno":1,"updatetime":"2016-06-22 11:11:32","goodsLogo":"testGoodsLogo"},{"detailId":207,"recipeId":61,"goodsId":2,"materialName":"R-02","materialSum":1,"seqno":2,"updatetime":"2016-06-22 11:11:33","goodsLogo":"testGoodsLogo"},{"detailId":208,"recipeId":61,"goodsId":5,"materialName":"R-05","materialSum":1,"seqno":3,"updatetime":"2016-06-22 11:11:33","goodsLogo":"testGoodsLogo"},{"detailId":209,"recipeId":61,"goodsId":7,"materialName":"R-07","materialSum":1,"seqno":4,"updatetime":"2016-06-22 11:11:33","goodsLogo":"testGoodsLogo"}]
         */

        private List<RecipeListEntity> recipeList;

        public void setYesterdaySum(int yesterdaySum) {
            this.yesterdaySum = yesterdaySum;
        }

        public void setProGoodsName(String proGoodsName) {
            this.proGoodsName = proGoodsName;
        }

        public void setProSum(int proSum) {
            this.proSum = proSum;
        }

        public void setProGoodsId(int proGoodsId) {
            this.proGoodsId = proGoodsId;
        }

        public void setTodaySum(int todaySum) {
            this.todaySum = todaySum;
        }

        public void setRecipeList(List<RecipeListEntity> recipeList) {
            this.recipeList = recipeList;
        }

        public int getYesterdaySum() {
            return yesterdaySum;
        }

        public String getProGoodsName() {
            return proGoodsName;
        }

        public int getProSum() {
            return proSum;
        }

        public int getProGoodsId() {
            return proGoodsId;
        }

        public int getTodaySum() {
            return todaySum;
        }

        public List<RecipeListEntity> getRecipeList() {
            return recipeList;
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
            /**
             * detailId : 206
             * recipeId : 61
             * goodsId : 19
             * materialName : R-19
             * materialSum : 1
             * seqno : 1
             * updatetime : 2016-06-22 11:11:32
             * goodsLogo : testGoodsLogo
             */

            private List<DetailsEntity> details;

            public void setRecipeId(int recipeId) {
                this.recipeId = recipeId;
            }

            public void setRecipeName(String recipeName) {
                this.recipeName = recipeName;
            }

            public void setComId(int comId) {
                this.comId = comId;
            }

            public void setRecipeType(String recipeType) {
                this.recipeType = recipeType;
            }

            public void setProOutputId(int proOutputId) {
                this.proOutputId = proOutputId;
            }

            public void setStatue(int statue) {
                this.statue = statue;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public void setProOutputName(String proOutputName) {
                this.proOutputName = proOutputName;
            }

            public void setGoodsLogo(String goodsLogo) {
                this.goodsLogo = goodsLogo;
            }

            public void setDetails(List<DetailsEntity> details) {
                this.details = details;
            }

            public int getRecipeId() {
                return recipeId;
            }

            public String getRecipeName() {
                return recipeName;
            }

            public int getComId() {
                return comId;
            }

            public String getRecipeType() {
                return recipeType;
            }

            public int getProOutputId() {
                return proOutputId;
            }

            public int getStatue() {
                return statue;
            }

            public long getCreateTime() {
                return createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public String getProOutputName() {
                return proOutputName;
            }

            public String getGoodsLogo() {
                return goodsLogo;
            }

            public List<DetailsEntity> getDetails() {
                return details;
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

                public void setDetailId(int detailId) {
                    this.detailId = detailId;
                }

                public void setRecipeId(int recipeId) {
                    this.recipeId = recipeId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public void setMaterialName(String materialName) {
                    this.materialName = materialName;
                }

                public void setMaterialSum(int materialSum) {
                    this.materialSum = materialSum;
                }

                public void setSeqno(int seqno) {
                    this.seqno = seqno;
                }

                public void setUpdatetime(String updatetime) {
                    this.updatetime = updatetime;
                }

                public void setGoodsLogo(String goodsLogo) {
                    this.goodsLogo = goodsLogo;
                }

                public int getDetailId() {
                    return detailId;
                }

                public int getRecipeId() {
                    return recipeId;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public String getMaterialName() {
                    return materialName;
                }

                public int getMaterialSum() {
                    return materialSum;
                }

                public int getSeqno() {
                    return seqno;
                }

                public String getUpdatetime() {
                    return updatetime;
                }

                public String getGoodsLogo() {
                    return goodsLogo;
                }
            }
        }
    }
}
