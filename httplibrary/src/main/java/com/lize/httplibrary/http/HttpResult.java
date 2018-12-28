package com.lize.httplibrary.http;

import com.lize.httplibrary.http.exception.HttpError;


import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 */
public final class HttpResult<T> {

    public static final  int NO_RESPONE_CODE = Integer.MIN_VALUE;
    public static final  int RESPONE_EXCEPTION_CODE = Integer.MAX_VALUE;
    private Response<T> body;
    private int code;
    private HttpError httpError;


    public HttpResult(int code,Response<T> body, HttpError httpError) {
        this.body = body;
        this.httpError = httpError;
        this.code = code;
    }

    public HttpError getHttpError(){
        return this.httpError;
    }

    public int getCode(){
        return this.code;
    }

    public Response<T> getBody(){
        return this.body;
    }
    /**
     * 判断 请求是否成功
     *
     * @return
     */
    public boolean isSuccessful(){
        return this.httpError == null;
    }

    /**
     * http 请求服务成功
     * @param t
     * @param <T>
     * @return
     */
    public static <T>HttpResult<T> success(int code,Response<T> t){
        return new HttpResult<>(code,t,null);
    }


    public static <T>HttpResult<T> error(int code,HttpError httpError){
        return new HttpResult<>(code,null,httpError);
    }
}
