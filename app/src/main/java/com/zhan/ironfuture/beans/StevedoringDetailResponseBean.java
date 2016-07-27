package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class StevedoringDetailResponseBean extends BaseResponseBean{
    /**
     * goodsBack : {"orderId":65,"totalAmount":200,"productAddressId":3,"productAddressName":"AAA","destinationId":2,"destinationName":"伍氏集团","penalty":0,"remainingTime":1466611200000,"goodsBackLsit":[{"goodsId":8,"goodsName":"R-08","goodsSum":2,"spaceId":0,"recipeId":0},{"goodsId":10,"goodsName":"R-10","goodsSum":1,"spaceId":0,"recipeId":0}],"state":0}
     * state : 0
     * orderId : 65
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * orderId : 65
         * totalAmount : 200
         * productAddressId : 3
         * productAddressName : AAA
         * destinationId : 2
         * destinationName : 伍氏集团
         * penalty : 0
         * remainingTime : 1466611200000
         * goodsBackLsit : [{"goodsId":8,"goodsName":"R-08","goodsSum":2,"spaceId":0,"recipeId":0},{"goodsId":10,"goodsName":"R-10","goodsSum":1,"spaceId":0,"recipeId":0}]
         * state : 0
         */

        private GoodsBackEntity goodsBack;
        private int state;
        private int orderId;

        public void setGoodsBack(GoodsBackEntity goodsBack) {
            this.goodsBack = goodsBack;
        }

        public void setState(int state) {
            this.state = state;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public GoodsBackEntity getGoodsBack() {
            return goodsBack;
        }

        public int getState() {
            return state;
        }

        public int getOrderId() {
            return orderId;
        }

        public static class GoodsBackEntity {
            private int orderId;
            private int totalAmount;
            private int productAddressId;
            private String productAddressName;
            private int destinationId;
            private String destinationName;
            private int penalty;
            private long remainingTime;
            private int state;
            /**
             * goodsId : 8
             * goodsName : R-08
             * goodsSum : 2
             * spaceId : 0
             * recipeId : 0
             */

            private List<GoodsBackLsitEntity> goodsBackLsit;

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public void setTotalAmount(int totalAmount) {
                this.totalAmount = totalAmount;
            }

            public void setProductAddressId(int productAddressId) {
                this.productAddressId = productAddressId;
            }

            public void setProductAddressName(String productAddressName) {
                this.productAddressName = productAddressName;
            }

            public void setDestinationId(int destinationId) {
                this.destinationId = destinationId;
            }

            public void setDestinationName(String destinationName) {
                this.destinationName = destinationName;
            }

            public void setPenalty(int penalty) {
                this.penalty = penalty;
            }

            public void setRemainingTime(long remainingTime) {
                this.remainingTime = remainingTime;
            }

            public void setState(int state) {
                this.state = state;
            }

            public void setGoodsBackLsit(List<GoodsBackLsitEntity> goodsBackLsit) {
                this.goodsBackLsit = goodsBackLsit;
            }

            public int getOrderId() {
                return orderId;
            }

            public int getTotalAmount() {
                return totalAmount;
            }

            public int getProductAddressId() {
                return productAddressId;
            }

            public String getProductAddressName() {
                return productAddressName;
            }

            public int getDestinationId() {
                return destinationId;
            }

            public String getDestinationName() {
                return destinationName;
            }

            public int getPenalty() {
                return penalty;
            }

            public long getRemainingTime() {
                return remainingTime;
            }

            public int getState() {
                return state;
            }

            public List<GoodsBackLsitEntity> getGoodsBackLsit() {
                return goodsBackLsit;
            }

            public static class GoodsBackLsitEntity {
                private int goodsId;
                private String goodsName;
                private int goodsSum;
                private int spaceId;
                private int recipeId;

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }

                public void setGoodsSum(int goodsSum) {
                    this.goodsSum = goodsSum;
                }

                public void setSpaceId(int spaceId) {
                    this.spaceId = spaceId;
                }

                public void setRecipeId(int recipeId) {
                    this.recipeId = recipeId;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public int getGoodsSum() {
                    return goodsSum;
                }

                public int getSpaceId() {
                    return spaceId;
                }

                public int getRecipeId() {
                    return recipeId;
                }
            }
        }
    }
}
