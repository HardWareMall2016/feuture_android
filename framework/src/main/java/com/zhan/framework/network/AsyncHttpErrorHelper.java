package com.zhan.framework.network;

import android.util.Log;

import com.zhan.framework.R;
import com.zhan.framework.common.context.GlobalContext;

import cz.msebera.android.httpclient.client.HttpResponseException;

/**
 * Created by WuYue on 2016/4/25.
 */
public class AsyncHttpErrorHelper {
    private final static String TAG="AsyncHttpErrorHelper";

    public static String getMessage(Throwable error) {
        if(error!=null&&(error instanceof HttpResponseException)){
            HttpResponseException httpResponseException= (HttpResponseException) error;
            Log.i(TAG, "AsyncHttpErrorHelper error statusCode = " + httpResponseException.getStatusCode());
            Log.i(TAG, "AsyncHttpErrorHelper error Message = " + httpResponseException.getMessage());
            switch (httpResponseException.getStatusCode()){
                case 404:
                    return GlobalContext.getInstance().getString(R.string.url_not_exists);
                case 422:
                case 401:
                    return GlobalContext.getInstance().getString(R.string.generic_server_down);
                case 503:
                    return GlobalContext.getInstance().getString(R.string.service_unavailable);
                default:
                    return GlobalContext.getInstance().getString(R.string.generic_error);
            }
        }else{
            return GlobalContext.getInstance().getString(R.string.generic_error);
        }
    }
}
