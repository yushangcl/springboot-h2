//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.whh.springboot.h2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JdbcUtil {
    private static final Log logger = LogFactory.getLog(JdbcUtil.class);
    private String url;
    private String classDriver;
    private String userName;
    private String password;
    private Connection conn = null;
    private PreparedStatement pst = null;
    private boolean hasBatch = false;

    public JdbcUtil() {
        this.conn = this.getConnectionNoPool();
    }

    private Connection getConnectionNoPool() {
        if(this.conn != null) {
            return this.conn;
        } else {
            Connection connection = null;

            try {
                // 此处修改为 spring boot 默认 datasource 配置
                ResourceBundle rbn = ResourceBundle.getBundle("application");
                this.url = rbn.getString("spring.datasource.url");
                this.classDriver = rbn.getString("spring.datasource.driver-class-name");
                this.userName = rbn.getString("spring.datasource.username");
                this.password = rbn.getString("spring.datasource.password");
            } catch (Exception var5) {
                logger.error("application.properties not found", var5);
            }

            try {
                Class.forName(this.classDriver);
                connection = DriverManager.getConnection(this.url, this.userName, this.password);
            } catch (ClassNotFoundException var3) {
                logger.error("jdbc class not found", var3);
            } catch (SQLException var4) {
                logger.error("", var4);
            }

            return connection;
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        logger.debug(sql);
        this.pst = this.conn.prepareStatement(sql, 1004, 1007);
        return this.pst.executeQuery();
    }

    public Object executeScalar(String sql) throws SQLException {
        ResultSet rs = this.executeQuery(sql);
        return rs.first()?rs.getObject(1):null;
    }

    public ResultSet executeQuery(String sql, Object[] objs) throws SQLException {
        logger.debug(sql);
        this.pst = this.conn.prepareStatement(sql, 1004, 1007);
        if(objs != null) {
            for(int i = 0; i < objs.length; ++i) {
                this.pst.setObject(i + 1, objs[i]);
            }
        }

        return this.pst.executeQuery();
    }

    public int executeUpdate(String sql) throws SQLException {
        logger.debug(sql);
        this.pst = this.conn.prepareStatement(sql);
        return this.pst.executeUpdate();
    }

    public int executeUpdate(String sql, Object[] objs) throws SQLException {
        logger.debug(sql);
        this.pst = this.conn.prepareStatement(sql);
        if(objs != null) {
            for(int i = 0; i < objs.length; ++i) {
                this.pst.setObject(i + 1, objs[i]);
            }
        }

        return this.pst.executeUpdate();
    }

    public void addBatch(String sql, Object[] objs) throws SQLException {
        logger.debug(sql);
        if(this.pst == null && !this.hasBatch) {
            this.pst = this.conn.prepareStatement(sql);
        }

        if(objs != null) {
            for(int i = 0; i < objs.length; ++i) {
                this.pst.setObject(i + 1, objs[i]);
            }
        }

        if(null != this.pst) {
            this.pst.addBatch();
            this.hasBatch = true;
        }

    }

    public int[] executeBatch() throws SQLException {
        if(!this.hasBatch) {
            throw new SQLException("Do not add batch");
        } else {
            this.hasBatch = false;
            return this.pst.executeBatch();
        }
    }

    public void close() {
        try {
            if(this.pst != null) {
                this.pst.close();
                this.pst = null;
            }
        } catch (Exception var3) {
            ;
        }

        try {
            if(this.conn != null) {
                this.conn.close();
                this.conn = null;
            }
        } catch (Exception var2) {
            ;
        }

    }

    public void beginTransaction() throws SQLException {
        this.conn.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        this.conn.commit();
        this.conn.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        this.conn.rollback();
    }
}
