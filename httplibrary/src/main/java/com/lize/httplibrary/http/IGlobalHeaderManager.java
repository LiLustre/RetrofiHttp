package com.lize.httplibrary.http;

import java.util.Map;

import okhttp3.Headers;

/**
 * Created by Lize on 2018/12/3
 */
public interface IGlobalHeaderManager {

    /**
     * 获取响应的 header值
     * @param headers
     * @param key
     * @return
     */
    String getResponeHeaderValue(Headers headers, String key);

    /**
     * 获取保存的 header 用于添加到请求头
     * @return
     */
    Map<String,String> getRequestHeader();

}
