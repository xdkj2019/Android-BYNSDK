package com.xidian.bynadsdk.network.response;

/**
 * Created by Zaifeng on 2018/2/28.
 * 返回结果封装
 */

public class Response<T> {

    private int code; // 返回的code
    private T data; // 具体的数据结果
    private String message; // message 可用来返回接口的说明
    private String runtime;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
