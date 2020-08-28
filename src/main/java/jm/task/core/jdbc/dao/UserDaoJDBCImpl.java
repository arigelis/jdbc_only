package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    UserService userService;
    private static final String INSERT_SQL = "INSERT INTO users (firstname, lastname, age) values (?,?,?)";
    private static final String DELETE_SQL = "DELETE FROM users WHERE id = (?)";
    private static final String SELECT_SQL = "SELECT * FROM users ORDER BY id;";

    public UserDaoJDBCImpl(UserService userService) {
        this.userService = userService;
    }

    public void createUsersTable() {
        try (Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id INTEGER AUTO_INCREMENT PRIMARY KEY, \n" +
                    "    firstname VARCHAR(30), \n" +
                    "    lastname VARCHAR(30), \n" +
                    "    age INTEGER\n" +
                    ");");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection con = Util.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(DELETE_SQL);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try {
            Connection con = Util.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_SQL);
            ResultSet rs = preparedStatement.executeQuery(SELECT_SQL);
            if (rs.next()) {
                User tmpUser = new User();
                tmpUser.setId((long) rs.getInt("id"));
                tmpUser.setName(rs.getString("firstname"));
                tmpUser.setLastName(rs.getString("lastname"));
                tmpUser.setAge((byte) rs.getInt("age"));
                usersList.add(tmpUser);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Connection con = Util.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
