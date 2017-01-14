package com.snail.service;

/**
 * Created by Administrator on 2017/1/14.
 */
public interface BaseService<PO,PK> extends InsertService<PO,PK>, QueryService<PO,PK>,
        UpdateService<PO>, DeleteService<PK>, MapperService {

}
