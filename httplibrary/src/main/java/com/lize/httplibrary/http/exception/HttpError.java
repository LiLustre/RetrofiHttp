package com.lize.httplibrary.http.exception;


import retrofit2.Response;

/**
 * Created by Lize on 2018/11/30
 */
public class HttpError extends RuntimeException {
    private String msg;
    private Response response;
    private Object errorBody;
    public HttpError(String msg, Object errorBody) {
        if (errorBody instanceof Throwable){
            initCause((Throwable) errorBody);
        }
        if (errorBody instanceof Response){
          this.response = (Response) errorBody;
        }
        this.errorBody = errorBody;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }


    public Response getResponse() {
        return response;
    }

    public Object getErrorBody() {
        return errorBody;
    }

    @Override
    public String toString() {
        return "HttpError {msg="
                + msg
                + ", body="
                + errorBody
                + '}';
    }
}
