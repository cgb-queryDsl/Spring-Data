package com.nhnacademy.edu.jdbc1.repository;

import com.nhnacademy.edu.jdbc1.service.login.User;
import com.nhnacademy.edu.jdbc1.service.login.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT id, username, password, created_at FROM JdbcUsers WHERE username=?",
                (resultSet, rowNum) ->
                        new User(resultSet.getLong("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getTimestamp("created_at")),
                username));
    }
}
