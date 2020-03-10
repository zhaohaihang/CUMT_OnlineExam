package com.cumt.onlineexam.dao;

import com.cumt.onlineexam.po.Paper;
import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.po.Student_Paper;
import com.cumt.onlineexam.vo.GradeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.data.domain.Pageable;

public interface StudentPaperRepository extends JpaRepository<Student_Paper,Long >, JpaSpecificationExecutor<Student_Paper> {

    Student_Paper findByStudentAndPaper(Student student, Paper paper);

    @Transactional
    @Modifying
    @Query(nativeQuery = true ,value ="insert into t_student_paper (student_id,paper_id,score,submit_time) values (:#{#s_id},:#{#p_id},:#{#sumscore},:#{#submittime}) ")
    void savesumscore(@Param("s_id") Long s_id, @Param("p_id") Long p_id, @Param("sumscore") int sumscore,@Param("submittime") String submittime);

    @Query(nativeQuery = true ,value ="select t_student_paper.student_id , t_student_paper.paper_id ,t_student_paper.submit_time,t_student_paper.score from t_student_paper,t_student,t_paper where" +
            " t_student_paper.student_id=t_student.id and t_student_paper.paper_id=t_paper.id and " +
        "t_student.name like :#{#gradeQuery.stuname} and t_student.grade like :#{#gradeQuery.stugrade} " +
        "and t_student.student_num like :#{#gradeQuery.stunum} and t_paper.name like :#{#gradeQuery.papername}")
    Page<Student_Paper> findByGradeQuety(Pageable pageable,  @Param("gradeQuery") GradeQuery gradeQuery);

    @Query(nativeQuery = true ,value ="select t_student_paper.student_id , t_student_paper.paper_id ,t_student_paper.submit_time,t_student_paper.score from t_student_paper,t_student,t_paper where" +
            " t_student_paper.student_id=t_student.id and t_student_paper.paper_id=t_paper.id and t_student.id=:#{#student.id}")
    Page<Student_Paper> findByStudent(Pageable pageable,  @Param("student") Student student);
}
