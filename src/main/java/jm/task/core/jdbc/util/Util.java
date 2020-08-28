package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static String url = "jdbc:mysql://127.0.0.1:3306/usersdb?serverTimezone=Europe/Moscow&useSSL=false";
    private static String username = "root";
    private static String password = "01v891ea";

    public static Connection getConnection() throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
