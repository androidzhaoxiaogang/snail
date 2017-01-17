package com.snail.service;

import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Administrator on 2017/1/14.
 */
public interface MapperService {
    <M extends Mapper> M getMapper();
}
