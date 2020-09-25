package com.internet.mixshop.dao.jdbc;

import com.internet.mixshop.dao.ShoppingCartDao;
import com.internet.mixshop.exception.DataProcessingException;
import com.internet.mixshop.lib.Dao;
import com.internet.mixshop.model.Product;
import com.internet.mixshop.model.ShoppingCart;
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
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts "
                    + "WHERE user_id = ? AND is_deleted = false;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getCartFromResult(resultSet, connection));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find the shopping cart for user id "
                    + userId, e);
        }
    }

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO shopping_carts (user_id) VALUES (?);";
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, cart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                cart.setId(resultSet.getLong(1));
            }
            addProductsToCart(cart, connection);
            return cart;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create " + cart, e);
        }
    }

    @Override
    public Optional<ShoppingCart> getById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts "
                    + "WHERE user_id = ? AND is_deleted = false;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getCartFromResult(resultSet, connection));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find the shopping cart with id " + id, e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        String query = "DELETE FROM shopping_carts_products WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cart.getId());
            statement.executeUpdate();
            addProducts(cart.getProducts(), cart.getId());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update shopping cart " + cart.getId(), e);
        }
        return cart;
    }

    @Override
    public boolean delete(Long cartId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "UPDATE shopping_carts SET is_deleted = true "
                    + "WHERE cart_id = ? AND is_deleted = false;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete cart with id " + cartId, e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM shopping_carts WHERE is_deleted = false;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<ShoppingCart> carts = new ArrayList<>();
            while (resultSet.next()) {
                carts.add(getCartFromResult(resultSet, connection));
            }
            return carts;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping carts from DB.", e);
        }
    }

    private void addProductsToCart(ShoppingCart shoppingCart, Connection connection)
            throws SQLException {
        String query = "INSERT INTO shopping_carts_products (cart_id, product_id) "
                + "VALUES (?, ?);";
        for (Product product : shoppingCart.getProducts()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCart.getId());
            statement.setLong(2, product.getId());
            statement.executeUpdate();
        }
    }

    private List<Product> getProductsInCart(Long cartId, Connection connection)
            throws SQLException {
        String query = "SELECT * FROM product p JOIN shopping_carts_products sp "
                + "ON p.product_id = sp.product_id "
                + "WHERE cart_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cartId);
        ResultSet resultSet = statement.executeQuery();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            double price = resultSet.getBigDecimal("price").doubleValue();
            Product product = new Product(name, price);
            product.setId(resultSet.getLong("product_id"));
            products.add(product);
        }
        return products;
    }

    private ShoppingCart getCartFromResult(ResultSet resultSet, Connection connection)
            throws SQLException {
        Long cartId = resultSet.getLong("cart_id");
        Long userId = resultSet.getLong("user_id");
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        shoppingCart.setId(cartId);
        shoppingCart.setProducts(getProductsInCart(cartId, connection));
        return shoppingCart;
    }

    private void addProducts(List<Product> products, long cartId) {
        String query = "INSERT INTO shopping_carts_products (cart_id, product_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : products) {
                statement.setLong(1, cartId);
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't add products to cart: "
                    + cartId, e);
        }
    }
}
