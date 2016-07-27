package com.zhan.framework.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.zhan.framework.R;
import com.zhan.framework.common.context.GlobalContext;

/**
 * Created by WuYue on 2016/4/19.
 */
public class VolleyErrorHelper {


    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError) {
            return context.getResources().getString(
                    R.string.generic_server_down);
        } else if (isServerProblem(error)) {
            return handleServerError(error, context);
        } else if (isNetworkProblem(error)) {
            return context.getResources().getString(R.string.no_internet);
        }
        return context.getResources().getString(R.string.generic_error);
    }


    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError)
                || (error instanceof NoConnectionError);
    }

    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError)
                || (error instanceof AuthFailureError);
    }

    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;
        NetworkResponse response = error.networkResponse;
        if (response != null) {
            Log.e("VolleyErrorHelper","Error statusCode : "+response.statusCode);
            if(response.data!=null){
                String errorStr=new String(response.data);
                Log.e("VolleyErrorHelper","Error Str : "+errorStr);
            }
            switch (response.statusCode) {
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
        }
        return context.getResources().getString(R.string.generic_error);
    }
}
