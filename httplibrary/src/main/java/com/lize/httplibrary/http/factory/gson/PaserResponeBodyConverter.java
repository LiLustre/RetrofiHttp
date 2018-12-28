package com.lize.httplibrary.http.factory.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.lize.httplibrary.http.exception.PaserJsonException;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Lize on 2018/11/30
 */
public class PaserResponeBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;


    PaserResponeBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            return adapter.read(jsonReader);
        } catch (Exception e){
            e.printStackTrace();
            throw new PaserJsonException(e);
        }finally {
            value.close();
        }
    }
}
