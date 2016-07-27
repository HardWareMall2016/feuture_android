package com.zhan.framework.base;

import org.aisen.orm.annotation.PrimaryKey;

import java.util.UUID;

/**
 * Created by WuYue on 2016/4/22.
 */
public class CacheDataBaseBean {
    //唯一主键
    @PrimaryKey(column = "cacheDataId")
    String cacheDataId = UUID.randomUUID().toString();

    public String getCacheDataId() {
        return cacheDataId;
    }

    public void setCacheDataId(String cacheDataId) {
        this.cacheDataId = cacheDataId;
    }
}
