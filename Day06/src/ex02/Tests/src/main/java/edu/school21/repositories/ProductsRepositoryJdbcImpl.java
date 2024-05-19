package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        String SQLQuery = "SELECT * FROM products";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productList.add(new Product(rs.getLong(1), rs.getString(2), rs.getLong(3)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String SQLQuery = "SELECT * FROM products WHERE id = ?";
        Product product;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            product = new Product(rs.getLong(1), rs.getString(2), rs.getLong(3));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(product);
    }

    @Override
    public void update(Product product) {
        String SQLQuery = "UPDATE products SET name = ?, price = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery)) {
            ps.setString(1, product.getName());
            ps.setLong(2, product.getPrice());
            ps.setLong(3, product.getId());

            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Product product) {
        String SQLQuery = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery)) {
            ps.setString(1, product.getName());
            ps.setLong(2, product.getPrice());

            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String SQLQuery = "DELETE FROM products WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQuery)) {
            ps.setLong(1, id);

            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
