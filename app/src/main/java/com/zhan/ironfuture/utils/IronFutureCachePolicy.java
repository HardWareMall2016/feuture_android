package com.zhan.ironfuture.utils;

import com.alibaba.fastjson.JSON;
import com.zhan.framework.network.BaseCachePolicy;
import com.zhan.framework.network.HttpRequestParams;
import com.zhan.ironfuture.base.BaseRequestBean;
import com.zhan.ironfuture.base.UserInfo;

/**
 * Created by WuYue on 2016/4/22.
 */
public class IronFutureCachePolicy extends BaseCachePolicy {

    @Override
    public String getCacheOwner() {
        if(UserInfo.getCurrentUser()==null||!UserInfo.getCurrentUser().isLogin()){
            return VISITOR;
        }
        return String.valueOf(UserInfo.getCurrentUser().getUserID());
    }

    @Override
    public String generateCacheKey(String apiUrl, Object requestParams) throws Exception {
        String cacheKey;
        if (requestParams == null) {
            cacheKey = md5(apiUrl);
        } else if (requestParams instanceof BaseRequestBean) {
            //只比对具体参数部分，BaseRequestBean中的值始终保持不变
            //每次登录后token都发生变化,UserId 已经在Extra.owner中体现了
            BaseRequestBean requestBean = (BaseRequestBean) requestParams;
            String originToken = requestBean.getToken();
            int originUserId = requestBean.getUserID();
            //去掉差异化的值
            requestBean.setToken("");
            requestBean.setUserID(0);
            String key = apiUrl + ":" + JSON.toJSONString(requestBean);
            //还原原始值
            requestBean.setToken(originToken);
            requestBean.setUserID(originUserId);
            cacheKey = md5(key);
        }else if(requestParams instanceof HttpRequestParams){
            HttpRequestParams formRequestParams= (HttpRequestParams) requestParams;
            String key = apiUrl + ":" + formRequestParams.toString();
            cacheKey = md5(key);
        }else{
            throw new Exception("Unrecognized requestParams type : "+requestParams.getClass());
        }
        return cacheKey;
    }
}
