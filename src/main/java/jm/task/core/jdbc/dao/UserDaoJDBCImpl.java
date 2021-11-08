package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String INSERT_USER = "INSERT INTO users (`name`, `last_name`, `age`) VALUES(?, ?, ?)";
//    private static long id = 1;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (
                Connection connection = Util.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String SQL = "CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id bigint NOT NULL AUTO_INCREMENT,\n" +
                    "    name VARCHAR(45) NOT NULL,\n" +
                    "    last_name VARCHAR(45) NOT NULL,\n" +
                    "    age tinyint NOT NULL,\n" +
                    "    primary key (id)\n" +
                    ");";
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (
                Connection connection = Util.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String SQL = "DROP TABLE IF EXISTS users;";
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (
                Connection connection = Util.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_USER);
        ) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (
                Connection connection = Util.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String SQL = "DELETE FROM users\n" +
                    "WHERE id = '" + id + "';";
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> people = new ArrayList<>();
        String query = "Select * from users";
        try (
                Connection connection = Util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_Name"));
                user.setAge(resultSet.getByte("age"));
                people.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }

    public void cleanUsersTable() {
        try (
                Connection connection = Util.getConnection();
                Statement statement = connection.createStatement();
        ) {
            String SQL = "TRUNCATE TABLE users";
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
