package com.snail.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by Administrator on 2017/1/16.
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
