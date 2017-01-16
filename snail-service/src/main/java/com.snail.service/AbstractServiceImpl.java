package com.snail.service;

import com.snail.domain.po.BasePo;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * Created by zhaoxiaogang on 2017/1/14.
 */
public abstract class AbstractServiceImpl<Po extends BasePo<PK>, PK> implements BaseService<Po, PK>  {
    @Resource
    private Mapper mapper;

    @Override
    public Mapper getMapper(){
        return mapper;
    }
}
