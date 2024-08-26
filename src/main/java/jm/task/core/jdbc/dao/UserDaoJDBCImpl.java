package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "lastName VARCHAR(100), " +
                    "age TINYINT" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastName, age)" +
                " values (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultRows = statement.executeQuery("SELECT * FROM users");
            while (resultRows.next()) {
                result.add(new User(
                        resultRows.getString("name"),
                        resultRows.getString("lastName"),
                        resultRows.getByte("age")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
