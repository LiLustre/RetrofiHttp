package com.lize.httplibrary.http;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lize.httplibrary.http.factory.call.BaseCallAdapterFactory;
import com.lize.httplibrary.http.factory.gson.PaserGsonFactory;
import com.lize.httplibrary.http.interceptor.HeaderInterceptor;
import com.lize.httplibrary.util.LogUtils;
import com.lize.httplibrary.util.ValueUtil;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by Sky on 2017-11-3.
 */

public class HttpManager {

    /**
     * 网络请求超时时间毫秒
     */
    public static final int DEFAULT_TIMEOUT = 30000;
    private static HttpManager instance;
    private Map<String, Retrofit> retrofitMap = new HashMap<>();
    private Map<String,HeaderInterceptor> headerInterceptorMap = new HashMap<>();
    private  Cache cache;

    //单例模式
    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }


    public  void init(Application context){
        File cacheFile = new File(context.getCacheDir(), "cache");
        cache= new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        getInstance();
    }
    /**
     *
     * 添加请求头拦截器
     * @param baseUrl
     * @param headerInterceptor
     */
    public void setHeaderInterceptorMap(String baseUrl, HeaderInterceptor headerInterceptor) {
        if (headerInterceptorMap == null){
            headerInterceptorMap = new HashMap<>();
        }
        if (baseUrl!=null && headerInterceptor!=null){
            this.headerInterceptorMap.put(baseUrl,headerInterceptor);
        }

    }

    public Retrofit createRetrofit(String baseUrl) {
        if (isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (retrofitMap.get(baseUrl) != null) {
            return retrofitMap.get(baseUrl);
        }
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        HttpLoggingInterceptor loggInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    LogUtils.i("OKHttp-----", message);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("OKHttp-----", message);
                }
            }
        });
        //本地缓存
        loggInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//设置拦截器等级，BODY表示针对请求响应都体拦截

        //统一响应头 拦截器，例如 登录的token 等
        HeaderInterceptor headerInterceptor = null;
        if (headerInterceptorMap!=null){
           if (headerInterceptorMap.containsKey(baseUrl)){
               headerInterceptor = headerInterceptorMap.get(baseUrl);
           }
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(loggInterceptor)
                .cache(cache);
        if (headerInterceptor!=null){
            builder.addInterceptor(headerInterceptor);
        }
        OkHttpClient okHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                                .client(okHttpClient)
                                .addConverterFactory(PaserGsonFactory.create(gson))
                                .addCallAdapterFactory(BaseCallAdapterFactory.create())
                                .baseUrl(baseUrl)
                                .build();
        retrofitMap.put(baseUrl, retrofit);
        return retrofit;

    }

    private boolean isEmpty(String baseUrl) {
        return !ValueUtil.isStringValid(baseUrl);
    }

    public static Retrofit getRetrofit(String baseUrl) {
        return getInstance().createRetrofit(baseUrl);
    }
}
