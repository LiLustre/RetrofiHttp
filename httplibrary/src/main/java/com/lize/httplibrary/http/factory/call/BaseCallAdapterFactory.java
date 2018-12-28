package com.lize.httplibrary.http.factory.call;



import com.lize.httplibrary.http.BaseCall;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by Lize on 2018/11/30
 */
public class BaseCallAdapterFactory extends CallAdapter.Factory {


    public static BaseCallAdapterFactory create() {
        return new BaseCallAdapterFactory();
    }

    /**
     * 获取CallAdapter
     * @param returnType APIService 方法返回的类型
     * @param annotations
     * @param retrofit
     * @return
     */

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        // 根据 returnType 返回 类型 例如 Class<?>,
        //  Type 是所有类型的 父类
        Class<?> rawType = getRawType(returnType);
        if (rawType !=BaseCall.class){
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException("Call return type must be parameterized as BaseCall<Foo> or BaseCall<? extends Foo>");
        }
        //获取 返回类型中 的泛型
         Type respooneType = getParameterUpperBound(0, (ParameterizedType) returnType);
        return new BaseCallAdapter<>(respooneType);
    }
}
