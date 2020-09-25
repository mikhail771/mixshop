package com.internet.mixshop.dao.jdbc;

import com.internet.mixshop.dao.OrderDao;
import com.internet.mixshop.exception.DataProcessingException;
import com.internet.mixshop.lib.Dao;
import com.internet.mixshop.model.Order;
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
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(Long userId) {
        String query = "SELECT * FROM orders WHERE user_id = ? AND is_deleted = false";
        List<Order> userOrders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userOrders.add(buildOrder(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get orders by user: "
                    + userId, e);
        }
        for (Order order : userOrders) {
            order.setProducts(getProductsListByOrderId(order.getId()));
        }
        return userOrders;
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders(user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
            statement.close();
            addProductsToOrder(order.getProducts(), order.getId(), connection);
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Order with id " + order.getId()
                    + " is not added!", e);
        }
    }

    @Override
    public Optional<Order> getById(Long id) {
        String query = "SELECT * FROM orders WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = getOrder(resultSet, connection);
                return Optional.ofNullable(order);
            }
            statement.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Order update(Order order) {
        String query = "DELETE FROM orders_products WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            statement.executeUpdate();
            statement.close();
            addProductsToOrder(order.getProducts(), order.getId(), connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update order "
                    + order, e);
        }
        return order;
    }

    @Override
    public boolean delete(Long orderId) {
        String query = "UPDATE orders SET is_deleted = true WHERE order_id = ? "
                + "AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            return statement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with id " + orderId, e);
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can' get orders", e);
        }
        for (Order order : orders) {
            order.setProducts(getProductsListByOrderId(order.getId()));
        }
        return orders;
    }

    private Order getOrder(ResultSet resultSet, Connection connection) throws SQLException {
        Long orderId = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        List<Product> products = getProductsListByOrderId(orderId);
        return new Order(orderId, products, userId);
    }

    private void addProductsToOrder(List<Product> products, Long orderId, Connection connection)
            throws SQLException {
        String query = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?)";
        try (connection) {
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : products) {
                statement.setLong(1, orderId);
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add products to order: " + orderId, e);
        }
    }

    private Order buildOrder(ResultSet resultSet) throws SQLException {
        Long cartId = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        Order newOrder = new Order(userId);
        newOrder.setId(cartId);
        return newOrder;
    }

    private List<Product> getProductsListByOrderId(long orderId) {
        List<Product> orderedProducts = new ArrayList<>();
        String query = "SELECT product_id, name, price FROM product "
                + "JOIN orders_products USING (product_id) "
                + "WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("product_id");
                String productName = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Product productFromOrder = new Product(productName, price);
                productFromOrder.setId(id);
                orderedProducts.add(productFromOrder);
            }
            return orderedProducts;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get products with ID:" + orderId, e);
        }
    }
}
