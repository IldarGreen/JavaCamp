package edu.school21.repositories;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;
import java.sql.SQLException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;


public class EmbeddedDataSourceTest {
    private DataSource dataSource;

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setName("Tests")
                .setType(HSQL)
                .addScript("schema.sql")
                .addScripts("data.sql")
                .build();
    }

    @Test
    void getConnectionTest() throws SQLException {
        assertNotNull(dataSource.getConnection());
    }

    @AfterEach
    void close() throws SQLException {
        dataSource.getConnection().close();
    }
}
