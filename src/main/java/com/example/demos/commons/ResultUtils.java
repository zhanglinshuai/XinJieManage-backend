package com.example.demos.commons;

/**
 * 通用返回对象工具类
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"返回数据成功");
    }
    public static <T> BaseResponse<T> failed(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }
    public static <T> BaseResponse<T> failed(ErrorCode errorCode,String description){
        return new BaseResponse<>(errorCode,description);
    }
    public static <T> BaseResponse<T> failed(ErrorCode errorCode,String message,String description){
        return new BaseResponse<>(errorCode.getErrorCode(),null,message,description);
    }

    public static <T> BaseResponse<T> failed(int code,String message,String description){
        return new BaseResponse<>(code,null,message,description);
    }

}
