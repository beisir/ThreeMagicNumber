package com.hc360.dataweb.model

/**
 * Created by home on 2017/2/9.
 */
class AboveDataBean {
    String errno;
    Map<String,Object> data;

    AboveDataBean(String errno, Map<String, Object> data) {
        this.errno = errno
        this.data = data
    }

    @Override
    public String toString() {
        return "AboveDataBean{" +
                "errno='" + errno + '\'' +
                ", data=" + data +
                '}';
    }
}
