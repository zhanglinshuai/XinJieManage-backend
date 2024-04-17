package com.example.demos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demos.pojo.domain.User;
import com.example.demos.service.UserService;
import com.example.demos.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final String SALT = "XinJieManage";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //非空校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
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
        //校验账号中是否含有非法字符
        String regEx = "[\\u00A0\\s\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern compile = Pattern.compile(regEx);
        Matcher matcher = compile.matcher(userAccount);
        //如果find找到成功之后，返回-1
        if (matcher.find()) {
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
}




