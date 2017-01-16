package com.snail.sample.impl;

import com.snail.sample.domain.po.User;
import com.snail.sample.service.UserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * Created by zhaoxiaogang on 2017/1/14.
 */

@Service("userService")
public class UserServiceImpl implements UserService<User, Integer> {

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
        return null;
    }
}
