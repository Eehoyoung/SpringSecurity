package com.example.springsecurity.student;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentManger implements AuthenticationProvider, InitializingBean {

    private HashMap<String, Student> studentDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        StudentAuthenticationToken token=(StudentAuthenticationToken) authentication;
        if(studentDB.containsKey(token.getCredentials())){
            Student student = studentDB.get(token.getCredentials());
            return StudentAuthenticationToken.builder()
                    .principal(student)
                    .details(student.getUsername())
                    .authenticated(true) //false로 입력하면 핸들링했다는것이 되기때문에 문제발생
                    .build();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == StudentAuthenticationToken.class;
//     UsernamePasswordAuthenticationToken class 형태의 토큰을 받으면 검증해주는 provider로 행동
    }

    public List<Student> myStudentList(String teacherId){
        return studentDB.values().stream().filter(s -> s.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    Set.of(
           new Student("hong","홍길동",Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "choi"),
           new Student("kang","강감찬",Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")),"choi"),
           new Student("lee","이순신",Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")),"choi")
    ).forEach(s ->
      studentDB.put(s.getId(),s)
        );
    }
//    bean이 초기화 됬을때 student DB에다가
}
