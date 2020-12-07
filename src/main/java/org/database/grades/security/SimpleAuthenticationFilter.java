package org.database.grades.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 继承了了默认的 UsernamePasswordAuthenticationFilter 可以根据用户类型进行验证
 */
public class SimpleAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //简单处理用户名和密码
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthRequest(request);
        setDetails(request, authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }


    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String role = request.getParameter("roleSelected");
//      将角色和用户名拼接起来传给DetailsService
        String roleUsername = String.format("%s%s%s", role, String.valueOf(Character.LINE_SEPARATOR), username.trim());
        return new UsernamePasswordAuthenticationToken(
                roleUsername, password);

    }
}