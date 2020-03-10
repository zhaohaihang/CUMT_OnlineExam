package com.cumt.onlineexam.dao;

import com.cumt.onlineexam.po.Paper;
import com.cumt.onlineexam.po.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaperRepository extends JpaRepository<Paper,Long> , JpaSpecificationExecutor<Paper>{

    @Query(nativeQuery = true ,value = "" +
            "select * from t_paper p \n" +
            "where p.teacher_id= :#{#student.teacher.id}  \n" +
            "and p.grade=:#{#student.grade} \n" +
            "and  p.id not in(select distinct paper_id from t_student_paper " +
            "where student_id=:#{#student.id})")
    Page<Paper> getImcompPaper(@Param("student") Student student, Pageable pageable);
}
