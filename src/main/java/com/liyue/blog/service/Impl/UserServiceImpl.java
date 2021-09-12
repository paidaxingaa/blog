package com.liyue.blog.service.Impl;

import com.liyue.blog.dao.UserMapper;
import com.liyue.blog.entity.User;
import com.liyue.blog.service.UserService;
import com.liyue.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liyue
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(String username, String password) {
        return userMapper.findUserByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
