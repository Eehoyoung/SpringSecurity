package com.example.springsecurity.controller;

import com.example.springsecurity.student.Student;
import com.example.springsecurity.student.StudentManger;
import com.example.springsecurity.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

///api/teacher/students
@RestController
@RequestMapping("/api/teacher")
public class ApiTeacherController {

    @Autowired
    StudentManger studentManger;

    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping("/students")
    public List<Student> studentList(@AuthenticationPrincipal Teacher teacher) {
        return studentManger.myStudentList(teacher.getId());
    }
}