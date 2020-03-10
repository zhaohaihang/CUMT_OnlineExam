package com.cumt.onlineexam.service;

import com.cumt.onlineexam.dao.PaperRepository;
import com.cumt.onlineexam.dao.QuestionRepository;
import com.cumt.onlineexam.dao.StudentPaperQuestionRepository;
import com.cumt.onlineexam.dao.StudentRepository;
import com.cumt.onlineexam.po.Paper;
import com.cumt.onlineexam.po.Question;
import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.po.Student_Paper_Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentPaperQuestionServiceImpl implements StudentPaperQuestionService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Student_Paper_Question getSPByStudentAndPaperAndQuestion(Student student, Paper paper, Question question) {
        return studentPaperQuestionRepository.findByStudentAndPaperAndQuestion(student,paper,question);
    }

    @Autowired
    private StudentPaperQuestionRepository studentPaperQuestionRepository;
    @Override
    public void saveanswer(Long s_id, Long p_id, Long q_id, String ans, int correct) {
        studentPaperQuestionRepository.saveanswer(s_id,p_id,q_id,ans,correct);
    }


}
//
//        Student student = studentRepository.getOne(s_id);
//        Paper paper =paperRepository.getOne(p_id);
//        Question question=questionRepository.getOne(q_id);
//        Student_Paper_Question student_paper_question=new Student_Paper_Question();
//
//        student_paper_question.setStudent(student);
//        student_paper_question.setPaper(paper);
//        student_paper_question.setQuestion(question);
//        student_paper_question.setAnswer(ans);
//        student_paper_question.setCorrect(correct);
//
//        student.getStudent_paper_questions().add(student_paper_question);
//       // paper.getStudent_paper_questions().add(student_paper_question);
//      //  question.getStudent_paper_questions().add(student_paper_question);
//
//        //studentPaperQuestionRepository.save(student_paper_question);
//        studentRepository.save(student);
//        //paperRepository.save(paper);
//        //questionRepository.save(question);
//    }
//}
