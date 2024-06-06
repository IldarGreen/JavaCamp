package edu.school21.service.repositories;


import edu.school21.service.models.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;


public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String SQLquery = "SELECT * FROM users WHERE email = '" + email + "'";
        return Optional.of(Objects.requireNonNull(jdbcTemplate.query(SQLquery, new UserMapper()).stream().findFirst().orElse(null)));
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
        String SQLquery = "INSERT INTO users (email) VALUES (?)";
        jdbcTemplate.update(SQLquery, entity.getEmail());
    }

    @Override
    public void update(User entity) {
        String SQLquery = "UPDATE users SET email = ? WHERE id = ?";
        jdbcTemplate.update(SQLquery, entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        String SQLquery = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(SQLquery, id);
    }

}
