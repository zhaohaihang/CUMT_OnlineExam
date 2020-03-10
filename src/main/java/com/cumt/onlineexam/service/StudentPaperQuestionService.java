package com.cumt.onlineexam.service;

import com.cumt.onlineexam.po.Paper;
import com.cumt.onlineexam.po.Question;
import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.po.Student_Paper_Question;
import com.cumt.onlineexam.vo.GradeQuery;

public interface StudentPaperQuestionService {
    void saveanswer (Long s_id,Long p_id,Long q_id,String ans,int correct);
    Student_Paper_Question getSPByStudentAndPaperAndQuestion(Student student , Paper paper, Question question);
}
