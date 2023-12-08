package com.example.greenpage.controller;
import com.example.greenpage.model.Header;
import com.example.greenpage.model.entity.Student;
import com.example.greenpage.myapp.CrudInterface;
import com.example.greenpage.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentApiController implements CrudInterface<Student> {

    private final StudentService studentService;


    @Override
    @PostMapping("")
    public ResponseEntity<Header<Student>> create(@RequestBody Header<Student> request) {
        return studentService.create(request);
    }
//    @Override
//    @PostMapping("")
//    public Header<Student> create(@RequestBody Header<Student> request) {
//        return studentService.create(request);
//    }


//    @Override
//    @GetMapping("{id}")
//    public Header<Student> read(@PathVariable(name = "id") Integer id) {
//        return studentService.read(id);
//    }
    @Override
    @GetMapping("")
    public Header<Student> read(Integer id) {
        return studentService.read(studentService.getId());
    }

    @Override
    @PutMapping("")
    public Header<Student> update(@RequestBody  Header<Student>  request) {
        System.out.println("업데이트 하자");
        System.out.println(studentService.getId());
        request.getData().setStudentId(studentService.getId());
        return studentService.update(request);
    }

//    @Override
//    @DeleteMapping("{id}")
//    public Header delete(@PathVariable Integer id) {
//        return studentService.delete(id);
//    }
    @Override
    @DeleteMapping("")
    public Header delete(Integer id) {
        return studentService.delete(studentService.getId());
    }


    @GetMapping("/manager")
    public List<Student> getListAllStudents() {
        System.out.println("리스트 다 보여줘");
//        System.out.println(studentService.getAllStudents());
        return studentService.getAllStudents();
    }

}

