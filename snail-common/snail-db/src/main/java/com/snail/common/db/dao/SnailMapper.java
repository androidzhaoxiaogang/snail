package com.snail.common.db.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 *
 * @author zhao xiaogang
 * @since 2016-03-29
 */
public interface SnailMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
