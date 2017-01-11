package com.snail.datasource;


import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 动态数据源sql执行器
 */
public class DynamicDataSourceSqlExecutorService extends AbstractJdbcSqlExecutor implements ExpressionScopeBean {

    @Resource
    protected DynamicDataSource dynamicDataSource;

    @Override
    public Connection getConnection() {
        DataSource dataSource = dynamicDataSource.getActiveDataSource();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        boolean isConnectionTransactional = DataSourceUtils.isConnectionTransactional(connection, dataSource);
        if (logger.isDebugEnabled()) {
            logger.debug("DataSource ({}) JDBC Connection [{}] will {} be managed by Spring", DataSourceHolder.getActiveSourceId(), connection, (isConnectionTransactional ? "" : "not"));
        }
        return connection;
    }

    @Override
    public void releaseConnection(Connection connection) throws SQLException {
        if (logger.isDebugEnabled()) {
            logger.debug("Releasing DataSource ({}) JDBC Connection [{}]", DataSourceHolder.getActiveSourceId(), connection);
        }
        DataSourceUtils.releaseConnection(connection, dynamicDataSource.getActiveDataSource());
    }

    @Override
    @Transactional(readOnly = true)
    public <T> List<T> list(SQL sql, ObjectWrapper<T> wrapper) throws SQLException {
        return super.list(sql, wrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T single(SQL sql, ObjectWrapper<T> wrapper) throws SQLException {
        return super.single(sql, wrapper);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> list(SQL sql) throws SQLException {
        return super.list(sql);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> single(SQL sql) throws SQLException {
        return super.single(sql);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> list(String sql) throws SQLException {
        return super.list(sql);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> list(String sql, Object param) throws SQLException {
        return super.list(sql, param);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> single(String sql) throws SQLException {
        return super.single(sql);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> single(String sql, Object param) throws SQLException {
        return super.single(sql, param);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void exec(String sql) throws SQLException {
        super.exec(new SimpleSQL(sql));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void exec(SQL sql) throws SQLException {
        super.exec(sql);
    }

}
