package com.snail.service;

import com.snail.domain.po.BasePo;

/**
 * Created by Administrator on 2017/1/14.
 */
public interface InsertService<PO,PK> extends MapperService{

    default PK insert(PO po){
        PK primaryKey = null;
        if (po instanceof BasePo) {
            if (((BasePo) po).getId() == null)
                ((BasePo) po).setId(BasePo.primaryKey());
            primaryKey = (PK) ((BasePo) po).getId();
        }
        getMapper().insert(po);
        return primaryKey;
    }

    default PK insertSelective(PO po){
        PK primaryKey = null;
        if (po instanceof BasePo) {
            if (((BasePo) po).getId() == null)
                ((BasePo) po).setId(BasePo.primaryKey());
            primaryKey = (PK) ((BasePo) po).getId();
        }
        getMapper().insertSelective(po);
        return primaryKey;
    }
}
