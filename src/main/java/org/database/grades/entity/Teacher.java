package org.database.grades.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@Entity
public class Teacher implements UserDetails {
    @Id
    @GeneratedValue
    Long teacherId;

    @Column(unique = true, nullable = false)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String teacherName;

    @OneToMany(mappedBy = "teacher")
    Set<Course> courses;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_teacher";
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
