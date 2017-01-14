package com.snail.service;

import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */
public interface UpdateService<PO> extends MapperService{
    default int update(PO po)  {
        return getMapper().updateByPrimaryKey(po);
    }

    /**
     * 批量修改记录
     *
     * @param poList 要修改的记录集合
     * @return 影响记录数
     */
    int update(List<PO> poList);

    /**
     * 修改不为空数据
     *
     * @param po 要修改的数据
     * @return
     */
    default int updateSelective(PO po) {
        return getMapper().updateByPrimaryKeySelective(po);
    }

    /**
     * 保存或修改
     *
     * @param po 要修改的数据
     * @return
     */
    int saveOrUpdate(PO po);
}
