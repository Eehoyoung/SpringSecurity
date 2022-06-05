package com.example.springsecurity.config;

import com.example.springsecurity.student.StudentManger;
import com.example.springsecurity.teacher.TeacherManger;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Order(2)
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final StudentManger studentManger;
    private final TeacherManger teacherManger;

    public SecurityConfig(StudentManger studentManger, TeacherManger teacherManger) {
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
        CustomLoginFilter filter = new CustomLoginFilter(authenticationManager());
        http
                .authorizeRequests(request->
                        request.antMatchers("/","/login").permitAll()
                                .anyRequest().authenticated()

                )
                .formLogin(
                login->login.loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/",false)
                        .failureUrl("/login-error")
               )
              .addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .exceptionHandling(
                        e->e.accessDeniedPage("/access-denied"))
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        ;
    }
}