package com.zhan.ironfuture.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/31.
 */
public class ProductLineProductDetailContent implements Serializable {
    private String prolineName ;
    private int prolineId;
    private int recipeId ;
    private String prolineType ;

    public String getProlineType() {
        return prolineType;
    }

    public void setProlineType(String prolineType) {
        this.prolineType = prolineType;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }


    public String getProlineName() {
        return prolineName;
    }

    public void setProlineName(String prolineName) {
        this.prolineName = prolineName;
    }

    public int getProlineId() {
        return prolineId;
    }

    public void setProlineId(int prolineId) {
        this.prolineId = prolineId;
    }
}
