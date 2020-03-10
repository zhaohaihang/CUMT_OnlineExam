package com.cumt.onlineexam.service;

import com.cumt.onlineexam.po.Paper;
import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.vo.PaperQuery;
import com.cumt.onlineexam.vo.PaperQuestions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaperService {
    Page<Paper> listPaper(Pageable pageable);

    Paper getPaperById(Long id);
    void deletePaper(Long id);
    Paper savePaper(Paper paper);
    Paper updatePaper(Long id,Paper paper);
    Page <Paper> listPaper(Pageable pageable , PaperQuery paperQuery);
    void addquestion(Long paperid,Long questionid);
    void deletequestion(Long paperid,Long questionid);
    Page<Paper> listImcompPaper(Student student,Pageable pageable);
}
