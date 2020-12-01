package org.database.grades.service.impl;

import org.database.grades.entity.Admin;
import org.database.grades.entity.Student;
import org.database.grades.entity.Teacher;
import org.database.grades.repository.AdminRepository;
import org.database.grades.repository.StudentRepository;
import org.database.grades.repository.TeacherRepository;
import org.database.grades.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @description 根据角色在不同的数据库表中，根据用户名查出相应的UserDetails,传给下一步验证
 */
@Service
public class DetailsService implements UserDetailsService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String[] roleAndUsername = StringUtils.split(s, String.valueOf(Character.LINE_SEPARATOR));
        if (s == null || Objects.requireNonNull(roleAndUsername).length != 2)
            throw new UsernameNotFoundException("role and username must be provided!");

        String role = roleAndUsername[0];
        String username = roleAndUsername[1];
        switch (role) {
            case "student":
                Optional<Student> student = this.studentRepository.findByUsername(username);
                if (student.isEmpty())
                    throw new UsernameNotFoundException("该学生用户不存在！");
                return student.get();
            case "teacher":
                Optional<Teacher> teacher = this.teacherRepository.findByUsername(username);
                if (teacher.isEmpty())
                    throw new UsernameNotFoundException("该教师用户不存在！");
                return teacher.get();
            case "admin":
                Optional<Admin> admin = this.adminRepository.findById(username);
                if (admin.isEmpty())
                    throw new UsernameNotFoundException("该管理员用户不存在！");
                return admin.get();
            default:
                throw new UsernameNotFoundException("用户类型错误！");
        }
    }

    public void changePassword(String rawPassword) {
        String encodePassword = "{bcrypt}" + passwordEncoder.encode(rawPassword);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getClass().equals(Student.class)) {
            ((Student) userDetails).setPassword(encodePassword);
            studentRepository.save((Student) userDetails);
        } else if (userDetails.getClass().equals(Teacher.class)) {
            ((Teacher) userDetails).setPassword(encodePassword);
            teacherRepository.save((Teacher) userDetails);
        }
    }

    public boolean authenticate(String rawPassword) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String pureEncodePassword = userDetails.getPassword().replace("{bcrypt}", "");
        return passwordEncoder.matches(rawPassword, pureEncodePassword);
    }
}