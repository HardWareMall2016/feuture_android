package com.zhan.ironfuture.db.beans;

import com.zhan.framework.base.CacheDataBaseBean;

/**
 * Created by WuYue on 2016/4/22.
 */
public class ExampleCacheBean extends CacheDataBaseBean {
    String name;
    long time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
