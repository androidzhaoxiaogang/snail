package com.snail.service;

/**
 * Created by Administrator on 2017/1/14.
 */
public interface DeleteService<PK> extends MapperService {
    default int delete(PK pk) {
        return getMapper().deleteByPrimaryKey(pk);
    }
}
