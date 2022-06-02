package com.example.springsecurity.web.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SecurityMessage {
//    Authentication과 이 페이지가 어떤 페이지인지 말해주는 메세지
    private Authentication auth;
    private String message;
}
