package org.database.grades.Controller;

import org.database.grades.entity.Course;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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

    //学生可以选择的课程
    List<Course> stuCanSelect = new ArrayList<>();

    private String getCurrentStudentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @RequestMapping("/main")
    public String hello(Model msg) {
        try {
            String username = getCurrentStudentUsername();
            Student student = studentService.getStudentByUsername(getCurrentStudentUsername());
            List<StudentCourse> studentCourses = studentCourseService.getAllStudentCourseByStudent(student);
            //已选课程门数

            //总学分
            int totalCredit = 0;
            double totalWeightedScore = 0;
            for (var i : studentCourses) {
                if (i.getFinalScore() != null && i.getFinalScore() != 0) {
                    int credit = i.getCourse().getSubject().getCredit();
                    totalCredit += credit;
                    double weightedScore = credit * i.getFinalScore();
                    totalWeightedScore += weightedScore;
                }
            }
            msg.addAttribute("coursesNumber",studentCourses.size());
            msg.addAttribute("totalCredit", totalCredit);
            msg.addAttribute("averageScore", totalWeightedScore / totalCredit);
            msg.addAttribute("trainProgram", student.getTrainingProgram().getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                course.add(i.getCourse().getSubject().getSubjectId().toString()); //subjectId
                course.add(i.getCourse().getClassNumber().toString()); //classNumber
                course.add(i.getCourse().getSubject().getCredit().toString());//credit
                course.add(i.getCourse().getTeacher().getTeacherName());//teacher
                course.add(studentService.isCompulsory(student, i.getCourse()) ? "必修" : "选修");
                course.add(i.getAttendance().toString());
                course.add(i.getUsualScore().toString());
                course.add(String.valueOf(i.getFinalScore()));//score
                response.add(course);
            }
            msg.addAttribute("sc_info", response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/student/show_courses_list";
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

    @GetMapping("/showPassedCourses")
    public String showPassedCourses(Model msg) {
        try {
            String username = getCurrentStudentUsername();
            Student student = studentService.getStudentByUsername(getCurrentStudentUsername());
            List<StudentCourse> allSelectedStudentCourses = student.getStudentCourses();
            List<List<String>> response1 = new ArrayList<>();
            List<List<String>> response2 = new ArrayList<>();
            TrainingProgram trainingProgram = student.getTrainingProgram();
            int credit_passed = 0;
            int credit_failed = 0;
            for (var i : allSelectedStudentCourses) {
                List<String> course = new ArrayList<>();
                course.add(i.getCourse().getSubject().getSubjectName()); //courseName
                course.add(i.getCourse().getSubject().getSubjectId().toString()); //subjectId
                course.add(i.getCourse().getClassNumber().toString()); //classNumber
                course.add(i.getCourse().getSubject().getCredit().toString());//credit
                course.add(i.getCourse().getTeacher().getTeacherName());//teacher
                course.add(studentService.isCompulsory(student, i.getCourse()) ? "必修" : "选修");
                course.add(i.getAttendance().toString());
                course.add(i.getUsualScore().toString());
                course.add(String.valueOf(i.getFinalScore()));//score
                if (i.getFinalScore() > 60) {
                    response1.add(course);
                    credit_passed += i.getCourse().getSubject().getCredit();
                } else {
                    response2.add(course);
                    credit_failed += i.getCourse().getSubject().getCredit();
                }
            }
            msg.addAttribute("sc_passed_info", response1);
            msg.addAttribute("sc_failed_info", response2);
            msg.addAttribute("credit_passed", credit_passed);
            msg.addAttribute("credit_failed", credit_failed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/student/show_passed_courses";
    }

    @GetMapping("/showAllOptionalCourses")
    public String showAllOptionalCourses(Model msg) {
        try {
            String username = getCurrentStudentUsername();
            Student student = studentService.getStudentByUsername(getCurrentStudentUsername());
            List<StudentCourse> allSelectedStudentCourses = student.getStudentCourses();
            Long[] selectedCourseId = new Long[allSelectedStudentCourses.size()];
            for (int i = 0; i < allSelectedStudentCourses.size(); i++) {
                StudentCourse sc = allSelectedStudentCourses.get(i);
                selectedCourseId[i] = sc.getCourse().getCourseId();
            }
            List<Course> courses = courseService.findAllCourses();
            List<List<String>> response = new ArrayList<>();
            for (Course course : courses) {
                boolean selectOrnot = false;
                for (Long l : selectedCourseId) {
                    if (l.equals(course.getCourseId())) {
                        selectOrnot = true;
                        break;
                    }
                }
                //如果该生未选择该课程，则可以选择
                if (!selectOrnot) {
                    List<String> c = new ArrayList<>();
                    c.add(course.getSubject().getSubjectName()); //courseName
                    c.add(course.getSubject().getSubjectId().toString()); //subjectId
                    c.add(course.getClassNumber().toString()); //classNumber
                    c.add(course.getSubject().getCredit().toString());//credit
                    c.add(course.getTeacher().getTeacherName());//teacher
                    c.add(studentService.isCompulsory(student, course) ? "必修" : "选修");
                    response.add(c);
                    stuCanSelect.add(course);
                }
            }
            msg.addAttribute("allCourses", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/student/show_Optional_Courses";
    }

    @PostMapping("/showAllOptionalCourses")
    public String selectAllOptionalCourses(@RequestParam("subjectId") String subjectId,
                                           @RequestParam("classNum") String classNum) {
        try {
            String username = getCurrentStudentUsername();
            Student student = studentService.getStudentByUsername(getCurrentStudentUsername());
            for (var i : stuCanSelect) {
                if (i.getSubject().getSubjectId() == Integer.parseInt(subjectId)
                        && i.getClassNumber() == Integer.parseInt(classNum)) {
                    Long courseId = i.getCourseId();
                    List<StudentCourse> sc = student.getStudentCourses();
                    StudentCourse studentCourse = new StudentCourse();
                    studentCourse.setStudent(student);
                    studentCourse.setCourse(courseService.findCourseByCourseId(courseId));
                    studentCourse.setAttendance((short) 0);
                    studentCourse.setUsualScore((short) 0);
                    studentCourse.setFinalScore((short) 0);
                    studentCourseService.EditStudentCourseInfo(studentCourse);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/student/showAllOptionalCourses";
    }
}
