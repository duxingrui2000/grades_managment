package org.database.grades.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Student implements UserDetails {
    @Id
    @GeneratedValue
    Long studentId;

    @Column(unique = true)
    String username;

    String password;

    @Column(length = 15)
    String studentName;

    @OneToMany(mappedBy = "student")
    List<StudentCourse> studentCourses;

    Boolean gender;

    @ManyToOne
    TrainingProgram trainingProgram;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_student";
            }
        };
        return Collections.singleton(authority);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
