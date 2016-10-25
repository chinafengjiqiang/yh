package com.yh.model;

/**
 * Created by FQ.CHINA on 2016/5/10.
 */
public class RetVO {
    private boolean success = false;//操作是否成功

    private String msg = ""; //操作完成返回的信息

    private Object obj = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
