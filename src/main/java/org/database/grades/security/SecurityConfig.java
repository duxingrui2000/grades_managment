package org.database.grades.security;

import org.database.grades.entity.Admin;
import org.database.grades.repository.AdminRepository;
import org.database.grades.service.impl.DetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AdminRepository adminRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService myDetailsService() {
        return new DetailsService();
    }

    /**
     * @description 为自定义的Filter创建Java Bean
     */
    @Bean
    SimpleAuthenticationFilter simpleAuthenticationFilter() throws Exception {
        SimpleAuthenticationFilter filter = new SimpleAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler());
        return filter;
    }


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myDetailsService());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(simpleAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.formLogin()
                .loginPage("/index")
                .loginProcessingUrl("/login")
                .successForwardUrl("/admin/hello")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/assert/**", "/index", "/login").permitAll()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/student/**").hasRole("student")
                .antMatchers("/teacher/**").hasRole("teacher");

        http.csrf().disable();


    }
}
