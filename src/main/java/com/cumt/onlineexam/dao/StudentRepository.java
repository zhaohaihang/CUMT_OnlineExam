package com.cumt.onlineexam.dao;

import com.cumt.onlineexam.po.Question;
import com.cumt.onlineexam.po.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> , JpaSpecificationExecutor<Student> {
    Student findByStudentNumAndPassword(String student_num,String password);

    @Query("select distinct grade from Student s")
    List<String> getAllGrade();
}
