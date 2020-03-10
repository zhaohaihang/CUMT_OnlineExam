package com.cumt.onlineexam.service;

import com.cumt.onlineexam.NotFoundException;
import com.cumt.onlineexam.dao.StudentRepository;
import com.cumt.onlineexam.po.Student;
import com.cumt.onlineexam.po.Teacher;
import com.cumt.onlineexam.utils.MyBeanUtils;
import com.cumt.onlineexam.vo.StudentQuery;
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
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student checkStudent(String username, String password) {
        Student student=studentRepository.findByStudentNumAndPassword(username,password);
        return student;
    }

    @Override
    public List<String> listGrade() {
        return studentRepository.getAllGrade();
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.getOne(id);
    }

    @Override
    public Page<Student> getAllStudent(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<Student> listStudent(Pageable pageable, StudentQuery student) {
        return studentRepository.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                List<Predicate> predicateList =new ArrayList<>();

                if (!"".equals(student.getStuname()) && student.getStuname()!=null ){
                    predicateList.add(cb.like(root.<String>get("name"),"%"+ student.getStuname()+ "%"));
                }
                if (!"".equals(student.getStunum()) && student.getStunum()!=null ){
                    predicateList.add(cb.like(root.<String>get("studentNum"),"%"+ student.getStunum()+ "%"));
                }
                if (!"".equals(student.getStugrade()) && student.getStugrade()!=null ){
                    predicateList.add(cb.like(root.<String>get("grade"),"%"+ student.getStugrade()+ "%"));
                }
                if (student.getTeacherid()!=null){
                    predicateList.add(cb.equal(root.<Student>get("teacher").get("id"),student.getTeacherid()));
                }
                cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        },pageable);
    }


    @Override
    public Student getOneById(Long id) {
        return studentRepository.getOne(id);
    }

    @Transactional
    @Override
    public Student saveStudent(Student student) {
        if (student.getId()==null){
            student.setCreateTime(new Date());
            student.setUpdateTime(new Date());
        }else{
            student.setUpdateTime(new Date());
        }
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public Student updateStudent(Long id, Student student) {
        Student s =studentRepository.getOne(id);
        if (s==null){
            throw new NotFoundException("该学生不存在");
        }
        BeanUtils.copyProperties(student,s, MyBeanUtils.getNullPropertyNames(student));
        s.setUpdateTime(new Date());
        return studentRepository.save(s);
    }
}
