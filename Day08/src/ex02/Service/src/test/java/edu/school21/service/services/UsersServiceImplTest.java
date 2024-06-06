package edu.school21.service.services;


import edu.school21.service.config.ApplicationConfig;
import edu.school21.service.config.TestApplicationConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UsersServiceImplTest {
    private static DataSource dataSource;
    private static UsersService usersServiceJdbc;
    private static UsersService usersServiceJdbcTemplate;

    @BeforeAll
    static void befor() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        dataSource = applicationContext.getBean("dataSource", DataSource.class);
        usersServiceJdbc = applicationContext.getBean("usersServiceJdbc", UsersService.class);
        usersServiceJdbcTemplate = applicationContext.getBean("usersServiceJdbcTemplate", UsersService.class);
    }

    @BeforeEach
    private void init() {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS users;");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                    "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                    "email VARCHAR(30) NOT NULL," +
                    "password VARCHAR(36) NOT NULL" +
                    ");");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Bob@mail.com", "Jonny@mail.com", "Anne@mail.com"})
    public void isSignUp(String email) {
        assertNotNull(usersServiceJdbc.signUp(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Bob@mail.com", "Jonny@mail.com", "Anne@mail.com"})
    public void isSignUpTemplate(String email) {
        assertNotNull(usersServiceJdbcTemplate.signUp(email));
    }

}
