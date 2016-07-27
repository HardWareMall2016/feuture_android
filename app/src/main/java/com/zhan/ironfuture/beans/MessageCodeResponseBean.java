package com.zhan.ironfuture.beans;

import com.zhan.ironfuture.base.BaseResponseBean;

/**
 * Created by Administrator on 2016/5/12.
 */
public class MessageCodeResponseBean extends BaseResponseBean {
    /**
     * code : 0
     * message : 5429
     * data : null
     */


    private Object data;

    public void setData(Object data) {
        this.data = data;
    }


    public Object getData() {
        return data;
    }
}
