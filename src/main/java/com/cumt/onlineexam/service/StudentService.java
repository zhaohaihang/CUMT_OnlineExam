package com.cumt.onlineexam.service;

import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.vo.StudentQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    Student checkStudent(String username,String password);
    List<String> listGrade();
    Student getById(Long id);
    Page<Student> getAllStudent(Pageable pageable);
    Page<Student> listStudent(Pageable pageable, StudentQuery studentQuery);
    Student getOneById(Long id);
    Student saveStudent(Student student);
    Student updateStudent(Long id ,Student student);
}
