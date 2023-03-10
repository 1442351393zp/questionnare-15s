package com.infoac.cas.dto;

/**
 * @Author: SenjiePeng
 * @Description:
 * @Date: Created in  2019/7/9 10:57
 */
public class BaseResult<T> {
    private  String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
