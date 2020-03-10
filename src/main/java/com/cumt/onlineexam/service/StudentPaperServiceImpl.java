package com.cumt.onlineexam.service;

import com.cumt.onlineexam.dao.PaperRepository;
import com.cumt.onlineexam.dao.StudentPaperRepository;
import com.cumt.onlineexam.dao.StudentRepository;
import com.cumt.onlineexam.po.Paper;
import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.po.Student_Paper;
import com.cumt.onlineexam.vo.GradeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import org.springframework.data.domain.Pageable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StudentPaperServiceImpl implements StudentPaperService {

    @Autowired
    private StudentPaperRepository studentPaperRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Override
    public Student_Paper getSPByStudentAndPaper(Student student, Paper paper) {
        return studentPaperRepository.findByStudentAndPaper(student, paper);
    }

    @Override
    public void savesumscore(Long s_id, Long p_id, int sumscore) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println();
        String submitdate = dateFormat.format(date).toString();
        studentPaperRepository.savesumscore(s_id, p_id, sumscore, submitdate);
    }

    @Override
    public Page<Student_Paper> getStudentPaperScore(Pageable pageable, GradeQuery gradeQuery) {
        GradeQuery gt = new GradeQuery();
        gt.setStunum('%'+gradeQuery.getStunum()+'%');
        gt.setPapername('%'+gradeQuery.getPapername()+'%');
        gt.setStugrade('%'+gradeQuery.getStugrade()+'%');
        gt.setStuname('%'+gradeQuery.getStuname()+'%');
        Page<Student_Paper> sps  =studentPaperRepository.findByGradeQuety(pageable,gt);
        return sps;
    }

    @Override
    public Page<Student_Paper> getStudentPaperScore(Pageable pageable,Student student ) {
        Page<Student_Paper> sps  =studentPaperRepository.findByStudent(pageable,student);
        return sps;
    }
}


