package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    UserServiceImpl userService;

    public UserDaoJDBCImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id INTEGER AUTO_INCREMENT PRIMARY KEY, \n" +
                "    firstname VARCHAR(30), \n" +
                "    lastname VARCHAR(30), \n" +
                "    age INTEGER\n" +
                ");";
        try (Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = String.format("INSERT INTO users (firstname, lastname ,age) VALUES (%s,%s,%s)", "'" + name + "'", "'" + lastName + "'", "'" + age + "'");
        try (Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = String.format("delete from users where id = " + id);
        try (Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try {
            Connection con = Util.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users ORDER BY id;");
            if (rs.next()) {
                User tmpUser = new User();
                tmpUser.setId((long) rs.getInt("id"));
                tmpUser.setName(rs.getString("firstname"));
                tmpUser.setLastName(rs.getString("lastname"));
                tmpUser.setAge((byte) rs.getInt("age"));
                usersList.add(tmpUser);
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
