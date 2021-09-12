package com.liyue.blog.dao;

import com.liyue.blog.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liyue
 */
public interface UserMapper {
    /**
     * 返回所有用户
     * @return List<User>
     */
    public List<User> getAllUser();

    /**
     * 登陆验证
     * @param username
     * @param password
     * @return User
     */
    public User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
