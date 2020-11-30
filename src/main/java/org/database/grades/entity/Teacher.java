package org.database.grades.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.net.URLStreamHandlerFactory;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
public class Teacher implements UserDetails {
    @Id
    @GeneratedValue
    Long teacherId;

    @Column(unique = true)
    String username;

    String password;

    @Column(nullable = false)
    String teacherName;

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
