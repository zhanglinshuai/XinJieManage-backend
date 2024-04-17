package com.example.demos.exception;

import com.example.demos.commons.ErrorCode;

public class BaseException extends RuntimeException{


    private int code;
    private String description;


    public BaseException(int code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
    public BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getErrorCode();
        this.description = errorCode.getDescription();
    }
    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getErrorCode();
        this.description = errorCode.getDescription();
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
