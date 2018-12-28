package com.lize.httplibrary.http;



import com.lize.httplibrary.http.TagManager.CallManager;
import com.lize.httplibrary.http.exception.HttpError;
import com.lize.httplibrary.http.paser.HttpParser;
import com.lize.httplibrary.http.paser.IHttpParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 */
public abstract class BaseCallBack<T> implements Callback<T> {
    public IHttpParser<T> httpParser = new HttpParser<>();

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        HttpResult<T> httpResult = httpParser.parseResponse(call, response);
        callResult(call, httpResult,response.raw());
    }
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        HttpResult<T>  httpResult = httpParser.parserThrowable(call, t);
        callResult(call, httpResult,null);
    }

    private void callResult(Call<T> call, HttpResult<T>  httpResult,okhttp3.Response rawResponse) {
        try{
            if (call.isCanceled()) {
                onCancele(call);
            }
            if (httpResult.isSuccessful()){
                onSuccess(call,httpResult.getBody());
            }else {
                onError(call,httpResult.getHttpError());
            }
        }finally {
            CallManager.getInstance().remove(call);
        }

    }

    public abstract void onSuccess(Call<T> call,Response<T> t);

    public abstract void onError(Call<T> call,HttpError error);

    public  void onCancele(Call<T>call){

    }
}
