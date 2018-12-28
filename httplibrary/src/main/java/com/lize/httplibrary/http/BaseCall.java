package com.lize.httplibrary.http;


import com.lize.httplibrary.http.TagManager.CallManager;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 */
public    class BaseCall<T> {

    private final Call<T> call;

    public BaseCall(Call<T> call) {
        this.call = call;
    }

    public void enqueue(Object tag, BaseCallBack<T> callback){
        Utils.checkNotNull(callback,"callback is NULL");
        if (tag!=null){
            CallManager.getInstance().add(call,tag);
        }
        call.enqueue(callback);
    }



    public Response execute() throws IOException {
        return call.execute();
    }

    public boolean isExecuted() {
        return call.isExecuted();
    }


    public void cancel() {
        call.cancel();
    }


    public boolean isCanceled() {
        return call.isCanceled();
    }

    @Override
    public Call clone() {
        return call.clone();
    }


    public Request request() {
        return call.request();
    }
}
