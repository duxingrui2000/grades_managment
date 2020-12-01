package org.database.grades.Controller;

import net.bytebuddy.asm.Advice;
import org.database.grades.entity.Student;
import org.database.grades.entity.StudentCourse;
import org.database.grades.entity.TrainingProgram;
import org.database.grades.service.CourseService;
import org.database.grades.service.StudentCourseService;
import org.database.grades.service.StudentService;
import org.database.grades.service.impl.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Autowired
    StudentCourseService studentCourseService;

    @Autowired
    CourseService courseService;

    @Autowired
    DetailsService detailsService;

    private String getCurrentStudentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @RequestMapping("/main")
    public String hello() {
        return "/student/main";
    }

    @GetMapping("/allCourses")
    public String showAllSelectedCourses(Model msg) {
        try {
            String username = getCurrentStudentUsername();
            Student student = studentService.getStudentByUsername(getCurrentStudentUsername());
            List<StudentCourse> allSelectedStudentCourses = student.getStudentCourses();
            List<List<String>> response = new ArrayList<>();
            TrainingProgram trainingProgram = student.getTrainingProgram();
            for (var i : allSelectedStudentCourses) {
                List<String> course = new ArrayList<>();
                course.add(i.getCourse().getSubject().getSubjectName()); //courseName
                course.add(i.getCourse().getCourseId().toString()); //courseId
                course.add(i.getCourse().getClassNumber().toString()); //classNumber
                course.add(i.getCourse().getSubject().getCredit().toString());//credit
                course.add(studentService.isCompulsory(student, i.getCourse()) ? "必修" : "选修");
                course.add(String.valueOf(i.getFinalScore() + i.getFinalScore()));//score
                response.add(course);
            }
            msg.addAttribute("sc_info", response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/student/selected_courses_list";
    }

    @GetMapping("/changePassword")
    public String toChangePasswordPage(Model msg) {
        return "/student/change_password";
    }

    @GetMapping("/changePassword/error")
    public String toChangePasswordErrorPage(Model msg) {
        return "/student/change_password";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword, Model msg) {
        try {
            if (!detailsService.authenticate(oldPassword)) {
                msg.addAttribute("msg", "原密码错误");
                return "/student/change_password";
            }
            if (!newPassword.equals(confirmPassword)) {
                msg.addAttribute("msg", "新密码与确认密码不匹配");
                return "/student/change_password";
            }
            detailsService.changePassword(newPassword);
            return "redirect:/student/main";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/student/main";
    }


}
