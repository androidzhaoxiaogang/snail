package com.snail.common.domain.po;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.UUID;

/**
 * PO对象基类，实现基本的属性和方法。新建的PO都应继承该类
 */
public class BasePo<PK> implements Serializable, Cloneable {
    private static final long serialVersionUID = 9197157871004374522L;
    /**
     * 主键
     */
    @Transient
    protected PK id;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }


    @Override
    public int hashCode() {
        if (getId() == null) return 0;
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return this.hashCode() == obj.hashCode();
    }

    /**
     * 创建主键
     *
     */
    public static String primaryKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }



}
