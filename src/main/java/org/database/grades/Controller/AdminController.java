package org.database.grades.Controller;

import org.database.grades.entity.*;
import org.database.grades.service.*;
import org.database.grades.service.impl.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    TeacherService teacherService;
    @Autowired
    DetailsService detailsService;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    TrainingProgramService trainingProgramService;
    @Autowired
    SubjectService subjectService;
    
    @RequestMapping("/main")
    public String hello() {
        return "/admin/main";
    }
    
    @GetMapping("/setTeachers")
    public String toSetTeachers(Model msg){
        try {
            List<Teacher> teachers = teacherService.getAllTeachers();
            List<List<String>> teacher_list = new ArrayList<>();
            for (Teacher teacher:teachers){
                List<String> list = new ArrayList<>();
                list.add(teacher.getTeacherId().toString());
                list.add(teacher.getTeacherName());
                list.add(teacher.getUsername());
                list.add(teacher.getPassword());
                teacher_list.add(list);
            }
            msg.addAttribute("teachers",teacher_list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/setTeachers";
    }
    @PostMapping("/setTeachers")
    public String toAddTeachers(@RequestParam("teacherName")String teacherName,
                                @RequestParam("userName")String userName,
                                @RequestParam("userPsw")String userPsw,Model msg){
        try {
            List<Teacher> teachers = teacherService.getAllTeachers();
            for(Teacher teacher:teachers){
                if(teacher.getUsername().equals(userName)){
                    msg.addAttribute("msg","username重复了");
                    return "redirect:/admin/setTeachers";
                }
            }
            Teacher new_teacher = new Teacher();
            new_teacher.setTeacherName(teacherName);
            new_teacher.setUsername(userName);
            new_teacher.setPassword(userPsw);
            teacherService.EditTeacherInfo(new_teacher);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/setTeachers";
    }
    
    @GetMapping("/setStudents")
    public String toSetStudents(Model msg){
        try {
            List<Student> students = studentService.getAllStudents();
            List<List<String>> student_list = new ArrayList<>();
            for (Student student:students){
                List<String> list = new ArrayList<>();
                list.add(student.getStudentId().toString());
                list.add(student.getStudentName());
                list.add(student.getGender().toString());
                list.add(student.getUsername());
                list.add(student.getPassword());
                list.add(student.getTrainingProgram().getName());
                student_list.add(list);
            }
            msg.addAttribute("students",student_list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/setStudents";
    }
    @PostMapping("/setStudents")
    public String toAddStudents(@RequestParam("studentName")String studentName,
                                @RequestParam("studentGender")String studentGender,
                                @RequestParam("userName")String userName,
                                @RequestParam("userPsw")String userPsw,
                                @RequestParam("collegeName")String collegeName,
                                Model msg){
        try {
            List<Student> students = studentService.getAllStudents();
            for(Student student:students){
                if(student.getUsername().equals(userName)){
                    msg.addAttribute("msg","username重复了");
                    return "redirect:/admin/setStudents";
                }
            }
            Student new_student = new Student();
            boolean gen = true;
            gen = studentGender.equals("男");
            
            new_student.setStudentName(studentName);
            new_student.setGender(gen);
            new_student.setUsername(userName);
            new_student.setPassword(userPsw);
            boolean setTpSuccess = false;
            List<TrainingProgram> trainingPrograms_list = trainingProgramService.GetAllTrainingProgram();
            for(TrainingProgram tp:trainingPrograms_list){
                if(tp.getName().equals(collegeName)){
                    new_student.setTrainingProgram(tp);
                    setTpSuccess = true;
                }
            }
            if(!setTpSuccess){
                msg.addAttribute("msg","学院不存在重复了");
                return "redirect:/admin/setStudents";
            }else {
                studentService.EditStudentInfo(new_student);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/setStudents";
    }
    @GetMapping("setTrainingProgram")
    public String toSetTrainingProgram(Model msg) {
        try{
            List<TrainingProgram> trainingPrograms_list = trainingProgramService.GetAllTrainingProgram();
            List<List<String>> tp_list = new ArrayList<>();
            for(TrainingProgram trainingProgram:trainingPrograms_list){
                List<String> list = new ArrayList<>();
                list.add(trainingProgram.getId().toString());
                list.add(trainingProgram.getName());
                tp_list.add(list);
            }
            msg.addAttribute("tp_list",tp_list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/setTrainingProgram";
    }
    @PostMapping("setTrainingProgram")
    public String setTrainingProgram(@RequestParam("name")String name,Model msg){
        try{
            List<TrainingProgram> trainingPrograms_list = trainingProgramService.GetAllTrainingProgram();
            List<List<String>> tp_list = new ArrayList<>();
            for(TrainingProgram trainingProgram:trainingPrograms_list){
                if(trainingProgram.getName().equals(name)){
                    msg.addAttribute("msg","学院名重复");
                    return "redirect:/admin/setTrainingProgram";
                }
            }
            TrainingProgram new_tp = new TrainingProgram();
            new_tp.setName(name);
            trainingProgramService.EditTrainingProgramService(new_tp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/setTrainingProgram";
    }
    
    
    //给学院预置课程
    @GetMapping("setCollegeCourse")
    public String toSetCollegeCourse(Model msg){
        try{
            List<TrainingProgram> trainingPrograms_list = trainingProgramService.GetAllTrainingProgram();
            List<List<String>> responseFirst = new ArrayList<>();
            List<List<String>> responseSecond = new ArrayList<>();
            for(TrainingProgram trainingProgram:trainingPrograms_list){
                List<String> list = new ArrayList<>();
                list.add(trainingProgram.getId().toString());
                list.add(trainingProgram.getName());
                Set<Subject> subjects = trainingProgram.getSubjects();
                for(var i:subjects){
                    List<String> l = new ArrayList<>();
                    l.add(trainingProgram.getId().toString());//学院号
                    l.add(i.getSubjectId().toString());//该学院下的课程ID名字学分
                    l.add(i.getSubjectName());
                    l.add(i.getCredit().toString());
                    responseSecond.add(l);
                }
                responseFirst.add(list);
            }
            msg.addAttribute("collegeName",responseFirst);
            msg.addAttribute("collegeCourses",responseSecond);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/setCollegeCourse";
    }
    @PostMapping("setCollegeCourse")
    public String SetCollegeCourse(@RequestParam("collegeId")String collegeId,
                                   @RequestParam("subjectId")String subjectId,
                                   Model msg){
        try {
            TrainingProgram tp = trainingProgramService.GetTrainingProgramById(Long.parseLong(collegeId));
            List<Subject> nowAllSubjects = subjectService.findAllSubjects();
            Set<Subject> old_subjects = tp.getSubjects();
            for(Subject subject:nowAllSubjects){
                if(subject.getSubjectId().equals(Long.parseLong(subjectId))){
                    old_subjects.add(subject);
                }
            }
            trainingProgramService.EditTrainingProgramService(tp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/setCollegeCourse";
    }
    
    
    
    //给学校设置开设课程
    @GetMapping("setUniversityCourse")
    public String toSetUniversityCourse(Model msg) {
        try{
            List<Course> allCourses = courseService.findAllCourses();
            List<Subject> allSubjects = subjectService.findAllSubjects();
            List<List<String>> responseFirst = new ArrayList<>();
            List<List<String>> responseSecond = new ArrayList<>();
            //添加所有科目
            for(var i:allSubjects){
                List<String> subject = new ArrayList<>();
                subject.add(i.getSubjectId().toString());
                subject.add(i.getSubjectName());
                subject.add(i.getCredit().toString());
                responseFirst.add(subject);
            }
            //添加所有课程
            for(var i:allCourses){
                List<String> course = new ArrayList<>();
                course.add(i.getSubject().getSubjectName()); //courseName
                course.add(i.getSubject().getSubjectId().toString()); //subjectId
                course.add(i.getClassNumber().toString()); //classNumber
                course.add(i.getSubject().getCredit().toString());//credit
                course.add(i.getTeacher().getTeacherId().toString());
                course.add(i.getTeacher().getTeacherName());
                responseSecond.add(course);
            }
            msg.addAttribute("allSubjects",responseFirst);
            msg.addAttribute("allCourses",responseSecond);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/setUniversityCourse";
    }
    
    @PostMapping("setUniversityCourse")
    public String setUniversityCourse(@RequestParam("subjectName")String subjectName,
                                      @RequestParam("subjectCredit")String subjectCredit,
                                      @RequestParam("teacherCourseName")String teacherCourseName,
                                      @RequestParam("teacherCourseNum")String teacherCourseNum,
                                      @RequestParam("teacherId")String teacherId,Model msg){
        try{
            if(subjectName.length()!=0&&subjectCredit.length()!=0){
                List<Subject> allSubjects = subjectService.findAllSubjects();
                for(var i:allSubjects){
                    if(i.getSubjectName().equals(subjectName)){
                        msg.addAttribute("msg","课程名重复！");
                        return "redirect:/admin/setUniversityCourse";
                    }
                }
                Subject new_subject = new Subject();
                new_subject.setSubjectName(subjectName);
                new_subject.setCredit(Short.parseShort(subjectCredit));
                subjectService.EditSubjectInfo(new_subject);
            }
            if(teacherCourseName.length()!=0&&teacherCourseNum.length()!=0&&teacherId.length()!=0){
                Subject subject = subjectService.findSubjectByName(teacherCourseName);
                List<Course> nowHave = courseService.findAllBySubject(subject);//现在已经有的开设该课程的信息
                for(Course course:nowHave){
                    if(course.getClassNumber().equals(Integer.parseInt(teacherCourseNum))){
                        msg.addAttribute("msg","该课程的课序号重复！");
                        return "redirect:/admin/setUniversityCourse";
                    }
                }
                Teacher new_teacher = teacherService.getTeacherByID(Long.parseLong(teacherId));
                Course new_course = new Course();
                new_course.setSubject(subject);
                new_course.setClassNumber(Integer.parseInt(teacherCourseNum));
                new_course.setTeacher(new_teacher);
                courseService.EditRequirement_Announcement(new_course);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/setUniversityCourse";
    }
    
    
    
    
    @GetMapping("/changePassword")
    public String toChangePasswordPage(Model msg) {
        return "/admin/change_password";
    }
    
    @GetMapping("/changePassword/error")
    public String toChangePasswordErrorPage(Model msg) {
        return "/admin/change_password";
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
            return "redirect:/admin/main";
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/main";
    }
    
    
}
