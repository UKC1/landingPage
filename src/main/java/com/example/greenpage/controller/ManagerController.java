package com.example.greenpage.controller;

import com.example.greenpage.service.StudentService;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    private HttpSession session;

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("")
    public String listAllStudents() {

        // 관리자가 아니면 로그인 페이지로 리디렉션
        if (isAdmin() == false) {
            return "redirect:/";
        }
        return "/manager/list.html";
    }


    @GetMapping("/detail/{id}")
    public String detailStudents() {
        // 관리자가 아니면 로그인 페이지로 리디렉션
        if (!isAdmin()) {
            return "redirect:/";
        }
        System.out.println("detail 페이지 반환");
        return "/manager/detail.html";
    }

    private boolean isAdmin() {
        String userName = (String) session.getAttribute("userName");
        return "admin".equals(userName);
    }
}