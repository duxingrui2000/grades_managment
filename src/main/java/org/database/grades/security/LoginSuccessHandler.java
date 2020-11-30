package org.database.grades.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String role = null;
        //项目根地址
        String path = request.getContextPath();
        //主机+端口号+项目根路径
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        role = authorities.iterator().next().getAuthority();

        switch (role) {
            case "ROLE_student":
                response.sendRedirect(basePath + "student/main");
                break;
            case "ROLE_teacher":
                response.sendRedirect(basePath + "teacher/main");
                break;
            case "ROLE_admin":
                response.sendRedirect(basePath + "admin/main");
                break;
            default:
                response.sendRedirect(basePath + "/error");
        }
    }

}
