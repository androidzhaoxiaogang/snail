package com.snail.datasource;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceHolder {

    private static DynamicDataSource dynamicDataSource;

    private static DataSource defaultDataSource;

    public void init(DataSource dataSource) throws SQLException {
        if (null != dataSource) {
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                install(dataSource);
            } finally {
                if (null != connection)
                    connection.close();
            }
        }
    }

    public static DataSource getActiveSource() {
        if (dynamicDataSource != null) {
            return dynamicDataSource.getActiveDataSource();
        }
        return defaultDataSource;
    }

    public static String getActiveSourceId() {
        if (DynamicDataSource.getActiveDataSourceId() != null) {
            return DynamicDataSource.getActiveDataSourceId();
        }
        return "default";
    }

    public static DataSource getDefaultDataSource() {
        return defaultDataSource;
    }

    public static void install(DynamicDataSource dynamicDataSource) {
        if (DataSourceHolder.dynamicDataSource != null) {
            throw new UnsupportedOperationException();
        }
        DataSourceHolder.dynamicDataSource = dynamicDataSource;
    }

    public static void install(DataSource dataSource) {
        if (DataSourceHolder.defaultDataSource != null) {
            throw new UnsupportedOperationException();
        }
        DataSourceHolder.defaultDataSource = dataSource;
    }
}
