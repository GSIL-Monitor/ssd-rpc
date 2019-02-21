package com.shabro.rpc.util;

import java.io.Serializable;

/**
 * Created by kui on 2017/8/29.
 */
public class ApiResponse implements Serializable {

    private Integer state;
    private String message;
    private Object data;

    public  ApiResponse(){
        super();
    }
    public ApiResponse(Integer state, String message) {
        super();
        this.state = state;
        this.message = message;
    }

    public ApiResponse(Integer state, String message, Object data) {
        super();
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
