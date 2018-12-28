package com.lize.httplibrary.http.factory.call;

import com.lize.httplibrary.http.BaseCall;


import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * Created by Lize on 2018/11/30
 */
public class BaseCallAdapter<R> implements CallAdapter<R, Object> {
    private final Type responseType;

    public BaseCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    /**
     * 返回一个Call 的子类或者自己的实例
     * 将Call 构建成  Call 的子类或者自己 返回
     *
     * @param call
     * @return
     */
    @Override
    public Object adapt(Call<R> call) {
        return new BaseCall<R>(call);
    }
}
