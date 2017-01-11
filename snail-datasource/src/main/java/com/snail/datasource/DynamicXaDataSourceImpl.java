package com.snail.datasource;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public class DynamicXaDataSourceImpl extends AbstractDataSource implements DynamicDataSource, XADataSource, Closeable {
    private Logger               logger            = LoggerFactory.getLogger(DynamicDataSource.class);
    private DataSource defaultDataSource = null;
    protected DynamicDataSourceService dynamicDataSourceService;

    public DynamicXaDataSourceImpl(DataSource defaultDataSource) {
        Assert.notNull(defaultDataSource);
        this.defaultDataSource = defaultDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getActiveDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getActiveDataSource().getConnection(username, password);
    }

    public DataSource getActiveDataSource() {
        String sourceId = DynamicDataSource.getActiveDataSourceId();
        logger.info("use datasource:{}", sourceId == null ? "default" : sourceId);
        if (sourceId == null || dynamicDataSourceService == null) return defaultDataSource;
        DataSource dataSource = dynamicDataSourceService.getDataSource(sourceId);
        if (dataSource == null) {
            logger.info("use datasource:{} fail,because its not exists! use default datasource now.", sourceId);
            return defaultDataSource;
        }
        return dataSource;
    }

    public XADataSource getActiveXADataSource() {
        DataSource activeDs = getActiveDataSource();
        XADataSource xaDataSource;
        if (activeDs instanceof XADataSource)
            xaDataSource = ((XADataSource) activeDs);
        else if (activeDs instanceof AtomikosDataSourceBean) {
            xaDataSource = ((AtomikosDataSourceBean) activeDs).getXaDataSource();
        } else {
            throw new UnsupportedOperationException(activeDs.getClass() + " is not XADataSource");
        }
        return xaDataSource;
    }

    public synchronized void setDynamicDataSourceService(DynamicDataSourceService dynamicDataSourceService) {
        if (this.dynamicDataSourceService != null) throw new UnsupportedOperationException();
        this.dynamicDataSourceService = dynamicDataSourceService;
    }

    @Override
    public XAConnection getXAConnection() throws SQLException {
        return getActiveXADataSource().getXAConnection();
    }

    @Override
    public XAConnection getXAConnection(String user, String password) throws SQLException {
        return getActiveXADataSource().getXAConnection(user, password);
    }

    public void close() {
        try {
            if (dynamicDataSourceService != null)
                dynamicDataSourceService.destroyAll();
        } catch (Exception e) {
            logger.error("close datasource error", e);
        }
    }
}
