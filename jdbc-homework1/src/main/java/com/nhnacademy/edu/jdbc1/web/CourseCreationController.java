package com.nhnacademy.edu.jdbc1.web;

import com.nhnacademy.edu.jdbc1.service.course.CourseResponse;
import com.nhnacademy.edu.jdbc1.service.course.CourseService;
import com.nhnacademy.edu.jdbc1.service.course.CourseRegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CourseCreationController {
    private final CourseService courseService;

    public CourseCreationController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String root(ModelMap modelMap) {
        List<CourseResponse> courses = courseService.findAllCourses();
        modelMap.addAttribute("courses", courses);

        return "index";
    }

    @GetMapping("/create")
    public String createCourseForm() {
        return "courseForm";
    }

    @PostMapping("/create")
    public String createCourse(@ModelAttribute CourseRegisterRequest request) {
        courseService.createCourse(request.getTeacherName(), request.getSubjectName());

        return "redirect:/";
    }

    @GetMapping("/update/{courseId}")
    public String updateCourseForm(@PathVariable long courseId, ModelMap modelMap) {
        CourseResponse course = courseService.findCourseById(courseId);
        modelMap.addAttribute("course", course);

        return "updateForm";
    }

    @PutMapping("/courses/{courseId}")
    public String updateCourse(@ModelAttribute("courseUpdate") CourseRegisterRequest request,
                               @PathVariable long courseId) {
        courseService.update(courseId, request.getTeacherName(), request.getSubjectName());

        return "redirect:/";
    }

    @DeleteMapping("/courses/{courseId}")
    public String deleteCourse(@PathVariable long courseId) {
        courseService.deleteCourse(courseId);

        return "redirect:/";
    }
}
