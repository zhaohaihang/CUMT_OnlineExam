package com.cumt.onlineexam.service;

import com.cumt.onlineexam.po.Paper;
import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.po.Student_Paper;
import com.cumt.onlineexam.vo.GradeQuery;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface StudentPaperService  {
    Student_Paper getSPByStudentAndPaper(Student student , Paper paper);
    void savesumscore(Long s_id,Long p_id,int sumscore);
    Page<Student_Paper> getStudentPaperScore(Pageable pageable, GradeQuery gradeQuery);
    Page<Student_Paper> getStudentPaperScore(Pageable pageable,Student student);
}
