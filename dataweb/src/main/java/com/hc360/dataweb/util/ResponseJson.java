package com.hc360.dataweb.util;

/**
 * Created by dell on 2017/7/11.
 */
public class ResponseJson {

    private Integer errno;

    private String msg;

    private Object data;

    public Integer getErrno() {
        return errno;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
