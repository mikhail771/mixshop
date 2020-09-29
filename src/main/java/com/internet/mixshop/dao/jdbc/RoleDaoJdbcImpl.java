package com.internet.mixshop.dao.jdbc;

import com.internet.mixshop.dao.RoleDao;
import com.internet.mixshop.exception.DataProcessingException;
import com.internet.mixshop.lib.Dao;
import com.internet.mixshop.model.Role;
import com.internet.mixshop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class RoleDaoJdbcImpl implements RoleDao {
    @Override
    public Role create(Role role) {
        String query = "INSERT INTO roles(role_name) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, role.getRoleName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                role.setId(resultSet.getLong(1));
            }
            return role;
        } catch (SQLException e) {
            throw new DataProcessingException("Role " + role.getRoleName() + " is not added!", e);
        }
    }

    @Override
    public Optional<Role> getById(Long roleId) {
        String query = "SELECT * FROM roles WHERE role_id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, roleId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = getRole(resultSet);
                return Optional.ofNullable(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get role with id = " + roleId, e);
        }
        return Optional.empty();
    }

    @Override
    public Role update(Role role) {
        String query = "UPDATE roles SET role_name = ?, "
                + "WHERE role_id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, role.getRoleName());
            statement.setLong(2, role.getId());
            statement.executeUpdate();
            return role;
        } catch (SQLException e) {
            throw new DataProcessingException("Role " + role.getRoleName()
                    + " can't be updated", e);
        }
    }

    @Override
    public boolean delete(Long roleId) {
        String query = "UPDATE roles SET is_deleted = true WHERE role_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, roleId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete role with id " + roleId, e);
        }
    }

    @Override
    public List<Role> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM roles WHERE is_deleted = false;";
            List<Role> roles = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = getRole(resultSet);
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of roles", e);
        }
    }

    @Override
    public Set<Role> getRolesByUserId(Long userId) {
        String query = "SELECT r.role_id, r.role_name FROM roles r "
                + "JOIN users_roles ur ON ur.role_id = r.role_id "
                + "where ur.user_id = ?;";;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> roles = new HashSet<>();
            while (resultSet.next()) {
                Role role = getRole(resultSet);
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get roles for user with id = " + userId, e);
        }
    }

    private Role getRole(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("role_id");
        String name = resultSet.getString("role_name");
        return new Role(id, name);
    }
}
