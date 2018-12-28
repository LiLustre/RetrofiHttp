package com.lize.httplibrary.http.exception;

import java.io.IOException;

/**
 * Created by Lize on 2018/11/30
 * json 解析异常，用于 异常判断
 */
public class PaserJsonException extends IOException {
    private static final long serialVersionUID = 5566288521902249012L;

    public PaserJsonException(Throwable cause) {
        super(cause);
    }
}
