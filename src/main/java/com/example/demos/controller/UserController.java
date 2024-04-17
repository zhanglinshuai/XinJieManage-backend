package com.example.demos.controller;

import com.example.demos.pojo.domain.User;
import com.example.demos.request.LoginRequest;
import com.example.demos.request.RegisterRequest;
import com.example.demos.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("register")
    public Long userRegister(@RequestBody RegisterRequest registerRequest) {
        //请求参数非空校验
        if (registerRequest == null) {
            return null;
        }
        String userAccount = registerRequest.getUserAccount();
        String userPassword = registerRequest.getUserPassword();
        String planetCode = registerRequest.getPlanetCode();
        String checkPassword = registerRequest.getCheckPassword();
        //参数非空校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            return null;
        }
        //账号长度最少为4位
        if (userAccount.length() < 4) {
            return null;
        }
        //密码长度最少为6位
        if (userPassword.length() < 6 || checkPassword.length() < 6) {
            return null;
        }
        //密码与校验密码是否相等
        if (userPassword.equals(checkPassword)) {
            return null;
        }
        //星球编号长度最多为5位
        if (planetCode.length() > 4) {
            return null;
        }
        //校验用户账号是否含有非法字符
        boolean result = userService.verifyUserAccount(userAccount);
        if (result) {
            return null;
        }
        long userId = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        if (userId < 0) {
            return null;
        }
        return userId;
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        //用户非空判断
        if (loginRequest == null) {
            return null;
        }
        String userAccount = loginRequest.getUserAccount();
        String userPassword = loginRequest.getUserPassword();
        //参数非空判断
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        //账号长度不少于4位
        if (userAccount.length() < 4) {
            return null;
        }
        //密码不少于6位
        if (userPassword.length() < 6) {
            return null;
        }
        //校验用户账号是否含有特殊字符
        boolean result = userService.verifyUserAccount(userAccount);
        if (!result) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }


    @PostMapping("/logout")
    public boolean userLogout(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        boolean result = userService.userLogout(request);
        if (!result) {
            return false;
        }
        return true;
    }


    @GetMapping("/select/name")
    public User selectUserByName(String username, HttpServletRequest request) {
        //参数判空
        if (StringUtils.isBlank(username)) {
            return null;
        }
        if (request == null) {
            return null;
        }
        //返回脱敏后的用户信息
        User user = userService.queryUserByName(username, request);
        if (user == null) {
            return null;
        }
        return user;
    }

    @GetMapping("/getCurrent")
    public User getCurrentUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        User currentUser = userService.getCurrentUser(request);
        if (currentUser == null) {
            return null;
        }
        return currentUser;
    }

    @GetMapping("/get/users")
    public List<User> getUsers(HttpServletRequest request) {
        if (request == null) {
            return new ArrayList<>();
        }
        List<User> userList = userService.getUserList(request);
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList;
    }

    @PostMapping("/deleteById")
    public boolean deleteUserById(long id,HttpServletRequest request) {
        if (id < 0) {
            return false;
        }
        boolean result = userService.deleteUserById(id, request);
        if (!result){
            return false;
        }
        return true;
    }

}
