package com.snail.service;

import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */
public interface QueryService<PO,PK> extends MapperService{
    default List<PO> selectAll(){
        return getMapper().selectAll();
    }

    default List<PO> selectByExample(PK pk) {
        return getMapper().selectByExample(pk);
    }

    default PO selectByPk(PK pk) {
        return (PO) getMapper().selectByPrimaryKey(pk);
    }
}
