package com.lize.httplibrary.http.interceptor;


import com.lize.httplibrary.http.IGlobalHeaderManager;
import com.lize.httplibrary.util.ValueUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lize on 2018/11/30
 */
public class HeaderInterceptor implements Interceptor {

    private IGlobalHeaderManager globalHeaderManager;
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        //添加公用请求头
        if (globalHeaderManager != null) {
            Map<String, String> headerMap = globalHeaderManager.getRequestHeader();
            if (headerMap!=null&&!headerMap.isEmpty()) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (ValueUtil.isStringValid(entry.getKey()) &&
                            ValueUtil.isStringValid(entry.getValue())
                            ) {
                        builder.addHeader(entry.getKey(),entry.getValue());
                    }
                }
            }
        }
        Response response = chain.proceed(builder.build());
        return chain.proceed(builder.build());
    }

    public void setGlobalHeaderManager(IGlobalHeaderManager globalHeaderManager) {
        this.globalHeaderManager = globalHeaderManager;
    }
}
