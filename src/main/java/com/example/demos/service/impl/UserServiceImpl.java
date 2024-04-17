package com.example.demos.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demos.pojo.domain.User;
import com.example.demos.service.UserService;
import com.example.demos.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.demos.constants.UserConstants.*;

/**
 * @author 86175
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-04-17 11:56:39
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        //非空校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            return -1;
        }
        //账号长度最少为4位
        if (userAccount.length() < 4) {
            return -1;
        }
        //密码长度最少为6位
        if (userPassword.length() < 6 || checkPassword.length() < 6) {
            return -1;
        }
        //密码与校验密码是否相等
        if (userPassword.equals(checkPassword)) {
            return -1;
        }
        //星球编号长度最多为5位
        if (planetCode.length() > 4) {
            return -1;
        }
        //校验账号中是否含有非法字符
        boolean result = verifyUserAccount(userAccount);
        if (!result){
            return -1;
        }
        //对密码进行加密
        String safetyPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());
        //查询账号是否重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        Long count = userMapper.selectCount(userQueryWrapper);
        //如果count>0说明账号重复了
        if (count > 0) {
            return -1;
        }
        //创建新用户,并将新用户设置值，将密码密文存储到数据库中
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(safetyPassword);
        boolean save = this.save(user);
        //如果save为false
        if (!save) {
            return -1;
        }
        return user.getId();
    }


    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //非空判断
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
        //校验用户账号含有特殊字符
        boolean result = verifyUserAccount(userAccount);
        if (!result) {
            return null;
        }
        //从数据库中查询用户，校验账号和密码与存入数据库中的密文密码是否相等
        String safetyPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        userQueryWrapper.eq("userPassword", safetyPassword);
        User user = this.getOne(userQueryWrapper);
        //对用户信息进行脱敏
        User safetyUser = getSafetyUser(user);
        //将用户信息存储到session中
        request.getSession().setAttribute(USER_LOGIN_STATUS, safetyUser);
        //返回脱敏后的用户信息
        return safetyUser;
    }


    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request==null){
            return false;
        }
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return true;
    }

    @Override
    public User queryUserByName(String username, HttpServletRequest request) {
        //非空校验
        if (StringUtils.isBlank(username)) {
            return null;
        }
        //用户长度校验
        if (username.length() < 4) {
            return null;
        }
        //是否为管理员校验
        boolean admin = isAdmin(request);
        if (!admin) {
            return null;
        }
        //是管理员根据用户名称进行查询用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = this.getOne(userQueryWrapper);
        if (user == null) {
            return null;
        }
        //对用户信息进行脱敏,返回脱敏后的用户信息
        return getSafetyUser(user);
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        if (request==null){
            return null;
        }
        //从登录态中获取登录用户信息
        User user = getUser(request);
        if (user == null) {
            return null;
        }
        //返回脱敏后的用户信息
        return getSafetyUser(user);
    }


    @Override
    public List<User> getUserList(HttpServletRequest request) {
        if (request==null){
            return null;
        }
        User user = getUser(request);
        if (user == null) {
            return null;
        }
        //判断用户是否为管理员
        boolean admin = isAdmin(request);
        if (!admin) {
            return null;
        }
        //直接查询用户列表
        List<User> userList = this.list();
        //返回脱敏后的用户信息列表
        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }


    @Override
    public boolean deleteUserById(long id,HttpServletRequest request) {
        //从登录态中取出用户信息
        User user = getUser(request);
        if (user == null) {
            return false;
        }
        //校验用户是否为管理员
        boolean admin = isAdmin(request);
        if (!admin){
            return false;
        }
        boolean result = this.removeById(id);
        if (!result){
            return false;
        }
        return true;
    }


    /**
     * 用户信息脱敏
     *
     * @param originUser
     * @return 脱敏后的用户信息
     */
    public User getSafetyUser(User originUser) {
        User user = new User();
        user.setUsername(originUser.getUsername());
        user.setUserAccount(originUser.getUserAccount());
        user.setAvatarUrl(originUser.getAvatarUrl());
        user.setGender(originUser.getGender());
        user.setCreateTime(originUser.getCreateTime());
        user.setUpdateTime(originUser.getUpdateTime());
        user.setIsDelete(originUser.getIsDelete());
        user.setUserRole(originUser.getUserRole());
        user.setPlanetCode(originUser.getPlanetCode());
        user.setEmail(originUser.getEmail());
        user.setPhone(originUser.getPhone());
        user.setTags(originUser.getTags());
        user.setProfile(originUser.getProfile());
        return user;
    }

    /**
     * 校验用户是否为管理员
     *
     * @param request
     * @return
     */
    public boolean isAdmin(HttpServletRequest request) {
        User user = getUser(request);
        return user.getUserRole().equals(ADMIN_AUTH);
    }


    /**
     * 校验用户账号是否含有非法字符
     *
     * @param userAccount
     * @return
     */
    public  boolean verifyUserAccount(String userAccount) {
        String regEx = "[\\u00A0\\s\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern compile = Pattern.compile(regEx);
        Matcher matcher = compile.matcher(userAccount);
        //如果find找到成功之后，返回-1
        if (matcher.find()) {
            return false;
        }
        return true;
    }

    /**
     * 从登录态中获取用户信息
     *
     * @param request
     * @return
     */
    private  User getUser(HttpServletRequest request) {
        //从登录态中获取用户信息
        Object obj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) obj;
        return user;
    }
}




