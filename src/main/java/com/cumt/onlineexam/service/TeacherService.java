package com.cumt.onlineexam.service;

import com.cumt.onlineexam.po.Teacher;
import com.cumt.onlineexam.vo.TeacherQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {
    Teacher checkTeacher(String username,String password);
    List<Teacher> listTeacher();
    Page<Teacher> getAllTeacher(Pageable pageable);
    Page<Teacher> listTeacher(Pageable pageable, TeacherQuery teacherQuery);
    Teacher saveTeacher(Teacher teacher);
    Teacher updateTeacher(Long id ,Teacher teacher);
    Teacher getOneById(Long id);
    Teacher getOneByName(String name);
 }
