package com.example.demos.exception;

import com.example.demos.commons.BaseResponse;
import com.example.demos.commons.ErrorCode;
import com.example.demos.commons.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public <T> BaseResponse<T> baseExceptionHandler(BaseException e) {
        return ResultUtils.failed(e.getCode(),e.getMessage(),e.getDescription());
    }
    @ExceptionHandler(RuntimeException.class)
    public <T>BaseResponse<T> runtimeExceptionHandler(RuntimeException e){
        return ResultUtils.failed(ErrorCode.SYSTEM_ERROR,"",e.getMessage());
    }


}
