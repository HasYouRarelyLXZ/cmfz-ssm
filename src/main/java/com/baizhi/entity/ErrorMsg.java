package com.baizhi.entity;

import java.io.Serializable;

public class ErrorMsg implements Serializable {
    private String error;
    private String errmsg;

    public ErrorMsg() {
    }

    public ErrorMsg(String error, String errmsg) {
        this.error = error;
        this.errmsg = errmsg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
