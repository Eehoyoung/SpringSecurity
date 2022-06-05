package com.example.springsecurity.config;

import com.example.springsecurity.student.StudentManger;
import com.example.springsecurity.teacher.TeacherManger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(1)
@Configuration
public class MobSecurityConfig extends WebSecurityConfigurerAdapter {


    private final StudentManger studentManger;
    private final TeacherManger teacherManger;

    public MobSecurityConfig(StudentManger studentManger, TeacherManger teacherManger) {
        this.studentManger = studentManger;
        this.teacherManger = teacherManger;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(studentManger);
        auth.authenticationProvider(teacherManger);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")
                .csrf().disable()
                .authorizeRequests(request-> request.anyRequest().authenticated())
                .httpBasic()
        ;
    }
}