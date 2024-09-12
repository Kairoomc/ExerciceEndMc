package me.kairomc.architecture.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static final String URL = "jdbc:mysql://localhost:8889/";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private final DataSource dataSource;

    public DatabaseConnectionManager() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000); 

        this.dataSource = new HikariDataSource(config);
    }
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
