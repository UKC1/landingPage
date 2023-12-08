package com.example.greenpage.service;

import com.example.greenpage.model.Header;
import com.example.greenpage.model.entity.Student;
import com.example.greenpage.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    private Integer id;

    public ResponseEntity<Header<Student>> create(Header<Student> request) {
        for (Student s : studentRepository.findAll()) {
            if (s.getPhoneNumber().equals(request.getData().getPhoneNumber())) {
                System.out.println("이미 있는 번호입니다");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Header.ERROR("이미 참여 완료하셨습니다."));
            }
        }
        Student savedStudent = studentRepository.save(request.getData());
        return ResponseEntity.ok(Header.ACK(savedStudent));
    }


    public Header<Student> read(Integer id) {
        Header<Student> ack = Header.ACK(studentRepository.findById(id).orElse(null));
        return ack;
    }

    public Header<Student> update(Header<Student> request) {
        Header<Student> ack = Header.ACK(studentRepository.save(request.getData()));
        return ack;
    }

    public Header delete(Integer id) {
        studentRepository.deleteById(id);
        return Header.ACK();
    }


    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        System.out.println("리스트 토스");
        return students;
//        return students.stream().map(this::toStudentResponse).collect(Collectors.toList());
    }

    private Header<Student> response(Student student){

        Student studentResponse = Student.builder()
                .studentId(student.getStudentId())
                .name(student.getName())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .status(student.getStatus())
                .memo(student.getMemo())
                .build();

        return Header.ACK(studentResponse);
    }

    public void getStudentId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
}
