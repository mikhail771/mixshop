package com.internet.mixshop.dao.jdbc;

import com.internet.mixshop.dao.ProductDao;
import com.internet.mixshop.exception.DataProcessingException;
import com.internet.mixshop.lib.Dao;
import com.internet.mixshop.model.Product;
import com.internet.mixshop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        String query = "INSERT INTO product(name, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Product " + product.getName() + " is not added!", e);
        }
    }

    @Override
    public Optional<Product> getById(Long id) {
        String query = "SELECT * FROM product WHERE product_id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = getProduct(resultSet);
                return Optional.ofNullable(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get product with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE product SET name = ?, "
                + "price = ? WHERE product_id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Product " + product + " can't be updated", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE product SET is_deleted = true WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete product with id " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM product WHERE is_deleted = false;";
            List<Product> products = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = getProduct(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of products", e);
        }
    }

    private Product getProduct(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        return new Product(id, name, price);
    }
}
