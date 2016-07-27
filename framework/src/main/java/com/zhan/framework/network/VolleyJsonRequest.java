package com.zhan.framework.network;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by WuYue on 2016/4/21.
 */
public class VolleyJsonRequest extends JsonRequest<String> {

    public VolleyJsonRequest(String url, String requestBody, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(TextUtils.isEmpty(requestBody)?0:1, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException var4) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

}
