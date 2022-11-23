package com.nhnacademy.edu.jdbc1.repository;

import com.nhnacademy.edu.jdbc1.service.subject.Subject;
import com.nhnacademy.edu.jdbc1.service.subject.SubjectRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcSubjectRepository implements SubjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcSubjectRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Subject findById(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, created_at FROM JdbcSubjects WHERE id=?",
                (resultSet, rowNum) ->
                        new Subject(resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getTimestamp("created_at")),
                id);
    }

    @Override
    public List<Subject> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, created_at FROM JdbcSubjects",
                (resultSet, rowNum) ->
                        new Subject(resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getTimestamp("created_at")));
    }

    @Override
    public int insert(Subject subject) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO JdbcSubjects(name, created_at) VALUES (?,?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, subject.getName());
                    ps.setTimestamp(2, new Timestamp(subject.getCreatedAt().getTime()));

                    return ps;
                }, generatedKeyHolder);

        return generatedKeyHolder.getKey().intValue();
    }

    @Override
    public void update(long id, String subjectName) {
        jdbcTemplate.update(
                "UPDATE JdbcSubjects SET name=? WHERE id=?",
                subjectName,
                id
        );
    }
}
