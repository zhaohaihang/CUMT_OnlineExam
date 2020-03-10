package com.cumt.onlineexam.service;

import com.cumt.onlineexam.NotFoundException;
import com.cumt.onlineexam.dao.PaperRepository;
import com.cumt.onlineexam.dao.QuestionRepository;
import com.cumt.onlineexam.dao.StudentPaperRepository;
import com.cumt.onlineexam.po.*;
import com.cumt.onlineexam.utils.MyBeanUtils;
import com.cumt.onlineexam.vo.PaperQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentPaperRepository studentPaperRepository;


    @Override
    public Page<Paper> listPaper(Pageable pageable) {
        return paperRepository.findAll(pageable);
    }

    @Override
    public void deletePaper(Long id) {
        paperRepository.deleteById(id);
    }

    @Override
    public Paper getPaperById(Long id) {
        return paperRepository.getOne(id);
    }

    @Transactional
    @Override
    public Paper savePaper(Paper paper) {
        if (paper.getId() == null) {
            paper.setCreateTime(new Date());
            paper.setUpdateTime(new Date());
        } else {
            paper.setUpdateTime(new Date());
        }
        return paperRepository.save(paper);
    }

    @Override
    public Paper updatePaper(Long id, Paper paper) {
        Paper p = paperRepository.getOne(id);
        if (p == null) {
            throw new NotFoundException("该试卷不存在");
        }
        BeanUtils.copyProperties(paper, p, MyBeanUtils.getNullPropertyNames(paper));
        p.setUpdateTime(new Date());
        return paperRepository.save(p);
    }

    @Transactional
    @Override
    public Page<Paper> listPaper(Pageable pageable, PaperQuery paper) {
        return paperRepository.findAll(new Specification<Paper>() {
            @Override
            public Predicate toPredicate(Root<Paper> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                List<Predicate> predicateList = new ArrayList<>();

                if (!"".equals(paper.getPaperName()) && paper.getPaperName() != null) {
                    predicateList.add(cb.like(root.<String>get("name"), "%" + paper.getPaperName() + "%"));
                }
                if (paper.getTeacherId() != null) {
                    predicateList.add(cb.equal(root.<Teacher>get("teacher").get("id"), paper.getTeacherId()));
                }

                cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public void addquestion(Long paperid, Long questionid) {
        Paper paper = paperRepository.getOne(paperid);
        Question question = questionRepository.getOne(questionid);
        paper.getQuestions().add(question);
        question.getPapers().add(paper);
        paperRepository.save(paper);
        questionRepository.save(question);
    }

    @Override
    public void deletequestion(Long paperid, Long questionid) {
        Paper paper = paperRepository.getOne(paperid);
        Question question = questionRepository.getOne(questionid);
        paper.getQuestions().remove(question);
        question.getPapers().remove(paper);
        paperRepository.save(paper);
        questionRepository.save(question);
    }

    @Override
    public Page<Paper> listImcompPaper(Student student,Pageable pageable) {
        return paperRepository.getImcompPaper(student,pageable);
    }

}

