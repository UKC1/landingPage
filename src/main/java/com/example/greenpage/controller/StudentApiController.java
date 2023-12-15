package com.example.greenpage.controller;
import com.example.greenpage.model.Header;
import com.example.greenpage.model.entity.Student;
import com.example.greenpage.myapp.CrudInterface;
import com.example.greenpage.service.StudentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentApiController implements CrudInterface<Student> {

    @Autowired
    private StudentService studentService;
    @Autowired
    private HttpSession session;


    @Override
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Header<Student> request) {
        if (request.getData().getName().equals("admin") && request.getData().getAge().equals("00")) {
            session.setAttribute("userName", "admin");
            return ResponseEntity.ok(Map.of("redirect", "/manager"));
        } else {
            session.removeAttribute("userName");
            return ResponseEntity.ok(studentService.create(request));
        }
    }

    @Override
    @GetMapping("{id}")
    public Header<Student> read(@PathVariable("id") Integer id) {
        return studentService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Student> update(@RequestBody  Header<Student> request) {
        return studentService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header<Student> delete(@PathVariable("id") Integer id) {
        return studentService.delete(id);
    }


    @GetMapping("/manager")
    public List<Student> getListAllStudents() {
        return studentService.getAllStudents();
    }
}

