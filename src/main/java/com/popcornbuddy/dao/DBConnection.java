package main.java.com.popcornbuddy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection { // DB커넥션
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/popcornbuddy?useUnicode=true&characterEncoding=utf8";
    private static final String USERNAME = "bitcamp01";
    private static final String PASSWORD = "qwer1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}