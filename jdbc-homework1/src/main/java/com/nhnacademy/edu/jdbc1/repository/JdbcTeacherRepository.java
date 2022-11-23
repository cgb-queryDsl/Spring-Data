package com.nhnacademy.edu.jdbc1.repository;

import com.nhnacademy.edu.jdbc1.service.teacher.Teacher;
import com.nhnacademy.edu.jdbc1.service.teacher.TeacherRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcTeacherRepository implements TeacherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTeacherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Teacher findById(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, created_at FROM JdbcTeachers WHERE id=?",
                (resultSet, rowNum) ->
                        new Teacher(resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getTimestamp("created_at")),
                id);
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, created_at FROM JdbcTeachers",
                (resultSet, rowNum) ->
                        new Teacher(resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getTimestamp("created_at")));
    }

    @Override
    public int insert(Teacher teacher) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO JdbcTeachers(name, created_at) VALUES (?,?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, teacher.getName());
                    ps.setTimestamp(2, new Timestamp(teacher.getCreatedAt().getTime()));

                    return ps;
                }, generatedKeyHolder);

        return generatedKeyHolder.getKey().intValue();
    }

    @Override
    public void update(long id, String teacherName) {
        jdbcTemplate.update(
                "UPDATE JdbcTeachers SET name=? WHERE id=?",
                teacherName,
                id
        );
    }
}
