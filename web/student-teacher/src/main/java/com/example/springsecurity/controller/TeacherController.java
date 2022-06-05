package com.example.springsecurity.controller;

import com.example.springsecurity.student.StudentManger;
import com.example.springsecurity.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private StudentManger studentManger;

    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping("/main")
    public String main(@AuthenticationPrincipal Teacher teacher , Model model) {
        model.addAttribute("studentList", studentManger.myStudent(teacher.getId()));
        return "TeacherMain";
    }
}