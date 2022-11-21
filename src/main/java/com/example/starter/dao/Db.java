package com.example.starter.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Db {
    protected Db() {}

    private static Logger logger = Logger.getLogger(Db.class.getName());
    private static HikariDataSource pool;

    static {
        try {

            HikariConfig config = new HikariConfig();
            config.setMaximumPoolSize(10);
            config.setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
            config.setJdbcUrl("jdbc:sqlserver://127.0.0.1:1433");
            config.addDataSourceProperty("user", "SA");
            config.addDataSourceProperty("password", "Thang1997");
            config.addDataSourceProperty("databaseName", "tms_db");
            config.setPoolName("tms_db");
            config.setMinimumIdle(1);
            config.setMaximumPoolSize(20);
            config.setConnectionTestQuery("SELECT GETDATE();");
            pool = new HikariDataSource(config);
            logger.info("Pool created");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }
    }

    public static void close(AutoCloseable... objs) {
        for (AutoCloseable obj : objs)
            try {
                if (obj != null)
                    obj.close();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            }
    }

    protected static Connection getConnection() throws SQLException {
        return pool.getConnection();
    }
}
