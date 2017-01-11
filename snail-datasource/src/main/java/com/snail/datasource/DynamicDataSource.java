package com.snail.datasource;


import com.snail.common.util.ThreadLocalUtils;

import javax.sql.DataSource;

/**
 * 动态数据源接口,此接口实现多数据源的动态切换
 *
 * @see DataSourceHolder
 */
public interface DynamicDataSource extends DataSource {
    String DATA_SOURCE_FLAG = "data-source-id";

    String DATA_SOURCE_FLAG_LAST = "data-source-id-last";

    /**
     * 使用上一次调用的数据源
     */
    static void useLast() {
        use(ThreadLocalUtils.get(DATA_SOURCE_FLAG_LAST));
    }

    /**
     * 选中参数(数据源ID)对应的数据源,如果数据源不存在,将使用默认数据源
     *
     * @param dataSourceId 数据源ID
     */
    static void use(String dataSourceId) {
        ThreadLocalUtils.put(DATA_SOURCE_FLAG, dataSourceId);
    }

    /**
     * 获取当前使用的数据源ID,如果不存在则返回null
     *
     * @return 数据源ID
     */
    static String getActiveDataSourceId() {
        return ThreadLocalUtils.get(DATA_SOURCE_FLAG);
    }

    /**
     * 切换为默认数据源,并指定是否记住上一次选中的数据源
     *
     * @param rememberLast 是否记住上一次选中的数据源
     */
    static void useDefault(boolean rememberLast) {
        if (getActiveDataSourceId() != null && rememberLast)
            ThreadLocalUtils.put(DATA_SOURCE_FLAG_LAST, getActiveDataSourceId());
        ThreadLocalUtils.remove(DATA_SOURCE_FLAG);
    }

    /**
     * 切换为默认数据源并记住上一次使用的数据源
     *
     * @see this#useDefault(boolean)
     */
    static void useDefault() {
        useDefault(true);
    }

    /**
     * 获取当前激活的数据源
     *
     * @return
     */
    DataSource getActiveDataSource();

}
