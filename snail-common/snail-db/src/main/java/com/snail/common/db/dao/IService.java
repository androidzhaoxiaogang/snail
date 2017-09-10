package com.snail.common.db.dao;

import java.util.List;


public interface IService<T> {

    T select(T entity);

    T selectById(Object key);

    int insert(T entity);

    T insertReturn(T entity);

    int insertSelective(T entity);

    T insertSelectiveReturn(T entity);

    int deleteById(Object key);

    void delete(T entity);

    int update(T entity);

    T updateReturn(T entity);

    int updateSelective(T entity);

    T updateSelectiveReturn(T entity);

    List<T> selectListAll();

    List<T> selectByExample(Object example);

    List<T> selectBatchByIds(List<Object> ids);

    Long selectCount(T entity);

    void deleteBatchByIds(List<Object> ids);

    void updateBatch(List<T> entitys);

    void insertBatch(List<T> list);
}
