package com.example.springsecurity.teacher;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
public class TeacherManger implements AuthenticationProvider, InitializingBean {

    private HashMap<String, Teacher> teacherDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TeacherAuthenticationToken token=(TeacherAuthenticationToken) authentication;
        if(teacherDB.containsKey(token.getCredentials())){
            Teacher teacher = teacherDB.get(token.getCredentials());
            return TeacherAuthenticationToken.builder()
                    .principal(teacher)
                    .details(teacher.getUsername())
                    .authenticated(true) //false로 입력하면 핸들링했다는것이 되기때문에 문제발생
                    .build();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == TeacherAuthenticationToken.class;
//     UsernamePasswordAuthenticationToken class 형태의 토큰을 받으면 검증해주는 provider로 행동
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    Set.of(
           new Teacher("choi","최선생",Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")) , null)
//           ,new Teacher("kim","김선생",Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")), null),
//           new Teacher("lee","이선생",Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")), null)
    ).forEach(s ->
            teacherDB.put(s.getId(),s)
        );
    }
//    bean이 초기화 됬을때 student DB에다가
}
