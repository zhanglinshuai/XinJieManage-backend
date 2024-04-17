package com.example.demos.service;

import com.example.demos.pojo.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

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



}
