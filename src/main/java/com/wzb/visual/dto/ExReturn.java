package com.wzb.visual.dto;

import java.util.HashMap;

//封装json数据以供前台获取
//public class ExReturn extends HashMap<String,Object> {
public class ExReturn {
    private boolean success;
    private Object msg;
    private Object data;

    public static final String DATA = "data";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
//        put(DATA,data);
    }
}
