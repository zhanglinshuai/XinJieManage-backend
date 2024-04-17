package com.example.demos.commons;

/**
 * 通用返回对象
 */
public class BaseResponse<T> {

    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = "";
    }
    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
        this.message = "";
        this.description ="";
    }
    public BaseResponse(ErrorCode errorCode,String description) {
        this.code = errorCode.getErrorCode();
        this.data = null;
        this.message = errorCode.getMessage();
        this.description =description;
    }
    public BaseResponse(ErrorCode errorCode) {
        this.code = errorCode.getErrorCode();
        this.data = null;
        this.message = errorCode.getMessage();
        this.description =errorCode.getDescription();
    }




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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
