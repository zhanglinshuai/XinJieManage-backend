package com.example.demos.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 */
@Data
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = -1450788861160085314L;

    private String userAccount;

    private String userPassword;


}
