package com.sanzong.obe.utils;

import lombok.Data;

@Data
public class ResponseBody {
    private String status;
    private Object data;
    private String msg;

    public ResponseBody(String status, Object data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public ResponseBody(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    //getters and setters
}