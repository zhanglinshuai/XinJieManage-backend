package com.example.demos.service;

import com.example.demos.pojo.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 86175
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-04-17 11:56:39
*/
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户登录
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param request
     * @return  脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 用户信息脱敏
     * @param originUser 初始用户
     * @return  脱敏后的用户信息
     */
     User getSafetyUser(User originUser);
}
