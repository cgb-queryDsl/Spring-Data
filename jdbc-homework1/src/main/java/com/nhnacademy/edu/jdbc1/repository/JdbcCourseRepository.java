package com.nhnacademy.edu.jdbc1.repository;

import com.nhnacademy.edu.jdbc1.service.course.Course;
import com.nhnacademy.edu.jdbc1.service.course.CourseRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class JdbcCourseRepository implements CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCourseRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Course findById(long id) {
        String sql = "SELECT c.id as c_id, c.teacher_id , c.subject_id, c.created_at as c_created_at,\n" +
                "       s.id as s_id, s.name as s_name, s.created_at as s_created_at, \n" +
                "       t.id as t_id, t.name as t_name, t.created_at as t_created_at  \n" +
                "FROM JdbcCourses as c    \n" +
                "    INNER JOIN JdbcSubjects as s ON c.subject_id = s.id \n" +
                "    INNER JOIN JdbcTeachers as t ON c.teacher_id = t.id\n" +
                "WHERE c.id = ?;";

        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) ->
                        new Course(resultSet.getLong("c_id"),
                                resultSet.getLong("t_id"),
                                resultSet.getLong("s_id"),
                                resultSet.getTimestamp("c_created_at")),
                id);
    }

    @Override
    public List<Course> findAll() {
        String sql = "SELECT c.id as c_id, c.teacher_id , c.subject_id, c.created_at as c_created_at,\n" +
                "       s.id as s_id, s.name as s_name, s.created_at as s_created_at, \n" +
                "       t.id as t_id, t.name as t_name, t.created_at as t_created_at  \n" +
                "FROM JdbcCourses as c    \n" +
                "    INNER JOIN JdbcSubjects as s ON c.subject_id = s.id \n" +
                "    INNER JOIN JdbcTeachers as t ON c.teacher_id = t.id;";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) ->
                        new Course(resultSet.getLong("c_id"),
                                resultSet.getLong("t_id"),
                                resultSet.getLong("s_id"),
                                resultSet.getTimestamp("c_created_at")));
    }

    @Override
    public int insert(Course course) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO JdbcCourses(teacher_id, subject_id, created_at) VALUES (?,?,?)";
        return jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setLong(1, course.getTeacherId());
                    ps.setLong(2, course.getSubjectId());
                    ps.setTimestamp(3, new Timestamp(course.getCreatedAt().getTime()));

                    return ps;
                }, generatedKeyHolder);
    }

    @Override
    public int deleteById(long id) {
        return jdbcTemplate.update(
                "DELETE FROM JdbcCourses WHERE id=?",
                id
        );
    }
}
