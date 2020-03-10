package com.cumt.onlineexam.dao;

import com.cumt.onlineexam.po.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StudentPaperQuestionRepository extends JpaRepository<Student_Paper_Question,Long >,
        JpaSpecificationExecutor<Student_Paper_Question> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true ,value ="insert into t_student_paper_question (student_id,paper_id,question_id,answer,correct) values (:#{#s_id},:#{#p_id},:#{#q_id},:#{#ans},:#{#correct}) ")
    void saveanswer(@Param("s_id") Long s_id, @Param("p_id") Long p_id,
                        @Param("q_id") Long q_id, @Param("ans") String ans,
                        @Param("correct") int correct);

    Student_Paper_Question findByStudentAndPaperAndQuestion(Student student, Paper paper, Question question);



}
