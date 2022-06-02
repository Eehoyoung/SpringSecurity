package com.example.springsecurity.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig<rolehi> extends WebSecurityConfigurerAdapter {


    private final CustomAuthDetails customAuthDetails;

    public SecurityConfig(CustomAuthDetails customAuthDetails) {
        this.customAuthDetails = customAuthDetails;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(
                        User.withDefaultPasswordEncoder()
                                .username("user1")
                                .password("1111")
                                .roles("USER")
        ).withUser(
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("2222")
                        .roles("ADMIN")
                );
    }

//    admin이 user페이지에 접근 가능하게 설정
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(request-> request
                        .antMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(
                        login->login.loginPage("/login")
                                .permitAll() //무한루프 방지
                                .defaultSuccessUrl("/",false)
//                                  로그인후 갈때가 없으면 루트페이지로 이동
                                .failureUrl("/login-error")
                                .authenticationDetailsSource(customAuthDetails)
                )
                .logout(logout->logout.logoutSuccessUrl("/login"))
                .exceptionHandling(
                        exception->exception.accessDeniedPage("/access-denied"))
        ;
    }
//웹 리소스에 대해서는 필터가 적용되지 않도록 ignor시켜줘야 한다.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                )
            ;
    }
}