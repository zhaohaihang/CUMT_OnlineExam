package com.cumt.onlineexam.service;

import com.cumt.onlineexam.NotFoundException;
import com.cumt.onlineexam.dao.QuestionRepository;
import com.cumt.onlineexam.po.Chapter;
import com.cumt.onlineexam.po.Point;
import com.cumt.onlineexam.po.Question;
import com.cumt.onlineexam.po.Teacher;
import com.cumt.onlineexam.utils.MyBeanUtils;
import com.cumt.onlineexam.vo.QuestionQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Page<Question> listQuestion(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<Question> listQuestion(Pageable pageable, QuestionQuery question) {
        return questionRepository.findAll(new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicateList =new ArrayList<>();
                if (!"".equals(question.getType()) && question.getType()!=null ){
                    predicateList.add(cb.like(root.<String>get("type"),"%"+ question.getType()+ "%"));
                }
                if (!"".equals(question.getDiffcult()) && question.getDiffcult()!=null ){
                    predicateList.add(cb.equal(root.<Long>get("diffcult"), question.getDiffcult()));
                }
                if (question.getChapterId()!=null){
                    predicateList.add(cb.equal(root.<Chapter>get("chapter").get("id"),question.getChapterId()));
                }
                if (question.getTeacherId()!=null){
                    predicateList.add(cb.equal(root.<Teacher>get("teacher").get("id"),question.getTeacherId()));
                }
                if (question.getPointId()!=null){
                    Join<Question, Point> joins = root.join("points",JoinType.INNER);
                    predicateList.add(cb.equal(joins.<Long>get("id"),question.getPointId()));
                }
                cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.getOne(id);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Question saveQuestion(Question question) {
        if (question.getId()==null){
            question.setCreateTime(new Date());
            question.setUpdateTime(new Date());
        }else{
            question.setUpdateTime(new Date());
        }
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Long id, Question question) {
        Question q =questionRepository.getOne(id);
        if (q==null){
            throw new NotFoundException("该题目不存在");
        }
        BeanUtils.copyProperties(question,q, MyBeanUtils.getNullPropertyNames(question));
        q.setUpdateTime(new Date());
        return questionRepository.save(q);
    }
}
