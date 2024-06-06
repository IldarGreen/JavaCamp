package edu.school21.sockets.repositories;


import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Component("usersRepositoryJdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        new InitRepository(dataSource).initDatabase("users_init.sql");
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String SQLquery = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.query(SQLquery, new Object[]{username}, new UserMapper()).stream().findAny();
    }

    @Override
    public Optional<User> findById(Long id) {
        String SQLquery = "SELECT * FROM users WHERE id = " + id;
        return Optional.of((User) jdbcTemplate.query(SQLquery, new UserMapper()));
    }

    @Override
    public List<User> findAll() {
        String SQLquery = "SELECT * FROM users";
        return jdbcTemplate.query(SQLquery, new UserMapper());
    }

    @Override
    public void save(User entity) {
        String SQLquery = "INSERT INTO users (username, password) VALUES (?, ?) ";
        if (jdbcTemplate.update(SQLquery, entity.getUsername(), entity.getPassword()) == 0) {
            System.err.println("User '" + entity + "' not saved.");
        }
    }

    @Override
    public void update(User entity) {
        String SQLquery = "UPDATE users SET username = ?, password = ? WHERE id = ?";
        if (jdbcTemplate.update(SQLquery, entity.getUsername(), entity.getPassword(), entity.getId()) == 0) {
            System.err.println("User '" + entity + "' not update.");
        }
    }

    @Override
    public void delete(Long id) {
        String SQLquery = "DELETE FROM users WHERE id = ?";
        if (jdbcTemplate.update(SQLquery, id) == 0) {
            System.err.println("User with id: " + id + " not found.");
        }
    }

}
