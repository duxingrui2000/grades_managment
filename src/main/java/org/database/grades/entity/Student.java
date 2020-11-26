package org.database.grades.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Data
public class Student implements UserDetails {
    @Id
    Long studentId;

    String password;

    @Column(length = 15)
    String studentName;

    Boolean gender;

//    @OneToMany
//    @JoinTable(name = "student_course")
//    Set<StudentCourse> studentCourses;

    @ManyToOne
    TrainingProgram trainingProgram;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "student";
            }
        };
        return Collections.singleton(authority);
    }

    @Override
    public String getUsername() {
        return studentId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
