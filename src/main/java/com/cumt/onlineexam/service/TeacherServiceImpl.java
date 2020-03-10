package com.cumt.onlineexam.service;

import com.cumt.onlineexam.NotFoundException;
import com.cumt.onlineexam.dao.TeacherRepository;
import com.cumt.onlineexam.po.Question;
import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.po.Teacher;
import com.cumt.onlineexam.utils.MyBeanUtils;
import com.cumt.onlineexam.vo.StudentQuery;
import com.cumt.onlineexam.vo.TeacherQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeacherServiceImpl  implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public Teacher checkTeacher(String username, String password) {
        Teacher teacher = teacherRepository.findByTeacherNumAndPassword(username,password);
        return teacher;
    }

    @Override
    public List<Teacher> listTeacher() {
        return teacherRepository.findAll();
    }

    @Override
    public Page<Teacher> getAllTeacher(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<Teacher> listTeacher(Pageable pageable, TeacherQuery teacher) {
        return teacherRepository.findAll(new Specification<Teacher>() {
            @Override
            public Predicate toPredicate(Root<Teacher> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                List<Predicate> predicateList =new ArrayList<>();

                if (!"".equals(teacher.getTeaname()) && teacher.getTeaname()!=null ){
                    predicateList.add(cb.like(root.<String>get("name"),"%"+ teacher.getTeaname()+ "%"));
                }
                if (!"".equals(teacher.getTeanum()) && teacher.getTeanum()!=null ){
                    predicateList.add(cb.like(root.<String>get("teacherNum"),"%"+ teacher.getTeanum()+ "%"));
                }

                cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        },pageable);
    }


    @Transactional
    @Override
    public Teacher saveTeacher(Teacher teacher) {
        if (teacher.getId()==null){
            teacher.setCreateTime(new Date());
            teacher.setUpdateTime(new Date());
        }else{
            teacher.setUpdateTime(new Date());
        }
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher teacher) {
        Teacher t =teacherRepository.getOne(id);
        if (t==null){
            throw new NotFoundException("该老师不存在");
        }
        BeanUtils.copyProperties(teacher,t, MyBeanUtils.getNullPropertyNames(teacher));
        t.setUpdateTime(new Date());
        return teacherRepository.save(t);
    }

    @Override
    public Teacher getOneById(Long id) {
        return teacherRepository.getOne(id);
    }

    @Override
    public Teacher getOneByName(String name) {
        return teacherRepository.findByName(name);
    }
}
