package com.nhnacademy.edu.jdbc1.service.course;

import com.nhnacademy.edu.jdbc1.service.subject.Subject;
import com.nhnacademy.edu.jdbc1.service.subject.SubjectRepository;
import com.nhnacademy.edu.jdbc1.service.teacher.Teacher;
import com.nhnacademy.edu.jdbc1.service.teacher.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultCourseService implements CourseService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<CourseResponse> findAllCourses() {
        List<CourseResponse> courseList = new ArrayList<>();

        List<Course> courses = courseRepository.findAll();

        for (Course course : courses) {
            Teacher teacher = teacherRepository.findById(course.getTeacherId());
            Subject subject = subjectRepository.findById(course.getSubjectId());
            CourseResponse response = new CourseResponse(course.getId(), teacher.getName(), subject.getName(), course.getCreatedAt());
            courseList.add(response);
            log.info(response.toString());
        }

        return courseList;
    }

    @Override
    public CourseResponse findCourseById(long courseId) {
        Course course = courseRepository.findById(courseId);
        Teacher teacher = teacherRepository.findById(course.getTeacherId());
        Subject subject = subjectRepository.findById(course.getSubjectId());

        return new CourseResponse(course.getId(), teacher.getName(), subject.getName(), course.getCreatedAt());
    }

    @Override
    @Transactional
    public void createCourse(String teacherName, String subjectName) {
        Teacher teacher = new Teacher(teacherName, new Timestamp(new Date().getTime()));
        int teacherId = teacherRepository.insert(teacher);

        Subject subject = new Subject(subjectName, new Timestamp(new Date().getTime()));
        int subjectId = subjectRepository.insert(subject);

        Course course = new Course(teacherId, subjectId, new Timestamp(new Date().getTime()));
        courseRepository.insert(course);
    }

    @Override
    @Transactional
    public void update(long courseId, String teacherName, String subjectName) {
        Course course = courseRepository.findById(courseId);
        long teacherId = course.getTeacherId();
        teacherRepository.update(teacherId, teacherName);

        long subjectId = course.getSubjectId();
        subjectRepository.update(subjectId, subjectName);
    }

    @Override
    @Transactional
    public void deleteCourse(long courseId) {
        courseRepository.deleteById(courseId);
    }
}
