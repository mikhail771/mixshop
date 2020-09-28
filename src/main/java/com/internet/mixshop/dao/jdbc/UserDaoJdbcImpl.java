package com.internet.mixshop.dao.jdbc;

import com.internet.mixshop.dao.RoleDao;
import com.internet.mixshop.dao.UserDao;
import com.internet.mixshop.exception.DataProcessingException;
import com.internet.mixshop.lib.Dao;
import com.internet.mixshop.lib.Inject;
import com.internet.mixshop.model.Role;
import com.internet.mixshop.model.User;
import com.internet.mixshop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Inject
    private RoleDao roleDao;

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUser(resultSet);
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user " + login, e);
        }
        return Optional.empty();
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users(name, login, password) VALUES (?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            statement.close();
            setRoleForUser(connection, user.getId());
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("User " + user.getName() + " is not added!", e);
        }
    }

    @Override
    public Optional<User> getById(Long userId) {
        String query = "SELECT * FROM users WHERE user_id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUser(resultSet);
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user with id = " + userId, e);
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET name = ?, "
                + "login = ?, password = ? WHERE user_id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("User " + user.getName() + " can't be updated", e);
        }
    }

    @Override
    public boolean delete(Long userId) {
        String query = "UPDATE users SET is_deleted = true WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete user with id " + userId, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM users WHERE is_deleted = false;";
            List<User> users = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of users", e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        Set<Role> roles = roleDao.getRolesByUserId(id);
        return new User(id, name, login, password, roles);
    }

    private void setRoleForUser(Connection connection, Long id)
            throws SQLException {
        String query = "INSERT INTO users_roles SET user_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeUpdate();
    }
}
