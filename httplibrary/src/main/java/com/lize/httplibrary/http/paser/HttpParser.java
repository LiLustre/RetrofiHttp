package com.lize.httplibrary.http.paser;


import com.lize.httplibrary.http.HttpResult;
import com.lize.httplibrary.http.exception.HttpError;
import com.lize.httplibrary.http.exception.PaserJsonException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 * 响应解析
 */
public class HttpParser<T> implements IHttpParser<T> {
    @Override
    public HttpResult parseResponse(Call<T> call, Response<T> response) {
        if (response != null) {
            int code = response.code();
            if (code >= 200 && code < 300) {
                //响应成功
                return HttpResult.success(code, response);
            }
            String msg;
            switch (code) {
                case 404:
                    msg = "请求资源不存在";
                    break;
                case 500:
                    msg = "服务器错误";
                    break;
                case 401:
                case 403:
                    msg = "身份认证失败";
                    break;
                case 422:
                    msg = "请求参数错误";
                    break;
                default:
                    msg = "服务器异常";
                    break;
            }
            return HttpResult.error(code, new HttpError(msg, response));

        } else {
            return HttpResult.error(HttpResult.NO_RESPONE_CODE, new HttpError("请求失败", null));
        }
    }

    @Override
    public HttpResult parserThrowable(Call<T> call, Throwable t) {
        String msg;
        if (t instanceof SocketTimeoutException) {
            msg = "连接超时";
        } else if (t instanceof ConnectException) {
            msg = "网络连接失败";
        } else if (t instanceof UnknownHostException) {
            msg = "连接服务异常";
        } else if (t instanceof SocketException) {
            msg = "服务异常";
        } else if (t instanceof PaserJsonException) {
            msg = "数据解析异常";
        } else {
            msg = "服务异常";
        }

        return HttpResult.error(HttpResult.RESPONE_EXCEPTION_CODE, new HttpError(msg, t));
    }
}
