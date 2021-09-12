package com.liyue.blog.service;

import com.liyue.blog.entity.User;

/**
 * @author liyue
 */
public interface UserService {
    /**
     * 后台登陆
     * @param username
     * @param password
     * @return
     */
    public User login(String username,String password);
}
