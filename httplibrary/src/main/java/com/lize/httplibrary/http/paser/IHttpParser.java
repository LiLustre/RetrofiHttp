package com.lize.httplibrary.http.paser;



import com.lize.httplibrary.http.HttpResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 */
public interface IHttpParser<T> {


    HttpResult parseResponse(Call<T> call, Response<T> response);

    HttpResult parserThrowable(Call<T> call, Throwable t);

}
