package com.snail.datasource;


import java.lang.annotation.*;

/**
 * 通过注解，动态切换数据源。在动态数据源启用时才生效
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseDataSource {
    /**
     * 数据源ID{@link DataSource#getId()},通过{@link DynamicDataSource}进行数据源切换。
     * <br>
     * 如果数据源id不存在，将使用默认的数据源
     *
     * @return 数据源ID
     * @see DynamicDataSource#use(String)
     */
    String value();

}
