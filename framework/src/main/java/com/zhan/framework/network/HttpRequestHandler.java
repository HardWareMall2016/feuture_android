package com.zhan.framework.network;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zhan.framework.R;
import com.zhan.framework.base.CacheDataBaseBean;
import com.zhan.framework.cache.CacheData;
import com.zhan.framework.cache.CacheManager;
import com.zhan.framework.common.context.GlobalContext;
import com.zhan.framework.cache.CacheUtility;

import org.aisen.orm.extra.Extra;
import java.util.List;

/**
 * 作者：伍岳 on 2015/12/3 11:51
 * 邮箱：wuyue8512@163.com
 //
 //         .............................................
 //                  美女坐镇                  BUG辟易
 //         .............................................
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 //
 */
public abstract class HttpRequestHandler implements HttpRequestCallback {
    public enum ResultCode {
        noNetwork, timeout, failed, success, canceled
    }

    private Fragment mFragment;
    private Extra mCacheKey;
    private BaseCachePolicy mBaseCachePolicy;

    private String mApiUrl;
    private Object mRequestParams;

    public HttpRequestHandler(Fragment fragment){
        mFragment=fragment;
    }

    /**
     * 是否缓存请求数据
     * @return
     */
    public boolean isCacheData(){
        return false;
    }

    /**
     * 本次请求是否使用缓存数据,默认情况下打开缓存(isCacheData)就使用缓存数据
     * @return
     */
    public boolean useCacheData(){
        return isCacheData();
    }

    /***
     * 缓存有效期，默认不过期
     * @return
     */
    public long getExpiredSeconds(){
        if(mBaseCachePolicy==null){
            return -1;
        }
        return mBaseCachePolicy.getExpiredSeconds();
    }

    public boolean continueRequest(){
        return true;
    }


    public void setCachePolicy(BaseCachePolicy cachePolicy,String apiUrl, Object requestParams){
        mBaseCachePolicy=cachePolicy;
        mApiUrl=apiUrl;
        mRequestParams=requestParams;
    }

    private Extra getCacheKey(){
        if(mCacheKey!=null){
            return mCacheKey;
        }
        mCacheKey= generateCacheKey(mApiUrl, mRequestParams);
        return mCacheKey;
    }

    private String getCacheKeyStr(){
        if(getCacheKey()==null){
            return null;
        }
        return String.format("%s_%s",mCacheKey.getOwner(),mCacheKey.getKey());
    }

    /***
     * 生成 cache key
     * @param apiUrl
     * @param requestParams
     */
    private Extra generateCacheKey(String apiUrl, Object requestParams){
        if(mBaseCachePolicy==null){
            return null;
        }
        if(apiUrl==null){
            return null;
        }
        String cacheOwner=mBaseCachePolicy.getCacheOwner();
        Extra Key= null;
        try {
            String cacheKey = mBaseCachePolicy.generateCacheKey(apiUrl, requestParams);
            Key = new Extra(cacheOwner, cacheKey);
        } catch (Exception e) {
            Log.e("HttpRequestUtils",e.getMessage());
        }
        return Key;
    }

    /***
     * 在网络请求开始前,比onPrepare先调用
     * 在这里可以获取缓存(getCacheData)和做请求前的初始化动作
     */
    public void onBeforeRequest(){}

    public boolean findCacheDataAndContinue(){
        boolean continueRequest=true;
        if(useCacheData()&&getCacheKey()!=null){
            //获取缓存数据
            CacheData cacheData=CacheManager.readObject(getCacheKeyStr());
            //缓存数据没有过期
            if(!isCacheExpired(cacheData)){
                onPrepare();
                //返回缓存数据
                onRequestSucceeded(cacheData.getData(),true);
                continueRequest=continueRequest();
            }
        }
        return continueRequest;
    }

    private boolean isCacheExpired(CacheData cacheData){
        if(cacheData==null){
            return true;
        }

        //如果没有联网，认为还没过期，可直接显示缓存数据
        if (!Connectivity.isConnected(GlobalContext.getInstance())) {
            return false;
        }

        if(getExpiredSeconds()==-1){
            return false;
        }

        return cacheData.getUpdateTime() + getExpiredSeconds() * 1000 <= System.currentTimeMillis();
    }

    public <T extends CacheDataBaseBean> List<T> getCacheData(Class<T> cacheDataCls) {
        if(getCacheKey()==null){
            return null;
        }
        return CacheUtility.findCacheData(mCacheKey,cacheDataCls);
    }

    /***
     * 一般可在onRequestSucceeded解析出结果后调用
     * @param dataList
     * @param cls
     * @param <T>
     */
    public <T extends CacheDataBaseBean> void putCacheData(List<T> dataList, Class<T> cls) {
        if(getCacheKey()==null){
            return;
        }
        CacheUtility.putCacheData(mCacheKey,dataList,cls);
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onRequestFailed(String errorMsg) {
        //如果Fragment对应的Activity已经销毁，就不需要返回了
        if(mFragment!=null&&mFragment.getActivity()==null){
            return;
        }
        onRequestFinished(ResultCode.failed,errorMsg);
    }

    @Override
    public void onRequestFailedNoNetwork() {
        onRequestFinished(ResultCode.noNetwork,GlobalContext.getInstance().getString(R.string.network_disconnect));
    }

    @Override
    public void onTimeout() {
        //如果Fragment对应的Activity已经销毁，就不需要返回了
        if(mFragment!=null&&mFragment.getActivity()==null){
            return;
        }
        onRequestFinished(ResultCode.timeout, GlobalContext.getInstance().getString(R.string.network_timeout));
    }

    @Override
    public void onRequestCanceled() {
        //如果Fragment对应的Activity已经销毁，就不需要返回了
        if(mFragment!=null&&mFragment.getActivity() == null) {
            return;
        }
        onRequestFinished(ResultCode.canceled,GlobalContext.getInstance().getString(R.string.request_canceled));
    }

    @Override
    public void onRequestSucceeded(String content, boolean isCacheData) {
        //如果Fragment对应的Activity已经销毁，就不需要返回了
        if(mFragment!=null&&mFragment.getActivity()==null){
            return;
        }
        if(isCacheData()&&getCacheKey()!=null&&!isCacheData){
            new SaveCacheTask(getCacheKeyStr(),content).execute();
        }

        onRequestSucceeded(content);

        onRequestFinished(ResultCode.success,content);
    }

    public void onRequestSucceeded(String content) {

    }

    public void onRequestFinished(ResultCode resultCode,String result) {

    }

    private class SaveCacheTask extends AsyncTask<Void, Void, Void> {
        private String mData;
        private String mCacheKey;

        private SaveCacheTask(String key,String data) {
            mCacheKey=key;
            mData=data;
        }

        @Override
        protected Void doInBackground(Void... params) {
            CacheData cacheData=new CacheData();
            cacheData.setData(mData);
            cacheData.setUpdateTime(System.currentTimeMillis());

            CacheManager.saveObject(cacheData, mCacheKey);
            return null;
        }
    }
}
