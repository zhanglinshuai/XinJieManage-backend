package com.example.demos.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data
public class RegisterRequest implements Serializable {


    private static final long serialVersionUID = -1541563849833927287L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}
