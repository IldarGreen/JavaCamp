package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class ProductsRepositoryJdbcImplTest {
    private DataSource dataSource;
    ProductsRepositoryJdbcImpl productsRepository;

    final List<Product> EXPECTED_FIND_ALL_PRODUCT = Arrays.asList(
            new Product(0L, "Bread", 50L),
            new Product(1L, "Milk", 80L),
            new Product(2L, "Tea", 210L),
            new Product(3L, "Coffee", 1000L),
            new Product(4L, "Sugar", 100L),
            new Product(5L, "Salt", 90L)
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2L, "Tea", 210L);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(3L, "Coffee", 2000L);
    final Product EXPECTED_SAVE_PRODUCT = new Product(6L, "Sausage", 250L);

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setName("Tests")
                .setType(HSQL)
                .addScript("schema.sql")
                .addScripts("data.sql")
                .build();

        productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void testFindAll() {
        assertEquals(EXPECTED_FIND_ALL_PRODUCT, productsRepository.findAll());
    }

    @Test
    void testFindById() {
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productsRepository.findById(2L).get());
    }

    @Test
    void testUpdate() {
        productsRepository.update(EXPECTED_UPDATED_PRODUCT);
        assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepository.findById(3L).get());
    }

    @Test
    void testSave() {
        productsRepository.save(EXPECTED_SAVE_PRODUCT);
        assertEquals(EXPECTED_SAVE_PRODUCT, productsRepository.findById(6L).get());
    }

    @Test
    void testDelete() {
        productsRepository.delete(1L);
        assertFalse(productsRepository.findById(1L).isPresent());
    }

    @AfterEach
    void close() throws SQLException {
        dataSource.getConnection().close();
    }
}
