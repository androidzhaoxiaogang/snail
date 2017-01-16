package com.snail.sample.dao;

import com.snail.sample.domain.po.User;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by zhaoxiaogang on 2017/1/15.
 */
public interface UserDao<User> extends Mapper<User> {
}
