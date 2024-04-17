package com.example.demos.commons;


/**
 * 自定义错误码
 */
public enum ErrorCode {
    SUCCESS(0,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求参数为空",""),

    NOT_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"无权限",""),
    SYSTEM_ERROR(50000,"系统内部异常","")
    ;


    private final int errorCode;
    private final String message;
    private final String description;

    ErrorCode(int errorCode, String message, String description) {
        this.errorCode = errorCode;
        this.message = message;
        this.description = description;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

}
