package com.snail.sample.impl;

import com.snail.sample.dao.UserMapper;
import com.snail.sample.domain.po.User;
import com.snail.sample.service.UserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by zhaoxiaogang on 2017/1/14.
 */

@Service("userService")
public class UserServiceImpl implements UserService<User, Integer> {
    @Resource
    private UserMapper userMapper;

    @Override
    public int update(List<User> users) {
        return 0;
    }

    @Override
    public int saveOrUpdate(User user) {
        return 0;
    }

    @Override
    public Mapper getMapper() {
        return this.userMapper;
    }
}
