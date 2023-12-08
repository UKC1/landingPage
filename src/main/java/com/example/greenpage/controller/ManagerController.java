package com.example.greenpage.controller;

import com.example.greenpage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public String listAllStudents() {
        System.out.println("관리자 모드 들어왔어 페이지 넘겨줘");
        return "/manager/list.html";
    }

    @GetMapping("/detail/{id}")
    public String detailStudents(@PathVariable("id") Integer id) {
        System.out.println("상세페이지 수정가자");
        studentService.getStudentId(id);
//        System.out.println(studentService.read(id));
        return "/manager/detail.html";
    }

//    @GetMapping("/detail/{id}")
//    public String detailStudents(@PathVariable("id") Integer id) {
//        System.out.println("상세페이지 수정가자");
//        studentService.getStudentId(id);
////        System.out.println(studentService.read(id));
//        return "index";
//    }


}