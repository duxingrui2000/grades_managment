package org.database.grades.Controller;

import org.database.grades.entity.StudentCourse;
import org.database.grades.service.CourseService;
import org.database.grades.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    private Long getCurrentStudentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.valueOf(authentication.getName());
    }

    @RequestMapping("/main")
    public String hello() {
        return "/student/main";
    }

    @GetMapping("/allCourses")
    public String showAllSelectedCourses(Model msg) {
        try {
            Set<StudentCourse> allSelectedStudentCourses = this.studentService.getStudent(getCurrentStudentId()).getStudentCourses();
            List<List<String>> response = new ArrayList<>();
            for (var i : allSelectedStudentCourses) {
                List<String> course = new ArrayList<>();
                course.add(i.getCourse().getSubject().getSubjectName()); //courseName
                course.add(i.getCourse().getCourseId().toString()); //courseId
                course.add(i.getCourse().getClassNumber().toString()); //classNumber
                course.add(i.getCourse().getSubject().getCredit().toString());//credit
                course.add(String.valueOf(i.getFinalScore() + i.getFinalScore()));//score
                response.add(course);
            }
            msg.addAttribute("info", response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/student/selected_courses_list";
    }


}
