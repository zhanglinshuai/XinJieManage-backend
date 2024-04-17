package com.example.demos.service;

import com.example.demos.pojo.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 86175
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2024-04-17 11:56:39
 */
public interface UserService extends IService<User> {


    /**
     * 用户注册
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 用户信息脱敏
     *
     * @param originUser 初始用户
     * @return 脱敏后的用户信息
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @param request
     * @return
     */
    User queryUserByName(String username, HttpServletRequest request);

    /**
     * 获取当前用户信息
     *
     * @param request
     * @return
     */
    User getCurrentUser(HttpServletRequest request);

    /**
     * 校验用户是否为管理员
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 校验用户账号是否含有非法字符
     * @param userAccount
     * @return
     */
    boolean verifyUserAccount(String userAccount);

    /**
     * 获取所有用户信息
     * @param request
     * @return
     */
    List<User> getUserList(HttpServletRequest request);


    /**
     * 删除用户
     * @param request
     * @param id  要删除的用户id
     * @return
     */
    boolean deleteUserById(long id,HttpServletRequest request);
}
