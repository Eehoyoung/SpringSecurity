package com.example.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.example.springsecurity.user",
        "com.example.springsecurity"
})

@EntityScan(basePackages = {
        "com.example.springsecurity.user.domain"
})

@EnableJpaRepositories(basePackages = {
        "com.example.springsecurity.user.repository"
})

public class UserDetailsTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserDetailsTestApplication.class, args);
    }
}
