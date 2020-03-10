package com.cumt.onlineexam.dao;

import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.po.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeacherRepository extends JpaRepository<Teacher,Long  >, JpaSpecificationExecutor<Teacher> {

    Teacher findByTeacherNumAndPassword(String teacher_num,String password);
    Teacher findByName(String name);
}
