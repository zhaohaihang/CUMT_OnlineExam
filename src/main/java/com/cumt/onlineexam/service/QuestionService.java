package com.cumt.onlineexam.service;

import com.cumt.onlineexam.po.Question;
import com.cumt.onlineexam.vo.QuestionQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    Page <Question> listQuestion(Pageable pageable);
    Page <Question> listQuestion(Pageable pageable , QuestionQuery questionQuery);
    Question getQuestionById(Long id);
    void deleteQuestion(Long id);

    Question saveQuestion(Question question);
    Question updateQuestion(Long id,Question question);
}
