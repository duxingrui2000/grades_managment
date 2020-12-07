package org.database.grades.Controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.database.grades.entity.Course;
import org.database.grades.entity.Student;
import org.database.grades.entity.StudentCourse;
import org.database.grades.entity.Teacher;
import org.database.grades.repository.CourseRepository;
import org.database.grades.repository.TeacherRepository;
import org.database.grades.service.CourseService;
import org.database.grades.service.StudentCourseService;
import org.database.grades.service.TeacherService;
import org.database.grades.service.impl.CourseServiceImpl;
import org.database.grades.service.impl.DetailsService;
import org.dom4j.rule.Mode;
import org.hibernate.query.criteria.internal.expression.function.LengthFunction;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import java.util.*;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @Autowired
    DetailsService detailsService;
    @Autowired
    StudentCourseService studentCourseService;
    @RequestMapping("/main")
    public String hello() {
        return "/teacher/main";
    }
    
    
    
    private String getCurrentTeacherUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    
    public void commonToSaveCourse(List<Course> allTaughtedCourses,String SubjectId,String ClassNum,String require,String announce){
        try {
            for(var i:allTaughtedCourses){
                if(i.getSubject().getSubjectId() == Integer.parseInt(SubjectId)
                        && i.getClassNumber() == Integer.parseInt(ClassNum)){
                    if(!announce.equals("") ){
                        i.setAnnouncement(announce);
                    }
                    if(!require.equals("")){
                        i.setRequirement(require);
                    }
                    courseService.EditRequirement_Announcement(i);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @GetMapping("/allCourses")
    public String showTheCourseTaught(Model msg){
        try {
            String username = getCurrentTeacherUserName();
            Teacher teacher = teacherService.getTeacherByUsername(username);
            List<Course> allTaughtedCourses = courseService.findAllCoursesByTeacherId(teacher.getTeacherId());
            List<List<String>> response = new ArrayList<>();
            for(var i:allTaughtedCourses){
                List<String> course = new ArrayList<>();
                course.add(i.getSubject().getSubjectName()); //courseName
                course.add(i.getSubject().getSubjectId().toString()); //subjectId
                course.add(i.getClassNumber().toString()); //classNumber
                course.add(i.getSubject().getCredit().toString());//credit
                response.add(course);
            }
            msg.addAttribute("Teaching_courses", response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/teacher/teaching_courses_list";
    }
    
    @GetMapping("/allStudents")
    public String showTheCourseStudents(Model msg){
        try {
            String username = getCurrentTeacherUserName();
            Teacher teacher = teacherService.getTeacherByUsername(username);
            List<Course> allTaughtedCourses = courseService.findAllCoursesByTeacherId(teacher.getTeacherId());
            List<List<String>> responseFirst = new ArrayList<>();//课程信息返回
            List<List<String>> responseSecond = new ArrayList<>();//对应的学生信息返回
            
            
            for(var i:allTaughtedCourses){
                //添加课程信息
                List<String> course = new ArrayList<>();
                course.add(i.getSubject().getSubjectName()); //courseName
                course.add(i.getSubject().getSubjectId().toString()); //subjectId
                course.add(i.getClassNumber().toString()); //classNumber
//                responseFirst.add(course);
                
                int count = 0;
                //添加改课程学生信息
                Set<StudentCourse> sc = i.getStudentCourses();
                for (StudentCourse studentCourse : sc) {
                    List<String> student = new ArrayList<>();
//                    student.add(i.getSubject().getSubjectName());
                    student.add(i.getSubject().getSubjectId().toString());
//                    student.add(i.getClassNumber().toString());
                    
                    student.add(studentCourse.getStudent().getStudentName());
                    student.add(studentCourse.getStudent().getStudentId().toString());
                    student.add(studentCourse.getStudent().getTrainingProgram().getName());
                    student.add(studentCourse.getAttendance().toString());
                    if(!responseSecond.contains(student)){
                        count++;
                        responseSecond.add(student);
                    }
                }
                //添加人数
                course.add(String.valueOf(count));
                responseFirst.add(course);
            }
            msg.addAttribute("Teaching_courses",responseFirst);
            msg.addAttribute("Teaching_courses_students",responseSecond);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/teacher/teaching_courses_students_list";
    }
    
    @GetMapping("/allAttendance")
    public String addTheCourseAttendance(Model msg){
        try {
            String username = getCurrentTeacherUserName();
            Teacher teacher = teacherService.getTeacherByUsername(username);
            List<Course> allTaughtedCourses = courseService.findAllCoursesByTeacherId(teacher.getTeacherId());
            List<List<String>> responseFirst = new ArrayList<>();//课程信息返回
            List<List<String>> responseSecond = new ArrayList<>();//对应的学生信息返回
        
        
            for(var i:allTaughtedCourses){
                //添加课程信息
                List<String> course = new ArrayList<>();
                course.add(i.getSubject().getSubjectName()); //courseName
                course.add(i.getSubject().getSubjectId().toString()); //subjectId
                course.add(i.getClassNumber().toString()); //classNumber
//                responseFirst.add(course);
            
                int count = 0;
                //添加改课程学生信息
                Set<StudentCourse> sc = i.getStudentCourses();
                for (StudentCourse studentCourse : sc) {
                    List<String> student = new ArrayList<>();
//                    student.add(i.getSubject().getSubjectName());
                    student.add(i.getSubject().getSubjectId().toString());
//                    student.add(i.getClassNumber().toString());
                
                    student.add(studentCourse.getStudent().getStudentName());
                    student.add(studentCourse.getStudent().getStudentId().toString());
                    student.add(studentCourse.getStudent().getTrainingProgram().getName());
                    student.add(studentCourse.getAttendance().toString());
                    if(!responseSecond.contains(student)){
                        count++;
                        responseSecond.add(student);
                    }
                }
                //添加人数
                course.add(String.valueOf(count));
                responseFirst.add(course);
            }
            msg.addAttribute("Teaching_courses",responseFirst);
            msg.addAttribute("Teaching_courses_students",responseSecond);
            return "/teacher/courseAttendance";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/teacher/courseAttendance";
    }
    
    @PostMapping("/allAttendance")
    public String ToRecordCourseAnnouncement(@RequestParam("SubjectIdAttendance") String SubjectIdAttendance,
                                            @RequestParam("ClassNumAttendance") String ClassNumAttendance,
                                            @RequestParam("newAttendance") String newAttendance){
        try {
            String username = getCurrentTeacherUserName();
            Teacher teacher = teacherService.getTeacherByUsername(username);
            List<Course> allTaughtedCourses = courseService.findAllCoursesByTeacherId(teacher.getTeacherId());
            
            String cut = "\\s+";
            String[] skipStuId = newAttendance.trim().split(cut);
            Long[] skipClassStuId = new Long[skipStuId.length];
            for(int i = 0; i<skipStuId.length; i++){
                skipClassStuId[i] = Long.valueOf(skipStuId[i]);
            }
            List<Long> skipStuList = Arrays.asList(skipClassStuId);
            for(var i:allTaughtedCourses){
                if(i.getSubject().getSubjectId() == Integer.parseInt(SubjectIdAttendance)
                        && i.getClassNumber() == Integer.parseInt(ClassNumAttendance)){
                    Set<StudentCourse> sc = i.getStudentCourses();
                    for (StudentCourse studentCourse : sc) {
                        if(skipStuList.contains((studentCourse.getStudent().getStudentId()))){
                            studentCourse.setAttendance((short) (studentCourse.getAttendance()+1));
                            studentCourseService.EditStudentCourseInfo(studentCourse);
                        }
                    }
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/teacher/allAttendance";
    }
    
    @GetMapping("/studentScore")
    public String ToRecordStudentScore(Model msg){
        try {
            String username = getCurrentTeacherUserName();
            Teacher teacher = teacherService.getTeacherByUsername(username);
            List<Course> allTaughtedCourses = courseService.findAllCoursesByTeacherId(teacher.getTeacherId());
            List<List<String>> responseFirst = new ArrayList<>();//课程信息返回
            List<List<String>> responseSecond = new ArrayList<>();//对应的学生信息返回
            
            for(var i:allTaughtedCourses){
                //添加课程信息
                List<String> course = new ArrayList<>();
                course.add(i.getSubject().getSubjectName()); //courseName
                course.add(i.getSubject().getSubjectId().toString()); //subjectId
                course.add(i.getClassNumber().toString()); //classNumber
//                responseFirst.add(course);
            
                int count = 0;
                //添加改课程学生信息
                Set<StudentCourse> sc = i.getStudentCourses();
                for (StudentCourse studentCourse : sc) {
                    List<String> student = new ArrayList<>();
                    student.add(i.getSubject().getSubjectId().toString());
                    student.add(studentCourse.getStudent().getStudentName());
                    student.add(studentCourse.getStudent().getStudentId().toString());
                    student.add(studentCourse.getStudent().getTrainingProgram().getName());
                    student.add(studentCourse.getAttendance().toString());
                    student.add(studentCourse.getUsualScore().toString());
                    student.add(studentCourse.getFinalScore().toString());
                    if(!responseSecond.contains(student)){
                        count++;
                        responseSecond.add(student);
                    }
                }
                //添加人数
                course.add(String.valueOf(count));
                responseFirst.add(course);
            }
            msg.addAttribute("Teaching_courses",responseFirst);
            msg.addAttribute("Teaching_courses_students",responseSecond);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/teacher/courseStudentScore";
    }
    
    @PostMapping("/studentScore")
    public String RecordStudentScore(@RequestParam("SubjectIdScore") String SubjectIdScore,
                                     @RequestParam("ClassNumScore") String ClassNumScore,
                                     @RequestParam("Proportion") String Proportion,
                                     @RequestParam("usualFinalScore") String usualFinalScore){
        try {
            String username = getCurrentTeacherUserName();
            Teacher teacher = teacherService.getTeacherByUsername(username);
            List<Course> allTaughtedCourses = courseService.findAllCoursesByTeacherId(teacher.getTeacherId());
            String cut = "\\s+";
            String[] scoreProportion = Proportion.trim().split(cut);
            String[] id_usual_final = usualFinalScore.trim().split(cut);
            Short[] scoreProp = new Short[3];
            List<List<Long>> StuId = new ArrayList<>();
            for(int i=0; i<3; i++){
                scoreProp[i] = Short.parseShort(scoreProportion[i]);
            }
            for(int i=0; i<id_usual_final.length; i++){
                if(i%3 == 0){
                    List<Long> l = new ArrayList<>();
                    l.add(Long.parseLong(id_usual_final[i]));
                    l.add(Long.parseLong(id_usual_final[i+1]));
                    l.add(Long.parseLong(id_usual_final[i+2]));
                    StuId.add(l);
                }
            }
            for(var i:allTaughtedCourses){
                //找到这门课
                if(i.getSubject().getSubjectId() == Integer.parseInt(SubjectIdScore)
                        && i.getClassNumber() == Integer.parseInt(ClassNumScore)){
                    short maxAttendance = 0;
                    //找到选了这门课的学生
                    Set<StudentCourse> sc = i.getStudentCourses();
                    for (StudentCourse studentCourse : sc) {
                        maxAttendance = (studentCourse.getAttendance()>maxAttendance)?studentCourse.getAttendance():maxAttendance;
                    }
                    for (StudentCourse studentCourse : sc) {
                        //避免ID号错误
                        for (List<Long> longs : StuId) {
                            Long id = studentCourse.getStudent().getStudentId();
                            if (longs.get(0).equals(id))//不要用==,longcatch中的数字没问题 超过后无法判断
                            {
                                short attendanceScore = (short) Math.round((double) (studentCourse.getAttendance() * scoreProp[0] / maxAttendance));
                                short usualScore = longs.get(1).shortValue();
                                short finalExamScore = longs.get(2).shortValue();
                                short finalScore = (short) Math.round((attendanceScore + (double)usualScore * scoreProp[1]/100 + (double)finalExamScore * scoreProp[2]/100));
                                studentCourse.setUsualScore(usualScore);
                                studentCourse.setFinalScore(finalScore);
                                studentCourseService.EditStudentCourseInfo(studentCourse);
                            }
                        }
                    }
                    break;
                }
            }
            return "redirect:/teacher/studentScore";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/teacher/studentScore";
    }
    
    
    @GetMapping("/allRequires")
    public String TheCourseRequires(Model msg){
        try {
            String username = getCurrentTeacherUserName();
            Teacher teacher = teacherService.getTeacherByUsername(username);
            List<Course> allTaughtedCourses = courseService.findAllCoursesByTeacherId(teacher.getTeacherId());
            List<List<String>> response = new ArrayList<>();
            for(var i:allTaughtedCourses){
                List<String> course = new ArrayList<>();
                course.add(i.getSubject().getSubjectName()); //courseName
                course.add(i.getSubject().getSubjectId().toString()); //subjectId
                course.add(i.getClassNumber().toString()); //classNumber
                course.add(i.getRequirement());//requirement
                response.add(course);
            }
            msg.addAttribute("Teaching_courses", response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/teacher/add_courses_requirement";
    }
    
    @PostMapping("/allRequires")
    public String addTheCourseRequires(@RequestParam("SubjectIdRequirement") String SubjectIdRequirement,
                                       @RequestParam("ClassNumRequirement") String ClassNumRequirement,
                                       @RequestParam("newRequirement") String newRequirement){
        try {
            String username = getCurrentTeacherUserName();
            Teacher teacher = teacherService.getTeacherByUsername(username);
            List<Course> allTaughtedCourses = courseService.findAllCoursesByTeacherId(teacher.getTeacherId());
            commonToSaveCourse(allTaughtedCourses,SubjectIdRequirement,ClassNumRequirement,newRequirement,"");
            return "redirect:/teacher/allRequires";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/teacher/allRequires";
    }
    @GetMapping("/allAnnouncements")
    public String EditAnnouncement(Model msg){
        try {
            String username = getCurrentTeacherUserName();
            Teacher teacher = teacherService.getTeacherByUsername(username);
            List<Course> allTaughtedCourses = courseService.findAllCoursesByTeacherId(teacher.getTeacherId());
            List<List<String>> response = new ArrayList<>();
            for(var i:allTaughtedCourses){
                List<String> course = new ArrayList<>();
                course.add(i.getSubject().getSubjectName()); //courseName
                course.add(i.getSubject().getSubjectId().toString()); //subjectId
                course.add(i.getClassNumber().toString()); //classNumber
                course.add(i.getAnnouncement());//announcement
                response.add(course);
            }
            msg.addAttribute("Teaching_courses_announcement", response);
            return "/teacher/editAnnouncement";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/teacher/editAnnouncement";
    }
    @PostMapping("/allAnnouncements")
    public String EditTheCourseAnnouncement(@RequestParam("SubjectIdAnnouncement") String SubjectIdAnnouncement,
                                            @RequestParam("ClassNumAnnouncement") String ClassNumAnnouncement,
                                            @RequestParam("newAnnouncement") String newAnnouncement){
        try {
            String username = getCurrentTeacherUserName();
            Teacher teacher = teacherService.getTeacherByUsername(username);
            List<Course> allTaughtedCourses = courseService.findAllCoursesByTeacherId(teacher.getTeacherId());
            commonToSaveCourse(allTaughtedCourses,SubjectIdAnnouncement,ClassNumAnnouncement,"",newAnnouncement);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/teacher/allAnnouncements";
    }
    
    
    
    @GetMapping("/changePassword")
    public String toChangePasswordPage(Model msg) {
        return "/teacher/change_password";
    }
    
    @GetMapping("/changePassword/error")
    public String toChangePasswordErrorPage(Model msg) {
        return "/teacher/change_password";
    }
    
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword, Model msg) {
        try {
            if (!detailsService.authenticate(oldPassword)) {
                msg.addAttribute("msg", "原密码错误");
                return "/teacher/change_password";
            }
            if (!newPassword.equals(confirmPassword)) {
                msg.addAttribute("msg", "新密码与确认密码不匹配");
                return "/teacher/change_password";
            }
            detailsService.changePassword(newPassword);
            return "redirect:/teacher/main";
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/teacher/main";
    }
}
